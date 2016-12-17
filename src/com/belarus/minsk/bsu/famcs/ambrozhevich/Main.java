package com.belarus.minsk.bsu.famcs.ambrozhevich;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        BlockingQueue<String> tasks = new LinkedBlockingQueue<>();
        FileWriter fileWriter = new FileWriter("output.txt");
        Printer p1 = new Printer("printer1", tasks, fileWriter);
        Printer p2 = new Printer("printer2", tasks, fileWriter);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        t1.start();
        t2.start();

        long lastRead = 0;
        while (true) {
            File file = new File("tasks.txt");
            try {
                if (file.canRead()) {
                    RandomAccessFile raf = new RandomAccessFile(file, "r");
                    raf.seek(lastRead);
                    String line;
                    while ((line = raf.readLine()) != null) {
                        if (!line.equals(""))
                            tasks.put(line);
                    }
                    lastRead = raf.getFilePointer();
                    raf.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
