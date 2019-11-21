package com.invillia.banktestunitintegration.controller;

import com.invillia.banktestunitintegration.domain.request.PersonRequest;
import com.invillia.banktestunitintegration.domain.response.PersonResponse;
import com.invillia.banktestunitintegration.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public ResponseEntity find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "id", required = false) Long id
    ) {
        PersonRequest personRequestFilter = new PersonRequest();
        personRequestFilter.setId(id);
        personRequestFilter.setName(name);
        personRequestFilter.setCpf(cpf);


        List<PersonResponse> personResponseList = personService.find(personRequestFilter);
        return ResponseEntity.ok(personResponseList);
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable final Long id) {
        PersonResponse personResponse = personService.findById(id);
        return ResponseEntity.ok(personResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable final Long id,
                                 @Valid @RequestBody final PersonRequest personRequest) {

        Long idPerson = personService.update(id, personRequest);

        final URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/persons/{id}")
                .build(idPerson);

        return ResponseEntity.created(location).build();
    }


    @PostMapping
    public ResponseEntity save(@Valid @RequestBody final PersonRequest personRequest) {

        Long idPerson = personService.save(personRequest);

        final URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/persons/{id}")
                .build(idPerson);

        return ResponseEntity.created(location).build();
    }
}
