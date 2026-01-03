package Arkanoid.Testing;

import Arkanoid.Shapes.Line;
import Arkanoid.Shapes.Point;
import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {

    public void drawRandomCircles() {
        Random rand = new Random(); // create a random-number generator
        // Create a window with the title "Random Circles Example"
        // which is 400 pixels wide and 300 pixels high.
        GUI gui = new GUI("Random Circles Example", 400, 300);
        DrawSurface d = gui.getDrawSurface();
        for (int i = 0; i < 10; ++i) {
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(300) + 1; // get integer in range 1-300
            int r = 5*(rand.nextInt(4) + 1); // get integer in 5,10,15,20
            d.setColor(Color.RED);
            d.fillCircle(x,y,r);
        }
        gui.show(d);
    }
    public void drawRandomlines() {
        Random rand = new Random();
        GUI gui = new GUI("Random Lines", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        double [][] lines = new double[10][4];
        //Generete the lines
        for (int i = 0; i < 10; ++i) {
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(400) + 1; // get integer in range 1-400
            int x2 = rand.nextInt(400) + 1; // get integer in range 1-400
            int y2 = rand.nextInt(400) + 1; // get integer in range 1-400
            d.setColor(Color.PINK);
            d.drawLine(x, y, x2, y2);
            lines[i][0] = x;
            lines[i][1] = y;
            lines[i][2] = x2;
            lines[i][3] = y2;
            d.setColor(Color.blue);
            Line findmiddle = new Line(x,y,x2,y2);
            Point middle = findmiddle.middle();
            d.drawCircle((int)middle.getX(),(int)middle.getY(),3);
        }
        //Generate the intersection points
        for (int i= 0; i < 10 ; ++i) {
            Point point1_1 = new Point(lines[i][0],lines[i][1]);
            Point point1_2 = new Point(lines[i][2],lines[i][3]);
            Line line1 = new Line(point1_1,point1_2);
            for (int j=0; j < 10; ++j) {
                if (i!= j) {
                    Point line2_1 = new Point(lines[j][0],lines[j][1]);
                    Point line2_2 = new Point(lines[j][2],lines[j][3]);
                    Line line2 = new Line(line2_1,line2_2);
                    if (line1.isIntersecting(line2)) {
                        Point intersection = line1.intersectionWith(line2);
                        d.setColor(Color.BLACK);
                        d.drawCircle((int)intersection.getX(),(int)intersection.getY(),3);

                    }
                }
            }
        }
        gui.show(d);

    }

}