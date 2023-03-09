package com.example.application.numericsol;

import com.example.application.complex.ComplexNumber;

public class SweepSolver {

    // прогонка
    public static ComplexNumber[] run(ComplexNumber[] a, ComplexNumber[] b, ComplexNumber[] c, ComplexNumber[] f) {
        if (!(a.length == b.length && a.length == c.length && a.length == f.length)) {
            throw new IllegalArgumentException("Размеры массивов должны быть равны");
        }
        int n = a.length;
        ComplexNumber[] p = new ComplexNumber[n];
        ComplexNumber[] q = new ComplexNumber[n];
        ComplexNumber[] x = new ComplexNumber[n];
        p[0] = ComplexNumber.divide(b[0], c[0]);
        q[0] = ComplexNumber.divide(f[0], c[0]);
        for (int i = 1; i < n - 1; i++) {
            p[i] = ComplexNumber.divide(b[i], ComplexNumber.subtract(c[i], ComplexNumber.multiply(a[i], p[i - 1])));
            q[i] = ComplexNumber.divide(ComplexNumber.subtract(f[i], ComplexNumber.multiply(a[i], q[i - 1])), ComplexNumber.subtract(c[i], ComplexNumber.multiply(a[i], p[i - 1])));

        }
        int i = n - 2;
        q[i] = ComplexNumber.divide(ComplexNumber.subtract(f[i], ComplexNumber.multiply(a[i], q[i - 1])), ComplexNumber.subtract(c[i], ComplexNumber.multiply(a[i], p[i - 1])));
        x[i] = q[i];
        while (i > 0) {
            i = i - 1;
            x[i] = ComplexNumber.subtract(q[i], ComplexNumber.multiply(p[i], x[i + 1]));
        }
        return x;
    }

}
