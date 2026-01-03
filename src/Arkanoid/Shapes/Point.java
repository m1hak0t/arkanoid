package Arkanoid.Shapes;

public class Point {
    private double _x;
    private double _y;
    //constructor
    public Point(double x, double y) {
        _x = x;
        _y = y;
    }
    //return the distance of this point to the other point
    public double distance(Point other) {
        double x_distance = _x - other.getX();
        double y_distance = _y - other.getY();
        double distance = Math.sqrt(x_distance*x_distance+y_distance*y_distance);
        return distance;
    }
    // return true is the points are equal false otherwise
    public boolean equals(Point other) {
        double epsilon = 0.0001;
        if (other == null) {
            return false;
        }
        if (Math.abs((_x-other.getX())-(_y-other.getY())) < epsilon)
            return true;
        else
            return false;
    }
    public double getX() {
        return this._x;
    }
    public double getY(){
        return this._y;
    }
    //return the Y values of this point
    public boolean areClose(Point p, double epsilon) {
        if (this.distance(p) < epsilon) {
            return true;
        }
        return false;
    }
}
