import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {
    public static void main(String[] args) {
        String[] args1 = new String[6];
        args1[0] = "12";
        args1[1] = "2";
        args1[2] = "3";
        args1[3] = "4";
        args1[4] = "2";
        args1[5] = "9";

        MultipleFramesBouncingBallsAnimation newloop = new MultipleFramesBouncingBallsAnimation();
        newloop.draw2windows6balls(args1);
    }


    public void draw2windows6balls(String[] args) {
        Object[][] storage = new Object[6][6];
        Object[][] storage2 = new Object[6][6];
        //Initialize the GUI
        GUI gui = new GUI("title",600,600);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();
        //First ball init
        for (int i = 0; i < 6; i++) {
            int size = Integer.parseInt(args[i]);
            //Create a random location
            int x = rand.nextInt(50,500) + 1; // get integer in range 1-400
            int y = rand.nextInt(50,500) + 1; // get integer in range 1-400
            //Create a random angle
            int angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, (123 - size * 10) * 0.05);
            Ball ball = new Ball(new Point(x, y), 3*size, Color.WHITE);
            ball.setbounds(50,50 ,500,500);
            ball.setVelocity(v);
            storage[i][0] = ball;
        }
        //Second ball init
        for (int i = 0; i < 6; i++) {
            int size = Integer.parseInt(args[i]);
            //Create a random location
            int x = rand.nextInt(450,600) + 1; // get integer in range 1-400
            int y = rand.nextInt(450,600) + 1; // get integer in range 1-400
            //Create a random angle
            int angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, (123 - size * 10) * 0.05);
            Ball ball2 = new Ball(new Point(x, y), 3 * size, Color.gray);
            ball2.setbounds(450,450,600, 600);
            ball2.setVelocity(v);
            storage2[i][0] = ball2;
        }
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.GRAY);
            d.fillRectangle(50, 50, 500, 500);
            d.setColor(Color.YELLOW);
            d.fillRectangle(450, 450, 600, 600);
            //First frame
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
            //Second frame;
            for (int i=0; i < 6;i++) {
                //Move each ball forward
                Ball ball2 = (Ball) storage2[i][0];
                ball2.moveOneStep();
            }
            for (int j=0; j < 6;j++) {
                //Draw each ball
                Ball ball2 = (Ball) storage2[j][0];
                ball2.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

}
