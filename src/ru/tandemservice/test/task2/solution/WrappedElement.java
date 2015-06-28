package ru.tandemservice.test.task2.solution;

import ru.tandemservice.test.task2.IElement;

/**
 * ������� ��� {@link IElement}.
 * <p/>
 * �������� ��������� �� ������� �������� ������ ���� ����� � ������� � �������� ������� �����.
 */
public class WrappedElement implements Comparable<WrappedElement> {

    final IElement element;
    WrappedElement patient;
    WrappedElement donor;
    boolean inList = false;

    public WrappedElement(IElement element) {
        this.element = element;
    }

    @Override
    public int compareTo(WrappedElement o) {
        return Integer.compare(element.getNumber(), o.element.getNumber());
    }
}
