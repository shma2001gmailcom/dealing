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
    private final Semaphore item;
    private final Semaphore space;

    public BlockingQueueSemaphore(final int size) {
        queue = new LinkedList<T>();
        item = new Semaphore(0);
        space = new Semaphore(size);
    }

    public void add(final T element) throws InterruptedException {
        space.acquire();
        boolean isAdded = false;
        try {
            synchronized (this) {
                isAdded = queue.add(element);
            }
        } finally {
            if (!isAdded) {
                space.release();
            } else {
                item.release();
            }
        }
    }

    public synchronized int size() {
        return queue.size();
    }

    public T remove() throws InterruptedException {
        item.acquire();
        final T element;
        synchronized (this) {
            element = queue.remove(queue.size() - 1);
            space.release();
        }
        return element;
    }
}
