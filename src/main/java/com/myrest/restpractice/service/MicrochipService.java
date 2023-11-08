package com.myrest.restpractice.service;

import com.myrest.restpractice.excs.InvalidRequestParametersException;
import com.myrest.restpractice.excs.MicrochipNotFoundException;
import com.myrest.restpractice.model.Microchip;
import com.myrest.restpractice.repository.JsonRepositoryJacksonImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MicrochipService {

    @Autowired
    private JsonRepositoryJacksonImpl jacksonRepository;

    private Long idCounter = 1L;

    public Microchip getById(Long id) {
        List<Microchip> microchipList = jacksonRepository.getListFromJson();
        Microchip microchip = getMcFromListById(microchipList, id);
        return microchip;
    }

    public List<Microchip> getAll(String sortField) {
        List<Microchip> microchipList = jacksonRepository.getListFromJson();
        microchipList = switch (sortField) {
            case "", "id" -> microchipList
                    .stream()
                    .sorted(Comparator.comparingLong(Microchip::getId))
                    .toList();
            case "frametype" -> microchipList
                    .stream()
                    .sorted(Comparator.comparing(Microchip::getFrameType))
                    .toList();
            case "name" -> microchipList
                    .stream()
                    .sorted(Comparator.comparing(Microchip::getName))
                    .toList();
            case "price" -> microchipList
                    .stream()
                    .sorted(Comparator.comparingInt(Microchip::getPrice))
                    .toList();
            case "voltage" -> microchipList
                    .stream()
                    .sorted(Comparator.comparingDouble(Microchip::getVoltage))
                    .toList();
            default -> throw new InvalidRequestParametersException(sortField);
        };
        return microchipList;
    }

    public Long getAmountByVoltage(double voltage) {
        List<Microchip> microchipList = jacksonRepository.getListFromJson();
        Long mcWithVoltageAmount = microchipList
                .stream()
                .filter(microchip -> microchip.getVoltage() >= voltage)
                .count();
        return mcWithVoltageAmount;
    }

    public List<Microchip> createNewByList(List<Microchip> microchipList) {
        List<Microchip> microchipListCurr = jacksonRepository.getListFromJson();
        idCounter = microchipListCurr
                .stream()
                .mapToLong(Microchip::getId)
                .max()
                .orElse(1L);
        microchipList = microchipList
                .stream()
                .peek(microchip -> microchip.setId(++idCounter))
                .toList();
        microchipListCurr.addAll(microchipList);
        jacksonRepository.putListToJson(microchipListCurr);
        return microchipListCurr;
    }

    public List<Microchip> replaceFrameType(String formerFrameType, String newFrameType, Boolean printOnlyReplaced) {

        List<Microchip> fullMicrochipList = jacksonRepository.getListFromJson();
        List<Microchip> replacedMicrochipList = fullMicrochipList
                .stream()
                .filter(microchip -> microchip.getFrameType().equals(formerFrameType))
                .peek(microchip -> microchip.setFrameType(newFrameType))
                .toList();

            fullMicrochipList = fullMicrochipList
                    .stream()
                    .peek(microchip -> {
                        if (microchip.getFrameType().equals(formerFrameType)) {
                            microchip.setFrameType(newFrameType);
                        }
                    })
                    .toList();
            jacksonRepository.putListToJson(fullMicrochipList);

        return printOnlyReplaced ? replacedMicrochipList : fullMicrochipList;
    }

    public void deleteById(Long id) {
        List<Microchip> microchipList = jacksonRepository.getListFromJson();
        Microchip microchip = getMcFromListById(microchipList, id);
        microchipList.remove(microchip);
        jacksonRepository.putListToJson(microchipList);
    }

    private Microchip getMcFromListById(List<Microchip> microchipList, Long id) {
        return microchipList
                .stream()
                .filter(microchipItem -> microchipItem.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MicrochipNotFoundException(id));
    }
}
