package Arkanoid;

import Arkanoid.Engine.AnimationRunner;
import Arkanoid.Engine.Counter;
import Arkanoid.Engine.EndScreen;
import Arkanoid.Interfaces.Animation;
import Arkanoid.Interfaces.LevelInformation;
import Arkanoid.Levels.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Main {
    public static void main(String[] args) {
        // Create GUI and dependencies
        GUI gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui);

        // Create score counter (persists across levels)
        Counter score = new Counter();

        // Test with a single level
        LevelInformation level1 = new DirectHit();

        GameLevel game = new GameLevel(level1, ks, runner, score, 800, 600);
        game.initialize();
        game.run();

        // Enter the 4th level

        LevelInformation level4 = new FinalFour();
        GameLevel game4 = new GameLevel(level4, ks, runner, score, 800, 600);
        game4.initialize();
        game4.run();

        //If the player exit 4 levels -> game over
        Animation EndScreen = new EndScreen(ks, score.getValue());
        runner.run(EndScreen);

        System.out.println("Game Over! Final Score: " + score.getValue());

        gui.close();
    }
}