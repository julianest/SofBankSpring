package com.jhsoft.sofbank.domains.strategy;

import com.jhsoft.sofbank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import org.springframework.stereotype.Component;

@Component(value = "savingsAccountStrategy")
public class SavingsAccountStrategy implements ICalculationInterestStrategy {

    @Override
    public double calculationInterest(double balance, double rateInterest) {
        return balance * rateInterest;
    }
}
