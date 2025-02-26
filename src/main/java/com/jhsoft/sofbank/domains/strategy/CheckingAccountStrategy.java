package com.jhsoft.sofbank.domains.strategy;

import com.jhsoft.sofbank.domains.strategy.Interfaces.ICalculationInterestStrategy;
import org.springframework.stereotype.Component;

@Component(value = "checkingAccountStrategy")
public class CheckingAccountStrategy implements ICalculationInterestStrategy {


    @Override
    public double calculationInterest(double balance, double rateInterest) {
        if(balance <= 499){
            return balance * rateInterest * 0.05; //Se genera penalidad por saldo Bajo.
        }
        return balance * rateInterest;
    }
}
