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
        double stepSize = 2;
        double previousY = 0;
        double range = 0.1;
        for (int i = -4; i < 200; i ++) {
            double nextY = previousY + (range/2 - (random.nextDouble()*range));
            Segment segment = new Segment(new Vector2(i*stepSize, previousY), new Vector2(stepSize + i*stepSize, nextY));
            ExampleGraphics2D.GameObject edge = new ExampleGraphics2D.GameObject();
            BodyFixture segmentFixture = new BodyFixture(segment);
            segmentFixture.setFriction(0.6);
            edge.addFixture(segmentFixture);
            edge.setMass(MassType.INFINITE);
            edge.translate(0.0, -2.0);
            world.addBody(edge);
            previousY = nextY;
            range += 0.05;
        }
    }
}
