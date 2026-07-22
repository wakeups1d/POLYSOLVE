package com.polysolve.controller;

import com.polysolve.model.Polynomial;
import com.polysolve.model.PolynomialParser;

/*

 * PolySolveController - Controller of the POLYSOLVE application.
 * It receives requests from the GUI, performs the required
 * polynomial operations, and returns the results.
 * This keeps the GUI and business logic separate.
 
*/
public class PolySolveController {

    public Polynomial parse(String rawInput) {
        return PolynomialParser.parse(rawInput);
    }

    public Polynomial add(String rawA, String rawB) {
        return parse(rawA).add(parse(rawB));
    }

    public Polynomial subtract(String rawA, String rawB) {
        return parse(rawA).subtract(parse(rawB));
    }

    public Polynomial multiply(String rawA, String rawB) {
        return parse(rawA).multiply(parse(rawB));
    }

    public double evaluate(String rawA, String rawX) {
        double x = parseNumber(rawX, "x");
        return parse(rawA).evaluate(x);
    }

    public Polynomial differentiate(String rawA) {
        return parse(rawA).differentiate();
    }

    public Polynomial integrate(String rawA, String rawC) {
        double c = rawC == null || rawC.trim().isEmpty() ? 0.0 : parseNumber(rawC, "C");
        return parse(rawA).integrate(c);
    }

    public double definiteIntegral(String rawA, String rawLower, String rawUpper) {
        double lower = parseNumber(rawLower, "a");
        double upper = parseNumber(rawUpper, "b");
        return parse(rawA).definiteIntegral(lower, upper);
    }

    private double parseNumber(String raw, String fieldName) {
        try {
            return Double.parseDouble(raw.trim());
        } catch (Exception e) {
            throw new PolynomialParser.PolySolveParseException(
                    "\"" + raw + "\" is not a valid number for " + fieldName);
        }
    }
}
