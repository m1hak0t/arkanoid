public class Line {
    private Point start;
    private Point end;
    //defines the _a coefficient in the ax + b exaction
    private double _m;
    //defines the _a coefficient in the ax + b exaction
    private double _b;
    public Line(Point s, Point e) {
        this.start = s;
        this.end = e;
        _m = (end.getY()-start.getY())/(end.getX()-start.getX());
        _b = end.getY()- _m*end.getX();
    }
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1,y1);
        end = new Point(x2,y2);
        _m = (end.getY()-start.getY())/(end.getX()-start.getX());
        _b = end.getY()- _m*end.getX();
    }
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double new_x = (start.getX() + end.getX())/2;
        double new_y = (start.getY() + end.getY())/2;
        Point middlepoint = new Point(new_x, new_y);
        return middlepoint;
    }

    // Returns the start point of the line
    public Point start() {
        return start;
    }

    // Returns the end point of the line
    public Point end() {
        return end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        if (intersectionWith(other) != null) {
            return true;
        }
        return false;
    }
    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        //Let's suppose that the lines are infinite and check whether they have potencial to intersect
        //If m is equal the lines wont ever intersect
        if (_m == other._m) {
            return null;
        }
        //Check where it the intersection point of the infinite lines
        double x = (_b - other._b)/(other._m - _m);
        double y = _m*x + _b;
        //After we got the point let's calculate if this point exists on both lines
        boolean onfirst = x <= Math.max(start.getX(),end.getX()) && x >= Math.min(start.getX(),end.getX()) && (y <= Math.max(start.getY(),end.getY()) && y >= Math.min(start.getY(),end.getY()));
        boolean onsecond = x <= Math.max(other.start.getX(),other.end.getX()) && x >= Math.min(other.start.getX(),other.end.getX()) && (y <= Math.max(other.start.getY(),other.end.getY()) && y >= Math.min(other.start.getY(),other.end.getY()));
        if (onfirst && onsecond) {
            return new Point(x,y);
        }
        return null;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        if (start.getY() == other.start.getY() && start.getX() == other.start.getX()) {
            return true;
        }
        return false;
    }

}