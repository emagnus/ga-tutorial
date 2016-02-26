package no.emagnus.driving;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.RevoluteJoint;

import static no.emagnus.utils.Utils.DEGTORAD;

public class CarGenerator {

    public Body generateCar(World world) {
        /*BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyType.DYNAMIC;
        fixtureDef.density = 1;

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(2, 2);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3);

        bodyDef.position = new Vec2(0, 10);
        fixtureDef.shape = boxShape;
        Body bodyA = world.createBody(bodyDef);
        bodyA.createFixture(fixtureDef);

        bodyDef.position = new Vec2(3, 5);
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 0.6f;
        Body bodyB = world.createBody(bodyDef);
        bodyB.createFixture(fixtureDef);

        bodyDef.position = new Vec2(-3, 5);
        Body bodyC = world.createBody(bodyDef);
        bodyC.createFixture(fixtureDef);

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = bodyA;
        revoluteJointDef.bodyB = bodyB;
        revoluteJointDef.collideConnected = false;
        revoluteJointDef.localAnchorA.set(4, 4);
        revoluteJointDef.localAnchorB.set(0, 0);
        revoluteJointDef.enableMotor = true;
        revoluteJointDef.referenceAngle = -135 * DEGTORAD;
        revoluteJointDef.maxMotorTorque = 300;
        revoluteJointDef.motorSpeed = -6 * 360 * DEGTORAD;

        Joint joint = (RevoluteJoint) world.createJoint(revoluteJointDef);

        RevoluteJointDef revoluteJointDef2 = new RevoluteJointDef();
        revoluteJointDef2.bodyA = bodyA;
        revoluteJointDef2.bodyB = bodyC;
        revoluteJointDef2.collideConnected = false;
        revoluteJointDef2.localAnchorA.set(4, -4);
        revoluteJointDef2.localAnchorB.set(0, 0);
        revoluteJointDef2.enableMotor = true;
        revoluteJointDef.referenceAngle = 135 * DEGTORAD;
        revoluteJointDef2.maxMotorTorque = 300;
        revoluteJointDef2.motorSpeed = -6 * 360 * DEGTORAD;

        RevoluteJoint joint2 = (RevoluteJoint) world.createJoint(revoluteJointDef2);

        return bodyA;*/
        return null;
    }
}
