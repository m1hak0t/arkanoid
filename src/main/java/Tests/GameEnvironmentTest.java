package Tests;

import GameEngine.*;
import Interfaces.Collidable;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Color;

public class GameEnvironmentTest {

    private GameEnvironment env;
    private static final double EPSILON = 0.0001;

    // Mock Collidable for testing
    private class MockCollidable implements Collidable {
        private Rectangle rect;
        private String name;

        public MockCollidable(Rectangle rect, String name) {
            this.rect = rect;
            this.name = name;
        }

        @Override
        public Rectangle getCollisionRectangle() {
            return rect;
        }

        @Override
        public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
            // Simple bounce - reverse velocity
            return new Velocity(-currentVelocity.getX(), -currentVelocity.getY());
        }

        public String getName() {
            return name;
        }
    }

    @Before
    public void setUp() {
        env = new GameEnvironment();
    }

    @Test
    public void testEmptyEnvironment() {
        // Test with no collidables
        Line trajectory = new Line(new Point(0, 0), new Point(100, 100));
        CollisionInfo collision = env.getClosestCollision(trajectory);

        assertNull("No collision should occur in empty environment", collision);
    }

    @Test
    public void testSingleCollidableHit() {
        // Add a rectangle in the path
        Rectangle rect = new Rectangle(new Point(50, 50), 20, 20);
        MockCollidable collidable = new MockCollidable(rect, "Block1");
        env.addCollidable(collidable);

        // Trajectory that hits the rectangle
        Line trajectory = new Line(new Point(0, 0), new Point(100, 100));
        CollisionInfo collision = env.getClosestCollision(trajectory);

        assertNotNull("Collision should be detected", collision);
        assertNotNull("Collision point should exist", collision.collisionPoint());
        assertEquals("Should hit the correct object", collidable, collision.collisionObject());
    }

    @Test
    public void testSingleCollidableMiss() {
        // Add a rectangle NOT in the path
        Rectangle rect = new Rectangle(new Point(200, 200), 20, 20);
        MockCollidable collidable = new MockCollidable(rect, "Block1");
        env.addCollidable(collidable);

        // Trajectory that misses the rectangle
        Line trajectory = new Line(new Point(0, 0), new Point(50, 50));
        CollisionInfo collision = env.getClosestCollision(trajectory);

        assertNull("No collision should occur when trajectory misses", collision);
    }

    @Test
    public void testMultipleCollidablesClosestFirst() {
        // Add multiple rectangles at different distances
        Rectangle rect1 = new Rectangle(new Point(30, 30), 10, 10);
        Rectangle rect2 = new Rectangle(new Point(60, 60), 10, 10);
        Rectangle rect3 = new Rectangle(new Point(90, 90), 10, 10);

        MockCollidable collidable1 = new MockCollidable(rect1, "Close");
        MockCollidable collidable2 = new MockCollidable(rect2, "Medium");
        MockCollidable collidable3 = new MockCollidable(rect3, "Far");

        env.addCollidable(collidable3); // Add in random order
        env.addCollidable(collidable1);
        env.addCollidable(collidable2);

        // Trajectory that passes through all three
        Line trajectory = new Line(new Point(0, 0), new Point(100, 100));
        CollisionInfo collision = env.getClosestCollision(trajectory);

        assertNotNull("Collision should be detected", collision);
        assertEquals("Should hit the CLOSEST object", collidable1, collision.collisionObject());
    }

    @Test
    public void testMultipleCollidablesSomeInPath() {
        // Add rectangles, only some in the trajectory path
        Rectangle rect1 = new Rectangle(new Point(50, 50), 10, 10);
        Rectangle rect2 = new Rectangle(new Point(200, 200), 10, 10); // Not in path
        Rectangle rect3 = new Rectangle(new Point(80, 80), 10, 10);

        MockCollidable collidable1 = new MockCollidable(rect1, "InPath1");
        MockCollidable collidable2 = new MockCollidable(rect2, "NotInPath");
        MockCollidable collidable3 = new MockCollidable(rect3, "InPath2");

        env.addCollidable(collidable2);
        env.addCollidable(collidable3);
        env.addCollidable(collidable1);

        // Trajectory from (0,0) to (100,100)
        Line trajectory = new Line(new Point(0, 0), new Point(100, 100));
        CollisionInfo collision = env.getClosestCollision(trajectory);

        assertNotNull("Collision should be detected", collision);
        assertEquals("Should hit collidable1 (closest in path)", collidable1, collision.collisionObject());
    }

    @Test
    public void testHorizontalTrajectory() {
        // Test horizontal movement
        Rectangle rect = new Rectangle(new Point(50, 10), 10, 30);
        MockCollidable collidable = new MockCollidable(rect, "Wall");
        env.addCollidable(collidable);

        // Horizontal trajectory at y=20
        Line trajectory = new Line(new Point(0, 20), new Point(100, 20));
        CollisionInfo collision = env.getClosestCollision(trajectory);
    }
}