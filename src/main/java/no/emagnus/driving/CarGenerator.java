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
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.awt.*;

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
        Rectangle boxShape = new Rectangle(2, 0.5);
        BodyFixture carBodyFixture = new BodyFixture(boxShape);
        carBodyFixture.setFilter(filter);
        //carBodyFixture.setDensity(1 + Math.random() * 5);
        carBodyFixture.setDensity(blueprint.bodyDensity);
        carBodyFixture.setFriction(1);
        car.addFixture(carBodyFixture);
        car.setMass(MassType.NORMAL);

        car.translate(new Vector2(0, -1).add(startPoint));
        world.addBody(car);

        Circle backWheelShape = new Circle(blueprint.backWheelRadius);
        Circle frontWheelShape = new Circle(blueprint.frontWheelRadius);
        RenderableBody wheel1 = new RenderableBody(color);
        RenderableBody wheel2 = new RenderableBody(color);
        BodyFixture backWheelFixture = new BodyFixture(backWheelShape);
        backWheelFixture.setFriction(0.6);
        backWheelFixture.setFilter(filter);
        wheel1.addFixture(backWheelFixture);
        BodyFixture frontWheelFixture = new BodyFixture(frontWheelShape);
        frontWheelFixture.setFilter(filter);
        frontWheelFixture.setFriction(0.6);
        wheel2.addFixture(frontWheelFixture);
        wheel1.setMass(MassType.NORMAL);
        wheel2.setMass(MassType.NORMAL);

        wheel1.translate(new Vector2(-1, -1).add(startPoint));
        wheel2.translate(new Vector2(1, -1).add(startPoint));

        world.addBody(wheel1);
        world.addBody(wheel2);

        WheelJoint wheelJoint1 = new WheelJoint(wheel1, car, new Vector2(-1, -1).add(startPoint), new Vector2(0, 1).add(startPoint));
        wheelJoint1.setMotorSpeed(-12);
        wheelJoint1.setMaximumMotorTorque(25);
        wheelJoint1.setMotorEnabled(true);
        world.addJoint(wheelJoint1);

        WheelJoint wheelJoint2 = new WheelJoint(wheel2, car, new Vector2(1, -1).add(startPoint), new Vector2(0, 1).add(startPoint));
        wheelJoint2.setMotorSpeed(-12);
        wheelJoint2.setMaximumMotorTorque(25);
        wheelJoint2.setMotorEnabled(true);
        world.addJoint(wheelJoint2);

        return car;
    }

    private class CarBlueprint {
        private double backWheelRadius;
        private double frontWheelRadius;
        private double bodyDensity;

        public CarBlueprint(String genotype) {
            backWheelRadius = 0.4 + 0.05 * Integer.parseInt(genotype.substring(0, 3), 2);
            frontWheelRadius = 0.4 + 0.05 * Integer.parseInt(genotype.substring(3, 6), 2);
            bodyDensity = 0.5 + 0.05 * Integer.parseInt(genotype.substring(6, 10), 2);
        }
    }
}
