package ru.tandemservice.test.task2.solution1;


import ru.tandemservice.test.task2.IElement;

/**
 * ������� ��� {@link IElement}. ������������� �������� �������� ����� �������� �� ���������� �� ����� ������.
 */
public class WrappedElement implements Comparable<WrappedElement> {

    public final int initialNumber;
    public final IElement element;

    public WrappedElement(IElement element) {
        this.element = element;
        initialNumber = element.getNumber();
    }

    @Override
    public int compareTo(WrappedElement o) {
        return Integer.compare(initialNumber, o.initialNumber);
    }
}
