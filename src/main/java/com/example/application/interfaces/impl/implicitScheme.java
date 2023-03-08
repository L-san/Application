package com.example.application.interfaces.impl;

import com.example.application.interfaces.RunThroughMethod;

public class implicitScheme implements RunThroughMethod {
    @Override
    public double runThrough(double x) {
        return 0;
    }

    public void implicit(int I, int K, double l, double r, double k, int n) {
        double h_r = r / I;
        double h_z = l / K;
        double gamma = h_z / 2 / k / n / h_r;
        double[] p = new double[I - 2]; // i = 1:I-1
        double[] q = new double[K - 1]; // k = 1:K
        double[][] u = new double[I + 1][K + 1]; // u[0:I][0:K]

    }
}
