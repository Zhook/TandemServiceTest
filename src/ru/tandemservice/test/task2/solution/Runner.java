package ru.tandemservice.test.task2.solution;


public class Runner implements Runnable {
    final WrappedElement starter;
    int freeNumber;

    public Runner(WrappedElement starter, int freeNumber) {
        this.starter = starter;
        this.freeNumber = freeNumber;
    }

    @Override
    public void run() {
        WrappedElement next = starter;
        do {
            int buffNumber = next.element.getNumber();
            writeSafe(next, freeNumber);
            freeNumber = buffNumber;
            next = next.patient;
        } while (next != starter);
        writeSafe(next, freeNumber);
    }


    private void writeSafe(WrappedElement w, int number) {
        int buffNumber = w.element.getNumber();
        try {
            w.element.setupNumber(number);
        } catch (IllegalStateException e) {
            System.out.println("Error!");
            System.out.println("Chain starter-" + starter.element.getNumber());
            System.out.println("Element-" + buffNumber + ", newNumber-" + number);
            WrappedElement se = w.patient;
            int count = 0;
            while (se.patient != null && se != w) {
                //System.out.print(se.element.getNumber() + "->" + se.patient.element.getNumber() + "\t");
                if (se == starter)
                    System.out.println("jump " + count);
                count++;
                se = se.patient;
            }
            System.out.println("jump " + count);
        }
    }


}
