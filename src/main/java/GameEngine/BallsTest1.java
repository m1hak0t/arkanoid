package GameEngine;

import biuoop.GUI;
import biuoop.DrawSurface;

import java.awt.*;


public class BallsTest1 {
    public static void main(String[] args) {
        GUI gui = new GUI("Balls Test 1", 400, 400);
        DrawSurface d = gui.getDrawSurface();

        Ball b1 = new Ball(new Point(400,230),50, java.awt.Color.BLACK);
        Ball b2 = new Ball(new Point(200,130),50, Color.RED);
        Ball b3 = new Ball(new Point(150,200),60,java.awt.Color.BLUE);

        b1.drawOn(d);
        b2.drawOn(d);
        b3.drawOn(d);

        gui.show(d);
    }
}