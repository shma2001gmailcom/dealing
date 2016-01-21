package org.misha.erlang;

/**
 * author: misha
 * date: 8/13/15 10:36 PM.
 */
public class ErlangB {
    private final int m;
    private final double rho;

    public ErlangB(int m, double rho) {
        this.m = m;
        this.rho = rho;
    }

    public double value() {
        double term = 1d;
        for (int i = 0; i < m; ++i) {
            term = 1d + (((double) (1 + i)) / rho) * term;
        } 
        return 1d / term;
    }
}
