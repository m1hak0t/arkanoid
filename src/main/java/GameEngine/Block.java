package GameEngine;

import Interfaces.Collidable;
import biuoop.DrawSurface;

import java.awt.*;

public class Block implements Collidable {
    private Rectangle shape;
    private Color color;

    public Block (Point upperleft, double width, double height) {
        this.shape = new Rectangle(upperleft,width,height);
    }
    // Constructor with color
    public Block(Point upperleft, double width, double height, Color color) {
        this.shape = new Rectangle(upperleft, width, height);
        this.color = color;
    }
    // Setter for color
    public void setColor(Color color) {
        this.color = color;
    }

    // Getter for color
    public Color getColor() {
        return this.color;
    }
    // Draw the block on the surface
    public void drawOn(DrawSurface surface) {
        // Fill the rectangle with the block's color
        surface.setColor(color);
        surface.fillRectangle(
                (int) shape.getUpperLeft().getX(),
                (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(),
                (int) shape.getHeight()
        );

        // Draw black border for visibility
        surface.setColor(Color.BLACK);
        surface.drawRectangle(
                (int) shape.getUpperLeft().getX(),
                (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(),
                (int) shape.getHeight()
        );
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        ///If the collisionpoint is the part of the vertical wall on the left wall of the block
        Line myleftwall = shape.getLeft_wall();
        if (myleftwall.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
        }
        ///If the collisionpoiint is the part of the vertical wall on the right wall of the block
        Line myrightwall = shape.getRight_wall();
        if (myrightwall.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseX();
            return currentVelocity;
        }
        ///If the colllisionpoint is the part of the cealing of the block
        Line mycealing = shape.getCealing();
        if (mycealing.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseY();
            return currentVelocity;
        }
        ///If the collisionpoint is hte part o;f the floor of the block
        Line myfloor = shape.getFloor();
        if (myfloor.isPointOnSegment(collisionPoint.getX(),collisionPoint.getY())) {
            currentVelocity.reverseY();
            return currentVelocity;
        }
        return currentVelocity;
    }

}
