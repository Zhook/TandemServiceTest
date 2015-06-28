package ru.tandemservice.test.task2.solution;


import ru.tandemservice.test.task2.ElementExampleImpl;
import ru.tandemservice.test.task2.IElement;

import java.util.*;

public class Sorter {

    /**
     * �������� ����� ������ �� ������� ����������� �����.
     * � ������� ��������� �������� ������� �� 1�� ��������� �����.
     * �������� �����������������, �� ������� ���������� {@link IElement}({@link ElementExampleImpl}) �� ��������������.
     * <p/>
     * ����� ��������� O(n*log(n)), �� ������ O(n)
     * ��� "�������" ������ � ��������� n ��������� O(n).
     * <p/>
     * ���������� ������� setupNumber (n + ���������� �������).
     * ������ ������� 1.5*n ������� setupNumber ���� ������ ������� ��������� ������ ���� ���������.
     * ������ ������� ��������� ���������� n+1 ������� setupNumber, ���� ���� ����������� ������� �����.
     * <p/>
     *
     * @param elements �������� ������
     */
    public void assignNumbers(final List<IElement> elements) {

//      �������� ������������� � �� �������������� ����� ������ � �������� ���������
        List<WrappedElement> rawElements = makeCopy(elements);
        List<WrappedElement> sortedElements = new ArrayList<>(rawElements);
        Collections.sort(sortedElements);

//      ����� ���� ����� � ���������� ����� �������
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

//      ��������� ������ ��������� ��������� �����
        List<WrappedElement> megaList = makeChains(sortedElements);

//      ��������� ������ � ��������, ����� ��� ���������������
        NumberFinder finder = new NumberFinder(elements);
        for (WrappedElement starter : megaList) {
            assignChain(starter, finder.get());
        }
    }

    /**
     * ����� ������ ����� ��������� ������ � ��������� � �� O(nlogn).
     * �������� ������������� {@link WrappedElement}
     * <p/>
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
     * @param wrappedElements ������ ���� ���������
     * @return ������ ��������� ��������� ���� �������
     */
    private List<WrappedElement> makeChains(List<WrappedElement> wrappedElements) {
        LinkedList<WrappedElement> megaList = new LinkedList<>();
        for (WrappedElement wrappedElement : wrappedElements) {
            WrappedElement element = wrappedElement;
            if (!element.inList) {
                if (element.donor != null) {
                    while (!element.donor.inList) {
                        element.donor.inList = true;
                        element = element.donor;
                    }
                    megaList.add(element);
                }
            }
        }
        return megaList;
    }

    /**
     * ������� �������� ������������� ��������� �����.
     * ���������� �������� ������������� ���������� �����.
     * � ��� ���� �� �������� � ���������� ��������
     * <p/>
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
     * <p/>
     *
     * @param w      ������� ��� ������ ������
     * @param number ����� ��� ������
     */
    private void write(WrappedElement w, int number) {
        w.element.setupNumber(number);

//        int buffNumber = w.element.getNumber();
//        try {
//            w.element.setupNumber(number);
//        } catch (IllegalStateException e) {
//            System.out.println("Error!");
//            System.out.println("Element-" + buffNumber + ", newNumber-" + number);
//        }
    }


}
