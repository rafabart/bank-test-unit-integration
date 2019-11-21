package com.invillia.banktestunitintegration.service;

import com.invillia.banktestunitintegration.domain.request.PersonRequest;
import com.invillia.banktestunitintegration.domain.response.PersonResponse;

import java.util.List;

public interface PersonService {

    public List<PersonResponse> find(final PersonRequest personRequestFilter);

    public PersonResponse findById(final Long id);

    public Long update(final Long id, final PersonRequest personRequest);

    public void delete(final Long id);

    public Long save(final PersonRequest personRequest);

}
