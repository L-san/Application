package com.example.application.numericsol.krnikolson;

import com.example.application.complex.ComplexNumber;
import com.example.application.numericsol.SchemaSolver;
import com.example.application.numericsol.SweepSolver;

public class NicholsonSchemaSolver implements SchemaSolver {

    private final double hz;
    private final double hr;
    private final double kNumber;
    private final double n;

    private final double R;

    private final int I;

    private final int K;

    private final ComplexNumber gamma;

    private final ComplexNumber B;
    private final ComplexNumber D;

    public NicholsonSchemaSolver(int I, int K, double kNumber, double n, double R, double L) {
        this.I = I;
        this.K = K;
        this.kNumber = kNumber;
        this.n = n;
        this.R = R;

        this.hz = L / K;
        this.hr = R / I;

        this.gamma = gamma();
        this.B = ComplexNumber.subtract(ComplexNumber.REAL_ONE, ComplexNumber.multiplyOnNumber(gamma, 2));
        this.D = ComplexNumber.add(ComplexNumber.REAL_ONE, ComplexNumber.multiplyOnNumber(gamma, 2));
    }

    @Override
    public ComplexNumber[][] solve() {
        ComplexNumber[][] u = new ComplexNumber[I][K + 1];
        //initialize bounds-----------------------------
        for (int i = 0; i < I; i++) {
            u[i][0] = new ComplexNumber(psi(i), 0);
        }

        for (int k = 1; k < K + 1; k++) {
            for (int i = 0; i < I; i++) {
                u[i][k] = new ComplexNumber(0, 0);
            }
        }
        //-----------------------------------------------
        for (int k = 0; k < K; k++) {
            ComplexNumber[] a = new ComplexNumber[I + 1];
            ComplexNumber[] b = new ComplexNumber[I + 1];
            ComplexNumber[] c = new ComplexNumber[I + 1];
            ComplexNumber[] f = new ComplexNumber[I + 1];

            c[0] = ComplexNumber.REAL_ONE;
            b[0] = new ComplexNumber(0, 0);
            f[0] = new ComplexNumber(0, 0);

            for (int i = 1; i < I + 1; i++) {
                a[i] = A(i);
                c[i] = B;
                b[i] = C(i);
            }

            for (int i = 1; i < I - 1; i++) {
                System.out.println(i + " " + k);
                f[i] = ComplexNumber.subtract(ComplexNumber.subtract(ComplexNumber.multiply(D, u[i][k]), ComplexNumber.multiply(C(i), u[i + 1][k])),
                        ComplexNumber.multiply(A(i), u[i - 1][k]));
            }
            //f[I - 1] = ComplexNumber.subtract(ComplexNumber.multiply(D, u[I - 1][k]), ComplexNumber.multiply(A(I - 1), u[I - 1][k]));
            f[I - 1] = new ComplexNumber(0, 0);
            ComplexNumber[] uk = SweepSolver.run(a, b, c, f);
            for (int i = 0; i < I; i++) {
                u[i][k + 1] = uk[i];
            }
        }
        return u;
    }

    private ComplexNumber mu(int i) {
        double ri = i * hr;
        double imag = hz / 8 / hr / kNumber / n / ri;
        return new ComplexNumber(0, imag);
    }

    private ComplexNumber gamma() {
        double imag = hz / 4 / hr / hr / kNumber / n;
        return new ComplexNumber(0, imag);
    }

    private double psi(int i) {
        double x = i * hr / 0.1 / R;
        return 10 * Math.exp(-x * x);
    }

    private ComplexNumber A(int i) {
        return ComplexNumber.subtract(gamma, mu(i));
    }

    private ComplexNumber C(int i) {
        return ComplexNumber.add(gamma, mu(i));
    }

}
