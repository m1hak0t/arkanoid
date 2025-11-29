package GameEngine;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //Initialize the GUI
        GUI gui = new GUI("title", 400, 400);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        //Create a game environment
        GameEnvironment environment = new GameEnvironment();
        Block block1 = new Block(new Point(0,0),400,5, Color.BLUE);
        Block block2 = new Block(new Point(0,395),400,5,Color.BLUE);
        Block block3 = new Block(new Point(395,0),5,400,Color.BLUE);
        Block block4 = new Block(new Point(0,0),5,400,Color.BLUE);
        Block block5 = new Block(new Point(0,200),125,10,Color.BLUE);
        Block block6 = new Block(new Point(270,200),125,10,Color.BLUE);
        //Add the blocks to the backend environment
        environment.addCollidable(block1);
        environment.addCollidable(block2);
        environment.addCollidable(block3);
        environment.addCollidable(block4);
        environment.addCollidable(block5);
        environment.addCollidable(block6);




        String[] args1 = new String[]{"3","3","3","3","3","3"};
        Object[][] storage = new Object[6][6];

        for (int i = 0; i < 6; i++) {
            int size = Integer.parseInt(args1[i]);
            //Create a random location
            int x = rand.nextInt(400) + 1; // get integer in range 1-400
            int y = rand.nextInt(400) + 1; // get integer in range 1-400
            //Create a random angle
            int angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, (123 - size * 10) * 0.1);
            Ball ball = new Ball(new Point(x, y), 3 * size, Color.BLUE,environment);
            ball.setVelocity(v);
            storage[i][0] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            block1.drawOn(d);
            block2.drawOn(d);
            block3.drawOn(d);
            block4.drawOn(d);
            block5.drawOn(d);
            block6.drawOn(d);

            for (int i = 0; i < 6; i++) {
                //Move each ball forward
                Ball ball = (Ball) storage[i][0];
                ball.moveOneStep();
            }
            for (int j = 0; j < 6; j++) {
                //Draw each ball
                Ball ball = (Ball) storage[j][0];
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(20);  // wait for 50 milliseconds.
        }
    }
}