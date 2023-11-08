package com.restclient.restpracticeclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restclient.restpracticeclient.client.MicrochipClient;
import com.restclient.restpracticeclient.model.Microchip;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MicrochipService {

    private final MicrochipClient microchipClient;
    private final ScannerUtils scannerUtils;

    public void showMenu() {
        System.out.println("""
                                
                Выберите команду:
                1 - Вывести запись о микросхеме по id
                2 - Вывести все записи о микросхемах
                3 - Вывести количество записей о микросхемах где напряжение больше X вольт
                4 - Добавить запись(-и) о микросхеме(-ах) в список
                5 - Заменить в записи существующий тип корпуса микросхемы на новый
                6 - Удалить микросхему по id
                -1 - Завершить работу программы
                """);
        try {
            selectCommand();
        } catch (Exception e) {
            System.err.println("Произошла ошибка при работе программы, проверьте правильность ввода");
            System.err.println("Краткие сведения об ошибке: " + e.getMessage());
            showMenu();
        }
    }

    private void selectCommand() {
        Integer commandNumber = scannerUtils.tryToReadInt();
        switch (commandNumber) {
            case 1 -> serviceGetById();
            case 2 -> serviceGetAll();
            case 3 -> serviceGetAmountByVoltage();
            case 4 -> serviceCreateNewByList();
            case 5 -> serviceReplaceFrameType();
            case 6 -> serviceDeleteById();
            case -1 -> System.exit(0);
            default -> throw new IllegalArgumentException("Неверная команда меню");
        }
        showMenu();
    }

    private void serviceGetById() {
        System.out.print("Введите id нужной микросхемы: ");
        Long microchipId = scannerUtils.tryToReadLong();
        Microchip microchip;
        try {
            microchip = microchipClient.getById(microchipId);
        } catch (FeignException.NotFound e) {
            System.out.println("Микросхема с введённым id не найдена, проверьте правильность ввода");
            return;
        }
        System.out.println(microchip);
    }

    private void serviceGetAll() {
        String sortField = "";
        try {
            System.out.print("Опционально: введите название атрибута для сортировки (id/name/frameType/voltage/price): ");
            sortField = scannerUtils.readString().toLowerCase();
            List<Microchip> microchipList = microchipClient.getAll(sortField);
            microchipList.forEach(System.out::println);
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException("Неверный параметр, проверьте атрибут для сортировки " + sortField);
        }
    }

    private void serviceGetAmountByVoltage() {
        System.out.print("Опционально: введите нижний порог значения напряжения: ");
        Double lowestVoltage = scannerUtils.tryToReadDoubleForField();
        Long mcWithVoltageAmount = microchipClient.getAmountByVoltage(lowestVoltage);
        if (mcWithVoltageAmount == 0) {
            System.out.println("Микросхем c нижним порогом напряжения " + lowestVoltage + "V не найдено, проверьте правильность ввода");
        } else {
            System.out.print("Подходящих записей о микросхемах найдено: " + mcWithVoltageAmount);
        }
    }


    private void serviceCreateNewByList() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Microchip> microchipList = new ArrayList<>();
        System.out.println("Введите '1', чтобы ввести данные о новой микросхеме с помощью консоли");
        System.out.println("Нажмите Enter, если требуется передать данные с помощью внешнего файла");
        Double readFromConsoleIfNotFive = scannerUtils.tryToReadDoubleForField();
        if (readFromConsoleIfNotFive == 5) {
            microchipList = getMicrochipListFromFile(objectMapper);
        } else {
            addMicrochipToListFromConsole(microchipList);
        }
        microchipClient.createNewByList(microchipList);
        System.out.println("Микросхема(-ы) успешно добавлена(-ы)");
    }

    private void addMicrochipToListFromConsole(List<Microchip> microchipList) {
        System.out.println("Выбран режим получения данных из консоли");
        System.out.print("Введите название (name): ");
        String name = scannerUtils.readString();
        System.out.print("Введите типа корпуса (frameType): ");
        String frameType = scannerUtils.readString();
        System.out.print("Введите напряжение (voltage): ");
        Double voltage = scannerUtils.tryToReadDouble();
        System.out.print("Введите цену (price): ");
        Integer price = scannerUtils.tryToReadInt();
        Microchip microchip = Microchip
                .builder()
                .name(name)
                .frameType(frameType)
                .voltage(voltage)
                .price(price)
                .build();
        microchipList.add(microchip);
    }

    private List<Microchip> getMicrochipListFromFile(ObjectMapper objectMapper) {
        List<Microchip> microchipList;
        System.out.println("Выбран режим получения данных из файла");
        System.out.print("Введите путь к файлу: ");
        String filePath = scannerUtils.readString();
        File file = new File(filePath);
        try {
            microchipList = objectMapper
                    .readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Microchip.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return microchipList;
    }

    private void serviceReplaceFrameType() {
        System.out.print("Введите тип корпуса, который требуется заменить:");
        String formerFrameType = scannerUtils.readString();
        System.out.print("Введите заменяющий тип корпуса: ");
        String newFrameType = scannerUtils.readString();
        System.out.println("Опционально: Введите '1', если требуется вывести только записи с заменённым типом корпуса");
        Double printOnlyReplacedIfNotFive = scannerUtils.tryToReadDoubleForField();

        List<Microchip> microchipList = microchipClient
                .replaceFrameType(formerFrameType, newFrameType, printOnlyReplacedIfNotFive != 5);
        try {
            microchipList.forEach(System.out::println);
        } catch (NullPointerException e) {
            System.out.println("Список микросхем с соответствующим типом корпуса пуст, проверьте правильность ввода");
        }
    }

    private void serviceDeleteById() {
        System.out.print("Введите id микросхемы, которую требуется удалить: ");
        Long id = scannerUtils.tryToReadLong();
        try {
            microchipClient.deleteById(id);
        } catch (FeignException.NotFound e) {
            System.out.println("Микросхема с введённым id не найдена, проверьте правильность ввода");
            return;
        }
        System.out.println("Микросхема удалена");
    }
}