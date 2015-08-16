package org.misha;

import org.misha.domain.agent.CallCenter;
import org.misha.domain.dialer.Dialer;
import org.misha.domain.dialer.DialerImpl;

/**
 * author: misha
 * date: 8/15/15 11:57 PM.
 */
public class Launcher {
    public static void main(String ... args) {
        CallCenter callCenter = new CallCenter(5);
        Dialer dialer = new DialerImpl(callCenter, 10l);
        new Thread(dialer).start();
    }
}
