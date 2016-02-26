package no.emagnus.driving;

import no.emagnus.dyn4jtest.ExampleGraphics2D;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Segment;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class TerrainGenerator {

    private Random random = new Random();

    public void generateTerrain(World world) {
/*
        Rectangle floorRect = new Rectangle(15.0, 1.0);
        ExampleGraphics2D.GameObject floor = new ExampleGraphics2D.GameObject();
        BodyFixture carFixture = new BodyFixture(floorRect);
        carFixture.setFilter(new CategoryFilter(12L, 12L));
        floor.addFixture(carFixture);
        floor.setMass(MassType.INFINITE);
        // move the floor down a bit
        floor.translate(0.0, -4.0);
        world.addBody(floor);
*/
        Vector2[] vertices = new Vector2[100];
        double stepSize = 2;
        double previousY = 0;
        double range = 0.1;
        for (int i = -4; i < 100; i ++) {
            double nextY = range/2 - (random.nextDouble()*range);
            Segment segment = new Segment(new Vector2(i*stepSize, previousY), new Vector2(stepSize + i*stepSize, nextY));
            ExampleGraphics2D.GameObject edge = new ExampleGraphics2D.GameObject();
            edge.addFixture(segment);
            edge.setMass(MassType.INFINITE);
            edge.translate(0.0, -4.0);
            world.addBody(edge);
            previousY = nextY;
            range += 0.02;
        }
/*
        Segment edgeShape = new Segment()
        ExampleGraphics2D.GameObject polygon = new ExampleGraphics2D.GameObject();
        polygon.addFixture(edgeShape);
        polygon.setMass(MassType.INFINITE);
        world.addBody(polygon);
*/
        /*BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(-20, 0); // start a bit to the left
        Body staticBody = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();

        int stepSize = 20;
        int previousY = 0;
        int range = 10;
        for (int i = 0; i < 100; i ++) {
            int nextY = range/2 - random.nextInt(range);
            edgeShape.set(new Vec2(i*stepSize, previousY), new Vec2(stepSize + i*stepSize, nextY));
            fixtureDef.shape = edgeShape;
            staticBody.createFixture(fixtureDef);
            previousY = nextY;
            range++;
        }*/
    }
}
