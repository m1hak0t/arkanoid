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
 * Level 4: Final Four
 * 3 balls
 * 7 rows of 15 blocks each (full screen of blocks)
 */
public class FinalFour implements LevelInformation {
    
    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        
        // Three balls spread across angles
        velocities.add(Velocity.fromAngleAndSpeed(-70, 5));
        velocities.add(Velocity.fromAngleAndSpeed(-90, 5));
        velocities.add(Velocity.fromAngleAndSpeed(-110, 5));
        
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 12;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                // Blue sky with clouds
                d.setColor(new Color(30, 144, 255));
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                
                // Draw clouds
                drawCloud(d, 100, 400);
                drawCloud(d, 600, 500);
                
                // Rain effect from clouds
                d.setColor(Color.WHITE);
                for (int i = 0; i < 10; i++) {
                    d.drawLine(80 + (i * 10), 400, 60 + (i * 10), 600);
                    d.drawLine(580 + (i * 10), 500, 560 + (i * 10), 600);
                }
            }
            
            private void drawCloud(DrawSurface d, int x, int y) {
                // White cloud made of circles
                d.setColor(new Color(220, 220, 220));
                d.fillCircle(x, y, 25);
                d.fillCircle(x + 20, y - 10, 30);
                
                d.setColor(new Color(200, 200, 200));
                d.fillCircle(x + 40, y, 25);
                d.fillCircle(x + 50, y + 10, 22);
                d.fillCircle(x + 10, y + 10, 28);
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
        
        int blockWidth = 50;
        int blockHeight = 25;
        int startX = 25;
        int startY = 100;
        
        // 7 rows of 15 blocks each
        Color[] rowColors = {
            Color.GRAY,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.WHITE,
            Color.PINK,
            Color.CYAN
        };
        
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 15; col++) {
                Block block = new Block(
                    new Point(startX + (col * blockWidth), startY + (row * blockHeight)),
                    blockWidth,
                    blockHeight,
                    rowColors[row]
                );
                blocks.add(block);
            }
        }
        
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 105; // 7 * 15 = 105
    }
}