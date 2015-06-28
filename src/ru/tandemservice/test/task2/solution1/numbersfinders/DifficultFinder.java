package ru.tandemservice.test.task2.solution1.numbersfinders;


import ru.tandemservice.test.task2.solution1.WrappedElement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * �������� ������ ������� � �����
 */
public class DifficultFinder implements FreeNumbersFinder {

    LinkedList<Integer> pool = new LinkedList<>();

    /**
     * ����������� ��������� ��� ������� �� O(n) � ���� ������ O(n)
     *
     * @param sortedElements ������������� ������ ����������
     */
    public DifficultFinder(List<WrappedElement> sortedElements) {
        int number = Integer.MIN_VALUE;

        Iterator<WrappedElement> iterator = sortedElements.iterator();
        int element = iterator.next().initialNumber;

        while (number < Integer.MAX_VALUE && pool.size() < sortedElements.size()) {
            if (number < element) {         //���� ������� �� ��������� ����� ����������� � ���
                pool.add(number);
            } else if (number == element) { //���� ������� ���������, ������� ��������� �������
                if (iterator.hasNext()) {
                    element = iterator.next().initialNumber;
                }
            } else {                        //������ ��������� ���. ������ ����������� �� ��������� ����.
                pool.add(number);
            }
            number++;
        }
    }

    @Override
    public int getFreeNumber() {
        return pool.removeFirst();
    }
}