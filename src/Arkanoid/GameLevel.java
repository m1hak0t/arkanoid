package Arkanoid;

import java.awt.Color;

import Arkanoid.Interfaces.Animation;
import Arkanoid.Interfaces.Collidable;
import Arkanoid.Interfaces.Sprite;

import Arkanoid.Shapes.Point;
import Arkanoid.Sprites.Block;
import Arkanoid.Sprites.Paddle;
import Arkanoid.Testing.PrintingHitListener;
import biuoop.DrawSurface;
import Arkanoid.Engine.*;
import biuoop.KeyboardSensor;


public class GameLevel implements Animation {
   private SpriteCollection sprites;
   private GameEnvironment environment;
   private int width;
   private int height;
   private Arkanoid.Engine.Counter remainingblocks = new Counter();
   private Arkanoid.Engine.Counter remainingballs = new Counter();
   private Arkanoid.Engine.Counter scorecounter = new Counter();
   private BlockRemover listener = new BlockRemover(this,remainingblocks);
   private BallCollection ballCollection = new BallCollection();
   private PrintingHitListener print_listener = new PrintingHitListener();
   private BallRemover ballremover = new BallRemover(this, remainingballs);
   //private biuoop.DrawSurface d = gui.getDrawSurface();
   private boolean running = true;
   private KeyboardSensor keyboard; // <--- Add this field
   private AnimationRunner runner;

   public GameLevel(int width, int height, AnimationRunner runner, KeyboardSensor ks) {
      this.width = width;
      this.height = height;
      this.sprites = new SpriteCollection();
      this.environment = new GameEnvironment();
      this.keyboard = ks;
      this.runner = runner;
   }

   public void addSprite(Sprite s) {
      sprites.addSprite(s);
   }

   public void addCollidable(Collidable c) {
      environment.addCollidable(c);
   }

   public void removeCollidable(Collidable c) {
      environment.removeCollidable(c);
   }

   public void removeSprite(Sprite c) {
      sprites.removeSprite(c);
   }

   public void addToGame(Sprite s) {
      // Add as sprite
      addSprite(s);

      // Check if it's also Collidable before adding
      if (s instanceof Collidable) {
         addCollidable((Collidable) s);
      }
      if (s instanceof Ball) {
         ballCollection.addBall((Ball) s);
      }
   }

   public void removeFromGame(Sprite s) {
      // remove as sprite
      removeSprite(s);
      if (s instanceof Collidable) {
         removeCollidable((Collidable) s);
      }
      if (s instanceof Ball) {
         ballCollection.removeBall((Ball) s);
      }
   }

   public void addScore(int i) {
      scorecounter.increase(i);
   }

