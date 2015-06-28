package ru.tandemservice.test.task2.solution1.numbersfinders;


import ru.tandemservice.test.task2.solution1.WrappedElement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Механизм выдачи номеров с пулом
 */
public class DifficultFinder implements FreeNumbersFinder {

    LinkedList<Integer> pool = new LinkedList<>();

    /**
     * Конструктор заполняет пул номеров за O(n) и жрет памяти O(n)
     *
     * @param sortedElements сортированный список эелементов
     */
    public DifficultFinder(List<WrappedElement> sortedElements) {
        int number = Integer.MIN_VALUE;

        Iterator<WrappedElement> iterator = sortedElements.iterator();
        int element = iterator.next().initialNumber;

        while (number < Integer.MAX_VALUE && pool.size() < sortedElements.size()) {
            if (number < element) {         //если элемент не достигнут номер добавляется в пул
                pool.add(number);
            } else if (number == element) { //если элемент достигнут, берется следующий элемент
                if (iterator.hasNext()) {
                    element = iterator.next().initialNumber;
                }
            } else {                        //Больше элементов нет. Номера добавляются до заполнени пула.
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