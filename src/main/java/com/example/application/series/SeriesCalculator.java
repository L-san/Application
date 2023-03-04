package com.example.application.series;

import org.apache.commons.math3.special.Erf;

public class SeriesCalculator {
    private static int N;
    private static int remN;
    private static SeriesFunction seriesFunction;

    public SeriesCalculator(SeriesFunction seriesFunction) {
        this.seriesFunction = seriesFunction;
    }

    public static double getSeriesValue(int toN, double R, double r, double z) {
        double resultR = 0;
        double resultI = 0;
        for (int n = 1; n < toN + 1; n++) {
            double[] fw = seriesFunction.getValue(n, R, r, z);
            resultR = resultR + fw[0];
            resultI = resultI + fw[1];
        }
        return Math.sqrt(resultR * resultR + resultI * resultI);
    }

    public double getParallelSeriesValue(int fromN, int toN, double R, double r, double z) {
        return 0;//IntStream.range(fromN,toN).parallel().mapToDouble(seriesFunction::getValue).sum();
    }

    public static int remainder(double R, double accuracy) {
        double r = 0;
        double z = 0;
        double res100 = getSeriesValue(100, R, r, z);
        for (int i = 99; i > 0; i--) {
            double res = getSeriesValue(i, R, r, z);
            if (Math.abs(res100 - res) > accuracy) {
                N = i + 1;
                break;
            }
        }
        remainder2(0,accuracy);
        return N;
    }

    public static int remainder2(double R,double accuracy) {
        for (int i = 99; i > 0; i--) {
            if (Math.abs(seriesRemainder(i,10)) > accuracy) {
                remN = i + 1;
                break;
            }
        }
        return remN;
    }

    public void getN(double accuracy) {
        System.out.println("--------------\nТочность = " + accuracy + "\tNэксп = " + N + "\tNоц = " + remN);
    }

    private static double seriesRemainder(int N, double b) {
        double l = Math.PI / 8 / b;
        double l2 = l * l;
        return (1 / (2 * l2)) * Math.exp(-l2 * (4 * N - 1) * (4 * N - 1)) + Math.sqrt(Math.PI)/ 2 / l * (1 - Erf.erf(4 * N - 1));
    }

}
