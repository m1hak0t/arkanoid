package Arkanoid.Engine;

public class Counter {
   private int value = 0;
   // add number to current count.
   public void increase(int number) {
      value += number;
   }
   // subtract number from current count.
   public void decrease(int number) {
      value -= number;
   }
   // get current count.
   public int getValue() {
      return value;
   }
}