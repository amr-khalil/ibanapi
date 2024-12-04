package com.example.ibanapi.ibanvalidation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ibanapi.ibanvalidation.model.Iban;

public interface IbanRepository extends JpaRepository<Iban, Long> {

}
