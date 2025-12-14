package GameEngine;

import java.awt.Color;

import Interfaces.Collidable;
import Interfaces.Sprite;
import biuoop.GUI;

public class Game {
   private SpriteCollection sprites;
   private GameEnvironment environment;
   private GUI gui;
   private int width;
   private int height;

   public Game(int width, int height) {
      this.width = width;
      this.height = height;
      this.sprites = new SpriteCollection();
      this.environment = new GameEnvironment();
      this.gui = new GUI("Arkanoid", width, height);
   }

   public void addSprite(Sprite s) {
      sprites.addSprite(s);
   }


   public void addCollidable(Collidable c) {
      environment.addCollidable(c);
   }

   public void removeColldable(Collidable c) {
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
   }

   public void removeFromGame(Sprite s) {
      // remove as sprite
      removeSprite(s);

      // Check if it's also Collidable before adding
      if (s instanceof Collidable) {
         removeColldable((Collidable) s);
      }
   }


   public void initialize() {
      // Screen dimensions
      final int SCREEN_WIDTH = width;
      final int SCREEN_HEIGHT = height;
      final int WALL_THICKNESS = 25;

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

      // Left wall
      Block leftWall = new Block(
              new Point(0, 0),
              WALL_THICKNESS,
              SCREEN_HEIGHT,
              pastelPeriwinkle
      );
      this.addToGame(leftWall);

      // Right wall
      Block rightWall = new Block(
              new Point(SCREEN_WIDTH - WALL_THICKNESS, 0),
              WALL_THICKNESS,
              SCREEN_HEIGHT,
              pastelPeriwinkle
      );
      this.addToGame(rightWall);

      // Bottom wall
      Block bottomWall = new Block(
              new Point(0, SCREEN_HEIGHT - WALL_THICKNESS),
              SCREEN_WIDTH,
              WALL_THICKNESS,
              pastelLavender
      );
      this.addToGame(bottomWall);

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
              pastelPeach, gui);
      this.addToGame(paddle);

      // --- Create Ball ---
      Ball ball = new Ball(new Point(640, 400), 10, pastelRose,environment);  // Rose pink ball
      ball.setVelocity(Velocity.fromAngleAndSpeed(90, 5));  // Start moving up
      this.addToGame(ball);
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
         this.addToGame(brick);
      }
   }

   /**
    * Run the game -- start the animation loop
    */
   public void run() {
      biuoop.Sleeper sleeper = new biuoop.Sleeper();
      int framesPerSecond = 60;
      int millisecondsPerFrame = 1000 / framesPerSecond;

      while (true) {
         long startTime = System.currentTimeMillis();

         // Draw everything
         biuoop.DrawSurface d = gui.getDrawSurface();
         d.setColor(Color.BLUE);  // Blue background
         d.fillRectangle(0, 0, width, height);

         sprites.drawAllOn(d);
         gui.show(d);

         // Notify all sprites that time has passed
         sprites.notifyAllTimePassed();

         // Timing
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         if (milliSecondLeftToSleep > 0) {
            sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
}