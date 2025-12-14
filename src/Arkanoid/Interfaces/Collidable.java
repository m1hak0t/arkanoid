package Interfaces;
import GameEngine.Line;
import GameEngine.Rectangle;
import GameEngine.Velocity;
import GameEngine.Point;

public interface Collidable {
   // Return the "collision shape" of the object.
   Rectangle shape = null;
   Rectangle getCollisionRectangle();

   // Notify the object that we collided with it at collisionPoint with
   // a given velocity.
   // The return is the new velocity expected after the hit (based on
   // the force the object inflicted on us). 
   default Velocity hit(Point collisionPoint, Velocity currentVelocity){
         ///If the collisionpoint is the part of the vertical wall on the left wall of the block
         Line myleftwall = shape.getLeft_wall();
         if (myleftwall.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
         }
         ///If the collisionpoiint is the part of the vertical wall on the right wall of the block
         Line myrightwall = shape.getRight_wall();
         if (myrightwall.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
         }
         ///If the colllisionpoint is the part of the cealing of the block
         Line mycealing = shape.getCealing();
         if (mycealing.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseY();
            return currentVelocity;
         }
         ///If the collisionpoint is hte part o;f the floor of the block
         Line myfloor = shape.getFloor();
         if (myfloor.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseY();
            return currentVelocity;
         }
         return null;
   }
}