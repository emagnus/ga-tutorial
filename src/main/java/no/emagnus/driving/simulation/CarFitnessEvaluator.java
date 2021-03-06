package no.emagnus.driving.simulation;

import no.emagnus.rendering.RenderableBody;
import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.Individual;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CarFitnessEvaluator implements FitnessEvaluator {

    private boolean visualize;
    private static final double SCALE = 45.0;
    private static final double NANO_TO_BASE = 1.0e9;
    private JFrame jFrame;
    private Canvas canvas;

    private World world = new World();
    private static final int KILL_SIM_THRESHOLD = 5;
    private static final double TIME_STEP = 1.0 / 30.0;

    public CarFitnessEvaluator(boolean visualize) {
        this.visualize = visualize;
        if (visualize) {
            initJFrame();
        }
    }

    @Override
    public void evaluateFitness(Collection<Individual> population) {
        initializeSimulation(41);

        // generating cars from genotypes
        List<RenderableBody> cars = generateCars(population);

        // running simulation of cars driving
        runSimulation(cars);

        // assigning fitness to the individuals based on the cars' performance
        assignFitnessToPopulation(cars);

        // cleaning up after running the simulation
        cleanUpSimulation();
    }

    private void assignFitnessToPopulation(List<RenderableBody> cars) {
        for (RenderableBody car : cars) {
            // TODO Fix the fitness assignment
            car.getIndividual().setFitness(0);
        }
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

    private void initializeSimulation(int seed) {
        new TerrainGenerator(seed).generateTerrain(world);
    }

    private List<RenderableBody> generateCars(Collection<Individual> population) {
        List<RenderableBody> cars = new ArrayList<>();
        for (Individual individual : population) {
            cars.add(new CarFactory().generateCar(individual, world, new Vector2(-5, -1)));
        }

        return cars;
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

        canvas = new Canvas();
        Dimension size = new Dimension(800, 600);
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
        canvas.setIgnoreRepaint(true);

        jFrame.add(this.canvas);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);

        canvas.createBufferStrategy(2);
    }

    private void render(Graphics2D g) {
        double leaderX = -100;
        double leaderY = -1;
        for (Body car : world.getBodies()) {
            if (car.isDynamic() && car.getWorldCenter().x > leaderX) {
                leaderX = car.getWorldCenter().x;
                leaderY = car.getWorldCenter().y;
            }
        }
        g.translate(-1 * leaderX * SCALE, -1 * leaderY * SCALE);

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            RenderableBody go = (RenderableBody) this.world.getBody(i);
            go.render(g, 45.0);
        }
    }

    private void updateCanvas() {
        Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);

        double leaderX = -100;
        for (Body car : world.getBodies()) {
            if (car.isDynamic() && car.getWorldCenter().x > leaderX) {
                leaderX = car.getWorldCenter().x;
            }
        }

        g.setColor(Color.BLACK);
        g.drawString(String.format("Distance covered by leader: %.3f", leaderX), 20, 20);

        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(400, -300);
        g.transform(yFlip);
        g.transform(move);

        this.render(g);

        g.dispose();

        BufferStrategy strategy = this.canvas.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        Toolkit.getDefaultToolkit().sync();
    }
}
