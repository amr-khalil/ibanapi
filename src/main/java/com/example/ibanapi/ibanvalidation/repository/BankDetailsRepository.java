package com.example.ibanapi.ibanvalidation.repository;

import com.example.ibanapi.ibanvalidation.model.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailsRepository extends JpaRepository<BankDetails, String> {
}
