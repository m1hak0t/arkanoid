package Arkanoid.Sprites;

import Arkanoid.Engine.Ball;
import Arkanoid.Engine.Velocity;
import Arkanoid.Interfaces.Collidable;
import Arkanoid.Interfaces.Sprite;
import Arkanoid.Shapes.Line;
import Arkanoid.Shapes.Point;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle extends Block {
    private double MOVESPEED;
    private Velocity v_left;
    private Velocity v_right;
    private KeyboardSensor keyboard;
    private double screenWidth;
    private double paddleWidth;

    public Paddle(double screenWidth, double screenHeight, Color color,
                  KeyboardSensor keyboard, int paddleWidth, int paddleSpeed) {
        super(
                new Point(screenWidth/2 - paddleWidth/2, screenHeight - screenHeight/17),
                paddleWidth,
                screenHeight/17,
                color
        );

        this.MOVESPEED = paddleSpeed;
        this.v_left = new Velocity(-MOVESPEED, 0);
        this.v_right = new Velocity(MOVESPEED, 0);
        this.keyboard = keyboard;
        this.screenWidth = screenWidth;
        this.paddleWidth = paddleWidth;
    }

    public void moveOneStepLeft() {
        Point p = super.shape.getUpperLeft();
        Point newPos = v_left.applyToPoint(p);

        // Don't move past left wall (assume wall is at x=10)
        if (newPos.getX() >= 10) {
            super.shape.MoveToSpecificPoint(newPos);
        }
    }

    public void moveOneStepRight() {
        Point p = super.shape.getUpperLeft();
        Point newPos = v_right.applyToPoint(p);

        // Don't move past right wall (assume wall is at screenWidth - 10)
        if (newPos.getX() + paddleWidth <= screenWidth - 10) {
            super.shape.MoveToSpecificPoint(newPos);
        }
    }

    public void move() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveOneStepLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveOneStepRight();
        }
    }

    @Override
    public void timePassed() {
        move();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line leftWall = shape.getLeft_wall();
        Line rightWall = shape.getRight_wall();
        Line ceiling = shape.getCealing();
        Line floor = shape.getFloor();

        // Left wall collision - reverse horizontal direction
        if (leftWall.isPointOnSegment(collisionPoint.getX(), collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
        }

        // Right wall collision - reverse horizontal direction
        if (rightWall.isPointOnSegment(collisionPoint.getX(), collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
        }

        // Ceiling collision (top of paddle) - special 5-region behavior
        if (ceiling.isPointOnSegment(collisionPoint.getX(), collisionPoint.getY())) {
            // Get paddle boundaries
            double paddleLeft = ceiling.start().getX();
            double paddleRight = ceiling.end().getX();

            // Ensure left is actually less than right
            if (paddleLeft > paddleRight) {
                double temp = paddleLeft;
                paddleLeft = paddleRight;
                paddleRight = temp;
            }

            double width = paddleRight - paddleLeft;
            double segmentWidth = width / 5.0;

            // Determine which region (1-5) the collision occurred in
            double relativeX = collisionPoint.getX() - paddleLeft;
            int region = (int)(relativeX / segmentWidth) + 1;

            // Clamp region to valid range
            region = Math.max(1, Math.min(5, region));

            // Get current speed to maintain it
            double speed = currentVelocity.getSpeed();

            // Apply different angles based on region
            // Angle system: 0° = left, 90° = up, 180° = right, 270° = down
            switch (region) {
                case 1: // Far left - bounce at 60° (sharp left)
                    return Velocity.fromAngleAndSpeed(-150, speed);

                case 2: // Left - bounce at 30° (moderate left)
                    return Velocity.fromAngleAndSpeed(-165, speed);

                case 3: // Center - straight up
                    currentVelocity.reverseY();
                    return currentVelocity;

                case 4: // Right - bounce at 330° (moderate right)
                    return Velocity.fromAngleAndSpeed( -30, speed);

                case 5: // Far right - bounce at 300° (sharp right)
                    return Velocity.fromAngleAndSpeed(-15, speed);

                default: // Fallback - just reverse Y
                    currentVelocity.reverseY();
                    return currentVelocity;
            }
        }

        // Floor collision (bottom of paddle) - reverse vertical direction
        if (floor.isPointOnSegment(collisionPoint.getX(), collisionPoint.getY())) {
            currentVelocity.reverseY();
            return currentVelocity;
        }

        // No collision detected - return unchanged velocity
        return currentVelocity;
    }
}