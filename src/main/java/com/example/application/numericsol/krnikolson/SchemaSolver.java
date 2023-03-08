package com.example.application.numericsol.krnikolson;

import com.example.application.complex.ComplexNumber;

public class SchemaSolver {

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

    public SchemaSolver(int I, int K, double kNumber, double n, double R, double L) {
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

    public ComplexNumber[][] solve() {
        ComplexNumber[] alpha = new ComplexNumber[I];
        ComplexNumber[][] beta = new ComplexNumber[I][K];
        ComplexNumber[][] u = new ComplexNumber[I][K];
        //initialize bounds-----------------------------
        for (int i = 0; i < I; i++) {
            u[i][0] = new ComplexNumber(psi(i), 0);
        }

        for (int k = 1; k < K; k++) {
            for (int i = 0; i < I; i++) {
                u[i][k] = new ComplexNumber(0, 0);
            }
        }

        //initialize coefficients----------------------------------------------
        //------ make zeros
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < I; i++) {
                alpha[i] = new ComplexNumber(0, 0);
                beta[i][k] = new ComplexNumber(0, 0);
            }
        }

        alpha[1] = ComplexNumber.multiplyOnNumber(ComplexNumber.divide(C(1), B), -1);

        for (int k = 0; k < K - 1; k++) {
            ComplexNumber a1 = ComplexNumber.multiply(D, u[1][k]);
            ComplexNumber a2 = ComplexNumber.multiply(C(1), u[2][k]);
            ComplexNumber a3 = ComplexNumber.subtract(a2, a1);
            beta[1][k+1] = ComplexNumber.divide(a3, B);
        }

        for (int k = 0; k < K - 1; k++) {
            for (int i = 1; i < I - 1; i++) {
                alpha[i + 1] = getAlpha(i, alpha);
                beta[i][k + 1] = getBeta(i, k, u, alpha, beta);
            }
        }

        //calculate-----------------------------------------------

        for (int k = 0; k < K - 1; k++) {
            for (int i = I - 2; i > 0; i--) {
                u[i][k + 1] = ComplexNumber.add(ComplexNumber.multiply(alpha[i + 1], u[i + 1][k + 1]), beta[i + 1][k + 1]);
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


    private ComplexNumber getAlpha(int i, ComplexNumber[] alpha) {
        ComplexNumber div = ComplexNumber.add(ComplexNumber.multiply(A(i), alpha[i - 1]), B);
        return ComplexNumber.multiplyOnNumber(ComplexNumber.divide(C(i), div), -1);//ComplexNumber.multiplyOnNumber(ComplexNumber.divide(B, A(i)), -1);
    }

    private ComplexNumber getBeta(int i, int k, ComplexNumber[][] u, ComplexNumber[] alpha, ComplexNumber[][] beta) {
        ComplexNumber div = ComplexNumber.add(ComplexNumber.multiply(A(i), alpha[i - 1]), B);
        ComplexNumber a1 = ComplexNumber.multiply(D, u[i][k]);
        ComplexNumber a2 = ComplexNumber.multiply(C(i), u[i + 1][k]);
        ComplexNumber a3 = ComplexNumber.multiply(A(i), ComplexNumber.subtract(u[i - 1][k], beta[i - 1][k + 1]));
        ComplexNumber a = ComplexNumber.subtract(ComplexNumber.subtract(a1, a2), a3);
        return ComplexNumber.divide(a, div);
    }

    private ComplexNumber A(int i) {
        return ComplexNumber.subtract(gamma, mu(i));
    }

    private ComplexNumber F(int i, int k, ComplexNumber[][] u) {
        ComplexNumber uk = ComplexNumber.add(ComplexNumber.subtract(u[i + 1][k], ComplexNumber.multiplyOnNumber(u[i][k], 2)), u[i - 1][k]);
        ComplexNumber uk1 = ComplexNumber.subtract(u[i + 1][k], u[i - 1][k]);
        ComplexNumber ukg = ComplexNumber.multiply(gamma, uk);
        ComplexNumber uk1m = ComplexNumber.multiply(mu(i), uk1);
        return ComplexNumber.subtract(ComplexNumber.subtract(u[i][k], ukg), uk1m);
    }

    private ComplexNumber C(int i) {
        return ComplexNumber.add(gamma, mu(i));
    }

    /*private ComplexNumber getAlpha(int i, ComplexNumber beta[][], ComplexNumber alpha[]) {
        ComplexNumber alphac;
        if (i == 1) {
            alphac = ComplexNumber.multiplyOnNumber(
                    ComplexNumber.divide(
                            ComplexNumber.add(mu(1), gamma),
                            oneMinus2gamma),
                    -1);
        } else {
            ComplexNumber a1 = ComplexNumber.subtract(gamma, mu(i));
            ComplexNumber a2 = ComplexNumber.add(oneMinus2gamma, ComplexNumber.multiply(a1, alpha[i-1]));
            alphac = ComplexNumber.multiplyOnNumber(ComplexNumber.divide(ComplexNumber.add(mu(i), gamma), a2), -1);
        }
        return alphac;
    }

    private ComplexNumber getBeta(int i, int k, ComplexNumber u[][], ComplexNumber beta[][], ComplexNumber alpha[]) {
        ComplexNumber betac;
        if (i == 1) {
            ComplexNumber a1 = ComplexNumber.subtract(u[2][k], ComplexNumber.multiplyOnNumber(u[1][k], -2));
            ComplexNumber a2 = ComplexNumber.multiply(gamma, a1);
            ComplexNumber a3 = ComplexNumber.multiply(mu(1), u[2][k]);
            betac = ComplexNumber.subtract(ComplexNumber.subtract(u[1][k], a2), a3);
        } else {
            ComplexNumber a1 = ComplexNumber.subtract(gamma, mu(i));
            ComplexNumber a2 = ComplexNumber.add(oneMinus2gamma, ComplexNumber.multiply(a1, alpha[i - 1]));

            ComplexNumber a3 = ComplexNumber.multiply(ComplexNumber.add(ComplexNumber.subtract(u[i + 1][k], u[i][k]), u[i - 1][k]), gamma);
            ComplexNumber a4 = ComplexNumber.multiply(mu(i), ComplexNumber.subtract(u[i + 1][k], u[i - 1][k]));
            ComplexNumber a5 = ComplexNumber.multiply(a1, beta[i - 1][k + 1]);

            betac = ComplexNumber.divide(ComplexNumber.subtract(ComplexNumber.subtract(ComplexNumber.subtract(u[i][k], a3), a4), a5), a2);
        }
        return betac;
    }*/

}
