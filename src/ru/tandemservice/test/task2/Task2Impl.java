package ru.tandemservice.test.task2;

import ru.tandemservice.test.task2.solution.Sorter;

import java.util.List;

/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 * <p/>
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public class Task2Impl implements IElementNumberAssigner {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IElementNumberAssigner INSTANCE = new Task2Impl();

    @Override
    public void assignNumbers(final List<IElement> elements) {

        if (elements == null || elements.isEmpty()) {
            System.out.println("List empty");
            return;
        }


//      Алгоритм делит список на цепочки непрерывных замен
//      Общая сложность O(n*log(n)), по памяти O(n)
//      При "дорогой" записи и небольших n сложность O(n).
//      Количество вызовов setupNumber (n + количество цепочек)
//      Худший вариант 1.5*n вызовов setupNumber если список требует порпарную замену всех элементов
//      В среднем n вызовов setupNumber.

        Sorter sorter = new Sorter();
        sorter.assignNumbers(elements);
    }
}
