package com.itbulls.learnit.javacore.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MoneyTransactionServiceTest {

	private static final String MONEY_AMOUNT_EXCEPTION_MESSAGE = "Money ammount should be greater than zero";
	private static final String ACCOUNT_EXCEPTION_MESSAGE = "Account should not be null";
	private static final double RANDOM_MONEY_AMOUNT = 100;
	private static final double ZERO_MONEY_AMOUNT = 0;
	private static final double MORE_THAN_RANDOM_MONEY_AMOUNT = 200;
	private static final double NEGATIVE_MONEY_AMOUNT = -1;

	private static MoneyTransactionService testInstance;

	@BeforeAll
	static void setUp() {
		testInstance = new MoneyTransactionService();

	}

	@AfterEach
	void tearDown() {

	}

	@AfterAll
	static void afterAll() {

	}

	@Test
	void shouldTransferMoneyFromOneAccountToAnother() throws NotEnoughMoneyException {
		// GIVEN
		Account account1 = new Account(RANDOM_MONEY_AMOUNT);
		Account account2 = new Account(ZERO_MONEY_AMOUNT);

		// WHEN
		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
		// THEN

		assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount());
		assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount());
	}

	@Test
	void shouldThrowExceptionIfAccountFromIsNull() throws NotEnoughMoneyException {
		// Given
		Account accountFrom = null;
		Account accountTo = new Account(ZERO_MONEY_AMOUNT);
		// When

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(accountFrom, accountTo, RANDOM_MONEY_AMOUNT));

		// Then

		assertEquals(ACCOUNT_EXCEPTION_MESSAGE, exception.getMessage());
	}

	@Test
	void shouldThrowExceptionIfAccountToIsNull() throws NotEnoughMoneyException {
		// Given
		Account accountFrom = new Account(RANDOM_MONEY_AMOUNT);
		Account accountTo = null;
		// When

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(accountFrom, accountTo, RANDOM_MONEY_AMOUNT));

		// Then

		assertEquals(ACCOUNT_EXCEPTION_MESSAGE, exception.getMessage());
	}

	@Test
	void shouldThrowNotEnoughMoneyExceptionWhenTransferMoreMoney() {
		Account accountFrom = new Account(ZERO_MONEY_AMOUNT);
		Account accountTo = new Account(RANDOM_MONEY_AMOUNT);

		assertThrows(NotEnoughMoneyException.class,
				() -> testInstance.transferMoney(accountFrom, accountTo, RANDOM_MONEY_AMOUNT));

	}
	
	@Test
	void shouldThrowExceptionWhenTransferNegativeAmount() {
		Account accountFrom = new Account(RANDOM_MONEY_AMOUNT);
		Account accountTo = new Account(RANDOM_MONEY_AMOUNT);
		
		assertThrows(IllegalArgumentException.class, ()-> testInstance.transferMoney(accountFrom, accountTo, NEGATIVE_MONEY_AMOUNT));
		
	}
	
	@Test
	void shouldThrowExceptionWhenTransferZeroAmount() {
		Account accountFrom = new Account(RANDOM_MONEY_AMOUNT);
		Account accountTo = new Account(RANDOM_MONEY_AMOUNT);
		// Assertion 
		assertThrows(IllegalArgumentException.class,()->testInstance.transferMoney(accountFrom, accountTo, ZERO_MONEY_AMOUNT));
		
		
	}

}
