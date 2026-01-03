package Arkanoid.Interfaces;

import Arkanoid.Engine.Ball;
import Arkanoid.Engine.Velocity;
import Arkanoid.Shapes.Line;
import Arkanoid.Shapes.Point;
import Arkanoid.Shapes.Rectangle;

public interface Collidable {
   // Return the "collision shape" of the object.
   Rectangle shape = null;
   Rectangle getCollisionRectangle();

   // Notify the object that we collided with it at collisionPoint with
   // a given velocity.
   // The return is the new velocity expected after the hit (based on
   // the force the object inflicted on us).

    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}