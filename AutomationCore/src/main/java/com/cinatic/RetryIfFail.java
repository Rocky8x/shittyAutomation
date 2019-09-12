package com.cinatic;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryIfFail implements IRetryAnalyzer{
	
	int counter = 0;
	int limit = 3;

	@Override
	public boolean retry(ITestResult result) {
		 if (!result.isSuccess()) {                      //Check if test not succeed
	            if (counter < limit) {                            //Check if limit count is reached
	            	counter++;                                     //Increase the maxTry count by 1
	            	result.setStatus(ITestResult.FAILURE);  //Mark test as failed
	                return true;                                 //Tells TestNG to re-run the test
	            }
	        } else {
	        	result.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
	        }
	        return false;
	}
}
