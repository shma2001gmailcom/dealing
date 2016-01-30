package org.misha.queue.produser.consumer;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:12 AM
 */

public abstract class Consumer<T> implements Runnable {
    private final BlockingQueueSemaphore<T> queue;
    private final CountDownLatch latch;
    private static final Logger log = Logger.getLogger(Consumer.class);

    public Consumer(final BlockingQueueSemaphore<T> queue, final CountDownLatch latch) {
        this.queue = queue;
        this.latch = latch;
    }

    public abstract void consume(final T t) throws InterruptedException;

    public abstract boolean terminalCondition();

    @Override
    public void run() {
        while (!terminalCondition()) {
            try {
                final T removed = queue.remove();
                if (!terminalCondition()) {
                    consume(removed);
                } else {
                    throw new InterruptedException();
                }
                log.debug("size=" + queue.size() + " removed=" + removed);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        latch.countDown();
        if (latch.getCount() == 0) {
            Thread.currentThread().interrupt();
        }
    }
}

