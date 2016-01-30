package org.misha.queue.produser.consumer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * author: misha
 * date: 1/31/15 2:09 PM.
 */
public final class BlockingQueueSemaphore<T> {
    private final List<T> queue;
    private final Semaphore remove;
    private final Semaphore add;

    public BlockingQueueSemaphore(final int size) {
        queue = new LinkedList<T>();
        remove = new Semaphore(0);
        add = new Semaphore(size);
    }

    public void add(final T element) throws InterruptedException {
        add.acquire();
        boolean isAdded = false;
        try {
            synchronized (this) {
                isAdded = queue.add(element);
            }
        } finally {
            if (!isAdded) {
                add.release();
            } else {
                remove.release();
            }
        }
    }

    public synchronized int size() {
        return queue.size();
    }

    public T remove() throws InterruptedException {
        remove.acquire();
        final T element;
        synchronized (this) {
            element = queue.remove(queue.size() - 1);
            add.release();
        }
        return element;
    }
}
