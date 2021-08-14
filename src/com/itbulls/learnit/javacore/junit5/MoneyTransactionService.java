package com.itbulls.learnit.javacore.junit5;

public class MoneyTransactionService {
public boolean transferMoney(Account accountFrom, Account accountTo, double moneyAmountToSend) throws NotEnoughMoneyException {
	
	if(accountFrom==null || accountTo==null) {
		throw new IllegalArgumentException("Account should not be null");
	}
	if(moneyAmountToSend<=0) {
		throw new IllegalArgumentException("Money Amount should be greater than zero");
	}
	if(accountFrom.getMoneyAmount()<moneyAmountToSend) {
		throw new NotEnoughMoneyException();
	}
	
	accountFrom.setMoneyAmount(accountFrom.getMoneyAmount()-moneyAmountToSend);
	accountTo.setMoneyAmount(accountTo.getMoneyAmount()+moneyAmountToSend);
	
	return true;
}
}
