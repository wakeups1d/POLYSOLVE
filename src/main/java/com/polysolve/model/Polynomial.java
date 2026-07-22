package com.polysolve.model;

import java.util.Arrays;

/**
 * Polynomial
 * Immutable representation of a single-variable polynomial.
 * coefficients[i] is the coefficient of x^i (index = power).
 */
public class Polynomial {

    private final double[] coefficients;

    public Polynomial(double[] coefficients) {
        if (coefficients == null || coefficients.length == 0) {
            this.coefficients = new double[]{0.0};
        } else {
            this.coefficients = trim(coefficients);
        }
    }

    private double[] trim(double[] arr) {
        int last = arr.length - 1;
        while (last > 0 && arr[last] == 0.0) {
            last--;
        }
        return Arrays.copyOf(arr, last + 1);
    }

    public int degree() {
        return coefficients.length - 1;
    }

    public double getCoefficient(int power) {
        if (power < 0 || power >= coefficients.length) return 0.0;
        return coefficients[power];
    }

    // ---------------- ALGEBRA ----------------

    public Polynomial add(Polynomial other) {
        int len = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[len];
        for (int i = 0; i < len; i++) {
            result[i] = this.getCoefficient(i) + other.getCoefficient(i);
        }
        return new Polynomial(result);
    }

    public Polynomial subtract(Polynomial other) {
        int len = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[len];
        for (int i = 0; i < len; i++) {
            result[i] = this.getCoefficient(i) - other.getCoefficient(i);
        }
        return new Polynomial(result);
    }

    public Polynomial multiply(Polynomial other) {
        int len = this.coefficients.length + other.coefficients.length - 1;
        double[] result = new double[len];
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                result[i + j] += this.coefficients[i] * other.coefficients[j];
            }
        }
        return new Polynomial(result);
    }

    // ---------------- EVALUATION (Horner's method, O(n)) ----------------

    public double evaluate(double x) {
        double result = 0.0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            result = result * x + coefficients[i];
        }
        return result;
    }

    // ---------------- CALCULUS ----------------

    public Polynomial differentiate() {
        if (coefficients.length <= 1) {
            return new Polynomial(new double[]{0.0});
        }
        double[] result = new double[coefficients.length - 1];
        for (int i = 1; i < coefficients.length; i++) {
            result[i - 1] = coefficients[i] * i;
        }
        return new Polynomial(result);
    }

    public Polynomial integrate(double constantC) {
        double[] result = new double[coefficients.length + 1];
        result[0] = constantC;
        for (int i = 0; i < coefficients.length; i++) {
            result[i + 1] = coefficients[i] / (i + 1);
        }
        return new Polynomial(result);
    }

    public double definiteIntegral(double a, double b) {
        Polynomial antiderivative = this.integrate(0.0);
        return antiderivative.evaluate(b) - antiderivative.evaluate(a);
    }

    // ---------------- DISPLAY ----------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean firstTerm = true;

        for (int i = coefficients.length - 1; i >= 0; i--) {
            double coeff = coefficients[i];
            if (coeff == 0.0 && coefficients.length > 1) continue;

            if (!firstTerm) {
                sb.append(coeff < 0 ? " - " : " + ");
            } else if (coeff < 0) {
                sb.append("-");
            }

            double absCoeff = Math.abs(coeff);

            if (i == 0) {
                sb.append(formatNumber(absCoeff));
            } else if (i == 1) {
                if (absCoeff != 1.0) sb.append(formatNumber(absCoeff));
                sb.append("x");
            } else {
                if (absCoeff != 1.0) sb.append(formatNumber(absCoeff));
                sb.append("x^").append(i);
            }
            firstTerm = false;
        }

        if (sb.length() == 0) sb.append("0");
        return sb.toString();
    }

    private String formatNumber(double num) {
        if (num == Math.floor(num) && !Double.isInfinite(num)) {
            return String.valueOf((long) num);
        }
        return String.valueOf(Math.round(num * 1000.0) / 1000.0);
    }
}
