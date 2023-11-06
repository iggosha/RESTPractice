package com.restclient.restpracticeclient.service;

import com.restclient.restpracticeclient.client.MicrochipClient;
import com.restclient.restpracticeclient.model.Microchip;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class MicrochipService {

    private final MicrochipClient microchipClient;

    private static final Scanner read = new Scanner(System.in);

    public void showMenu() {
        System.out.println("""
                                
                Выберите команду:
                1 - Вывести запись о микросхеме по id
                2 - Вывести все записи о микросхемах
                3 - Вывести количество записей о микросхемах где напряжение больше X вольт
                4 - Добавить запись(-и) о микросхеме(-ах) в список
                5 - Заменить в записи существующий тип корпуса микросхемы на новый
                6 - Удалить микросхему по id
                """);
        selectCommand();
    }

    private void selectCommand() {
        int commandNumber = tryToReadInt();
        switch (commandNumber) {
            case 1 -> {
                System.out.println("Введите id нужной микросхемы:");
                long mcNumber = tryToReadLong();
                Microchip microchip;
                try {
                    microchip = microchipClient.getById(mcNumber);
                } catch (FeignException.NotFound e) {
                    System.out.println("Микросхема с введённым id не найдена");
                    break;
                }
                System.out.println(microchip);
            }
            case 2 -> {
                System.out.println("""
                        Если требуется, введите название атрибута для сортировки:
                        name / frameType / voltage / price""");
                String sortField = read.nextLine().toLowerCase();
                List<Microchip> microchipList = microchipClient.getAll(sortField);
                microchipList.forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("""
                        Если требуется, введите нижний порог значения напряжения:""");
                double lowestVoltage = tryToReadDoubleForVoltage();
                Long mcWithVoltageAmount = microchipClient.getAmountByVoltage(lowestVoltage);
                System.out.print("Подходящих записей о микросхемах найдено: ");
                if (mcWithVoltageAmount == null) {
                    mcWithVoltageAmount = 0L;
                }
                System.out.println(mcWithVoltageAmount);
            }
            default -> throw new IllegalArgumentException("Неверная команда");
        }
        showMenu();
    }

    private int tryToReadInt() {
        int number;
        try {
            number = Integer.parseInt(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    private long tryToReadLong() {
        long number;
        try {
            number = Long.parseLong(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    private double tryToReadDouble() {
        double number;
        try {
            number = Double.parseDouble(read.nextLine());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный ввод числа");
        }
        return number;
    }

    private double tryToReadDoubleForVoltage() {
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