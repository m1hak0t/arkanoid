package Arkanoid.Engine;

import Arkanoid.GameLevel;
import Arkanoid.Interfaces.HitListener;
import Arkanoid.Sprites.Block;


// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
   private GameLevel game;
   private Counter remainingBlocks;

   public BlockRemover(GameLevel game, Counter removedBlocks) {
      remainingBlocks = removedBlocks;
      this.game = game;
   }

   // Blocks that are hit should be removed
   // from the game. Remember to remove this listener from the block
   // that is being removed from the game.
   public void hitEvent(Block beingHit, Ball hitter) {
      //Remove the ball if the block is deadly
      game.removeFromGame(beingHit);
      System.out.println("Block removed:  " + beingHit);
      remainingBlocks.decrease(1);
      game.addScore(5);
      System.out.println("Current score:  " + remainingBlocks.getValue());
   }
}