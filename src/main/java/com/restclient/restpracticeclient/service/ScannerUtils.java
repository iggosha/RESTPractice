package com.restclient.restpracticeclient.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerUtils {

    private static final Scanner read = new Scanner(System.in);

    public String readString() {
        return read.nextLine();
    }

    public Integer tryToReadInt() {
        int number;
        try {
            number = Integer.parseInt(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    public Long tryToReadLong() {
        long number;
        try {
            number = Long.parseLong(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    public Double tryToReadDouble() {
        double number;
        try {
            number = Double.parseDouble(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    public Double tryToReadDoubleForField() {
        double number;
        try {
            String input = read.nextLine();
            if (input.isBlank()) {
                number = 5.0;
            } else {
                number = Double.parseDouble(input);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }
}
