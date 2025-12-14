package GameEngine;

// GameEngine.Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;
    private double speed;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        speed = Math.sqrt(dx*dx+dy*dy);
    }
    public static Velocity fromAngleAndSpeed(double angleDegrees, double speed) {
        // 1. Convert degrees to radians for the Trig functions
        double angleRadians = Math.toRadians(angleDegrees);

        // 2. Calculate components (v * cos(theta), v * sin(theta))
        double vx = speed * Math.cos(angleRadians);
        double vy = speed * Math.sin(angleRadians);

        return new Velocity(vx, vy);
    }

    // Inside your Velocity class:
    public double getSpeed() {
        // 3. Calculate magnitude dynamically so it never gets out of sync with dx/dy
        return Math.hypot(dx, dy);
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        Point newposition = new Point(p.getX() + dx, p.getY() + dy);
        return newposition;
    }

    public double getX() {
        return dx;
    }

    public double getY() {
        return dy;
    }
    public void reverseX() {this.dx = -dx;}
    public void reverseY() {this.dy = -dy;}

}