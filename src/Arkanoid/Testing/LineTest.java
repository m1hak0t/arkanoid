package Tests;
import GameEngine.Line;
import GameEngine.Point;

public class LineTest {

    // Helper class to manage test results and output
    static class TestResult {
        private String testName;

        public TestResult(String name, boolean result) {
            this.testName = name;
            System.out.println(name + ": " + (result ? "✅ PASSED" : "❌ FAILED"));
        }
    }

    // Tolerance for floating point comparisons
    private static final double EPSILON = 0.0001;

    // Helper function for comparing doubles
    public static boolean areClose(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public static void main(String[] args) {
        System.out.println("--- Starting Line Class Tests ---");

        // Set up standard points
        Point p1 = new Point(0, 0);
        Point p2 = new Point(10, 10);
        Point p3 = new Point(0, 10);
        Point p4 = new Point(10, 0);

        // -----------------------------------------------------
        // TEST GROUP 1: Constructor, Length, and Midpoint
        // -----------------------------------------------------
        System.out.println("\n--- Test Group 1: Basics ---");

        Line diagonal = new Line(p1, p2); // y = x
        Line horizontal = new Line(p3, p4); // y = -x + 10

        // 1.1 Test Length (0,0) to (10,10) -> sqrt(10^2 + 10^2) = sqrt(200) ≈ 14.142
        double expectedLength = Math.sqrt(200);
        boolean lengthPassed = areClose(diagonal.length(), expectedLength);
        new TestResult("Test 1.1 - Length", lengthPassed);

        // 1.2 Test Midpoint (0,0) to (10,10) -> (5, 5)
        Point actualMid = diagonal.middle();
        boolean midPassed = areClose(actualMid.getX(), 5.0) && areClose(actualMid.getY(), 5.0);
        new TestResult("Test 1.2 - Midpoint", midPassed);

        // 1.3 Test m and b coefficients (y = x, so m=1, b=0)
        boolean mbPassed = areClose(diagonal.getSlope(), 1.0) && areClose(diagonal.getIntercept(), 0.0);
        new TestResult("Test 1.3 - m/b Calculation", mbPassed);

        // -----------------------------------------------------
        // -----------------------------------------------------
        // TEST GROUP 2: Intersection Logic (`intersectionWith`)
        // -----------------------------------------------------
        System.out.println("\n--- Test Group 2: Intersections (y=x vs y=-x+10) ---");

        // 2.1 Standard Intersection (Expected: (5, 5))
        // Check the type returned by intersectionWith in your Line class.
        // We assume it returns a single Point or null.
        Point results2_1 = diagonal.intersectionWith(horizontal);

        boolean found2_1 = (results2_1 != null);
        boolean value2_1 = false;

        if (found2_1) {
            // FIX 1: Check both X and Y coordinates against the expected value (5.0)
            // FIX 2: Ensure your custom Point.getX() and Point.getY() return the correct type for comparison
            value2_1 = areClose(results2_1.getX(), 5.0) && areClose(results2_1.getY(), 5.0);
        }

        new TestResult("Test 2.1a - Found Intersection", found2_1);
        new TestResult("Test 2.1b - Intersection Value", value2_1);
    }
}