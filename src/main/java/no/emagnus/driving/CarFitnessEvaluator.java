package no.emagnus.driving;

import no.emagnus.ga.FitnessEvaluator;
import no.emagnus.ga.Individual;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CarFitnessEvaluator implements FitnessEvaluator<String> {

    private World world = new World();

    private static final int KILL_SIM_THRESHOLD = 5;
    private static final double TIME_STEP = 1.0 / 30.0;

    @Override
    public void evaluateFitness(Collection<Individual<String>> population) {
        initializeSimulation(43);

        Map<Individual, RenderableBody> cars = generateCars(population);

        System.out.println("Evaluating fitness of " + cars.size() + " cars @ " + (1.0/TIME_STEP) + "Hz");

        // running simulation of cars driving
        runSimulation(cars.values());

        // TODO assign fitness to the individuals according to their performance
        assignFitnessToPopulation(population, cars);

        // cleaning up after running the simulation
        cleanUpSimulation();
    }

    private void assignFitnessToPopulation(Collection<Individual<String>> population, Map<Individual, RenderableBody> cars) {
        for (Individual<String> individual : population) {
            RenderableBody car = cars.get(individual);
            double fitness = car.getWorldCenter().x;
            individual.setFitness(fitness);
        }
    }


    private void initializeSimulation(int seed) {
        new TerrainGenerator(seed).generateTerrain(world);
    }

    private Map<Individual, RenderableBody> generateCars(Collection<Individual<String>> population) {
        Map<Individual, RenderableBody> cars = new HashMap<>();
        for (Individual individual : population) {
            cars.put(individual, new CarGenerator().generateCar(world, new Vector2(-5,0)));
        }

        return cars;
    }

    private void runSimulation(Collection<RenderableBody> cars) {
        double stepsWithoutProgress = 0;
        double maxDistance = 0;
        double timeSimulated = 0;
        double timeStep = 0.03;

        long start = System.currentTimeMillis();
        while (stepsWithoutProgress < KILL_SIM_THRESHOLD) {
            world.update(timeStep);
            timeSimulated += timeStep;

            double maxDistanceThisStep = findMaxDistance(cars);
            if (maxDistanceThisStep > maxDistance) {
                maxDistance = maxDistanceThisStep;
                stepsWithoutProgress = 0;
            } else {
                stepsWithoutProgress += timeStep;
            }
        }

        long millisSpent = System.currentTimeMillis() - start;

        System.out.println(String.format("%.3f seconds simulated in %.1f seconds, max progress: %.3f", timeSimulated, millisSpent/1000.0, maxDistance));
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
}
