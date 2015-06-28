package ru.tandemservice.test.task2.solution2;

import ru.tandemservice.test.task2.IElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlowSorterForBig {


    public static void assignNumbers(final List<IElement> elements) {

//        получение отсортированной копии исходного списка с элементами обернутыми полем int содержащим начальный номер элемента O(n*log(n))
        ArrayList<WrappedElement2> rawElements = makeCopy(elements);
        ArrayList<WrappedElement2> sortedElements = new ArrayList<>(rawElements);
        Collections.sort(sortedElements);

        WrappedElement2 se;
        for (int j = 0; j < rawElements.size(); j++) {
            se = rawElements.get(j);
            se.setDonor(sortedElements.get(j));
        }

        int initialFreeNumber = Integer.MIN_VALUE;
        for (WrappedElement2 wrappedElement : sortedElements) {
            if (initialFreeNumber != wrappedElement.element.getNumber())
                break;
            initialFreeNumber++;
        }

        int free;
//        WrappedElement2 se;
        for (WrappedElement2 sortedElement : sortedElements) {
            se = sortedElement;
            free = initialFreeNumber;

            for (; ; ) {
                if (se.sorted) break;

                if (se.donor.element == se.element) {
                    se.sorted = true;
                    break;
                }

                if (se.mustbe == free) {
                    se.element.setupNumber(free);
                    se.sorted = true;
                    break;
                }

                if (se.mustbe == se.element.getNumber()) {
                    se.sorted = true;
                    break;
                }

                int donorNumber = se.donor.element.getNumber();
                int patientNumber = se.element.getNumber();

                se.donor.element.setupNumber(free);
                se.element.setupNumber(donorNumber);
                se.sorted = true;

                if (se.donor.donor == se) {
                    se.donor.element.setupNumber(patientNumber);
                    se.donor.sorted = true;
                    break;
                } else {
                    free = patientNumber;
                    se = se.donor;
                }
            }


        }


    }


    private static ArrayList<WrappedElement2> makeCopy(List<IElement> elements) {
        ArrayList<WrappedElement2> sortedElements = new ArrayList<>(elements.size());
        for (int j = 0; j < elements.size(); j++)
            sortedElements.add(new WrappedElement2(elements.get(j), j));
        return sortedElements;
    }

    static class WrappedElement2 implements Comparable<WrappedElement2> {

        final IElement element;
        final int index;
        boolean sorted = false;
        WrappedElement2 donor;
        int mustbe;

        WrappedElement2(IElement element, int index) {
            this.element = element;
            this.index = index;
        }

        public void setDonor(WrappedElement2 donor) {
            this.donor = donor;
            mustbe = donor.element.getNumber();
        }

        @Override
        public int compareTo(WrappedElement2 o) {
            return Integer.compare(element.getNumber(), o.element.getNumber());
        }

        static void print(List<WrappedElement2> elements) {
            for (WrappedElement2 element : elements) System.out.print(element.element.getNumber() + "\t");
            System.out.println();
        }
    }
}
