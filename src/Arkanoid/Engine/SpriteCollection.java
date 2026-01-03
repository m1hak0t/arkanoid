package Arkanoid.Engine;

import Arkanoid.Interfaces.Sprite;
import biuoop.DrawSurface;

import java.util.ArrayList;

public class SpriteCollection {
   //Create a private Arraylist for all the Sprite objects
   private ArrayList<Sprite> spritelist = new ArrayList<>();
   public void addSprite(Sprite s) {
      this.spritelist.add(s);
   }
   public void removeSprite(Sprite s) {
       spritelist.remove(s);
   }

   // call timePassed() on all spritelist.
   public void notifyAllTimePassed() {
      ArrayList<Sprite> copy = new ArrayList<>(spritelist);
      for (Sprite s :copy) {
         s.timePassed();
      }

   }

   // call drawOn(d) on all spritelist.
   public void drawAllOn(DrawSurface d) {
      for (Sprite s :spritelist) {
         s.drawOn(d);
      }
   }
   
   public ArrayList<Sprite> getSprites() {
      return spritelist;
   }
}