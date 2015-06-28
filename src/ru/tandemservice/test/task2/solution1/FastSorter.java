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
     * Общая сложность O(n*log(n)), по памяти O(n)
     * При "дорогой" записи и небольших n сложность O(n).
     * Худший вариант 2*n вызовов setupNumber если список отсортирован за исключенией первого номера, имеющего максимальное значение.
     * В среднем 1.5*n вызовов setupNumber.
     * <p/>
     * Из минусов
     * <ul>
     * <li>не паралелится </li>
     * <li>изменения оригинала(добавление или изменение) вызовет ошибку или оставит список не сортированным</li>
     * </ul>
     * <p/>
     *
     * @param elements сортируемый список
     */
    public static void assignNumbers(final List<IElement> elements) {

        if (elements == null || elements.isEmpty()) {
            System.out.println("List empty");
            return;
        }

        // получение отсортированной копии исходного списка с элементами обернутыми полем int, содержащим начальный номер элемента
        // O(n*log(n))
        List<WrappedElement> sortedElements = makeSortedCopy(elements);

        // получение механизма поиска незанятых номеров
        // O(n)
        FreeNumbersFinder finder = getFinder(sortedElements);

        // Замена номеров в исходном массиве на номера из сортированного.
        // Номер у исходного элемента меняется на свободный.
        // O(n)
        Iterator<WrappedElement> iterator = sortedElements.iterator();
        for (IElement patient : elements) {
            change(iterator.next(), patient, finder);
        }
    }

    /**
     * Метод делает копию исходного списка и сортирует её за O(nlogn).
     * Элементы оборачиваются {@link WrappedElement} содержащим поле int с начальным номером элемента.
     * <p/>
     *
     * @param elements исходный список элементов
     * @return отсортированный список
     */
    private static List<WrappedElement> makeSortedCopy(List<IElement> elements) {
        List<WrappedElement> sortedElements = new ArrayList<>(elements.size());
        for (IElement element : elements) sortedElements.add(new WrappedElement(element));
        Collections.sort(sortedElements);
        return sortedElements;
    }


    /**
     * Фабрика механизмов поиска свободных номеров в списке.
     * <p/>
     *
     * @param sortedElements сортированный массив элементов
     * @return механизм получения свободных номеров в списке
     */
    private static FreeNumbersFinder getFinder(List<WrappedElement> sortedElements) {
        int number;

        //Прекрасный и наиболее вероятный вариант:
        // Максимальный номер в списке далеко от Integer.MAX_VALUE.
        // Берется простой инкриментирующий механизм со стартовым значением на единицу больше максималльного номера в списке
        number = sortedElements.get(sortedElements.size() - 1).initialNumber;
        if (number < Integer.MAX_VALUE - sortedElements.size()) {
            return new SimpleFinder(number + 1);
        }

        //Второй вероятный прекрасный вариант:
        // Минимальный номер в списке далеко от Integer.MIN_VALUE.
        // Берется простой инкриментирующий механизм со стартовым значением Integer.MIN_VALUE
        // Далее если не повезло средней отвратительности и вероятности варианты:
        // Ищется промежуток между элементами достаточного размера.
        // Берется простой инкриментирующий механизм со стартовым значением минимум промежутка
        number = Integer.MIN_VALUE;
        for (WrappedElement element : sortedElements) {
            if (element.initialNumber - number > sortedElements.size() + 1) {
                return new SimpleFinder(number + 1);
            }
            number = element.initialNumber;
        }

        //Невероятный вариант.
        // Если исходный список не имеет между номерами достаточных промежутков,
        // то создается список доступных номеров из которого берутся номера с удалением.
        return new DifficultFinder(sortedElements);
    }

    /**
     * Метод меняет номер элемента на исходный номер элемента из сортированного списка.
     * Если номер элемента сортированного списка не был изменен до этого момента то ему будет присвоен свободный номер.
     * <p/>
     *
     * @param donor   элемент сортированного списка
     * @param patient элемент исходного списка
     * @param finder  механизм получения свободных номеров
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
