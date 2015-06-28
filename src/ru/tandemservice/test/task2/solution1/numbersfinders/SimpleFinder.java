package ru.tandemservice.test.task2.solution1.numbersfinders;


/**
 * ������� ���������������� ������� ������ �������
 */
public class SimpleFinder implements FreeNumbersFinder {

    int number = Integer.MIN_VALUE;

    public SimpleFinder(int number) {
        this.number = number+1;
    }

    @Override
    public int getFreeNumber() {
        return number++;
    }
}