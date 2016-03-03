package no.emagnus.driving;

import no.emagnus.dyn4jtest.RenderableBody;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Segment;
import org.dyn4j.geometry.Vector2;

import java.awt.*;
import java.util.Random;

public class TerrainGenerator {

    private int seed;

    public TerrainGenerator(int seed) {
        this.seed = seed;
    }

    public void generateTerrain(World world) {
        Random random = new Random(seed);
        double stepSize = 2;
        double previousY = 0;
        double range = 0.1;
        for (int i = -4; i < 200; i ++) {
            double nextY = previousY + (range/2 - (random.nextDouble()*range));
            Segment segment = new Segment(new Vector2(i*stepSize, previousY), new Vector2(stepSize + i*stepSize, nextY));
            RenderableBody edge = new RenderableBody(Color.BLACK);
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
