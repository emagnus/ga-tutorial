package no.emagnus.driving;

import no.emagnus.dyn4jtest.RenderableBody;
import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.Individual;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CarFitnessEvaluator implements FitnessEvaluator<String> {

    private JFrame jFrame;
    private Canvas canvas;
    private static final double SCALE = 45.0;
    public static final double NANO_TO_BASE = 1.0e9;

    private World world = new World();

    private static final int KILL_SIM_THRESHOLD = 2;
    private static final double TIME_STEP = 1.0 / 30.0;
    private boolean visualize;

    public CarFitnessEvaluator(boolean visualize) {
        this.visualize = visualize;
        if (visualize) {
            initJFrame();
        }
    }

    @Override
    public void evaluateFitness(Collection<Individual> population) {
        initializeSimulation(43);

        Map<Individual, RenderableBody> cars = generateCars(population);

        // running simulation of cars driving
        runSimulation(cars.values());

        // TODO assign fitness to the individuals according to their performance
        assignFitnessToPopulation(population, cars);

        // cleaning up after running the simulation
        cleanUpSimulation();
    }

    private void assignFitnessToPopulation(Collection<Individual> population, Map<Individual, RenderableBody> cars) {
        for (Individual individual : population) {
            RenderableBody car = cars.get(individual);
            double fitness = car.getWorldCenter().x;
            individual.setFitness(fitness);
        }
    }


    private void initializeSimulation(int seed) {
        new TerrainGenerator(seed).generateTerrain(world);
    }

    private Map<Individual, RenderableBody> generateCars(Collection<Individual> population) {
        Map<Individual, RenderableBody> cars = new HashMap<>();
        for (Individual individual : population) {
            cars.put(individual, new CarGenerator().generateCar(world, new Vector2(-5,0)));
        }

        return cars;
    }

    private void runSimulation(Collection<RenderableBody> cars) {
        double maxDistance = 0;
        double timeWithoutProgress = 0;

        if (visualize) {
            long last = System.nanoTime();

            while (timeWithoutProgress < KILL_SIM_THRESHOLD) {
                long time = System.nanoTime();
                long diff = time - last;
                last = time;
                double elapsedTime = diff / NANO_TO_BASE;
                // update the world with the elapsed time
                this.world.update(elapsedTime);

                double maxDistanceThisStep = findMaxDistance(cars);
                if (maxDistanceThisStep > maxDistance) {
                    maxDistance = maxDistanceThisStep;
                    timeWithoutProgress = 0;
                } else {
                    timeWithoutProgress += elapsedTime;
                }

                updateCanvas();
            }
        } else {
            System.out.println("Evaluating fitness of " + cars.size() + " cars @ " + (1.0/TIME_STEP) + "Hz");
            double timeSimulated = 0;

            long start = System.currentTimeMillis();
            while (timeWithoutProgress < KILL_SIM_THRESHOLD) {
                world.update(TIME_STEP);
                timeSimulated += TIME_STEP;

                double maxDistanceThisStep = findMaxDistance(cars);
                if (maxDistanceThisStep > maxDistance) {
                    maxDistance = maxDistanceThisStep;
                    timeWithoutProgress = 0;
                } else {
                    timeWithoutProgress += TIME_STEP;
                }
            }

            long millisSpent = System.currentTimeMillis() - start;

            System.out.println(String.format("%.3f seconds simulated in %.1f seconds, max progress: %.3f", timeSimulated, millisSpent / 1000.0, maxDistance));
        }
    }

    private void cleanUpSimulation() {
        world.removeAllBodies();
    }

    private double findMaxDistance(Collection<RenderableBody> cars) {
        double maxDistance = -10;
        for (Body car : cars) {
            if (car.getWorldCenter().x > maxDistance) {
                maxDistance = car.getWorldCenter().x;
            }
        }

        return maxDistance;
    }

    private void initJFrame() {
        jFrame = new JFrame("Cars!");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add a window listener
        jFrame.addWindowListener(new WindowAdapter() {
            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            @Override
            public void windowClosing(WindowEvent e) {
                // before we stop the JVM stop the example
                super.windowClosing(e);
            }
        });

        // create the size of the window
        Dimension size = new Dimension(800, 600);

        // create a canvas to paint to
        canvas = new Canvas();
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);

        canvas.setIgnoreRepaint(true);

        // add the canvas to the JFrame
        jFrame.add(this.canvas);

        // make the JFrame not resizable
        // (this way I dont have to worry about resize events)
        jFrame.setResizable(false);

        // size everything
        jFrame.pack();

        jFrame.setVisible(true);
        // enable double buffering (the JFrame has to be
        // visible before this can be done)
        canvas.createBufferStrategy(2);
    }

    private void render(Graphics2D g) {
        // lets draw over everything with a white background
        g.setColor(Color.WHITE);
        g.fillRect(-400, -300, 800, 600);

        // lets move the view up some
        double leaderX = -100;
        double leaderY = -1;
        for (Body car : world.getBodies()) {
            if (car.isDynamic() && car.getWorldCenter().x > leaderX) {
                leaderX = car.getWorldCenter().x;
                leaderY = car.getWorldCenter().y;
            }
        }
        g.translate(-1 * leaderX * SCALE, -1 * leaderY * SCALE);

        // draw all the objects in the world
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            // get the object
            RenderableBody go = (RenderableBody) this.world.getBody(i);
            // draw the object
            go.render(g, 45.0);
        }
    }

    private void updateCanvas() {
        // get the graphics object to render to
        Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(400, -300);
        g.transform(yFlip);
        g.transform(move);

        // now (0, 0) is in the center of the screen with the positive x axis
        // pointing right and the positive y axis pointing up

        // render anything about the Example (will render the World objects)
        this.render(g);

        // dispose of the graphics object
        g.dispose();

        // blit/flip the buffer
        BufferStrategy strategy = this.canvas.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }
}
