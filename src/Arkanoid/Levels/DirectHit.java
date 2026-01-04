package Arkanoid.Levels;

import Arkanoid.Engine.Velocity;
import Arkanoid.Interfaces.LevelInformation;
import Arkanoid.Interfaces.Sprite;
import Arkanoid.Shapes.Point;
import Arkanoid.Sprites.Block;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DirectHit implements LevelInformation {
    public int numberOfBalls() { return 1; }

    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(0, 4)); // Straight up
        return velocities;
    }

    public int paddleSpeed() { return 5; }
    public int paddleWidth() { return 80; }
    public String levelName() { return "Direct Hit"; }

    public Sprite getBackground() {
        return new Sprite() {
            public void drawOn(DrawSurface d) {
                d.setColor(Color.BLACK);
                d.fillRectangle(0, 0, 800, 600);
                d.setColor(Color.BLUE);
                d.drawCircle(400, 162, 60);
                d.drawCircle(400, 162, 90);
                d.drawLine(400, 182, 400, 262);
                d.drawLine(400, 142, 400, 62);
                d.drawLine(380, 162, 300, 162);
                d.drawLine(420, 162, 500, 162);
            }
            public void timePassed() {}
        };
    }

    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(new Point(380, 150), 40, 25, Color.RED));
        return blocks;
    }

    public int numberOfBlocksToRemove() { return 1; }
}