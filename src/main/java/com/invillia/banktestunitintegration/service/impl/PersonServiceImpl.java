package com.invillia.banktestunitintegration.service.impl;

import com.invillia.banktestunitintegration.domain.Person;
import com.invillia.banktestunitintegration.domain.request.PersonRequest;
import com.invillia.banktestunitintegration.domain.response.PersonResponse;
import com.invillia.banktestunitintegration.exception.GenericNotFoundException;
import com.invillia.banktestunitintegration.mapper.PersonMapper;
import com.invillia.banktestunitintegration.repository.PersonRepository;
import com.invillia.banktestunitintegration.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }


    @Transactional(readOnly = true)
    public List<PersonResponse> find(final PersonRequest personRequestFilter) {

        Person personFilter = personMapper.personRequestToPerson(personRequestFilter);

        final Example examplePerson = Example.of(personFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        final List<Person> persons = personRepository.findAll(examplePerson);

        return personMapper.personToPersonResponse(persons);
    }


    public PersonResponse findById(final Long id) {
        final Person person = personRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(
                "Pessoa de ID " + id + " não encontrada!"));

        return personMapper.personToPersonResponse(person);
    }


    public Long update(final Long id, final PersonRequest personRequest) {

        final Person person = personRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(
                "Pessoa de ID " + id + " não encontrada!"));

        personMapper.updatePersonByPersonRequest(person, personRequest);

        final Person personSaved = personRepository.save(person);

        return personSaved.getId();
    }


    public void delete(final Long id) {
        final Person person = personRepository.findById(id).orElseThrow(() -> new GenericNotFoundException(
                "Pessoa de ID " + id + " não encontrada!"));

        personRepository.delete(person);
    }


    public Long save(final PersonRequest personRequest) {

        final Person person = personMapper.personRequestToPerson(personRequest);

        final Person personSaved = personRepository.save(person);

        return personSaved.getId();
    }
}
