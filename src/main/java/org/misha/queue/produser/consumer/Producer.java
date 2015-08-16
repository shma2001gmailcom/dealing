package org.misha.queue.produser.consumer;

/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:12 AM
 */

public abstract class Producer<T> implements Runnable {
    private final BlockingQueueSemaphore<T> queue;

    public Producer(final BlockingQueueSemaphore<T> queue) {
        this.queue = queue;
    }

    public abstract T produce() throws InterruptedException;

    public abstract boolean terminalCondition();

    @Override
    public void run() {
        for (; ; ) {
            try {
                if (!terminalCondition()) {
                    final T toAdd = produce();
                    queue.add(toAdd);
                    System.err.println(toAdd + " added, size=" + queue.size());
                } else {
                    throw new InterruptedException();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

