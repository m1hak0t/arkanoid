package GameEngine;

import java.util.ArrayList;

public class Line {
    private static final double EPSILON = 1e-10;

    private Point start;
    private Point end;
    private boolean isVertical;
    private double slope;      // Only valid if !isVertical
    private double intercept;  // Only valid if !isVertical

    public Line(Point s, Point e) {
        this.start = s;
        this.end = e;
        calculateLineEquation();
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        calculateLineEquation();
    }

    private void calculateLineEquation() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();

        // Check if line is vertical (avoid division by zero)
        if (Math.abs(dx) < EPSILON) {
            isVertical = true;
            slope = 0;      // Not used for vertical lines
            intercept = 0;  // Not used for vertical lines
        } else {
            isVertical = false;
            slope = dy / dx;
            intercept = end.getY() - slope * end.getX();//Hello mechina
        }
    }

    public double length() {
        return start.distance(end);
    }

    public Point middle() {
        double newX = (start.getX() + end.getX()) / 2;
        double newY = (start.getY() + end.getY()) / 2;
        return new Point(newX, newY);
    }

    public Point start() {
        return start;
    }

    public Point end() {
        return end;
    }

    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    public Point intersectionWith(Line other) {
        // Case 1: Both lines are vertical
        if (this.isVertical && other.isVertical) {
            // Vertical lines are parallel (or overlapping)
            // Either way, no single intersection point
            return null;
        }

        // Case 2: This line is vertical, other is not
        if (this.isVertical) {
            return findIntersectionWithVertical(this, other);
        }

        // Case 3: Other line is vertical, this is not
        if (other.isVertical) {
            return findIntersectionWithVertical(other, this);
        }

        // Case 4: Both lines are non-vertical
        // Check if they are parallel
        if (Math.abs(this.slope - other.slope) < EPSILON) {
            return null;  // Parallel lines don't intersect
        }

        // Calculate intersection point
        double x = (other.intercept - this.intercept) / (this.slope - other.slope);
        double y = this.slope * x + this.intercept;

        // Check if intersection point is on both line segments
        if (isPointOnSegment(x, y) && other.isPointOnSegment(x, y)) {
            return new Point(x, y);
        }

        return null;
    }

    /**
     * Find intersection between a vertical line and a non-vertical line
     */
    private static Point findIntersectionWithVertical(Line vertical, Line nonVertical) {
        double x = vertical.start.getX();  // x-coordinate is fixed for vertical line
        double y = nonVertical.slope * x + nonVertical.intercept;

        // Check if this point is on both segments
        if (vertical.isPointOnSegment(x, y) && nonVertical.isPointOnSegment(x, y)) {
            return new Point(x, y);
        }

        return null;
    }

    /**
     * Check if a point (x, y) lies on this line segment
     */
    public boolean isPointOnSegment(double x, double y) {
        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        return (x >= minX - EPSILON && x <= maxX + EPSILON &&
                y >= minY - EPSILON && y <= maxY + EPSILON);
    }

    /**
     * Check if two lines are equal (same start and end points, in any order)
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }

        // Check if lines have same endpoints (in same order)
        boolean sameOrder = start.equals(other.start) && end.equals(other.end);

        // Check if lines have same endpoints (in reverse order)
        boolean reverseOrder = start.equals(other.end) && end.equals(other.start);

        return sameOrder || reverseOrder;
    }

    // Getters for debugging/testing
    public boolean isVertical() {
        return isVertical;
    }

    public double getSlope() {
        return isVertical ? Double.POSITIVE_INFINITY : slope;
    }

    public double getIntercept() {
        return intercept;
    }
    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        if (rect.intersectionPoints(this).isEmpty()) {
            return null;
        }
        //Generate the list of the intersection points
        java.util.List<Point> intersections = rect.intersectionPoints(this);

        return closestPointToStartGroupOfPoints(intersections);
    }
    public Point closestPointToStartGroupOfPoints(java.util.List<Point> group) {
        double mindistance = 400;
        int minimal_index =0 ;
        int i = 0;
        for (Point p: group) {
            if (this.start().distance(p) < mindistance) {
                mindistance = this.start().distance(p);
                minimal_index = i;
            }
            i++;
        }
        return group.get(minimal_index);
    }


}