package com.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.loans.config.LoansServiceConfig;
import com.loans.model.Customer;
import com.loans.model.Loans;
import com.loans.model.Properties;
import com.loans.repository.LoansRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private final LoansRepository loansRepository;
    private final LoansServiceConfig loansServiceConfig;

    public LoansController(LoansRepository loansRepository, LoansServiceConfig loansServiceConfig) {

        this.loansRepository = loansRepository;
        this.loansServiceConfig = loansServiceConfig;
    }

    @PostMapping("/loans")
    public List<Loans> getLoansDetails(@RequestBody Customer customer) {
        return loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
    }

    @GetMapping("/loan/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        Properties properties = new Properties(
            loansServiceConfig.getMsg(),
            loansServiceConfig.getBuildVersion(),
            loansServiceConfig.getMailDetails(),
            loansServiceConfig.getActiveBranches()
        );

        return ow.writeValueAsString(properties);
    }
}
