package GameEngine;

import Interfaces.Collidable;

public class Paddle implements Collidable {

    @Override
    public Rectangle getCollisionRectangle() {
        return null;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        return null;
    }
}
