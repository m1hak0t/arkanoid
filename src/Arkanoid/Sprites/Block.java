package Arkanoid.Sprites;
import Arkanoid.Engine.Ball;
import Arkanoid.Interfaces.HitListener;
import Arkanoid.Interfaces.HitNotifier;
import Arkanoid.Engine.Velocity;
import Arkanoid.Interfaces.Collidable;
import Arkanoid.Interfaces.Sprite;
import Arkanoid.Shapes.Rectangle;
import Arkanoid.Shapes.Point;
import Arkanoid.Shapes.Line;
import Arkanoid.Interfaces.HitListener;
import biuoop.DrawSurface;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;


public class Block implements Collidable, Sprite, HitNotifier {
    public Rectangle shape;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    boolean makes_balls_disappear = false;


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
    private void notifyHit(Ball hitter) {

            //System.out.println("Notify hit called");
            // Make a copy of the hitListeners before iterating over them.
            List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
            // Notify all listeners about a hit event
            for (HitListener hl : listeners) {
                hl.hitEvent(this, hitter);
            }
    }

    @Override
    public void timePassed() {

    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        notifyHit(hitter);
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


    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
        System.out.println("Listener added");
        System.out.println(hitListeners);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    public void make_deadly_for_balls() {
        makes_balls_disappear = true;
    }

    public boolean ifmakesballsdisappear() {
        return makes_balls_disappear;
    }
}