   public void initialize() {
      //Create a listener

      // Screen dimensions
      final int SCREEN_WIDTH = width;
      final int SCREEN_HEIGHT = height;
      final int WALL_THICKNESS = 10;

      // --- Pastel Color Palette ---
      Color pastelLavender = Color.decode("#E6E6FA");   // Soft lavender
      Color pastelPeach = Color.decode("#FFD4B2");      // Peachy cream
      Color pastelMint = Color.decode("#B4E7CE");       // Mint green
      Color pastelRose = Color.decode("#FFB6C1");       // Rose pink
      Color pastelSky = Color.decode("#A4D8E1");        // Sky blue
      Color pastelLemon = Color.decode("#FFF8B8");      // Lemon yellow
      Color pastelCoral = Color.decode("#F9A8A8");      // Coral
      Color pastelPeriwinkle = Color.decode("#CCCCFF"); // Periwinkle
      Color pastelBlush = Color.decode("#FFC3D5");      // Blush
      Color pastelSage = Color.decode("#C8E6C9");       // Sage green
      Color pastelLilac = Color.decode("#DDA0DD");      // Lilac

      // --- Create Border Walls ---

      // Top wall
      Block topWall = new Block(
              new Point(0, 0),
              SCREEN_WIDTH,
              WALL_THICKNESS,
              pastelLavender
      );
      this.addToGame(topWall);
      //topWall.addHitListener(listener);


      // Left wall
      Block leftWall = new Block(
              new Point(0, 0),
              WALL_THICKNESS,
              SCREEN_HEIGHT,
              pastelPeriwinkle
      );
      this.addToGame(leftWall);
      //leftWall.addHitListener(listener);

      // Right wall
      Block rightWall = new Block(
              new Point(SCREEN_WIDTH - WALL_THICKNESS, 0),
              WALL_THICKNESS,
              SCREEN_HEIGHT,
              pastelPeriwinkle
      );
      this.addToGame(rightWall);
      //rightWall.addHitListener(listener);

      // Bottom wall
      Block bottomWall = new Block(
              new Point(0, SCREEN_HEIGHT - WALL_THICKNESS),
              SCREEN_WIDTH,
              WALL_THICKNESS,
              pastelLavender
      );
      this.addToGame(bottomWall);
      //Make a wall deadly for balls
      bottomWall.make_deadly_for_balls();
      bottomWall.addHitListener(ballremover);
      //bottomWall.addHitListener(listener);

      // --- Create Brick Layout ---

      // Brick dimensions
      final int BRICK_WIDTH = 40;  // Approximate from screenshot
      final int BRICK_HEIGHT = 20;
      final int START_X = 170;     // Starting X position for first row
      final int START_Y = 150;     // Starting Y position

      // Row 1: Pastel Lilac gradient
      Color[] row1Colors = new Color[11];
      for (int i = 0; i < 11; i++) {
         row1Colors[i] = pastelLilac;
      }
      createBrickRow(START_X, START_Y, BRICK_WIDTH, BRICK_HEIGHT, row1Colors);

      // Row 2: Pastel Coral
      Color[] row2Colors = new Color[11];
      for (int i = 0; i < 11; i++) {
         row2Colors[i] = pastelCoral;
      }
      createBrickRow(START_X + BRICK_WIDTH, START_Y + BRICK_HEIGHT,
              BRICK_WIDTH, BRICK_HEIGHT, row2Colors);

      // Row 3: Pastel Lemon
      Color[] row3Colors = new Color[11];
      for (int i = 0; i < 11; i++) {
         row3Colors[i] = pastelLemon;
      }
      createBrickRow(START_X + BRICK_WIDTH * 2, START_Y + BRICK_HEIGHT * 2,
              BRICK_WIDTH, BRICK_HEIGHT, row3Colors);

      // Row 4: Pastel Sky Blue
      Color[] row4Colors = new Color[11];
      for (int i = 0; i < 11; i++) {
         row4Colors[i] = pastelSky;
      }
      createBrickRow(START_X + BRICK_WIDTH * 3, START_Y + BRICK_HEIGHT * 3,
              BRICK_WIDTH, BRICK_HEIGHT, row4Colors);

      // Row 5: Alternating Pastel Blush and Mint
      Color[] row5Colors = {pastelBlush, pastelMint, pastelBlush, pastelMint,
              pastelBlush, pastelMint, pastelBlush, pastelMint,
              pastelBlush, pastelMint, pastelBlush};
      createBrickRow(START_X + BRICK_WIDTH * 4, START_Y + BRICK_HEIGHT * 4,
              BRICK_WIDTH, BRICK_HEIGHT, row5Colors);

      // Row 6: Pastel Sage Green
      Color[] row6Colors = new Color[11];
      for (int i = 0; i < 11; i++) {
         row6Colors[i] = pastelSage;
      }
      createBrickRow(START_X + BRICK_WIDTH * 4, START_Y + BRICK_HEIGHT * 5,
              BRICK_WIDTH, BRICK_HEIGHT, row6Colors);

      // --- Create Paddle ---
      Paddle paddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT,
              pastelPeach, keyboard);
      this.addToGame(paddle);

      // --- Create Ball ---
      for (int i = 0; i < 25; i++) {
         Ball ball = new Ball(new Point(640 + 10* i, 400 + i), 10, pastelRose, environment);  // Rose pink ball
         ball.setVelocity(Velocity.fromAngleAndSpeed(90 + 10 * i, 5));  // Start moving up
         this.addToGame(ball);
      }
   }

   /**
    * Helper method to create a row of bricks
    */
   private void createBrickRow(int startX, int startY, int width, int height,
                               Color[] colors) {
      for (int i = 0; i < colors.length; i++) {
         Block brick = new Block(
                 new Point(startX + i * width, startY),
                 width,
                 height,
                 colors[i]

         );
         brick.addHitListener(listener);
         this.addToGame(brick);
         remainingblocks.increase(1);
      }
   }

   private void update_score(DrawSurface d,Counter counter) {
      String message = "SCORE: " + counter.getValue();
      d.setColor(Color.black);
      d.drawText(30, 45, message, 20);
      if (remainingblocks.getValue() == 0) {
         counter.increase(100);
      }
   }

   /**
    * Run the game -- start the animation loop
    */


   @Override
   public void doOneFrame(DrawSurface d) {
      d.setColor(Color.BLUE);  // Blue background
      d.fillRectangle(0, 0, width, height);

      update_score(d,scorecounter);
      sprites.drawAllOn(d);
      //gui.show(d);
      //Check if the game is paused
      if (this.keyboard.isPressed("p")) {
         this.runner.run(new PauseScreen(this.keyboard));
      }
      // Notify all sprites that time has passed
      sprites.notifyAllTimePassed();
      //Close the game if all the blocks are done
      if (remainingblocks.getValue() == 0) {
         //gui.close();
         this.running = false;
      }
      //Close the game if there are no balls
      if (ballCollection.get_amount() == 0)  {
         //gui.close();
         this.running = false;
      }
   }



   @Override
   public boolean shouldStop() {
      return !this.running;
   }
}