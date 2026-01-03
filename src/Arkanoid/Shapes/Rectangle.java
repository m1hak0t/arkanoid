package Arkanoid.Shapes;
import biuoop.DrawSurface;

import java.util.ArrayList;

public class Rectangle {
    public Point r_upperleft;
    private Point r_upperright;
    private Point r_downleft;
    private Point r_downright;
    private double r_width;
    private double r_height;
    private Line cealing;
    private Line floor;
    private Line left_wall;
    private Line right_wall;
    private Line[] lines;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        r_width = width;
        r_height = height;
        //Find the 4 points that define the rectangle
        this.r_upperleft = upperLeft;
        this.r_upperright = new Point(this.r_upperleft.getX() + width, r_upperleft.getY());
        this.r_downleft = new Point(r_upperleft.getX(), r_upperleft.getY() + height);
        this.r_downright = new Point(r_upperleft.getX() + width, r_upperleft.getY() + height);
        //Calculate the lines that define this rectangle
        this.cealing = new Line(r_upperleft, r_upperright);
        this.floor = new Line(r_downleft, r_downright);
        this.left_wall = new Line(r_upperleft, r_downleft);
        this.right_wall = new Line(r_downright, r_upperright);
        lines = new Line[]{cealing, floor, left_wall, right_wall};
    }
    public void MoveToSpecificPoint(Point p) {
        this.r_upperleft = p;
        this.r_upperright = new Point(this.r_upperleft.getX() + r_width, r_upperleft.getY());
        this.r_downleft = new Point(r_upperleft.getX(), r_upperleft.getY() + r_height);
        this.r_downright = new Point(r_upperleft.getX() + r_width, r_upperleft.getY() + r_height);
        //Calculate the lines that define this rectangle
        this.cealing = new Line(r_upperleft, r_upperright);
        this.floor = new Line(r_downleft, r_downright);
        this.left_wall = new Line(r_upperleft, r_downleft);
        this.right_wall = new Line(r_downright, r_upperright);
        lines = new Line[]{cealing, floor, left_wall, right_wall};

    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (lines[i].isIntersecting(line)) {
                result.add(lines[i].intersectionWith(line));
            }
        }
        return result;
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return r_width;
    }

    public double getHeight() {
        return r_height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return r_upperleft;
    }

    public void drawOn(DrawSurface l) {
        for (int i = 0; i < 4; i++) {
            l.drawLine((int) lines[i].start().getX(), (int) lines[i].start().getY(), (int) lines[i].end().getX(), (int) lines[i].end().getY());
        }
    }

    public Line getCealing() {
        return cealing;
    }

    public Line getFloor() {
        return floor;
    }

    public Line getLeft_wall() {
        return left_wall;
    }

    public Line getRight_wall() {
        return right_wall;

    }
}