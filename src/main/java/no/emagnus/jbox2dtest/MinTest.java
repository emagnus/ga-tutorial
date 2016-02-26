package no.emagnus.jbox2dtest;

import no.emagnus.driving.CarGenerator;
import no.emagnus.driving.TerrainGenerator;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

import javax.swing.*;
import java.awt.*;

public class MinTest {

    public static void main(String[] args) {

        World myWorld = new World();

        TerrainGenerator terrainGenerator = new TerrainGenerator();
        terrainGenerator.generateTerrain(myWorld);

        CarGenerator carGenerator = new CarGenerator();
        Body bodyA = carGenerator.generateCar(myWorld);

        JFrame jFrame = new JFrame("Test ja");
        jFrame.setLayout(new BorderLayout());
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //myWorld.drawDebugData();
        System.out.println("World created ...");

        float dt = 1.0f/60;
        int numberOfStepsMax = 6000;
        int numberOfStepsToDie = 120;
        int numberOfStepsCompleted = 0;

        double xMax = 0;


        long start = System.currentTimeMillis();
        System.out.println("Starting simulation, dt=" + dt + "s");
        for (int i = 0; i < numberOfStepsMax; i++) {
            //System.out.println("Position: " + bodyA.getPosition().x);
            myWorld.step(1);
            if (bodyA.getLocalCenter().x > xMax) {
                xMax = bodyA.getLocalCenter().x;
                numberOfStepsToDie = 60;
            } else {
                numberOfStepsToDie--;
            }
            numberOfStepsCompleted++;

            if (numberOfStepsToDie < 1) {
                System.out.println("Breaking early!");
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Simulated " + (dt*numberOfStepsCompleted) + " seconds in " + (end - start) + "ms. Total distance was " + xMax);
    }
}
