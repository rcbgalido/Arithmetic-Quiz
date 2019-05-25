package arithmeticquiz;

import java.util.Random;

/**
 *
 * @author rcbgalido
 */
public class NumbersGenerator {

    protected int[] run(int operation) {
        
        Random random = new Random();
        int[] numbers = new int[10];
        
        switch (operation) {
            case Main.ADDITION:
                for (int a = 0; a < Main.TOTAL_NUMBERS_PER_ROUND; a++) {
                    numbers[a] = 10 + random.nextInt(90); //10-99
                }   break;
            case Main.SUBTRACTION:
                for (int a = 0; a < Main.TOTAL_NUMBERS_PER_ROUND; a++) {
                    if (a % 2 == 0) { //minuend
                        numbers[a] = 50 + random.nextInt(50); //50-99
                    } else { // subtrahend
                        numbers[a] = 10 + random.nextInt(40); //10-49
                    }
                }   break;
            case Main.MULTIPLICATION:
                for (int a = 0; a < Main.TOTAL_NUMBERS_PER_ROUND; a++) {
                    if (a % 2 == 0) { // larger factor
                        numbers[a] = 10 + random.nextInt(90); //10-99
                    } else { //smaller factor
                        numbers[a] = 2 + random.nextInt(8); //2-9
                    }
                }   break;
            case Main.DIVISION:
                for (int a = 0; a < Main.TOTAL_NUMBERS_PER_ROUND; a = a + 2) {
                    int triesCount = 0;
                    
                    numbers[a] = 10 + random.nextInt(90); //initial value of dividend, 10-99
                    numbers[a + 1] = 2 + random.nextInt(98); //initial value of divisor, 2-99
                    
                    // while dividend is not yet divisible by the divisor
                    while (numbers[a] % numbers[a + 1] != 0 || numbers[a] == numbers[a + 1]) { //
                        if (triesCount != 30) { // if not yet reached thirty (30) tries, generate new value of divisor
                            numbers[a + 1] = 2 + random.nextInt(98); //2-99
                            triesCount++;
                        } else { // if already reached thirty (30) tries, generate new value of dividend
                            numbers[a] = 10 + random.nextInt(90); //10-99
                            triesCount = 0;
                        }
                    }
                }   break;
            default:
                break;
        }
        
        return numbers;
    }

}
