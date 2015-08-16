package org.misha.erlang;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class ErlangBTest {
    private static final Logger log = Logger.getLogger(ErlangBTest.class);
    public static final double VERY_GOOD_PRECISION = 1e-50;

    @Test
    public void test() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("./src/test/resources/table"));
        int i = 0;
        String[] parts;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            log.debug(line);
            if (line != null) {
                parts = line.split(" ");
                if (parts.length == 3) {
                    int servers = Integer.parseInt(parts[0]);
                    double flowTraffic = Double.parseDouble(parts[1]);
                    double lostProbability = Double.parseDouble(parts[2]);
                    assertTrue(
                            Math.abs(new ErlangB(servers, flowTraffic).value() - lostProbability) < VERY_GOOD_PRECISION
                    );
                }
            }
        }
    }
}