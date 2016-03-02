package no.emagnus.tsp;

import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.Individual;
import no.emagnus.tsp.data.TspData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TspFitnessEvaluator implements FitnessEvaluator<String> {

    private JFrame jFrame;
    private Canvas canvas;

    private static final int CANVAS_SIZE = 800;

    private boolean visualize;

    public TspFitnessEvaluator(boolean visualize) {
        this.visualize = visualize;
        if (visualize) {
            initJFrame();
        }
    }

    @Override
    public void evaluateFitness(Collection<Individual<String>> population) {
        List<TspDataPoint> route = TspData.getSmallDataset();

        Map<Individual, List<TspDataPoint>> routes = new HashMap<>();

        assignFitnessToRoutes(population, routes);

        for (int i = 0; i < 10; i++) {
            Collections.shuffle(route);
            if (visualize) {
                updateCanvas(route);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void assignFitnessToRoutes(Collection<Individual<String>> population, Map<Individual, List<TspDataPoint>> routes) {
        for (Individual<String> individual : population) {
            List<TspDataPoint> route = routes.get(individual);
            double distance = calculateRouteDistance(route);
            individual.setFitness(-distance); // the algorithm is maximizing fitness
        }
    }

    private double calculateRouteDistance(List<TspDataPoint> route) {
        double distance = 0;
        double previousX = route.get(0).x;
        double previousY = route.get(0).y;
        for (int i = 1; i < route.size(); i++) {
            double x = route.get(i).x;
            double y = route.get(i).y;

            distance += Math.sqrt(Math.pow(Math.abs(x-previousX), 2) + Math.pow(Math.abs(y - previousY), 2));
        }
        return distance;
    }

    private void initJFrame() {
        jFrame = new JFrame("TSP!");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the size of the window
        Dimension size = new Dimension(CANVAS_SIZE, CANVAS_SIZE);

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

        canvas.createBufferStrategy(1);
    }

    private void render(Graphics2D g, List<Point2D> route) {
        // lets draw over everything with a white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        g.setColor(Color.BLACK);
        for (Point2D point : route) {
            //g.fillRect(CANVAS_SIZE - (int) point.getY() - 2, CANVAS_SIZE - (int) point.getX() - 2, 4, 4);
            g.fillRect((int) point.getX() - 2, (int) point.getY() - 2, 4, 4);
        }

        /*g.setColor(Color.BLUE);
        Point2D prevPoint = route.get(0);
        for (int i = 1; i < route.size(); i++) {
            Point2D currentPoint = route.get(i);
            g.drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), (int)currentPoint.getX(), (int)currentPoint.getY());
            prevPoint = currentPoint;
        }

        Point2D lastPoint = route.get(0);
        g.drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), (int)lastPoint.getX(), (int)lastPoint.getY());
        */
    }

    private void updateCanvas(List<TspDataPoint> route) {
        // get the graphics object to render to
        Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        //AffineTransform yFlip = AffineTransform.getScaleInstance(1, 1);
        //AffineTransform move = AffineTransform.getTranslateInstance(0, -CANVAS_SIZE);
        //g.transform(yFlip);
        //g.transform(move);

        // now (0, 0) is in the center of the screen with the positive x axis
        // pointing right and the positive y axis pointing up
        List<Point2D> normalizedRoute = normalizeToWindowSize(route);

        // render anything about the Example (will render the World objects)
        this.render(g, normalizedRoute);

        // dispose of the graphics object
        g.dispose();

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    private List<Point2D> normalizeToWindowSize(List<TspDataPoint> route) {
        double maxX = 0;
        double maxY = 0;
        for (TspDataPoint dataPoint : route) {
            if (dataPoint.x > maxX) {
                maxX = dataPoint.x;
            }
            if (dataPoint.y > maxY) {
                maxY = dataPoint.y;
            }
        }

        double max = Math.max(maxX, maxY) / CANVAS_SIZE;

        List<Point2D> normalized = new ArrayList<>();
        for (TspDataPoint dataPoint : route) {
            normalized.add(new Point2D.Double(dataPoint.x / max, dataPoint.y / max));
        }

        return normalized;
    }

    public static void main(String[] args) {
        new TspFitnessEvaluator(true).evaluateFitness(Collections.<Individual<String>>emptyList());
    }
}
