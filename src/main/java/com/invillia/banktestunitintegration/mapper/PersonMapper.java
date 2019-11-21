package com.invillia.banktestunitintegration.mapper;

import com.invillia.banktestunitintegration.domain.Person;
import com.invillia.banktestunitintegration.domain.request.PersonRequest;
import com.invillia.banktestunitintegration.domain.response.PersonResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public PersonResponse personToPersonResponse(final Person person) {

        return PersonResponse.builder()
                .id(person.getId())
                .cpf(person.getCpf())
                .name(person.getName())
                .createdAt(person.getCreatedAt().format(formatter))
                .updatedAt(person.getUpdatedAt().format(formatter))
                .build();
    }

    public List<PersonResponse> personToPersonResponse(final List<Person> persons) {

        return persons.stream()
                .map(this::personToPersonResponse)
                .collect(Collectors.toList());
    }

    public Person personRequestToPerson(final PersonRequest personRequest) {

        Person person = new Person();
        person.setId(personRequest.getId());
        person.setCpf(personRequest.getCpf());
        person.setName(personRequest.getName());
        return person;
    }

    public void updatePersonByPersonRequest(final Person person, final PersonRequest personRequest) {

        person.setName(personRequest.getName());
        person.setCpf(personRequest.getCpf());
    }
}
