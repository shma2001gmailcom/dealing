package org.misha.domain.customer;

import org.apache.commons.math3.distribution.AbstractIntegerDistribution;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

import java.util.Random;

/**
 * author: misha
 * date: 8/15/15 7:29 PM.
 */
public class CallTimeRule {
    private final AbstractRealDistribution callTimeDistribution;
    private final AbstractIntegerDistribution isBusyDistribution;

    CallTimeRule(AbstractRealDistribution c, AbstractIntegerDistribution i) {
        callTimeDistribution = c;
        isBusyDistribution = i;
    }

    static CallTimeRule get() {
        return new CallTimeRule(null, null);//todo ms: add distributions
    }

    Long nextTakeCallTimeout() {
        return 11L;//todo ms: use callTimeDistribution
    }

    public boolean nextIsBusy() {
        return new Random().nextBoolean();//todo ms: use isBusyDistribution
    }

    public static double uniform() {
        return new Random(System.currentTimeMillis()).nextDouble();
    }

    public static double exp(double lambda) {
        if (lambda <= 0.0) {
            throw new IllegalArgumentException("Rate lambda must be positive");
        }
        return -Math.log(1 - uniform()) / lambda;
    }
}
