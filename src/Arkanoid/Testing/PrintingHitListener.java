package Arkanoid.Testing;

import Arkanoid.Engine.Ball;
import Arkanoid.Interfaces.HitListener;
import Arkanoid.Sprites.Block;
import java.time.LocalDateTime;

public class PrintingHitListener implements HitListener {

   public void hitEvent(Block beingHit, Ball hitter) {
      LocalDateTime currentDateTime = LocalDateTime.now();
      //System.out.println("A Block was hit:  " + currentDateTime);
   }
}