package com.example.application;

import java.util.ArrayList;

public class CalculatorTools {
    private final double R;
    private final double L;
    private double step;
    private final int nStep = 1000;

    private int N;
    private final double accuracy;
    private final SeriesFunction seriesFunction;
    private final SeriesCalculator seriesCalculator;

    public CalculatorTools(double R, double lambda, double n, double L, double beta, int N, double accuracy) {
        this.R = R;
        this.L = L;
        this.seriesFunction = new SeriesFunction(lambda, n, beta);
        this.seriesCalculator = new SeriesCalculator(seriesFunction);
        this.N = N;
        this.accuracy = accuracy;
    }

    public ArrayList<Point> calculate(double z, boolean isR) {
        ArrayList<Point> points = new ArrayList<>(nStep);
        double r = -R;
        if (N == 0) {
            N = SeriesCalculator.remainder(R, accuracy);
        }
        if(isR){
            double zL = 0;
            step = L / (nStep - 1);
            for (int i = 0; i <= nStep; i++) {
                double absR = seriesCalculator.getSeriesValue(N, R, z, zL);
                points.add(new Point(zL, absR));
                zL += step;
            }
        }else{
            step = 2 * R / (nStep - 1);
            for (int i = 0; i <= nStep; i++) {
                double absR = seriesCalculator.getSeriesValue(N, R, r, z);
                points.add(new Point(r, absR));
                r += step;
            }
        }
        seriesCalculator.getN(accuracy);
        return points;
    }
}
