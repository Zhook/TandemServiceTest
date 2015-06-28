package ru.tandemservice.test.task2.solution;

import ru.tandemservice.test.task2.IElement;

/**
 * Обертка над {@link IElement}.
 * <p/>
 * Содержит указатели на элемент которому отдаст свой номер и элемент у которого заберет номер.
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
