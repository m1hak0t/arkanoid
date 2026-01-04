package Arkanoid.Engine;

import Arkanoid.Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
public class AnimationRunner {
   private GUI gui;
   private int framesPerSecond = 60;
   private Sleeper sleeper = new Sleeper();
   public AnimationRunner(GUI gui_) {
      gui = gui_;
   }



   public void run(Animation animation) {
      int millisecondsPerFrame = 1000 / this.framesPerSecond;

      // 1. The loop checks the animation's own stopping condition
      while (!animation.shouldStop()) {
         long startTime = System.currentTimeMillis(); // record start time

         DrawSurface d = gui.getDrawSurface();

         // 2. THE MOST IMPORTANT LINE:
         // The runner hands the "blank canvas" to the animation and says:
         // "Draw yourself and update your logic for this one frame."
         animation.doOneFrame(d);

         // 3. Display what was drawn
         gui.show(d);

         // 4. Timing: Calculate how long the work took and sleep the rest
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         if (milliSecondLeftToSleep > 0) {
            this.sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
}
