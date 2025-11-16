package com.autonoma.service;

import com.autonoma.dto.PersonalRequest;
import com.autonoma.dto.PersonalResponse;
import com.autonoma.model.Personal;

import java.util.List;

public interface PersonalService {

    PersonalResponse save(PersonalRequest request);
    PersonalResponse update(Integer id, PersonalRequest request);
    List<PersonalResponse> findAll();
    PersonalResponse findById(Integer id);
    void delete(Integer id);

}
