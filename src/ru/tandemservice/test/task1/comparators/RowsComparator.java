package ru.tandemservice.test.task1.comparators;

import java.util.Comparator;

/**
 * ���������� ��� ������� ����� �� ������ columnIndex,
 * ����������� ������ �� ��������� ���������� ������ �����(int) ��� ������ �� �����.
 * <p/>
 * ���������:
 * null,
 * ������ ������ ��� ������� ������������� ������(<columnIndex+1),
 * ������ �� ������������ ������� null
 * ������ �� ������ ������������ �������
 * ���������:
 * ��������� ������������ �� ������� ������������,
 * ���� ��� ��������� �������� �� ������������ ��� int, ����� ��� String.
 * ���� ������ ������� ������� ��������� �� ������ ���� ����� ��������.
 */
public class RowsComparator implements Comparator<String[]> {

    private final int columnIndex;
    private final StringComparator stringComparator = new StringComparator();

    /**
     * ���������� ��� ���������� ������� �� �������
     *
     * @param columnIndex ������ ������� �� �������� ������������ ������
     */
    public RowsComparator(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * ������� ��������� �������
     * <ul>
     * <li>null ������ </li>
     * <li>������ �� ����������� ������</li>
     * <li>��������� �� ������� {@link StringComparator}</li>
     * </ul>
     *
     * @param s1 ������ ������ ��� ���������.
     * @param s2 ������ ������ ��� ���������.
     * @return -1 ���� s1 < s2,      0 ���� s1 = s2,      +1  ���� s1 > s2
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