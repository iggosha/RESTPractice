package com.myrest.restpractice.controller;

import com.myrest.restpractice.dto.Microscheme;
import com.myrest.restpractice.excs.Exc404;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private List<Microscheme> microschemaCatalog = new ArrayList<>(List.of(
            new Microscheme(1, "micro1","frame1", 5.5, 1200),
            new Microscheme(2, "micro2","frame2", 4.5, 1100)
    ));

    @GetMapping("/")
    public List<Microscheme> getWholeBook() {
        return microschemaCatalog;
    }

    @GetMapping("/{id}")
    public Microscheme getContact(@PathVariable long ud) {

        return ;
    }


    private Microscheme findContact(String num) {
        return microschemaCatalog.stream()
                .filter(contact -> contact.getNumber().equals(num))
                .findFirst()
                .orElseThrow(Exc404::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void postContact(@RequestBody Microscheme contact) {
        microschemaCatalog.add(contact);
    }

    @PutMapping("/{number}")
    public String putContact(@PathVariable String number, @RequestBody Microscheme contact) {
        Microscheme contactFromPB = findContact(contact.getNumber());
        contactFromPB.setNumber(contact.getNumber());
        return contactFromPB.getNumber();
    }
}
