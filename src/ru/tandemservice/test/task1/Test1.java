package ru.tandemservice.test.task1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test1 {

    public static void main(String args[]) {
        System.out.println("Raw:");
        List<String[]> rows = generate(10, false, 10, false, 10);
        print(rows);

        System.out.println();
        System.out.println("Sorted:");
        Task1Impl task1 = new Task1Impl();
        task1.sort(rows, 0);
        print(rows);
    }

    static List<String[]> generate(int rows, boolean nullableRow, int maxColumns, boolean randomNumberColumns, int maxStringLength) {
        RandomString randomString = new RandomString();

        int offset = 0;
        if (nullableRow) offset = 1;

        List<String[]> list = new ArrayList<String[]>();
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            int columns = maxColumns;
            if (randomNumberColumns) columns = random.nextInt(maxColumns + 1 + offset) - offset;

            String[] row = null;
            if (columns == 0) row = new String[0];
            if (columns > 0) {
                row = new String[columns];
                for (int j = 0; j < columns; j++)
                    row[j] = randomString.getString(maxStringLength);
            }

            list.add(row);
        }


        return list;
    }

    static void print(List<String[]> rows) {
        if (rows != null)
            for (String[] row : rows) {
                if (row == null) {
                    System.out.print(NULLROW);
                } else {
                    if (row.length == 0) {
                        System.out.print(EMPTYROW);
                    } else
                        for (String s : row) {
                            if (s == null) System.out.print(NULLVALUE);
                            else if (s.equals(" ")) System.out.print(SPACEVALUE);
                            else System.out.print(s);
                            System.out.print("\t");
                        }
                }
                System.out.println();
            }
    }

    private static final String NULLROW = "Null Row";
    private static final String EMPTYROW = "Empty Row";
    private static final String NULLVALUE = "Null Value";
    private static final String SPACEVALUE = "Space";
}
