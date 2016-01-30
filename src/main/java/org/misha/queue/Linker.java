package org.misha.queue;

import org.misha.queue.produser.consumer.Consumer;
import org.misha.queue.produser.consumer.Producer;

import java.util.concurrent.CountDownLatch;

/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:13 AM
 */

public final class Linker<T> {
    private final Producer<T> producer;
    private final Consumer<T> consumer;
    private final CountDownLatch latch;

    public Linker(final Producer<T> p, final Consumer<T> c, final CountDownLatch latch) {
        producer = p;
        consumer = c;
        this.latch = latch;
    }

    public void link() throws InterruptedException {
        final Thread t1 = new Thread(producer);
        final Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
        latch.await();
    }
}
