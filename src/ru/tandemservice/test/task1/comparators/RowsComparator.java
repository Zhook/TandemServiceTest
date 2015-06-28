package ru.tandemservice.test.task1.comparators;

import java.util.Comparator;

/**
 *  омпаратор дл€ массива строк по строке columnIndex,
 * разбивающий строку на подстроки содержащие только числа(int) или толкьо не числа.
 * <p/>
 * ѕриоритет:
 * null,
 * пустые записи или имеющие недостаточный размер(<columnIndex+1),
 * записи со сравниваемой строкой null
 * записи со пустой сравниваемой строкой
 * ќстальное:
 * подстроки сравниваютс€ до первого несовпадени€,
 * если обе подстроки числовые то сравниваютс€ как int, иначе как String.
 * ≈сли строки разного размера совпадают то первой идет более коротка€.
 */
public class RowsComparator implements Comparator<String[]> {

    private final int columnIndex;
    private final StringComparator stringComparator = new StringComparator();

    /**
     *  омпаратор дл€ сортировки записей по столбцу
     *
     * @param columnIndex индекс столбца по которому сравниваютс€ записи
     */
    public RowsComparator(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * ѕравила сравнени€ записей
     * <ul>
     * <li>null записи </li>
     * <li>записи не достаточной длинны</li>
     * <li>остальные по правилу {@link StringComparator}</li>
     * </ul>
     *
     * @param s1 перва€ запись дл€ сравнени€.
     * @param s2 втора€ запись дл€ сравнени€.
     * @return -1 если s1 < s2,      0 если s1 = s2,      +1  если s1 > s2
     * @see StringComparator
     */
    @Override
    public int compare(String[] s1, String[] s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;

        int requiredLength = columnIndex + 1;
        if (s1.length < requiredLength && s2.length < requiredLength) return 0;
        if (s1.length < requiredLength) return -1;
        if (s2.length < requiredLength) return 1;

        return stringComparator.compare(s1[columnIndex], s2[columnIndex]);
    }
}