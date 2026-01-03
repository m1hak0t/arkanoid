package Arkanoid.Testing;

import Arkanoid.Engine.Ball;
import Arkanoid.Engine.Velocity;
import Arkanoid.Shapes.Point;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    public static void draw6balls(String[] args) {
        Object[][] storage = new Object[6][6];
        //Initialize the GUI
        GUI gui = new GUI("title",400,400);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int size = Integer.parseInt(args[i]);
            //Create a random location
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(400) + 1; // get integer in range 1-400
            //Create a random angle
            int angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, (123 - size * 10) * 0.5);
            Ball ball = new Ball(new Point(x, y), 3*size, Color.BLUE);
            ball.setVelocity(v);
            storage[i][0] = ball;
        }
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            for (int i=0; i < 6;i++) {
                //Move each ball forward
                Ball ball = (Ball) storage[i][0];
                ball.moveOneStep();
            }
            for (int j=0; j < 6;j++) {
                //Draw each ball
                Ball ball = (Ball) storage[j][0];
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
        }
    public static void main(String[] args) {
        String[] args1 = new String[10];
        args1[0] = "12";
        args1[1] = "2";
        args1[2] = "3";
        args1[3] = "4";
        args1[4] = "2";
        args1[5] = "9";

        draw6balls(args1);
    }
}