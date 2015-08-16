package org.misha.queue.produser.consumer;

/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:12 AM
 */

public abstract class Consumer<T> implements Runnable {
    private final BlockingQueueSemaphore<T> queue;

    public Consumer(final BlockingQueueSemaphore<T> queue) {
        this.queue = queue;
    }

    public abstract void consume(final T t) throws InterruptedException;

    public abstract boolean terminalCondition();

    @Override
    public void run() {
        for (; ; ) {
            try {
                final T removed = queue.remove();
                if (!terminalCondition()) {
                    consume(removed);
                } else {
                    throw new InterruptedException();
                }
                System.err.println("size=" + queue.size() + " removed=" + removed);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

