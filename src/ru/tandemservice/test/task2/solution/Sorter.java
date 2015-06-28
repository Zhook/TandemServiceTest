package ru.tandemservice.test.task2.solution;


import ru.tandemservice.test.task2.ElementExampleImpl;
import ru.tandemservice.test.task2.IElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Sorter {

    /**
     * �������� ����� ������ �� ������� ����������� �����.
     * � ������� ��������� �������� ������� �� 1�� ��������� �����.
     * <p>
     * ����� ��������� O(n*log(n)), �� ������ O(n)
     * ��� "�������" ������ � ��������� n ��������� O(n).
     * <p>
     * ���������� ������� setupNumber (n + ���������� �������).
     * ������ ������� 1.5*n ������� setupNumber ���� ������ ������� �������� ������ ���� ���������.
     * ������ ������� ��������� ���������� n+1 ������� setupNumber, ���� ���� ����������� ������� �����.
     * <p>
     *
     * @param elements �������� ������
     */
    public void assignNumbers(final List<IElement> elements) {

//      �������� ������������� � �� �������������� ����� ������ � �������� ���������
        List<WrappedElement> rawElements = makeCopy(elements);
        List<WrappedElement> sortedElements = new ArrayList<>(rawElements);
        Collections.sort(sortedElements);

//      ����� ���� ����� � ���������� ����� ������� (���������� �������)
        Iterator<WrappedElement> sortedIterator = sortedElements.iterator();
        Iterator<WrappedElement> rawIterator = rawElements.iterator();
        while (sortedIterator.hasNext() && rawIterator.hasNext()) {
            WrappedElement sortedElement = sortedIterator.next();
            WrappedElement rawElement = rawIterator.next();
            if (sortedElement != rawElement) {
                sortedElement.patient = rawElement;
                rawElement.donor = sortedElement;
            }
        }

//      ������ �������
        NumberFinder finder = new NumberFinder(sortedElements);
        int freeNumber = finder.get();
        for (WrappedElement wrappedElement : sortedElements) {
            if (!wrappedElement.sorted) {
                if (wrappedElement.donor != null) {
                    assignChain(wrappedElement, freeNumber);
                }
            }
        }
    }

    /**
     * ����� ������ ����� ��������� ������ � ��������� � �� O(nlogn).
     * �������� ������������� {@link WrappedElement}
     * <p>
     *
     * @param elements �������� ������ ���������
     * @return ����� ��������� {@link WrappedElement} ���������
     */
    private ArrayList<WrappedElement> makeCopy(List<IElement> elements) {
        ArrayList<WrappedElement> wrappedElements = new ArrayList<>(elements.size());
        for (IElement element : elements) {
            wrappedElements.add(new WrappedElement(element));
        }
        return wrappedElements;
    }

    /**
     * ������� �������� ������������� ��������� �����.
     * ���������� �������� ������������� ���������� �����.
     * � ��� ���� �� �������� � ���������� ��������
     * <p>
     *
     * @param starter    ������ ������� ������� � �������� ���������� ������
     * @param freeNumber ��������� ����� ��� ������
     */
    public void assignChain(WrappedElement starter, int freeNumber) {
        WrappedElement next = starter;
        do {
            int buffNumber = next.element.getNumber();
            write(next, freeNumber);
            freeNumber = buffNumber;
            next = next.patient;
        } while (next != starter);
        write(next, freeNumber);
    }

    /**
     * ����� ��� ������� ����������� ������
     * <p>
     *
     * @param w      ������� ��� ������ ������
     * @param number ����� ��� ������
     */
    private void write(WrappedElement w, int number) {
        w.element.setupNumber(number);
        w.sorted = true;
    }


}
