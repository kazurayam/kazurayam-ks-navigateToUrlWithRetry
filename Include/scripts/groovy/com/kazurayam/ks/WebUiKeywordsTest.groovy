package com.kazurayam.ks;

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kazurayam.ks.WebUiKeywords as KZ

@RunWith(JUnit4.class)
public class WebUiKeywordsTest {

	@Before
	void setup() {
	}

	@Test
	void test_navigateToUrlWithRetry_xpath_success() {
		WebUI.openBrowser('')
		WebUI.setViewPortSize(400,300)
		def isReady = KZ.navigateToUrlWithRetry(
				url = 'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				successCriterial = '//h2[contains(., \'Login\')]')
		WebUI.closeBrowser()
		assertThat(isReady, is(true))
	}

	@Test
	void test_navigateToUrlWithRetry_xpath_failure() {
		WebUI.openBrowser(url)
		WebUI.setViewPortSize(400,300)
		def isReady = KZ.navigateToUrlWithRetry(
				url = 'https://katalon-demo-cura.herokuapp.com/profile.php#login',
				interval = 3,
				times = 2,
				successCriterial = '//h2[contains(., \'Make Appointment\')]'
				)
		WebUI.closeBrowser()
		assertThat(isReady, is(false))
	}
}