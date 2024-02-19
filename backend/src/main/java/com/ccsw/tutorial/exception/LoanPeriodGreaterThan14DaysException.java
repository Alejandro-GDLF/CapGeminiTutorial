package com.ccsw.tutorial.exception;

public class LoanPeriodGreaterThan14DaysException extends Exception{
	public LoanPeriodGreaterThan14DaysException() {
		super("Loan duration can't be more than 14 days");
	}
}
