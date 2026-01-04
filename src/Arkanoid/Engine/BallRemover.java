package Arkanoid.Engine;

import Arkanoid.GameLevel;
import Arkanoid.Interfaces.HitListener;
import Arkanoid.Sprites.Block;

public class BallRemover implements HitListener {
    Counter remainingBalls;
    GameLevel game;

    public BallRemover(GameLevel game, Counter removedBalls) {
        this.remainingBalls = removedBalls;
        this.game = game;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        game.removeFromGame(hitter);
        remainingBalls.decrease(1);
    }
}
