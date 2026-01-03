package Arkanoid.Engine;

import java.util.ArrayList;

public class BallCollection {
    private ArrayList<Ball> ballcollection = new ArrayList<>();

    public void addBall(Ball ball) {
        ballcollection.add(ball);
    }
    public void removeBall (Ball ball) {
        ballcollection.remove(ball);
    }
    public int get_amount() {
        ArrayList<Ball> copy = new ArrayList<>(ballcollection);
        return copy.size();
    }
}
