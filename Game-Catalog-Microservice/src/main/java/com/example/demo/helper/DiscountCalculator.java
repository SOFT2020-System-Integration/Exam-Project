package com.example.demo.helper;

public class DiscountCalculator {
    public static float calculate(float low, float high) {
        return Math.round(((high - low) / high) * 100);
    }
}
