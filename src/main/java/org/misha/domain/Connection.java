package org.misha.domain;

import org.misha.domain.agent.Agent;
import org.misha.domain.customer.Customer;

/**
 * author: misha
 * date: 8/15/15 6:59 PM.
 */
public class Connection implements Runnable {
    private final Agent agent;
    private final Customer customer;
    private long callTime;
    private final long waitingTime;

    private Connection(final Agent a, final Customer c, long wt) {
        agent = a;
        customer = c;
        this.waitingTime = wt;
        callTime = System.currentTimeMillis();
    }

    public static void startConnection(final Agent a, final Customer c, long waitingTime) {
        if (a.isFree().get() && c.isFree().get()) {
            System.err.println("agent=" + a + "  customer=" + c);
            a.setFree(false);
            c.isFree().set(false);
            new Thread(new Connection(a, c, waitingTime)).start();
        }
    }

    public void connect() {
        System.err.println("connect");
        while (!customer.isFree().get()) {
            customer.replyOrNot();
        }
        agent.setFree(true);
        callTime = System.currentTimeMillis() - callTime;
        System.err.println("\n\n\n\ncall time=" + callTime);
    }

    @Override
    public void run() {
        connect();
    }


}
