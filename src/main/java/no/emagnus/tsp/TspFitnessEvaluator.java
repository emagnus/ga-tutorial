package no.emagnus.tsp;

import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.Individual;
import no.emagnus.tsp.data.TspData;
import no.emagnus.tsp.data.TspDataPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TspFitnessEvaluator implements FitnessEvaluator {

    private JFrame jFrame;
    private Canvas canvas;

    private static final int CANVAS_SIZE = 800;

    private boolean visualize;
    private List<TspDataPoint> cities;

    public TspFitnessEvaluator(boolean visualize, List<TspDataPoint> cities) {
        this.visualize = visualize;
        this.cities = cities;
        if (visualize) {
            initJFrame();
        }
    }

    @Override
    public void evaluateFitness(Collection<Individual> population) {
        List<List<TspDataPoint>> routes = assignFitnessToRoutes(population);

        if (visualize) {
            for (List<TspDataPoint> route : routes) {
                updateCanvas(route);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<List<TspDataPoint>> assignFitnessToRoutes(Collection<Individual> population) {
        List<List<TspDataPoint>> routes = new ArrayList<>();
        for (Individual individual : population) {
            List<TspDataPoint> route =  createRouteFromGenotype(individual.getGenotype());
            // TODO Calculate the fitness of the route and assign it the the individual
            routes.add(route);
        }

        return routes;
    }

    private List<TspDataPoint> createRouteFromGenotype(int[] genotype) {
        List<TspDataPoint> route = new ArrayList<>();
        for (int idx : genotype) {
            route.add(cities.get(idx));
        }
        route.add(cities.get(0));
        return route;
    }

    /*
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
    }*/

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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        g.setColor(Color.BLACK);
        for (Point2D point : route) {
            g.fillRect((int) point.getX() - 2, (int) point.getY() - 2, 4, 4);
        }

        g.setColor(Color.BLUE);
        Point2D prevPoint = route.get(0);
        for (int i = 1; i < route.size(); i++) {
            Point2D currentPoint = route.get(i);
            g.drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), (int)currentPoint.getX(), (int)currentPoint.getY());
            prevPoint = currentPoint;
        }

        Point2D lastPoint = route.get(0);
        g.drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), (int)lastPoint.getX(), (int)lastPoint.getY());
    }

    private void updateCanvas(List<TspDataPoint> route) {
        Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();

        List<Point2D> normalizedRoute = normalizeToWindowSize(route);

        this.render(g, normalizedRoute);

        g.dispose();

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
        new TspFitnessEvaluator(true, TspData.getSmallDataset()).evaluateFitness(Collections.<Individual>emptyList());
    }
}
