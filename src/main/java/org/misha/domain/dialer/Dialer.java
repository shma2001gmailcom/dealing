package org.misha.domain.dialer;

import org.misha.domain.agent.Agent;
import org.misha.domain.customer.Customer;

/**
 * author: misha
 * date: 8/15/15 6:56 PM.
 * connects agents and customers
 */
public interface Dialer extends Runnable{
   void connect(Agent agent, Customer customer);
}
