package com.belarus.minsk.bsu.famcs.ambrozhevich;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

class Printer implements Runnable {

    private String name;
    private final FileWriter fileWriter;
    private final BlockingQueue<String> tasks;

    Printer(String name, BlockingQueue<String> tasks, FileWriter fileWriter) {
        this.name = name;
        this.tasks = tasks;
        this.fileWriter = fileWriter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (fileWriter) {
                    fileWriter.write(name + " " + tasks.take());
                    fileWriter.write(System.lineSeparator());
                    fileWriter.flush();
                }
                Thread.sleep((long) (Math.random() * 5000));
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
