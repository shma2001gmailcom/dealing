package org.misha.queue.produser.consumer;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:12 AM
 */

public abstract class Producer<T> implements Runnable {
    private final BlockingQueueSemaphore<T> queue;
    private final CountDownLatch latch;
    private static final Logger log = Logger.getLogger(Producer.class);

    public Producer(final BlockingQueueSemaphore<T> queue, final CountDownLatch latch) {
        this.queue = queue;
        this.latch = latch;
    }

    public abstract T produce() throws InterruptedException;

    public abstract boolean terminalCondition();

    @Override
    public void run() {
        while (!terminalCondition()) {
            try {
                if (!terminalCondition()) {
                    final T toAdd = produce();
                    queue.add(toAdd);
                    log.debug(toAdd + " added, size=" + queue.size());
                } else {
                    throw new InterruptedException();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        latch.countDown();
        if (latch.getCount() == 0) {
            Thread.currentThread().interrupt();
        }
    }
}

