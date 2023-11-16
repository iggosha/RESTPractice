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

    public void selectMode(String arg) {
        try {
            switch (arg) {
                // id (1)
                case "-get_id" -> serviceGetById();
                // sortField (name)
                case "-get_all" -> serviceGetAll();
                // voltage (4.5)
                case "-get_volt" -> serviceGetAmountByVoltage();
                // fileName (mcs.json)
                case "-post_file" -> serviceCreateNewByList(1);
                // name (mc8051)
                // frameType (ft5180)
                // voltage (4.5)
                // price (30_000)
                case "-post_cmd" -> serviceCreateNewByList(2);
                // former (ft5180)
                // new (ft1508)
                case "-put_ft" -> serviceReplaceFrameType();
                // id (1)
                case "-del_id" -> serviceDeleteById();
                default -> throw new IllegalArgumentException("Неверная команда меню");
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при работе программы, проверьте правильность ввода");
            System.err.println("Краткие сведения об ошибке: " + e.getMessage());
        }
    }

    private void serviceGetById() {
        System.out.println("Выбран режим: Вывести запись о микросхеме по id");
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
        System.out.println("Выбран режим: Вывести с сортировкой все записи о микросхемах");
        String sortField = "";
        try {
            System.out.print("Опционально: введите название атрибута для сортировки (id/name/frameType/voltage/price): ");
            sortField = scannerUtils.readString().toLowerCase();
            if (!sortField.equals("id") && !sortField.equals("name") && !sortField.equals("frametype")
                    && !sortField.equals("voltage") && !sortField.equals("price") && !sortField.isBlank()) {
                throw new IllegalArgumentException("Неверный параметр, проверьте атрибут для сортировки " + sortField);
            }
            List<Microchip> microchipList = microchipClient.getAll(sortField);
            microchipList.forEach(System.out::println);
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException("Неверный параметр, проверьте атрибут для сортировки " + sortField);
        }
    }

    private void serviceGetAmountByVoltage() {
        System.out.println("Выбран режим: Вывести количество записей о микросхемах где напряжение больше X вольт");
        System.out.print("Опционально: введите нижний порог значения напряжения: ");
        Double lowestVoltage = scannerUtils.tryToReadDoubleForField();
        Long mcWithVoltageAmount = microchipClient.getAmountByVoltage(lowestVoltage);
        if (mcWithVoltageAmount == 0) {
            System.out.println("Микросхем c нижним порогом напряжения " + lowestVoltage + "V не найдено, проверьте правильность ввода");
        } else {
            System.out.print("Подходящих записей о микросхемах найдено: " + mcWithVoltageAmount);
        }
    }

    private void serviceCreateNewByList(Integer source) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Microchip> microchipList = new ArrayList<>();
        if (source == 1) {
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
        System.out.println("Выбран режим получения данных из файла");
        System.out.print("Введите путь к файлу: ");
        List<Microchip> microchipList;
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
        System.out.println("Выбран режим: Заменить в записи существующий тип корпуса микросхемы на новый");
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
        System.out.println("Выбран режим: Удалить микросхему по id");
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