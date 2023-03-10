package com.example.application.numericsol.implicit;

import com.example.application.complex.ComplexNumber;
import com.example.application.numericsol.SchemaSolver;
import com.example.application.numericsol.SweepSolver;

public class ImplicitSchemaSolver implements SchemaSolver {

    private final double hz;
    private final double hr;
    private final double kNumber;
    private final double n;

    private final double R;

    private final int I;

    private final int K;

    private final ComplexNumber gamma;

    private final ComplexNumber B;

    public ImplicitSchemaSolver(int I, int K, double kNumber, double n, double R, double L) {
        this.I = I;
        this.K = K;
        this.kNumber = kNumber;
        this.n = n;
        this.R = R;

        this.hz = L / K;
        this.hr = R / I;

        this.gamma = gamma();
        this.B = ComplexNumber.subtract(ComplexNumber.REAL_ONE, ComplexNumber.multiplyOnNumber(gamma, 2));
    }

    @Override
    public ComplexNumber[][] solve() {
        ComplexNumber[][] u = new ComplexNumber[I][K + 1];
        for (int i = 0; i < I; i++) {
            u[i][0] = new ComplexNumber(psi(i), 0); // задаем на слое k = 0
        }

        for (int k = 1; k < K + 1; k++) {
            for (int i = 0; i < I; i++) {
                u[i][k] = new ComplexNumber(0, 0); // инициализируем всю сетку нулями
            }
        }
        for (int k = 1; k < K; k++) {
            // задаем вектора констант
            ComplexNumber[] a = new ComplexNumber[I + 1];
            ComplexNumber[] b = new ComplexNumber[I + 1];
            ComplexNumber[] c = new ComplexNumber[I + 1];
            ComplexNumber[] f = new ComplexNumber[I + 1];

            // инициализация на нулевом слое констант
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
                f[i] = u[i][k - 1];
            }
            f[I - 1] = new ComplexNumber(0, 0);
            ComplexNumber[] uk = SweepSolver.run(a, b, c, f);
            for (int i = 0; i < I; i++) {
                u[i][k] = uk[i];
            }
        }
        return u;
    }

    private ComplexNumber gamma() {
        double imagine = hz / 2 / hr / hr / kNumber / n;
        return new ComplexNumber(0, imagine);
    }

    private ComplexNumber mu(int i) {
        double ri = i * hr;
        double imagine = hz / 4 / hr / kNumber / n / ri;
        return new ComplexNumber(0, imagine);
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
