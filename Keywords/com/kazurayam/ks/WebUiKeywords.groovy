package com.kazurayam.ks

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class WebUiKeywords {

	/**
	 * try to navigate to the url,
	 * verify if it successfully navigated to the intended web page 
	 * by trying to find a HTML element that matches to the given XPath.
	 * if the pages is found not what was intended, will retry for n times 
	 * with the given Interval seconds.
	 * 
	 * @param url
	 * @param interval
	 * @param times
	 * @param criteriaXpath
	 * @return true when navigated to the intended web page, false otherwise after retries
	 */
	@Keyword
	static Boolean navigateToUrlWithRetry(String url, int interval = 10, int times = 5, String criteriaXpath) {
		TestObject successIfFound = new TestObject('navigateToUrlWithRetry_criteriaXpath')
		successIfFound.addProperty('xpath', ConditionType.EQUALS, criteriaXpath)
		return navigateToUrlWithRetry(url, interval, times, successIfFound)
	}

	/**
	 * try to navigate to the url.
	 * verify if it successfully navigated to the intended web page
	 * by trying to verify if the given TestObject is present in the page.
	 * if the page is found not what was intended, will retry for n times
	 * with the given interval seconds.
	 * 
	 * @param url
	 * @param interval
	 * @param times
	 * @param successIfFound
	 * @return true when navigated to the intended web page, false otherwise after retries
	 */
	@Keyword
	static Boolean navigateToUrlWithRetry(String url, int interval = 10, int times = 5, TestObject successIfFound) {
		Objects.requireNonNull(successIfFound)
		Closure cls = {
			return WebUI.verifyElementPresent(successIfFound, interval, FailureHandling.OPTIONAL)
		}
		return navigateToUrlWithRetry(url, interval, times, cls)
	}

	/**
	 * 
	 * @param url
	 * @param interval must be in the range of 1..100
	 * @param times must be in the range of 1..10
	 * @param navigationSuccessCriteria must return a Boolean value
	 * @return
	 */
	@Keyword
	static Boolean navigateToUrlWithRetry(String url, int interval = 10, int times = 5, Closure navigationSuccessCriteria) {
		Objects.requireNonNull(url)
		if (interval <= 0) {
			throw new IllegalArgumentException("interval must be larger than or equal to 1")
		}
		if (interval > 100) {
			throw new IllegalArgumentException("interval must be less than or equal to 100")
		}
		if (times <= 0) {
			throw new IllegalArgumentException("times must be larger than or equal to 1")
		}
		if (times > 10) {
			throw new IllegalArgumentException("times must be less than or equal to 10")
		}
		Objects.requireNonNull(navigationSuccessCriteria)
		//
		WebUI.navigateToUrl(url)
		WebUI.waitForPageLoad(30)
		for (int i = 0; i < times; i++) {
			Boolean successful = navigationSuccessCriteria.call()
			if (successful) {
				return true
			}
		}
		return false
	}

}
