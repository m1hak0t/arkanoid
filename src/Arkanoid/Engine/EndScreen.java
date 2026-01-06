package Arkanoid.Engine;


import Arkanoid.Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private int score;

    public EndScreen(KeyboardSensor k, int score) {
        this.keyboard = k;
        this.stop = false;
        this.score = score;
    }
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "Game over! Final Score :  " + score, 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) { this.stop = true; }
    }
    public boolean shouldStop() { return this.stop; }
}