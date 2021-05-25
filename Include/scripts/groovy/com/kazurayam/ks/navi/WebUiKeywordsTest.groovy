package com.kazurayam.ks.navi;

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

import com.kazurayam.ks.navi.WebUiKeywords as KZ

@RunWith(JUnit4.class)
public class WebUiKeywordsTest {

	@Before
	void setup() {
	}

	@Test
	void test_navigateToUrlWithRetry_xpath_success() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400, 600)
		def isReady = KZ.navigateToUrlWithRetry(
				'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				'//h2[contains(., \'Login\')]')
		WebUI.closeBrowser()
		assertThat(isReady, is(true))
	}

	@Test
	void test_navigateToUrlWithRetry_xpath_failure() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400, 600)
		def isReady = KZ.navigateToUrlWithRetry(
				'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				'//h2[contains(., \'Make Appointment\')]',
				2, 3)
		WebUI.closeBrowser()
		assertThat(isReady, is(false))
	}

	@Test
	void test_navigateToUrlWithRetry_TestObject_success() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400, 600)
		TestObject tObj = new TestObject("John Doe")
		tObj.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@value, 'John Doe')]")
		def isReady = KZ.navigateToUrlWithRetry(
				'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				tObj,
				2, 3)
		WebUI.closeBrowser()
		assertThat(isReady, is(true))
	}


	@Test
	void test_navigateToUrlWithRetry_Closure_success() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400, 600)
		Closure cls = { interval ->
			TestObject tObj1 = new TestObject("one")
			tObj1.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@value, 'John Doe')]")
			Boolean result1 = WebUI.verifyElementPresent(tObj1, interval, FailureHandling.OPTIONAL)
			if (result1) {
				TestObject tObj2 = new TestObject("two")
				tObj2.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@value, 'ThisIsNotAPassword')]")
				Boolean result2 = WebUI.verifyElementPresent(tObj2, interval, FailureHandling.OPTIONAL)
				if (result2) {
					return true
				}
			}
			return false
		}
		def isReady = KZ.navigateToUrlWithRetry(
				'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				cls,
				2, 3)
		WebUI.closeBrowser()
		assertThat(isReady, is(true))
	}


	/**
	 * expected page that shows "Humpty" "Dumpty" but it never comes
	 */
	@Test
	void test_navigateToUrlWithRetry_Closure_failure() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400, 600)
		Closure cls = { interval ->
			TestObject tObj1 = new TestObject("one")
			tObj1.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@value, 'Humpty')]")
			Boolean result1 = WebUI.verifyElementPresent(tObj1, interval, FailureHandling.OPTIONAL)
			if (result1) {
				TestObject tObj2 = new TestObject("two")
				tObj2.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@value, 'Dumpty')]")
				Boolean result2 = WebUI.verifyElementPresent(tObj2, interval, FailureHandling.OPTIONAL)
				if (result2) {
					return true
				}
			}
			return false
		}
		def isReady = KZ.navigateToUrlWithRetry(
				'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				cls,
				2, 3)
		WebUI.closeBrowser()
		assertThat(isReady, is(false))
	}
}