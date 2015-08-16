package org.misha.queue;

import org.misha.queue.produser.consumer.Consumer;
import org.misha.queue.produser.consumer.Producer;


/**
 * Author: mshevelin
 * Date: 2/3/15
 * Time: 10:13 AM
 */

public final class Linker<T> {
    private final Producer<T> producer;
    private final Consumer<T> consumer;

    public Linker(final Producer<T> p, final Consumer<T> c) {
        producer = p;
        consumer = c;
    }

    public void link() {
        final Thread t1 = new Thread(producer);
        final Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
    }
}
