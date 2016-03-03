package no.emagnus.rendering;

import no.emagnus.ga.Individual;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RenderableBody extends Body {

    protected Color color;

    public RenderableBody(Color color) {
        this.color = color;
    }

    public void render(Graphics2D g, double scale) {
        // save the original transform
        AffineTransform ot = g.getTransform();

        // transform the coordinate system from world coordinates to local coordinates
        AffineTransform lt = new AffineTransform();
        lt.translate(this.transform.getTranslationX() * scale, this.transform.getTranslationY() * scale);
        lt.rotate(this.transform.getRotation());

        // apply the transform
        g.transform(lt);

        // loop over all the body fixtures for this body
        for (BodyFixture fixture : this.fixtures) {
            // get the shape on the fixture
            Convex convex = fixture.getShape();
            Graphics2DRenderer.render(g, convex, scale, color);
        }

        // set the original transform
        g.setTransform(ot);
    }

    public double getDistanceTravelled() {
        return getWorldCenter().x;
    }

    public Individual getIndividual() {
        Object userData = getUserData();
        return (Individual) userData;
    }
}
