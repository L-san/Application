package com.example.application.series;

import com.example.application.Bessel;
import org.apache.commons.math3.special.BesselJ;

public class SeriesFunction {

    private double fi = 0;

    private double beta = 10;

    private double twoKN = 2 * 2 * 3.14 / (1e-6) * 1.5;

    @Deprecated
    public SeriesFunction() {
    }

    public SeriesFunction(double lambda, double n, double beta) {
        this.twoKN = 2 * 2 * 3.14 / (lambda) * n;
        this.beta = beta;
    }

    public double[] getValue(int value, double R, double r, double z) {
        double muN = Bessel.getRoot(value-1);
        double R2 = R * R;
        double beta2 = beta * beta;
        double bv0 = Bessel.getValue(0, muN * r / R);
        double bv1 = Bessel.getValue(1, muN);
        fi = muN * muN / R2 * z / twoKN;
        double val = 1 / beta * Math.exp(-muN * muN / 4 / beta2) * bv0 / bv1 / bv1;
        double gr = getReal(val);
        double gi = getImag(val);
        return new double[]{gr, gi};
    }

    public double getReal(double value) {
        return value * Math.cos(fi);
    }

    public double getImag(double value) {
        return value * Math.sin(fi);
    }

    public double remainder(int N) {
        return 0;
    }

}
