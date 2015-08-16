package org.misha.domain.agent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: misha
 * date: 8/15/15 6:48 PM.
 */
public interface Agent {

    AtomicBoolean isFree();

    void setFree(boolean b);

}
