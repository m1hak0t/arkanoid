package GameEngine;

import Interfaces.Sprite;
import biuoop.DrawSurface;

public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private int xbound;
    private int ybound;
    private int xstart;
    private int ystart;
    //The refference to the game environment
    private GameEnvironment env;
    //The Line object that defines the trajectory without any obsticales
    private Line traj;
    private float hue = 0.0f;

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
    public Ball(Point c, int r, java.awt.Color color, GameEnvironment environment){
        center = c;
        this.r =r;
        this.color = color;
        velocity = new Velocity(0,0);
        env = environment;

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

    @Override
    public void timePassed() {
        moveOneStep();
    }

    public void setVelocity(Velocity v) {
        velocity = v;
        traj = calculate_trajectory();
    }
    public void setVelocity(double dx, double dy) {
        velocity = new Velocity(dx, dy);
        traj = calculate_trajectory();
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
        traj = calculate_trajectory();
/*        //In case of intersection with x axis (LEFT WALL)
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
        this.center = this.getVelocity().applyToPoint(this.center);*/
        /// Check if the ball has obsticles
        CollisionInfo collision = env.getClosestCollision(traj);
        if (collision != null) {
            //Initialize where is the point of the collision
            Point collisionpoint = collision.collision;
            //If the expected collision point and the ball coordinates
            //It also passes it's radius to define what is "close" :)
            if (center.areClose(collisionpoint, this.r + 3)) {
                Velocity v = collision.object.hit(collisionpoint,this.velocity);
                this.velocity = v;
                /// Remove the block one collided
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
        updateColorByDirection();
        updateColorCyclic();

    }
    private void deleteBlock (Block s) {
    }

    //Method that would calculate the trajectory based on the given velocity
    private Line calculate_trajectory() {
        //For this we need the center point and the velocity value
        Point first_point = this.center;
        Point second_point = new Point(first_point.getX()+velocity.getX() * 10, first_point.getY() + velocity.getY() * 10);
        Line result = new Line(first_point,second_point);
        return result;
    }
    public void updateColorCyclic() {
        hue += 0.01f;
        if (hue > 1.0f) {
            hue = 0.0f;
        }
        this.color = java.awt.Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    // OPTION 2: Color based on speed (heatmap effect)
    public void updateColorBySpeed() {
        // Calculate speed magnitude
        double speed = Math.sqrt(velocity.getX() * velocity.getX() +
                velocity.getY() * velocity.getY());

        // Normalize speed (adjust maxSpeed based on your game)
        double maxSpeed = 15.0;
        double speedRatio = Math.min(speed / maxSpeed, 1.0);

        // Color gradient: Blue (slow) -> Cyan -> Green -> Yellow -> Red (fast)
        int red, green, blue;

        if (speedRatio < 0.25) {
            // Blue to Cyan
            double t = speedRatio / 0.25;
            red = 0;
            green = (int) (128 * t);
            blue = 255;
        } else if (speedRatio < 0.5) {
            // Cyan to Green
            double t = (speedRatio - 0.25) / 0.25;
            red = 0;
            green = (int) (128 + 127 * t);
            blue = (int) (255 * (1 - t));
        } else if (speedRatio < 0.75) {
            // Green to Yellow
            double t = (speedRatio - 0.5) / 0.25;
            red = (int) (255 * t);
            green = 255;
            blue = 0;
        } else {
            // Yellow to Red
            double t = (speedRatio - 0.75) / 0.25;
            red = 255;
            green = (int) (255 * (1 - t));
            blue = 0;
        }

        this.color = new java.awt.Color(red, green, blue);
    }

    // OPTION 3: Color based on position (rainbow across screen)
    public void updateColorByPosition() {
        // You'll need to set bounds first with setbounds()
        double x = this.center.getX();
        double y = this.center.getY();

        // Normalize position to 0-1 range
        double xRatio = (x - xstart) / (xbound - xstart);
        double yRatio = (y - ystart) / (ybound - ystart);

        // Clamp to 0-1
        xRatio = Math.max(0, Math.min(1, xRatio));
        yRatio = Math.max(0, Math.min(1, yRatio));

        // Create color based on position
        int red = (int) (xRatio * 255);
        int green = (int) (yRatio * 255);
        int blue = (int) ((1 - xRatio) * 255);

        this.color = new java.awt.Color(red, green, blue);
    }

    // OPTION 4: Color based on direction (shows where ball is heading)
    public void updateColorByDirection() {
        // Calculate angle of velocity
        double angle = Math.atan2(velocity.getY(), velocity.getX());

        // Convert angle to hue (0 to 360 degrees -> 0 to 1.0)
        float hue = (float) ((angle + Math.PI) / (2 * Math.PI));

        // Convert HSB to RGB
        this.color = java.awt.Color.getHSBColor(hue, 1.0f, 1.0f);
    }

}