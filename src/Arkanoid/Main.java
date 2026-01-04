package Arkanoid;
import Arkanoid.Engine.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800,600);
        KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui);
        Arkanoid.GameLevel game = new GameLevel(800,600,runner,ks);
        game.initialize();
        runner.run(game);


    }
}