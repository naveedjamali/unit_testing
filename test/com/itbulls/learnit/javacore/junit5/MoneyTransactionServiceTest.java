package com.itbulls.learnit.javacore.junit5;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Money Transaction service test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
	@DisplayName("Verify money transaction from one account to another account")
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
	void should_Throw_Exception_If_Account_From_Is_Null() throws NotEnoughMoneyException {
		// Given
		Account accountFrom = null;
		Account accountTo = new Account(RANDOM_MONEY_AMOUNT);
		// When

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(accountFrom, accountTo, RANDOM_MONEY_AMOUNT));

		// Then

		assertEquals(ACCOUNT_EXCEPTION_MESSAGE, exception.getMessage());
	}

	@Test
	void should_Throw_Exception_If_Account_To_Is_Null() throws NotEnoughMoneyException {
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
	void should_Throw_Not_Enough_Money_Exception_When_Transfer_More_Money() {
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
	
	@Test
	void groupAssertionExample() throws NotEnoughMoneyException {
		//In grouped assertion, all assertions are executed.
		//failures will be reported together.
		
		
		//Given
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		//WHEN
		
		testInstance.transferMoney(account1,account2, RANDOM_MONEY_AMOUNT);
		
		
		//THEN 
		assertAll("Money Transaction", 
				() -> assertEquals(ZERO_MONEY_AMOUNT,account1.getMoneyAmount()),
				() -> assertEquals(RANDOM_MONEY_AMOUNT,account2.getMoneyAmount()));
		
	}
	
	@Test
	void dependant_assertion_example() {
		
		//GIVEN
		
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		assertAll("Money Transaction",()->{
		//WHEN
			
			boolean isTransactionSucceeded = testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
			assertTrue(isTransactionSucceeded);
			
			// Execute only if the previous assertion is valid;
			
			assertAll("Money amount is changed on the accounts",
					()-> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
					()-> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount()));
		});
	}
	
	@Test
	@Timeout(2)
	void timeoutNotExceededWithResult() {
		//GIVEN
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		
		// The following assertion succeeds, and returns the supplied object;
		
		boolean actualResult = assertTimeout(Duration.ofSeconds(1),
				()->{
					return testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
				});
		
		assertTrue(actualResult);
		
	}
	
	@Test
	void testOnlyOnNaveedsWorkStation() {
		assumeTrue("truee".equals(System.getenv("IS_ANDRII_PIATAKHA_LAPTOP")), 
				() -> "Aborting this test, because it is running not on "
						+ "laptop of Andrii Piatakha");

		// GIVEN
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		
		// The following assertion succeeds, and returns the supplied object.
		boolean actualResult = assertTimeout(Duration.ofSeconds(1), () -> {
			return testInstance
					.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
		});
		assertTrue(actualResult);
		
	}
	
	@ParameterizedTest
	@ValueSource (ints = {100,20,50,-10})
	void parameterizedTestExample(int moneyAmount) throws NotEnoughMoneyException {
		assumeTrue(moneyAmount>0, ()->"Money amount cannot be negative");

		var account1 = new Account(moneyAmount);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		assertTrue(testInstance.transferMoney(account1, account2, moneyAmount));
	}
	

	
	@ParameterizedTest
	@NullSource
	@EmptySource
	@NullAndEmptySource
	void nullAndEmptySources(String text) {
		assertTrue(text == null || text.trim().isEmpty());
	}

	
	@ParameterizedTest
	@MethodSource("sourceMethod")
	void testMethodSource(String arg) {
		assertNotNull(arg);
	}
	
	// return type can be any type that can be converted to a Stream.
	// For example: Collection, Iterator, Iterable, IntStream, DoubleStream, LongStream
	static Stream<String> sourceMethod() {
		return Stream.of("John", "Walter", "Derek");
	}
	
	@ParameterizedTest
	@CsvSource({
	    "apple,         1",
	    "banana,        2",
	    "'lemon, lime', 0xF1"
	})
//	@Ignore
//	@Disabled
	@Disabled("Disabled until Defect #11 will be fixed")
	void testWithCsvSource(String fruit, int rank) {
	    assertNotNull(fruit);
	    assertNotEquals(0, rank);
	}
	

}
