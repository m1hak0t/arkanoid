package GameEngine;

import biuoop.GUI;
import java.awt.*;

public class Paddle extends Block {
    double MOVESPEED = 10;
    Velocity v_left = new Velocity(-MOVESPEED, 0);
    Velocity v_right = new Velocity(MOVESPEED, 0);
    GUI gui;
    biuoop.KeyboardSensor keyboard;

    public Paddle(double width, double height, Color color, GUI gui) {
        super(
                new Point(width/2 - width/10, height - height/12),
                width/5,
                height/30,
                color
        );
        this.gui = gui;
        keyboard = gui.getKeyboardSensor();
    }

    public void moveOneStepLeft() {
        GameEngine.Point p = super.shape.r_upperleft;
        super.shape.MoveToSpecificPoint(v_left.applyToPoint(p));
    }

    public void moveOneStepRight() {
        Point p = super.shape.r_upperleft;
        super.shape.MoveToSpecificPoint(v_right.applyToPoint(p));
    }

    public void move() {
        if (keyboard.isPressed("left")) {
            moveOneStepLeft();
        }
        if (keyboard.isPressed("right")) {
            moveOneStepRight();
        }
    }

    @Override
    public void timePassed() {
        move();
    }

    @Override
    public Velocity hit(GameEngine.Point collisionPoint, Velocity currentVelocity) {
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

            double paddleWidth = paddleRight - paddleLeft;
            double segmentWidth = paddleWidth / 5.0;

            // Determine which region (1-5) the collision occurred in
            double relativeX = collisionPoint.getX() - paddleLeft;
            int region = (int)(relativeX / segmentWidth) + 1;

            // Clamp region to valid range
            region = Math.max(1, Math.min(5, region));

            // Get current speed to maintain it
            double speed = currentVelocity.getSpeed();

            // Apply different angles based on region
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