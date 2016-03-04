package no.emagnus.driving;

import no.emagnus.ga.Individual;
import no.emagnus.rendering.RenderableBody;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.collision.Filter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.WheelJoint;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class CarGenerator {

    private static final long CATEGORY = 123456754321L;
    private static long FILTER_CATEGORY = 1L;

    public RenderableBody generateCar(Individual individual, World world, Vector2 startPoint) {
        Color color = new Color(
                (float)Math.random() * 0.5f + 0.5f,
                (float)Math.random() * 0.5f + 0.5f,
                (float)Math.random() * 0.5f + 0.5f);

        Filter filter = new CategoryFilter(CATEGORY, FILTER_CATEGORY << 1);

        CarBlueprint blueprint = new CarBlueprint(individual.getGenotype());

        RenderableBody car = new RenderableBody(color);
        car.setUserData(individual);

        Vector2 center = new Vector2(0, 0);

        // These triangles flip out if the vectors are used directly from the blueprint
        List<Triangle> triangles = Arrays.asList(
            new Triangle(center, new Vector2(blueprint.aPoint), new Vector2(blueprint.bPoint)),
            new Triangle(center, new Vector2(blueprint.bPoint), new Vector2(blueprint.cPoint)),
            new Triangle(center, new Vector2(blueprint.cPoint), new Vector2(blueprint.dPoint)),
            new Triangle(center, new Vector2(blueprint.dPoint), new Vector2(blueprint.ePoint)),
            new Triangle(center, new Vector2(blueprint.ePoint), new Vector2(blueprint.fPoint)),
            new Triangle(center, new Vector2(blueprint.fPoint), new Vector2(blueprint.gPoint)),
            new Triangle(center, new Vector2(blueprint.gPoint), new Vector2(blueprint.hPoint)),
            new Triangle(center, new Vector2(blueprint.hPoint), new Vector2(blueprint.aPoint)));

        for (Triangle triangle : triangles) {
            BodyFixture triangleFixture = new BodyFixture(triangle);
            triangleFixture.setFilter(filter);
            triangleFixture.setDensity(blueprint.bodyDensity);
            triangleFixture.setFriction(1);
            car.addFixture(triangleFixture);
        }

        car.setMass(MassType.NORMAL);
        car.translate(startPoint);

        Circle backWheelShape = new Circle(blueprint.backWheelRadius);
        Circle frontWheelShape = new Circle(blueprint.frontWheelRadius);
        RenderableBody backWheel = new RenderableBody(color);
        RenderableBody frontWheel = new RenderableBody(color);
        BodyFixture backWheelFixture = new BodyFixture(backWheelShape);
        backWheelFixture.setFriction(0.8);
        backWheelFixture.setFilter(filter);
        backWheel.addFixture(backWheelFixture);
        BodyFixture frontWheelFixture = new BodyFixture(frontWheelShape);
        frontWheelFixture.setFilter(filter);
        frontWheelFixture.setFriction(0.8);
        frontWheel.addFixture(frontWheelFixture);
        backWheel.setMass(MassType.NORMAL);
        frontWheel.setMass(MassType.NORMAL);

        backWheel.translate(blueprint.fPoint.add(startPoint));
        frontWheel.translate(blueprint.aPoint.add(startPoint));

        world.addBody(backWheel);
        world.addBody(frontWheel);
        world.addBody(car);

        WheelJoint wheelJoint1 = new WheelJoint(backWheel, car, new Vector2(-blueprint.fDistance, 0).add(startPoint), new Vector2(0, 2).add(startPoint));
        wheelJoint1.setMotorSpeed(-12);
        wheelJoint1.setMaximumMotorTorque(25);
        wheelJoint1.setMotorEnabled(true);
        world.addJoint(wheelJoint1);

        WheelJoint wheelJoint2 = new WheelJoint(frontWheel, car, new Vector2(blueprint.aDistance, 0).add(startPoint), new Vector2(0, 2).add(startPoint));
        wheelJoint2.setMotorSpeed(-12);
        wheelJoint2.setMaximumMotorTorque(25);
        wheelJoint2.setMotorEnabled(true);
        world.addJoint(wheelJoint2);

        return car;
    }

    private class CarBlueprint {
        private double frontWheelRadius;
        private double backWheelRadius;
        private double bodyDensity;
        private double aDistance;
        private double fDistance;

        private double bDistance;
        private double cDistance;
        private double dDistance;
        private double eDistance;
        private double gDistance;
        private double hDistance;

        private Vector2 aPoint;
        private Vector2 bPoint;
        private Vector2 cPoint;
        private Vector2 dPoint;
        private Vector2 ePoint;
        private Vector2 fPoint;
        private Vector2 gPoint;
        private Vector2 hPoint;

        public CarBlueprint(int[] genotype) {
            StringBuilder sb = new StringBuilder(genotype.length);
            for (int i : genotype) {
                sb.append(i);
            }

            String genotypeString = sb.toString();

            frontWheelRadius = 0.3 + 0.1 * Integer.parseInt(genotypeString.substring(0, 3), 2);
            backWheelRadius = 0.3 + 0.1 * Integer.parseInt(genotypeString.substring(3, 6), 2);
            bodyDensity = 0.5 + 0.08 * Integer.parseInt(genotypeString.substring(6, 10), 2);

            aDistance = 0.3 + 0.1 * Integer.parseInt(genotypeString.substring(10, 14), 2);
            bDistance = 0.25 + 0.1 * Integer.parseInt(genotypeString.substring(14, 18), 2);
            cDistance = 0.2 + 0.1 * Integer.parseInt(genotypeString.substring(18, 22), 2);
            dDistance = 0.25 + 0.1 * Integer.parseInt(genotypeString.substring(22, 26), 2);
            eDistance = 0.25 + 0.1 * Integer.parseInt(genotypeString.substring(26, 30), 2);
            fDistance = 0.3 + 0.1 * Integer.parseInt(genotypeString.substring(30, 34), 2);
            gDistance = 0.2 + 0.1 * Integer.parseInt(genotypeString.substring(34, 38), 2);
            hDistance = 0.2 + 0.1 * Integer.parseInt(genotypeString.substring(38, 42), 2);

            aPoint = new Vector2(aDistance, 0.0);
            bPoint = new Vector2(bDistance * Math.cos(Math.PI/5), bDistance * Math.sin(Math.PI/5));
            cPoint = new Vector2(cDistance * Math.cos(2 * Math.PI/5), cDistance * Math.sin(2 * Math.PI/5));
            dPoint = new Vector2(dDistance * Math.cos(3 * Math.PI/5), dDistance * Math.sin(3 * Math.PI/5));
            ePoint = new Vector2(eDistance * Math.cos(4 * Math.PI/5), eDistance * Math.sin(4 * Math.PI/5));
            fPoint = new Vector2(-fDistance, 0.0);
            gPoint = new Vector2(gDistance * Math.cos(4 * Math.PI/3), gDistance * Math.sin(4 * Math.PI/3));
            hPoint = new Vector2(hDistance * Math.cos(5 * Math.PI/3), hDistance * Math.sin(5 * Math.PI/3));
        }
    }
}
