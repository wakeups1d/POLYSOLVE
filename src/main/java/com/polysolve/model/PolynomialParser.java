package com.polysolve.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

 * PolynomialParser
 * Converts human-typed text into a Polynomial object.
 * Supports two input styles:
 *   1. Natural expression:      "3x^2 + 2x - 1"   or   "x^3 - x"
 *   2. Comma-separated coeffs:  "3,2,-1"  (highest degree first)
 
 */
public final class PolynomialParser {

    private static final Pattern TERM_PATTERN =
            Pattern.compile("([+-]?)\\s*(\\d*\\.?\\d*)\\s*(x(?:\\^(\\d+))?)?");



    public static Polynomial parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new PolySolveParseException("Input is empty. Try something like 3x^2 + 2x + 1");
        }

        String cleaned = input.replaceAll("\\s+", "");

        if (cleaned.contains(",")) {
            return parseCoefficientList(cleaned);
        }

        return parseExpression(cleaned);
    }

    private static Polynomial parseCoefficientList(String cleaned) {
        String[] parts = cleaned.split(",");
        double[] coeffs = new double[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) {
                coeffs[parts.length - 1 - i] = Double.parseDouble(parts[i]);
            }
        } catch (NumberFormatException e) {
            throw new PolySolveParseException("Could not read coefficient list: " + cleaned);
        }
        return new Polynomial(coeffs);
    }

    private static Polynomial parseExpression(String cleaned) {
        List<Double> coeffsByPower = new ArrayList<>();
        Matcher matcher = TERM_PATTERN.matcher(cleaned);

        boolean matchedAnyTerm = false;

        while (matcher.find()) {
            String signStr = matcher.group(1);
            String coeffStr = matcher.group(2);
            String xPart = matcher.group(3);
            String powerStr = matcher.group(4);

            boolean hasSign = signStr != null && !signStr.isEmpty();
            boolean hasCoeff = coeffStr != null && !coeffStr.isEmpty();
            boolean hasX = xPart != null && !xPart.isEmpty();

            if (!hasSign && !hasCoeff && !hasX) {
                continue; 
            }

            double sign = "-".equals(signStr) ? -1.0 : 1.0;
            int power;
            double coeff;

            if (hasX) {
                power = (powerStr != null && !powerStr.isEmpty()) ? Integer.parseInt(powerStr) : 1;
                coeff = hasCoeff ? Double.parseDouble(coeffStr) : 1.0;
            } else {
                if (!hasCoeff) {
                    continue;
                }
                power = 0;
                coeff = Double.parseDouble(coeffStr);
            }

            coeff *= sign;

            while (coeffsByPower.size() <= power) {
                coeffsByPower.add(0.0);
            }
            coeffsByPower.set(power, coeffsByPower.get(power) + coeff);
            matchedAnyTerm = true;
        }

        if (!matchedAnyTerm) {
            throw new PolySolveParseException(
                    "Could not understand \"" + cleaned + "\". Try a format like 3x^2+2x+1");
        }

        double[] coeffs = new double[coeffsByPower.size()];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = coeffsByPower.get(i);
        }
        return new Polynomial(coeffs);
    }

    /*

     * Thrown when user input cannot be parsed into a valid polynomial.
     * Extends RuntimeException so callers only need to catch it, not declare it.
     
     */
    public static class PolySolveParseException extends RuntimeException {
        public PolySolveParseException(String message) {
            super(message);
        }
    }
}
