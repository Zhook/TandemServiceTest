package ru.tandemservice.test.task2.solution1;


import ru.tandemservice.test.task2.IElement;
import ru.tandemservice.test.task2.solution1.numbersfinders.DifficultFinder;
import ru.tandemservice.test.task2.solution1.numbersfinders.FreeNumbersFinder;
import ru.tandemservice.test.task2.solution1.numbersfinders.SimpleFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FastSorter {

    /**
     * ����� ��������� O(n*log(n)), �� ������ O(n)
     * ��� "�������" ������ � ��������� n ��������� O(n).
     * ������ ������� 2*n ������� setupNumber ���� ������ ������������ �� ����������� ������� ������, �������� ������������ ��������.
     * � ������� 1.5*n ������� setupNumber.
     * <p/>
     * �� �������
     * <ul>
     * <li>�� ����������� </li>
     * <li>��������� ���������(���������� ��� ���������) ������� ������ ��� ������� ������ �� �������������</li>
     * </ul>
     * <p/>
     *
     * @param elements ����������� ������
     */
    public static void assignNumbers(final List<IElement> elements) {

        if (elements == null || elements.isEmpty()) {
            System.out.println("List empty");
            return;
        }

        // ��������� ��������������� ����� ��������� ������ � ���������� ���������� ����� int, ���������� ��������� ����� ��������
        // O(n*log(n))
        List<WrappedElement> sortedElements = makeSortedCopy(elements);

        // ��������� ��������� ������ ��������� �������
        // O(n)
        FreeNumbersFinder finder = getFinder(sortedElements);

        // ������ ������� � �������� ������� �� ������ �� ��������������.
        // ����� � ��������� �������� �������� �� ���������.
        // O(n)
        Iterator<WrappedElement> iterator = sortedElements.iterator();
        for (IElement patient : elements) {
            change(iterator.next(), patient, finder);
        }
    }

    /**
     * ����� ������ ����� ��������� ������ � ��������� � �� O(nlogn).
     * �������� ������������� {@link WrappedElement} ���������� ���� int � ��������� ������� ��������.
     * <p/>
     *
     * @param elements �������� ������ ���������
     * @return ��������������� ������
     */
    private static List<WrappedElement> makeSortedCopy(List<IElement> elements) {
        List<WrappedElement> sortedElements = new ArrayList<>(elements.size());
        for (IElement element : elements) sortedElements.add(new WrappedElement(element));
        Collections.sort(sortedElements);
        return sortedElements;
    }


    /**
     * ������� ���������� ������ ��������� ������� � ������.
     * <p/>
     *
     * @param sortedElements ������������� ������ ���������
     * @return �������� ��������� ��������� ������� � ������
     */
    private static FreeNumbersFinder getFinder(List<WrappedElement> sortedElements) {
        int number;

        //���������� � �������� ��������� �������:
        // ������������ ����� � ������ ������ �� Integer.MAX_VALUE.
        // ������� ������� ���������������� �������� �� ��������� ��������� �� ������� ������ �������������� ������ � ������
        number = sortedElements.get(sortedElements.size() - 1).initialNumber;
        if (number < Integer.MAX_VALUE - sortedElements.size()) {
            return new SimpleFinder(number + 1);
        }

        //������ ��������� ���������� �������:
        // ����������� ����� � ������ ������ �� Integer.MIN_VALUE.
        // ������� ������� ���������������� �������� �� ��������� ��������� Integer.MIN_VALUE
        // ����� ���� �� ������� ������� ���������������� � ����������� ��������:
        // ������ ���������� ����� ���������� ������������ �������.
        // ������� ������� ���������������� �������� �� ��������� ��������� ������� ����������
        number = Integer.MIN_VALUE;
        for (WrappedElement element : sortedElements) {
            if (element.initialNumber - number > sortedElements.size() + 1) {
                return new SimpleFinder(number + 1);
            }
            number = element.initialNumber;
        }

        //����������� �������.
        // ���� �������� ������ �� ����� ����� �������� ����������� �����������,
        // �� ��������� ������ ��������� ������� �� �������� ������� ������ � ���������.
        return new DifficultFinder(sortedElements);
    }

    /**
     * ����� ������ ����� �������� �� �������� ����� �������� �� �������������� ������.
     * ���� ����� �������� �������������� ������ �� ��� ������� �� ����� ������� �� ��� ����� �������� ��������� �����.
     * <p/>
     *
     * @param donor   ������� �������������� ������
     * @param patient ������� ��������� ������
     * @param finder  �������� ��������� ��������� �������
     */
    private static void change(WrappedElement donor, IElement patient, FreeNumbersFinder finder) {
        if (donor.element != patient) {

            int donorNumber = donor.initialNumber;
            int freeNumber = finder.getFreeNumber();

            if (donor.element.getNumber() == donor.initialNumber) {
                donor.element.setupNumber(freeNumber);
            }

            patient.setupNumber(donorNumber);
        }
    }
}
