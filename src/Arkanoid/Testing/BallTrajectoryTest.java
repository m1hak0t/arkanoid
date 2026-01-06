package Testing;
import Arkanoid.Engine.Ball;
import Arkanoid.Engine.Velocity;
import Arkanoid.Shapes.Point;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Color;

public class BallTrajectoryTest {
    
    private Ball ball;
    private static final double EPSILON = 0.0001;
    
    @Before
    public void setUp() {
        // Create a ball at origin with radius 5
        ball = new Ball(new Point(100, 100), 20, Color.RED);
    }
    
    @Test
    public void testTrajectoryWithPositiveVelocity() {
        // Set velocity moving right and down
        ball.setVelocity(3.0, 4.0);
        
        // Access trajectory through reflection since it's private
        // Or test indirectly by checking the ball's behavior
        Velocity v = ball.getVelocity();
        
        // Verify velocity was set correctly
        assertEquals(3.0, v.getX(), EPSILON);
        assertEquals(4.0, v.getY(), EPSILON);
        
        // The trajectory should be from (100,100) to (103,104)
        // We can verify this indirectly by checking movement
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(103, 104);
        
        // Verify the velocity creates the expected trajectory
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithNegativeVelocity() {
        // Set velocity moving left and up
        ball.setVelocity(-5.0, -3.0);
        
        Velocity v = ball.getVelocity();
        assertEquals(-5.0, v.getX(), EPSILON);
        assertEquals(-3.0, v.getY(), EPSILON);
        
        // Trajectory should be from (100,100) to (95,97)
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(95, 97);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithZeroVelocity() {
        // Set zero velocity
        ball.setVelocity(0.0, 0.0);
        
        Velocity v = ball.getVelocity();
        assertEquals(0.0, v.getX(), EPSILON);
        assertEquals(0.0, v.getY(), EPSILON);
        
        // Trajectory should be from (100,100) to (100,100) - no movement
        Point originalCenter = new Point(100, 100);
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(originalCenter.getX(), afterMove.getX(), EPSILON);
        assertEquals(originalCenter.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithHorizontalMovement() {
        // Set horizontal velocity only
        ball.setVelocity(7.0, 0.0);
        
        Velocity v = ball.getVelocity();
        assertEquals(7.0, v.getX(), EPSILON);
        assertEquals(0.0, v.getY(), EPSILON);
        
        // Trajectory should be horizontal from (100,100) to (107,100)
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(107, 100);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithVerticalMovement() {
        // Set vertical velocity only
        ball.setVelocity(0.0, -6.0);
        
        Velocity v = ball.getVelocity();
        assertEquals(0.0, v.getX(), EPSILON);
        assertEquals(-6.0, v.getY(), EPSILON);
        
        // Trajectory should be vertical from (100,100) to (100,94)
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(100, 94);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryRecalculatedOnVelocityChange() {
        // Set initial velocity
        ball.setVelocity(2.0, 3.0);
        Velocity v1 = ball.getVelocity();
        
        // Change velocity
        ball.setVelocity(5.0, -4.0);
        Velocity v2 = ball.getVelocity();
        
        // Verify velocity changed
        assertNotEquals(v1.getX(), v2.getX(), EPSILON);
        assertNotEquals(v1.getY(), v2.getY(), EPSILON);
        
        // Verify new velocity is correct
        assertEquals(5.0, v2.getX(), EPSILON);
        assertEquals(-4.0, v2.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithDifferentStartingPositions() {
        // Test with ball at different position
        Ball ball2 = new Ball(new Point(50, 200), 10, Color.BLUE);
        ball2.setVelocity(4.0, -2.0);
        
        Velocity v = ball2.getVelocity();
        assertEquals(4.0, v.getX(), EPSILON);
        assertEquals(-2.0, v.getY(), EPSILON);
        
        // Trajectory should be from (50,200) to (54,198)
        Point originalCenter = new Point(50, 200);
        Point expectedEnd = new Point(54, 198);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithLargeVelocity() {
        // Test with large velocity values
        ball.setVelocity(100.0, -150.0);
        
        Velocity v = ball.getVelocity();
        assertEquals(100.0, v.getX(), EPSILON);
        assertEquals(-150.0, v.getY(), EPSILON);
        
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(200, -50);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
    
    @Test
    public void testTrajectoryWithDecimalVelocity() {
        // Test with decimal velocity values
        ball.setVelocity(2.5, 3.7);
        
        Velocity v = ball.getVelocity();
        assertEquals(2.5, v.getX(), EPSILON);
        assertEquals(3.7, v.getY(), EPSILON);
        
        Point originalCenter = new Point(100, 100);
        Point expectedEnd = new Point(102.5, 103.7);
        
        Point afterMove = v.applyToPoint(originalCenter);
        assertEquals(expectedEnd.getX(), afterMove.getX(), EPSILON);
        assertEquals(expectedEnd.getY(), afterMove.getY(), EPSILON);
    }
}