package com.kazurayam.ks.navi

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


public class WebUiKeywords {

	/**
	 * try to navigate to the url,
	 * verify if it successfully navigated to the intended web page 
	 * by trying to find a HTML element that matches to the given XPath.
	 * if the pages is found not what was intended, will retry for n times 
	 * with the given Interval seconds.
	 * 
	 * @param url
	 * @param criteriaXpath
	 * @param times 1..30
	 * @param interval 1..60
	 * @return true when navigated to the intended web page, false otherwise after retries
	 */
	@Keyword
	static Boolean navigateToUrlWithRetry(String url, String criteriaXpath, Integer times = 5, Integer interval = 10) {
		Objects.requireNonNull(url)
		Objects.requireNonNull(criteriaXpath)
		TestObject successIfFound = new TestObject('navigateToUrlWithRetry_criteriaXpath')
		successIfFound.addProperty('xpath', ConditionType.EQUALS, criteriaXpath)
		return navigateToUrlWithRetry(url, successIfFound, interval, times)
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
	static Boolean navigateToUrlWithRetry(String url, TestObject successIfFound, Integer times = 5, Integer interval = 10) {
		Objects.requireNonNull(url)
		Objects.requireNonNull(successIfFound)
		Closure cls = {
			return WebUI.verifyElementPresent(successIfFound, interval, FailureHandling.OPTIONAL)
		}
		return navigateToUrlWithRetry(url, cls, interval, times)
	}

	/**
	 * 
	 * @param url
	 * @param interval must be in the range of 1..60
	 * @param times must be in the range of 1..10
	 * @param navigationSuccessCriteria must return a Boolean value
	 * @return
	 */
	@Keyword
	static Boolean navigateToUrlWithRetry(String url, Closure navigationSuccessCriteria, Integer times = 5, Integer interval = 10) {
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
		if (times > 30) {
			throw new IllegalArgumentException("times must be less than or equal to 30")
		}
		Objects.requireNonNull(navigationSuccessCriteria)
		//
		WebUI.navigateToUrl(url)
		WebUI.waitForPageLoad(30)
		for (int i = 0; i < times; i++) {
			Boolean successful = navigationSuccessCriteria.call(interval)
			if (successful) {
				return true
			}
		}
		return false
	}

}
