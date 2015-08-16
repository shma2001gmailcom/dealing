package org.misha.queue.produser.consumer;

import org.misha.queue.Linker;

import java.util.Random;

public class ProducerConsumerTest {


    public static void main(String... args) {
        final BlockingQueueSemaphore<Integer> queue = new BlockingQueueSemaphore<Integer>(11);
        final Producer<Integer> producer = new Producer<Integer>(queue) {
            long start = System.currentTimeMillis();

            public Integer produce() throws InterruptedException {
                final int result = new Random().nextInt(11);
                System.err.println("size=" + queue.size() + " " + "produced=" + result);
                return result;
            }

            @Override
            public boolean terminalCondition() {
                return System.currentTimeMillis() - start > 20000L;//insert 'false;' for infinite loop
            }
        };
        final Consumer<Integer> consumer = new Consumer<Integer>(queue) {
            long start = System.currentTimeMillis();

            @Override
            public void consume(final Integer integer) throws InterruptedException {
                System.err.println("size=" + queue.size() + " " + "consumed=" + integer);
            }

            @Override
            public boolean terminalCondition() {
                return System.currentTimeMillis() - start > 20000L;//insert 'false;' for infinite loop
            }
        };
        final Linker<Integer> linker = new Linker<Integer>(producer, consumer);
        linker.link();
    }
}