package com.example.application;

import com.example.application.complex.ComplexNumber;
import com.example.application.numericsol.SchemaSolver;
import com.example.application.numericsol.krnikolson.NikolsonSchemaSolver;
import com.example.application.series.SeriesFunction;

import java.util.ArrayList;

public class CalculatorTools {
    private final double R;
    private final double L;
    private double step;
    //private final int nStep = 1000;
    private final double k;
    private final double n;
    //private int N;
    //private final double accuracy;
    private final SeriesFunction seriesFunction;
   // private final SeriesCalculator seriesCalculator;

    public CalculatorTools(double R, double lambda, double n, double L, double beta, int N, double accuracy) {
        this.R = R;
        this.L = L;
        this.seriesFunction = new SeriesFunction(lambda, n, beta);
        //this.seriesCalculator = new SeriesCalculator(seriesFunction);
       // this.N = N;
        this.n = n;
        //this.accuracy = accuracy;
        this.k = 2 * Math.PI / lambda;
    }

    public ArrayList<Point> calculate(int z, boolean isR, int I, int K) {

        /*double r = -R;
        if (N == 0) {
            N = SeriesCalculator.remainder(R, accuracy);
        }*/
        /*if(isR){
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
        }*/
        SchemaSolver solver = new NikolsonSchemaSolver(I, K, k, n, R, L); // todo
        ComplexNumber[][] answer = solver.solve();
        ArrayList<Point> points ;
        if (isR) {
            points = new ArrayList<>(K);
            for (int k = 0; k < K; k++) {
                step = L / K;
                points.add(new Point(k * step, ComplexNumber.module(answer[z][k])));
            }
        } else {
            points = new ArrayList<>(I);
            for (int i = 0; i < I; i++) {
                step = R / I;
                points.add(new Point(i * step, ComplexNumber.module(answer[i][z])));
            }
        }
         //seriesCalculator.getN(accuracy);
        return points;
    }
}
