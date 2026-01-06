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

/**
 * Level 1: Direct Hit
 * Single ball, single block in the center
 * Ball flies directly to the block
 */
public class DirectHit implements LevelInformation {
    
    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        int number_of_balls = numberOfBalls();
        // Ball goes straight up (90 degrees) with speed 5
        for (int i = 0; i < number_of_balls; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(-70, 5));
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                // Dark blue background
                d.setColor(new Color(0, 0, 51));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                
                // Target circles around the block (centered at 400, 200)
                int centerX = 400;
                int centerY = 200;
                
                d.setColor(Color.BLUE);
                // Draw concentric circles as target
                for (int i = 1; i <= 3; i++) {
                    d.drawCircle(centerX, centerY, 50 * i);
                }
                
                // Draw crosshairs
                d.drawLine(centerX - 150, centerY, centerX - 50, centerY); // Left
                d.drawLine(centerX + 50, centerY, centerX + 150, centerY); // Right
                d.drawLine(centerX, centerY - 150, centerX, centerY - 50); // Top
                d.drawLine(centerX, centerY + 50, centerX, centerY + 150); // Bottom
            }

            @Override
            public void timePassed() {
                // Static background
            }
        };
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        
        // Single red block in the center
        Block block = new Block(
            new Point(385, 185), // Center it at (400, 200) with 30x30 size
            30,
            30,
            Color.RED
        );
        
        blocks.add(block);
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}