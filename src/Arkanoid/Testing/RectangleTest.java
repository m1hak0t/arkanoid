package Arkanoid.Testing;

import Arkanoid.Shapes.Line;
import Arkanoid.Shapes.Point;
import Arkanoid.Shapes.Rectangle;

import java.util.List;

// You would typically put this Test class in a separate 'tests' package,
// but we'll place it here for simplicity.
public class RectangleTest {

    // Helper class to manage test results and output
    static class TestResult {
        private String testName;
        private boolean passed;

        public TestResult(String name, boolean result) {
            this.testName = name;
            this.passed = result;
            System.out.println(name + ": " + (result ? "✅ PASSED" : "❌ FAILED"));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Starting Rectangle Class Tests ---");
        
        // 1. Setup a Test Rectangle: 
        // Corner at (10, 10), Width=100, Height=50
        // Expected corners: (10, 10), (110, 10), (10, -40), (110, -40)
        Point upperLeft = new Point(10, 10);
        double width = 100;
        double height = 50;
        Rectangle rect = new Rectangle(upperLeft, width, height);
        
        // -----------------------------------------------------
        // TEST GROUP 1: Constructor and Basic Accessors
        // -----------------------------------------------------

        // 1.1 Test Dimensions
        new TestResult("Test 1.1 - Width", rect.getWidth() == 100);
        new TestResult("Test 1.2 - Height", rect.getHeight() == 50);

        // 1.3 Test Upper-Left Point
        Point actualUL = rect.getUpperLeft();
        boolean ulPassed = (actualUL.getX() == 10) && (actualUL.getY() == 10);
        new TestResult("Test 1.3 - UpperLeft Point", ulPassed);
        
        // -----------------------------------------------------
        // TEST GROUP 2: Intersection Points Logic
        // -----------------------------------------------------
        
        // --- Test 2.1: Line passing through the middle (2 intersections) ---
        
        // Create a horizontal line from (0, -10) to (120, -10)
        // It should intersect the left wall and the right wall.
        Line intersectingLine = new Line(new Point(0, 20), new Point(120, 20));
        List<Point> results2_1 = rect.intersectionPoints(intersectingLine);
        
        boolean foundTwo = results2_1.size() == 2;
        System.out.println(results2_1.size());
        new TestResult("Test 2.1a - Two Intersections Found", foundTwo);

// ...

// Check if the expected intersection points are present (10, 20) and (110, 20)
// This logic is simplified and corrected to be robust against order.
        boolean pointsCorrect = false;

// Define the two required points
        Point requiredP1 = new Point(10, 20);
        Point requiredP2 = new Point(110, 20);

    // Use a counter to track how many required points were found
        int foundRequiredCount = 0;

        if (foundTwo) {
            // Check if the list contains the required points
            for (Point p : results2_1) {
                // NOTE: This relies on your custom Point.equals() method
                // being correctly implemented to compare X and Y values (using EPSILON).
                if (p.equals(requiredP1) || p.equals(requiredP2)) {
                    foundRequiredCount++;
                }
            }

            // Both required points must be found (Count should be exactly 2)
            pointsCorrect = (foundRequiredCount == 2);
        }
        new TestResult("Test 2.1b - Intersection Values Correct", pointsCorrect);


        // --- Test 2.2: Line missing the rectangle (0 intersections) ---

        // Line far below the rectangle
        Line nonIntersectingLine = new Line(new Point(0, 100), new Point(200, 100));
        List<Point> results2_2 = rect.intersectionPoints(nonIntersectingLine);
        
        new TestResult("Test 2.2 - Zero Intersections Found", results2_2.isEmpty());

        // --- Test 2.3: Line touching a corner (1 intersection) ---
        
        // Line going straight through the upper-left corner (10, 10)
        Line cornerLine = new Line(new Point(0, 0), new Point(20, 20)); 
        List<Point> results2_3 = rect.intersectionPoints(cornerLine);

        // It should find 2 intersections (cealing and left wall) unless your Line.isIntersecting
        // logic is carefully designed to return only one point at the corner. 
        // For simplicity, we check if it found at least one point.
        boolean onePointFound = results2_3.size() >= 1; 
        
        new TestResult("Test 2.3a - Corner Intersection Found", onePointFound);

        // Check the value of the corner point
        boolean cornerValueCorrect = false;
        if (onePointFound) {
            Point cornerResult = results2_3.get(0);
            cornerValueCorrect = cornerResult.getX() == 10 && cornerResult.getY() == 10;
        }
        new TestResult("Test 2.3b - Corner Value Correct (10, 10)", cornerValueCorrect);

        System.out.println("--- Rectangle Class Tests Finished ---");
    }
}