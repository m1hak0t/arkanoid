package Arkanoid.Engine;

import Arkanoid.Interfaces.Collidable;
import Arkanoid.Shapes.Line;
import Arkanoid.Shapes.Point;
import Arkanoid.Shapes.Rectangle;

import java.util.ArrayList;

public class GameEnvironment {
   private ArrayList<Collidable> collidables = new ArrayList<>();

   // add the given collidable to the environment.


   public void addCollidable(Collidable c) {
      collidables.add(c);
   }
   public void removeCollidable(Collidable c) {
      collidables.remove(c);
   }

   // Assume an object moving from line.start() to line.end().
   // If this object will not collide with any of the collidables
   // in this collection, return null. Else, return the information
   // about the closest collision that is going to occur.
   public CollisionInfo getClosestCollision(Line trajectory) {
      Point closestPoint = null;
      Collidable closestCollidable = null;
      double minDistance = Double.MAX_VALUE;

      // Loop through ALL collidables in the game
      for (Collidable c : collidables) {
         // Get the shape (Rectangle) from this collidable
         Rectangle rect = c.getCollisionRectangle();

         // Find intersection point with this specific rectangle
         Point intersection = trajectory.closestIntersectionToStartOfLine(rect);

         if (intersection != null) {
            double distance = trajectory.start().distance(intersection);

            // Keep track of the closest collision
            if (distance < minDistance) {
               minDistance = distance;
               closestPoint = intersection;
               closestCollidable = c; // Store which object we hit
            }
         }
      }

      if (closestPoint == null) {
         return null;
      }

      // Create CollisionInfo with BOTH the point AND the object
      return new CollisionInfo(closestPoint, closestCollidable);
   }

}