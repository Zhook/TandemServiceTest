package ru.tandemservice.test.task2.solution;


import ru.tandemservice.test.task2.ElementExampleImpl;
import ru.tandemservice.test.task2.IElement;

import java.util.*;

public class Sorter {

    /**
     * Алгоритм делит список на цепочки непрерывных замен.
     * В среднем несколько десятков цепочек на 1кк случайных чисел.
     * Возможно распараллеливание, но текущая реализация {@link IElement}({@link ElementExampleImpl}) не потокозащищена.
     * <p/>
     * Общая сложность O(n*log(n)), по памяти O(n)
     * При "дорогой" записи и небольших n сложность O(n).
     * <p/>
     * Количество вызовов setupNumber (n + количество цепочек).
     * Худший вариант 1.5*n вызовов setupNumber если список требует порпарную замену всех элементов.
     * Лучший вариант требующий сортировки n+1 вызовов setupNumber, если одна непрерывная цепочка замен.
     * <p/>
     *
     * @param elements исходный список
     */
    public void assignNumbers(final List<IElement> elements) {

//      Создание сортированной и не сорстированной копий списка с оберткой элементов
        List<WrappedElement> rawElements = makeCopy(elements);
        List<WrappedElement> sortedElements = new ArrayList<>(rawElements);
        Collections.sort(sortedElements);

//      Поиск всех целей и источников замен номеров
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

//      создается список стартовых элементов цепей
        List<WrappedElement> megaList = makeChains(sortedElements);

//      запускает замену в цепочках, место для многопоточности
        NumberFinder finder = new NumberFinder(elements);
        for (WrappedElement starter : megaList) {
            assignChain(starter, finder.get());
        }
    }

    /**
     * Метод делает копию исходного списка и сортирует её за O(nlogn).
     * Элементы оборачиваются {@link WrappedElement}
     * <p/>
     *
     * @param elements исходный список элементов
     * @return копия обернутых {@link WrappedElement} элементов
     */
    private ArrayList<WrappedElement> makeCopy(List<IElement> elements) {
        ArrayList<WrappedElement> wrappedElements = new ArrayList<>(elements.size());
        for (IElement element : elements) {
            wrappedElements.add(new WrappedElement(element));
        }
        return wrappedElements;
    }

    /**
     * @param wrappedElements список всех элементов
     * @return список начальных элементов всех цепочек
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
     * Первому элементу присваивается свободный номер.
     * Следующему элементу присваивается предыдущий номер.
     * И так пока не вернется к стартовому элементу
     * <p/>
     *
     * @param starter    первый элемент цепочки с которого начинается замена
     * @param freeNumber свободный номер для замены
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
     * Метод для удоства логирования ошибок
     * <p/>
     *
     * @param w      елемент для замены номера
     * @param number номер для замены
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
