navigateToUrlWithRetry keyword for Katalon Studio
=====

This project is a product inspired by the discussion

- https://forum.katalon.com/t/how-to-handle-500-internal-server-error/55038/15

This project provides a jar named `kazurayam-ks-navigateToUrlWithRetry-x.x.x.jar` 
which includes Custom keyword for Katalon Studio. You can download it and
locate it into the `Pluguins` folder of your project.

Have a look at the [demo](./Script/main/demo/Script1621949055858.groovy) for the sample code to see how to use.

### Method signature

#### (1) criteria expressed by a String of XPath

| position | parameter | description |
| -------- | --------- | ------------|
| 1 | url | URL to navigate to |
| 2 | xpath | the function will look for a HTML element that matches to the given xpath |
| 3 | times | how many times the function tries to navigate to the URL. must be 1 <= times <= 30 |
| 4 | interval | in seconds between navigation trials. must be 1 <= interval <= 100 |

#### (2) criteria expressed by a TestObject

| position | parameter | description |
| -------- | --------- | ------------|
| 1 | url | (same as above) |
| 2 | TestObject | the function will look for a HTML element that matches to the given TestObject |
| 3 | times | (same as above) |
| 4 | interval | (same as above) |

#### (3) criteria expressed by a Groovy Closure

| position | parameter | description |
| -------- | --------- | ------------|
| 1 | url | (same as above) |
| 2 | Closure | any Groovy Closure. The logic is totally up to you. See the [demo](./Script/main/demo/Script1621949055858.groovy) for sample case. The closure should accept a parameter of type Integer as 'interval'. It must return Boolean value. It should return true when successfully navigated to the intended page; false otherwise. |
| 3 | times | (same as above) |
| 4 | interval | (same as above) |

