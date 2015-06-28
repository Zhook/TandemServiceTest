package ru.tandemservice.test.task2;


import ru.tandemservice.test.task2.solution.Sorter;

import java.util.*;

public class Test2 {

    final static LinkedList<Long> RESULT = new LinkedList<>();
    final static int SIZE = 1_000_000;
    final static int ROUND = 10;

    public static void main(String args[]) {
        Sorter sorter = new Sorter();

        for (int i = 0; i < ROUND; i++) {
            final ElementExampleImpl.Context context = new ElementExampleImpl.Context();
            final List<IElement> elements = generate(SIZE, context);
//print2(elements);
            long start = System.currentTimeMillis();

            sorter.assignNumbers(elements);
//            print2(elements);
            long time = System.currentTimeMillis() - start;
            RESULT.add(time);
            System.out.println("work time:\t" + String.valueOf(time) + "\tOperation Count:\t" + String.valueOf(context.getOperationCount()));
        }

        long result = 0;
        for (Long l : RESULT) {
            result += l;
        }
        System.out.println("average work time: " + String.valueOf(result / ROUND) + "ms");
    }

    static List<IElement> generate(int size, ElementExampleImpl.Context context) {
        int[] numbers = new int[10 * size];
        for (int i = 0; i < 10 * size; i++) {
            numbers[i] = Integer.MIN_VALUE + i * (2 * (Integer.MAX_VALUE / size - 1));
        }

//        int[] numbers = new int[size];
//        for (int i = 0; i < size; i++) {
//            numbers[i] = i;
//        }

        shuffleArray(numbers);

        List<IElement> elements = new ArrayList<>();

        for (int i = 0; i < size; i++)
            elements.add(new ElementExampleImpl(context, numbers[i]));


        return Collections.unmodifiableList(elements);
    }

    static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void print2(List<IElement> elements) {
        for (IElement element : elements) System.out.print(element.getNumber() + "\t");
        System.out.println();
    }
}
