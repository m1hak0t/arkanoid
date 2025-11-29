package GameEngine;

// GameEngine.Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed;
        double dy = speed;
        return new Velocity(dx  * Math.cos(angle), dy * Math.sin(angle));
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