package com.example.application.complex;

public class ComplexNumber {

    private double real;
    private double imagine;

    public static ComplexNumber REAL_ONE = new ComplexNumber(1,0);
    public static ComplexNumber IMAGINE_ONE = new ComplexNumber(0,1);

    public ComplexNumber(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImagine() {
        return imagine;
    }

    public void setImagine(double imagine) {
        this.imagine = imagine;
    }

    public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
        double real = z1.getReal() + z2.getReal();
        double imagine = z1.getImagine() + z2.getImagine();
        return new ComplexNumber(real, imagine);
    }

    public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2) {
        double real = z1.getReal() - z2.getReal();
        double imagine = z1.getImagine() - z2.getImagine();
        return new ComplexNumber(real, imagine);
    }

    public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
        double real = z1.getReal() * z2.getReal() - z1.getImagine() * z2.getImagine();
        double imagine = z1.getImagine() * z2.getReal() + z1.getReal() * z2.getImagine();
        return new ComplexNumber(real, imagine);
    }

    public static ComplexNumber multiplyOnNumber(ComplexNumber z1, double number) {
        double real = z1.getReal() * number;
        double imagine = z1.getImagine() * number;
        return new ComplexNumber(real, imagine);
    }

    public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
        double real = (z1.getReal() * z2.getReal() + z1.getImagine() * z2.getImagine()) / (z2.getReal() * z2.getReal() + z2.getImagine() * z2.getImagine());
        double imagine = (z1.getImagine() * z2.getReal() - z1.getReal() * z2.getImagine()) / (z2.getReal() * z2.getReal() + z2.getImagine() * z2.getImagine());
        return new ComplexNumber(real, imagine);
    }

    public static double module(ComplexNumber z) {
        return Math.sqrt(z.getReal() * z.getReal() + z.getImagine() * z.getImagine());
    }

}
