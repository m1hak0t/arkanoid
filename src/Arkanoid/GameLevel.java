package Arkanoid;

import java.awt.Color;
import java.util.List;

import Arkanoid.Interfaces.Animation;
import Arkanoid.Interfaces.Collidable;
import Arkanoid.Interfaces.LevelInformation;
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
   private Counter remainingblocks;
   private Counter remainingballs;
   private Counter scorecounter;
   private BlockRemover blockRemover;
   private BallRemover ballRemover;
   private PrintingHitListener printListener;
   private boolean running;
   private KeyboardSensor keyboard;
   private AnimationRunner runner;
   private LevelInformation levelInfo;

   public GameLevel(LevelInformation levelInfo, KeyboardSensor ks,
                    AnimationRunner runner, Counter score, int width, int height) {
      this.width = width;
      this.height = height;
      this.sprites = new SpriteCollection();
      this.environment = new GameEnvironment();
      this.keyboard = ks;
      this.runner = runner;
      this.levelInfo = levelInfo;

      // Use the passed score counter to maintain score across levels
      this.scorecounter = score;

      // Create new counters for this level
      this.remainingblocks = new Counter();
      this.remainingballs = new Counter();

      // Create listeners
      this.blockRemover = new BlockRemover(this, remainingblocks);
      this.ballRemover = new BallRemover(this, remainingballs);
      this.printListener = new PrintingHitListener();

      this.running = false;
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

   public void removeSprite(Sprite s) {
      sprites.removeSprite(s);
   }

   public void addToGame(Sprite s) {
      addSprite(s);
      if (s instanceof Collidable) {
         addCollidable((Collidable) s);
      }
      if (s instanceof Ball) {
         // This is important for tracking balls!
         remainingballs.increase(1);
      }
   }

   public void removeFromGame(Sprite s) {
      removeSprite(s);
      if (s instanceof Collidable) {
         removeCollidable((Collidable) s);
      }
   }

   public void addScore(int points) {
      scorecounter.increase(points);
   }

   public int getRemainingBalls() {
      return remainingballs.getValue();
   }

   public int getRemainingBlocks() {
      return remainingblocks.getValue();
   }

   public void initialize() {
      // Screen dimensions
      final int SCREEN_WIDTH = width;
      final int SCREEN_HEIGHT = height;
      final int WALL_THICKNESS = 10;

      // --- Create Border Walls ---

      // Top wall
      Block topWall = new Block(
              new Point(0, 20), // Leave space for score display
              SCREEN_WIDTH,
              WALL_THICKNESS,
              Color.GRAY
      );
      this.addToGame(topWall);

      // Left wall
      Block leftWall = new Block(
              new Point(0, 20),
              WALL_THICKNESS,
              SCREEN_HEIGHT - 20,
              Color.GRAY
      );
      this.addToGame(leftWall);

      // Right wall
      Block rightWall = new Block(
              new Point(SCREEN_WIDTH - WALL_THICKNESS, 20),
              WALL_THICKNESS,
              SCREEN_HEIGHT - 20,
              Color.GRAY
      );
      this.addToGame(rightWall);

      // Bottom wall (death zone for balls)
      Block bottomWall = new Block(
              new Point(0, SCREEN_HEIGHT - WALL_THICKNESS),
              SCREEN_WIDTH,
              WALL_THICKNESS,
              Color.RED
      );
      this.addToGame(bottomWall);
      bottomWall.addHitListener(ballRemover);

      // --- Add blocks from LevelInformation ---
      List<Block> blocks = levelInfo.blocks();
      for (Block block : blocks) {
         block.addHitListener(blockRemover);
         block.addHitListener(printListener);
         this.addToGame(block);
         remainingblocks.increase(1);
      }

      // --- Create Paddle from LevelInformation ---
      Paddle paddle = new Paddle(
              SCREEN_WIDTH,
              SCREEN_HEIGHT,
              Color.ORANGE,
              keyboard,
              levelInfo.paddleWidth(),
              levelInfo.paddleSpeed()
      );
      this.addToGame(paddle);
   }

   private void createBallsOnTopOfPaddle() {
      int numBalls = levelInfo.numberOfBalls();
      List<Velocity> velocities = levelInfo.initialBallVelocities();

      int ballRadius = 10;
      int paddleY = height - 80; // Position above paddle (higher up)
      int spacing = 25;

      // If only one ball, center it. If multiple, spread them out
      int startX;
      if (numBalls == 1) {
         startX = width / 2;
      } else {
         int totalWidth = (numBalls - 1) * spacing;
         startX = (width - totalWidth) / 2;
      }

      for (int i = 0; i < numBalls; i++) {
         Point ballCenter = new Point(startX + (i * spacing), paddleY);

         // Create ball using your Ball constructor
         Ball ball = new Ball(ballCenter, ballRadius, Color.WHITE, environment);

         // Set velocity from level info
         ball.setVelocity(velocities.get(i));

         // Add to game (addToGame will increment remainingballs)
         this.addToGame(ball);

         // Debug print
         System.out.println("Created ball " + i + " at (" + ballCenter.getX() + ", " + ballCenter.getY() + ")");
      }

      System.out.println("Total balls created: " + remainingballs.getValue());
   }

   private void drawScoreAndLevelName(DrawSurface d) {
      // Background bar for score display
      d.setColor(Color.LIGHT_GRAY);
      d.fillRectangle(0, 0, width, 20);

      // Level name
      String levelNameText = "Level: " + levelInfo.levelName();
      d.setColor(Color.BLACK);
      d.drawText(width / 2 - 50, 15, levelNameText, 15);

      // Score
      String scoreText = "Score: " + scorecounter.getValue();
      d.drawText(30, 15, scoreText, 15);

      // Lives/Balls remaining
      String ballsText = "Balls: " + remainingballs.getValue();
      d.drawText(width - 100, 15, ballsText, 15);
   }

   @Override
   public void doOneFrame(DrawSurface d) {
      // Draw background from level info
      levelInfo.getBackground().drawOn(d);

      // Draw score bar and info
      drawScoreAndLevelName(d);

      // Draw all sprites
      sprites.drawAllOn(d);

      // Check for pause
      if (this.keyboard.isPressed("p")) {
         this.runner.run(new PauseScreen(this.keyboard));
      }

      // Notify all sprites that time has passed
      sprites.notifyAllTimePassed();

      // Check win condition (all blocks cleared)
      if (remainingblocks.getValue() == 0) {
         scorecounter.increase(100); // Bonus for clearing level
         this.running = false;
      }

      // Check lose condition (no balls left)
      if (remainingballs.getValue() == 0) {
         this.running = false;
      }
   }

   @Override
   public boolean shouldStop() {
      return !this.running;
   }

   public void run() {
      // Create balls at start of level
      this.createBallsOnTopOfPaddle();

      // Start the level
      this.running = true;

      // Use runner to run this animation
      this.runner.run(this);
   }
}