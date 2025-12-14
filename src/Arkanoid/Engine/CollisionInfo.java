package GameEngine;
import Interfaces.Collidable;
public class CollisionInfo {
   Point collision = null;
   Collidable object = null;

   public CollisionInfo(Point coll, Collidable obj) {
      collision = coll;
      object = obj;
   }
   // the point at which the collision occurs.
   public Point collisionPoint() {

      return collision;}

   // the collidable object involved in the collision.
   public Collidable collisionObject() {
      return object;
   }

}