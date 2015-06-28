package ru.tandemservice.test.task2.solution;

import ru.tandemservice.test.task2.IElement;

import java.util.List;

/**
 * класс ищущий свободные номера в исходном списке
 * В худшем случае O(n), в лучшем O(0).
 */
public class NumberFinder {
    final List<WrappedElement> elements;
    int number = Integer.MIN_VALUE;
    int index = 0;

    public NumberFinder(List<WrappedElement> elements) {
        this.elements = elements;
    }

    int get() {
        number++;
        if (index >= elements.size()) {
            //System.out.println("free number: " + number);
            return number;
        }
        for (; index < elements.size(); index++) {
            if (number != elements.get(index).element.getNumber())
                break;
            number++;
        }
        //System.out.println("free number: " + number);
        return number;
    }
}
