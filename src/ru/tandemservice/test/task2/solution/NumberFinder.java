package ru.tandemservice.test.task2.solution;

import ru.tandemservice.test.task2.IElement;

import java.util.List;

/**
 * ����� ������ ��������� ������ � �������� ������
 * � ������ ������ O(n), � ������ O(0).
 */
public class NumberFinder {
    final List<IElement> elements;
    int number = Integer.MIN_VALUE;
    int index = 0;

    public NumberFinder(List<IElement> elements) {
        this.elements = elements;
    }

    int get() {
        number++;
        if (index >= elements.size()) {
            //System.out.println("free number: " + number);
            return number;
        }
        for (; index < elements.size(); index++) {
            if (number != elements.get(index).getNumber())
                break;
            number++;
        }
        //System.out.println("free number: " + number);
        return number;
    }
}
