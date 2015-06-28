package ru.tandemservice.test.task1;

import java.util.Arrays;
import java.util.Random;

public class RandomString {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        tmp.append(' ')
                .append(',')
                .append('.')
                .append('/')
                .append(':');
        for (int i = 0; i < 3; i++)
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);

        for (char ch = 'A'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    private final Random random = new Random();

    public String getString(int maxLength) {
        int length = random.nextInt(maxLength + 2) - 1;
        if (length == -1) return null;
        if (length == 0) return " ";

        char[] buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buf);
    }
}
