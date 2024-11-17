package com.mynewuber.project.uber.UberApp.strategies;

import org.springframework.stereotype.Component;

import com.mynewuber.project.uber.UberApp.entities.enums.PaymentMethod;
import com.mynewuber.project.uber.UberApp.strategies.impl.CashPaymentStrategy;
import com.mynewuber.project.uber.UberApp.strategies.impl.WalletPaymentStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager  {
	

	
	private final WalletPaymentStrategy walletPaymentStrategy;
	private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
	}
}
