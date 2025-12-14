package Tests;
import GameEngine.*;
import GameEngine.Line;
import GameEngine.Point;

public class ClosestIntersectionTest {

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
        System.out.println("--- Starting Closest Intersection Tests ---\n");
        
        // Setup: Rectangle at (10, 10) with width=100, height=50
        // Corners: (10,10), (110,10), (10,60), (110,60)
        Point upperLeft = new Point(10, 10);
        Rectangle rect = new Rectangle(upperLeft, 100, 50);
        
        // -----------------------------------------------------
        // TEST 1: Line intersecting twice - closest to start
        // -----------------------------------------------------
        System.out.println("TEST 1: Horizontal line through rectangle");
        // Line from (0, 30) to (120, 30)
        // Should intersect at (10, 30) and (110, 30)
        // Closest to start (0, 30) is (10, 30)
        Line line1 = new Line(0, 30, 120, 30);
        Point closest1 = line1.closestIntersectionToStartOfLine(rect);
        
        boolean test1Exists = (closest1 != null);
        new TestResult("Test 1a - Result exists", test1Exists);
        
        if (test1Exists) {
            boolean test1Correct = Math.abs(closest1.getX() - 10) < 0.01 && 
                                   Math.abs(closest1.getY() - 30) < 0.01;
            new TestResult("Test 1b - Closest point is (10, 30)", test1Correct);
            System.out.println("  Expected: (10.0, 30.0)");
            System.out.println("  Got: (" + closest1.getX() + ", " + closest1.getY() + ")\n");
        }
        
        // -----------------------------------------------------
        // TEST 2: Line from inside rectangle going out
        // -----------------------------------------------------
        System.out.println("TEST 2: Line starting inside rectangle");
        // Line from (50, 30) [inside] to (150, 30) [outside]
        // Should only intersect right wall at (110, 30)
        Line line2 = new Line(50, 30, 150, 30);
        Point closest2 = line2.closestIntersectionToStartOfLine(rect);
        
        boolean test2Exists = (closest2 != null);
        new TestResult("Test 2a - Result exists", test2Exists);
        
        if (test2Exists) {
            boolean test2Correct = Math.abs(closest2.getX() - 110) < 0.01 && 
                                   Math.abs(closest2.getY() - 30) < 0.01;
            new TestResult("Test 2b - Closest point is (110, 30)", test2Correct);
            System.out.println("  Expected: (110.0, 30.0)");
            System.out.println("  Got: (" + closest2.getX() + ", " + closest2.getY() + ")\n");
        }
        
        // -----------------------------------------------------
        // TEST 3: Line missing the rectangle entirely
        // -----------------------------------------------------
        System.out.println("TEST 3: Line missing rectangle");
        // Line from (0, 0) to (5, 0) - completely outside
        Line line3 = new Line(0, 0, 5, 0);
        Point closest3 = line3.closestIntersectionToStartOfLine(rect);
        
        boolean test3Correct = (closest3 == null);
        new TestResult("Test 3 - Returns null for no intersection", test3Correct);
        System.out.println("  Expected: null");
        System.out.println("  Got: " + closest3 + "\n");
        
        // -----------------------------------------------------
        // TEST 4: Diagonal line - two intersections
        // -----------------------------------------------------
        System.out.println("TEST 4: Diagonal line");
        // Line from (0, 0) to (120, 70)
        // Should intersect top edge and right edge
        // Need to calculate which is closer to (0, 0)
        Line line4 = new Line(0, 0, 120, 70);
        Point closest4 = line4.closestIntersectionToStartOfLine(rect);
        
        boolean test4Exists = (closest4 != null);
        new TestResult("Test 4a - Result exists", test4Exists);
        
        if (test4Exists) {
            double dist = line4.start().distance(closest4);
            System.out.println("  Closest intersection at: (" + closest4.getX() + ", " + closest4.getY() + ")");
            System.out.println("  Distance from start: " + dist + "\n");
        }
        
        // -----------------------------------------------------
        // TEST 5: Vertical line
        // -----------------------------------------------------
        System.out.println("TEST 5: Vertical line through rectangle");
        // Line from (50, 0) to (50, 100)
        // Should intersect at (50, 10) [top] and (50, 60) [bottom]
        // Closest to (50, 0) is (50, 10)
        Line line5 = new Line(50, 0, 50, 100);
        Point closest5 = line5.closestIntersectionToStartOfLine(rect);
        
        boolean test5Exists = (closest5 != null);
        new TestResult("Test 5a - Result exists", test5Exists);
        
        if (test5Exists) {
            boolean test5Correct = Math.abs(closest5.getX() - 50) < 0.01 && 
                                   Math.abs(closest5.getY() - 10) < 0.01;
            new TestResult("Test 5b - Closest point is (50, 10)", test5Correct);
            System.out.println("  Expected: (50.0, 10.0)");
            System.out.println("  Got: (" + closest5.getX() + ", " + closest5.getY() + ")\n");
        }
        
        // -----------------------------------------------------
        // TEST 6: Line touching corner
        // -----------------------------------------------------
        System.out.println("TEST 6: Line touching corner");
        // Line from (0, 0) to (20, 20) passes through corner (10, 10)
        Line line6 = new Line(0, 0, 20, 20);
        Point closest6 = line6.closestIntersectionToStartOfLine(rect);
        
        boolean test6Exists = (closest6 != null);
        new TestResult("Test 6a - Result exists", test6Exists);
        
        if (test6Exists) {
            boolean test6Correct = Math.abs(closest6.getX() - 10) < 0.01 && 
                                   Math.abs(closest6.getY() - 10) < 0.01;
            new TestResult("Test 6b - Corner point is (10, 10)", test6Correct);
            System.out.println("  Expected: (10.0, 10.0)");
            System.out.println("  Got: (" + closest6.getX() + ", " + closest6.getY() + ")\n");
        }
        
        // -----------------------------------------------------
        // TEST 7: Line going away from rectangle
        // -----------------------------------------------------
        System.out.println("TEST 7: Line segment going away from rectangle");
        // Line from (0, 30) to (5, 30) - doesn't reach rectangle
        Line line7 = new Line(0, 30, 5, 30);
        Point closest7 = line7.closestIntersectionToStartOfLine(rect);
        
        boolean test7Correct = (closest7 == null);
        new TestResult("Test 7 - Returns null (line too short)", test7Correct);
        System.out.println("  Expected: null");
        System.out.println("  Got: " + closest7 + "\n");
        
        System.out.println("--- Closest Intersection Tests Finished ---");
    }
}