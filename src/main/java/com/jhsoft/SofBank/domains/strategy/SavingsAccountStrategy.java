package com.jhsoft.SofBank.domains.strategy;

import com.jhsoft.SofBank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import org.springframework.stereotype.Component;

@Component(value = "savingsAccountStrategy")
public class SavingsAccountStrategy implements ICalculationInterestStrategy {

    @Override
    public double calculationInterest(double balance, double rateInterest) {
        return balance * rateInterest;
    }
}
