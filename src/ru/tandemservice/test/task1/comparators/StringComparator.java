package ru.tandemservice.test.task1.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ���������� ��� �����, ����������� ������ �� ��������� ������������ ������ ���������� ������ ����� ��� �� �� ����������.
 * <p/>
 * ���������:
 * null ������
 * ������ ������
 * ���������:
 * ��������� ������������ �� ������� ������������,
 * ���� ��� ��������� �������� �� ������������ ��� int, ����� ��� String.
 * ���� ������ ������� ������� ��������� �� ������ ���� ����� ��������.
 */
public class StringComparator implements Comparator<String> {

    /**
     * ������� ��������� �����
     * <ul>
     * <li>null </li>
     * <li>������ ������.</li>
     * <li>��������� ������� �� ��������� ������������ ������ ���������� ������ ����� ��� �� �� ����������.
     * ���� ���� ������������ �������� �������� ������ ����� �� ������������ ��� Integer, ����� ��� String</li>
     * </ul>
     *
     * @param s1 ������ ������ ��� ���������.
     * @param s2 ������ ������ ��� ���������.
     * @return -1 ���� s1 < s2,      0 ���� s1 = s2,      +1  ���� s1 > s2
     */
    @Override
    public int compare(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;

        if (s1.equals(s2)) return 0;
        if (s1.length() == 0) return -1;
        if (s2.length() == 0) return 1;

        Iterator<String> words1 = splitString(s1).iterator();
        Iterator<String> words2 = splitString(s2).iterator();
        while (words1.hasNext() && words2.hasNext()) {
            String word1 = words1.next();
            String word2 = words2.next();

            if (!word1.equals(word2))
                return compareSubString(word1, word2);
        }

        if (words1.hasNext()) return 1;
        if (words2.hasNext()) return -1;

        return 0;
    }

    /**
     * ����� ����� ������ �� ��������� ������������ ������ ���������� ������ ����� ��� �� �� ����������.
     * ��������: "25r1ADe6 Qg" ������� �� ["25", "r", "1", "ADe", 6", " Qg"]
     *
     * @param s ������ ��� �������
     * @return List �� ��������
     */
    private static Iterable<String> splitString(String s) {
        List<String> result = new ArrayList<>();
        Matcher match = Pattern.compile("\\d+|\\D+").matcher(s);

        while (match.find())
            result.add(match.group());

        return result;
    }

    /**
     * ����� ������� �������� �������� ����� �� ����� � �������� ��� {@link java.lang.Integer}.
     * ��� ������� ���������� ��� {@link java.lang.String}</li>
     *
     * @param s1 ������ ������ ��� ���������.
     * @param s2 ������ ������ ��� ���������.
     * @return -1 ���� s1 < s2,      0 ���� s1 = s2,      +1  ���� s1 > s2
     */
    private static int compareSubString(String s1, String s2) {

        try {
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            return Integer.compare(i1, i2);
        } catch (NumberFormatException ignored) {
        }

        return s1.compareTo(s2);
    }


}
