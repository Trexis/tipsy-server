package net.trexis.tipsy.service.Impl;

import net.trexis.tipsy.service.FinancialInstitutionService;
import net.trexis.tipsy.service.TipsyService;
import org.springframework.stereotype.Service;

@Service
public class PayPalService implements FinancialInstitutionService, TipsyService {

    private boolean enabled;


    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
