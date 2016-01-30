package org.misha.domain.customer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: misha
 * date: 8/15/15 6:53 PM.
 * Takes a call or not. If takes, speaks callTimeRule.nextTakeCallTimeout() millis and hangs up.
 */
public class Customer {
    private final AtomicBoolean isFree = new AtomicBoolean(false);//todo ms: add distribution
    private final CallTimeRule callTimeRule;
    private final long timeout;

    public Customer(long t) {
        timeout = t;
        callTimeRule = CallTimeRule.get();
        isFree.set(new Random().nextBoolean());//todo ms: add distribution
    }

    public void takeCall() {
        isFree.set(false);
    }

    public void hangUp() {
        isFree.set(true);
    }

    public AtomicBoolean isFree() {
        return isFree;
    }

    public void replyOrNot() {
        boolean isBusy = callTimeRule.nextIsBusy();
        if (!isBusy) {
            takeCall();
            long callStartTime = System.currentTimeMillis();
            while (true) {
                if (!(System.currentTimeMillis() - callStartTime < callTimeRule.nextTakeCallTimeout())) {
                    break;
                }
            }
            hangUp();
        }
    }

    void makeCall() {
    }

    public long getTimeout() {
        return timeout;
    }

    public String toString() {
        return "Customer" + hashCode();
    }
}
