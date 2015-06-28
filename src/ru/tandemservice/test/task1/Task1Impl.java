package ru.tandemservice.test.task1;

import ru.tandemservice.test.task1.comparators.RowsComparator;

import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 * <p/>
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IStringRowsListSorter INSTANCE = new Task1Impl();

    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {

        if (rows == null) {
            System.out.println("Rows are null");
            return;
        }
        rows.sort(new RowsComparator(columnIndex));
    }

}
