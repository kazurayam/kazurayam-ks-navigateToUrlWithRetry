import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String url = 'https://katalon-demo-cura.herokuapp.com/profile.php#login'
Boolean success

// Successful case 
WebUI.openBrowser('')
WebUI.setViewPortSize(400, 600)
def xpath1 = '//h2[contains(., \'Login\')]'
success = CustomKeywords.'com.kazurayam.ks.navi.WebUiKeywords.navigateToUrlWithRetry'(url, xpath1, 2, 5)
if (!success) {
	KeywordUtil.markFailed("navigated to ${url} but no element that matches \'${xpath1}\' found")
}
WebUI.closeBrowser()


// Failure case
WebUI.openBrowser('')
WebUI.setViewPortSize(400, 600)
def xpath2 = '//h2[contains(., \'Make Appointment\')]'
success = CustomKeywords.'com.kazurayam.ks.navi.WebUiKeywords.navigateToUrlWithRetry'(url, xpath2, 2, 5)
if (!success) {
	KeywordUtil.markFailed("navigated to ${url} but no element that matches \'${xpath2}\' found")
}
WebUI.closeBrowser()


// verify with TestObject
WebUI.openBrowser('')
WebUI.setViewPortSize(400, 600)
def xpath3 = '//h2[contains(., \'Login\')]'
TestObject tObj = new TestObject("Login")
tObj.addProperty("xpath", ConditionType.EQUALS, xpath3)
success = CustomKeywords.'com.kazurayam.ks.navi.WebUiKeywords.navigateToUrlWithRetry'(url, tObj, 2, 5)
if (!success) {
	KeywordUtil.markFailed("navigated to ${url} but no element that matches \'${xpath3}\' found")
}
WebUI.closeBrowser()


// verify with Closure that expresses a complex condition
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
success = CustomKeywords.'com.kazurayam.ks.navi.WebUiKeywords.navigateToUrlWithRetry'(url, cls, 2, 5)
if (!success) {
	KeywordUtil.markFailed("navigated to ${url} but the loaded page was not what you expected")
}
WebUI.closeBrowser()

