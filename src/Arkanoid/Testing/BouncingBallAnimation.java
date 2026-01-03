package Arkanoid.Testing;

import Arkanoid.Engine.Ball;
import Arkanoid.Engine.Velocity;
import Arkanoid.Shapes.Point;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

public class BouncingBallAnimation {

    static private void drawAnimation(Point start, Velocity v) {
        GUI gui = new GUI("title",400,400);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start,10, java.awt.Color.RED);
        ball.setVelocity(v);
        while (true && v != null) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }

    }
    public static void DrawAnimatedBall (Point start, Velocity v, int size, java.awt.Color color, GUI gui, Sleeper sleeper) {
        Ball ball = new Ball(start,size, color);
        ball.setVelocity(v);
        while (true && v != null) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
    public static void main (String[] args){
        if (args.length < 4) {
            System.out.println("The terminal imput is empty or incorrect");
        }
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double c = Double.parseDouble(args[2]);
        double d = Double.parseDouble(args[3]);

        BouncingBallAnimation example = new BouncingBallAnimation();

        Velocity vel =  new Velocity(c,d);

        example.drawAnimation(new Point(a,b),vel);
    }
}
