import biuoop.DrawSurface;

import java.awt.*;

public class Ball {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private int xbound;
    private int ybound;
    private int xstart;
    private int ystart;

    public Ball(Point c, int r, java.awt.Color color){
        center = c;
        this.r =r;
        this.color = color;
        velocity = new Velocity(0,0);
        xbound = 400;
        ybound = 500;
        xstart = 0;
        ystart = 0;
    }
    public int getX() {
        return (int)center.getX();
    }
    public int getY() {
        return (int) center.getY();
    }
    public int getSize() {
        return (int)(Math.PI * r * r);
    }
    public java.awt.Color getColor() {
        return color;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
    surface.setColor(color);
    surface.fillCircle((int) this.center.getX(),(int) this.center.getY(),r);
    }

    public void setVelocity(Velocity v) {
        velocity = v;
    }
    public void setVelocity(double dx, double dy) {
        velocity = new Velocity(dx, dy);
    }
    public Velocity getVelocity() {
        return velocity;
    };
    public void setbounds(int xstart, int ystart, int xbound, int ybound){
        this.xbound = xbound;
        this.ybound = ybound;
        this.xstart = xstart;
        this.ystart = ystart;

    }

    public void moveOneStep() {
        //In case of intersection with x axis (LEFT WALL)
        if (this.center.getX() < xstart + r) {
            this.setVelocity(velocity.getX() * -1, velocity.getY());
        }
        //In case of intersection with right wall (x = 400)
        if (this.center.getX() > xbound - r ) {
            this.setVelocity(velocity.getX() * -1, velocity.getY());
        }
        //In case of intersection with the cealing (y axis)
        if (this.center.getY() < ystart + r) {
            this.setVelocity(velocity.getX(), velocity.getY() * -1);
        }
        //In case of intersection with the floor (y = 400)
        if (this.center.getY() > ybound - r) {
            this.setVelocity(velocity.getX(), velocity.getY() * -1);
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

}