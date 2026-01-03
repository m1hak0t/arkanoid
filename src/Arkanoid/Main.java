package Arkanoid;
import biuoop.GUI;
import biuoop.Sleeper;

import Arkanoid.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Arkanoid.Game game = new Game(800,600);
        game.initialize();
        game.run();
    }
}