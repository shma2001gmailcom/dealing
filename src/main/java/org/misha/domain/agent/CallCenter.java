package org.misha.domain.agent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: misha
 * date: 8/15/15 11:19 PM.
 *
 * Call Center simulator
 *
 * Ought to be thread-safe
 */
public class CallCenter implements Iterable<Agent> {
    private final List<Agent> callCenter = new CopyOnWriteArrayList<Agent>();

    public CallCenter(final int agentCount) {
        for (int i = 0; i < agentCount; ++i) {
            callCenter.add(new MyAgent(i)
            );
        }
    }

    @Override
    public Iterator<Agent> iterator() {
        return callCenter.iterator();
    }


    private static class MyAgent implements Agent {
        private final int agentId;
        private AtomicBoolean isFree;
        private AtomicLong freeTime;
        private AtomicLong currentFreeTime;
        private AtomicLong totalTime;
        private final long start;

        public MyAgent(int agentId) {
            this.agentId = agentId;
            isFree = new AtomicBoolean(true);
            freeTime = new AtomicLong(0L);
            currentFreeTime = new AtomicLong(0l);
            totalTime = new AtomicLong(0l);
            start = System.currentTimeMillis();
        }

        @Override
        public AtomicBoolean isFree() {
            return isFree;
        }

        @Override
        public void setFree(boolean free) {
            if (free) {
                currentFreeTime.set(System.currentTimeMillis());
            } else {
                freeTime.set(System.currentTimeMillis() - currentFreeTime.get());
            }
            totalTime.set(System.currentTimeMillis() - start);
            System.err.println("setFree=" + free + "\nuneff rate=" + ((double) (currentFreeTime.get())) / (double)totalTime.get());
            isFree.set(free);
        }

        public String toString() {
            return "Agent #" + agentId;
        }
    }
}
