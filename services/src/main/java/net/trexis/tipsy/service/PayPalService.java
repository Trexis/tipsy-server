package net.trexis.tipsy.service;

import org.springframework.stereotype.Service;

@Service
public class PayPalService implements FinancialInstitutionService {

    @Override
    public String getAccessToken() {
        return null;
    }
}
