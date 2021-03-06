package org.misha.domain.dialer;

import org.apache.log4j.Logger;
import org.misha.domain.Connection;
import org.misha.domain.agent.Agent;
import org.misha.domain.agent.CallCenter;
import org.misha.domain.customer.Customer;

import java.util.Random;

/**
 * author: misha
 * date: 8/15/15 11:15 PM.
 */
public class DialerImpl implements Dialer, Runnable {
    private final CallCenter callCenter;
    private final long waitingTime;
    private static final Logger log = Logger.getLogger(DialerImpl.class);

    public DialerImpl(CallCenter callCenter, long waitingTime) {
        this.callCenter = callCenter;
        this.waitingTime = waitingTime;
    }

    @Override
    public void connect(Agent agent, Customer customer) {
        Connection.startConnection(agent, customer, waitingTime);
    }

    @Override
    public void run() {
        for (; ; ) {
            int freeAgents = 0;
            for (Agent agent : callCenter) {
                if (agent.isFree().get()) {
                    connect(agent, new Customer(Math.abs(new Random(2l).nextLong())));//todo ms: use distribution
                    freeAgents = 1;
                }
            }
            if (freeAgents == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    log.debug("Thread #" + Thread.currentThread().getId() +" has been interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
