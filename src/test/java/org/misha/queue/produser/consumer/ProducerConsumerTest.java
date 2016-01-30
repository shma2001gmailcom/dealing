package org.misha.queue.produser.consumer;

import org.apache.log4j.Logger;
import org.misha.queue.Linker;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ProducerConsumerTest {
    private static final Logger log = Logger.getLogger(ProducerConsumerTest.class);

    public static void main(String... args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final BlockingQueueSemaphore<Integer> queue = new BlockingQueueSemaphore<Integer>(11);
        final Producer<Integer> producer = new Producer<Integer>(queue, latch) {
            long start = System.currentTimeMillis();

            public Integer produce() throws InterruptedException {
                final int result = new Random().nextInt(11);
                log.debug("size=" + queue.size() + " produced=" + result);
                return result;
            }

            @Override
            public boolean terminalCondition() {
                return System.currentTimeMillis() - start > 2000L;//insert 'false;' for infinite loop
            }
        };
        final Consumer<Integer> consumer = new Consumer<Integer>(queue, latch) {
            long start = System.currentTimeMillis();

            @Override
            public void consume(final Integer integer) throws InterruptedException {
                log.debug("size=" + queue.size() + " consumed=" + integer);
            }

            @Override
            public boolean terminalCondition() {
                return System.currentTimeMillis() - start > 20000L;//insert 'false;' for infinite loop
            }
        };
        final Linker<Integer> linker = new Linker<Integer>(producer, consumer, latch);
        try {
            linker.link();
            System.exit(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}