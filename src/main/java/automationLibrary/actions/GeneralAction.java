package automationLibrary.actions;

import automationLibrary.drivers.Driver;
import automationLibrary.executions.Execution;
import automationLibrary.initiations.Configurations;
import automationLibrary.utils.ReportManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GeneralAction {

    public static WebElement getElement(By by) {
        waitForElementPresent(by);
        WebElement element = null;
        try {
            Driver.setElementWait(0);
            element = Driver.instance.findElement(by);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        } finally {
            Driver.setElementWait(Configurations.IMPLICITLY_WAIT_TIME);
        }
        return element;
    }

    public static List<WebElement> getElements(By by) {
        waitForElementPresent(by);
        List<WebElement> elements = null;
        try {
            Driver.setElementWait(0);
            elements = Driver.instance.findElements(by);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        } finally {
            Driver.setElementWait(Configurations.IMPLICITLY_WAIT_TIME);
        }
        return elements;
    }

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForPageLoad() {
        String state = null;
        String oldstate = null;
        int i = 0;
        while (i < 5) {
            sleep(1000);
            state = ((JavascriptExecutor) Driver.instance).executeScript("return document.readyState;").toString();
            if (state.equals("interactive") || state.equals("loading"))
                break;

            if (i == 1 && state.equals("complete")) {
                return;
            }
            i++;
        }
        i = 0;
        oldstate = null;
        sleep(2000);

        while (true) {
            state = ((JavascriptExecutor) Driver.instance).executeScript("return document.readyState;").toString();
            if (state.equals("complete"))
                break;

            if (state.equals(oldstate))
                i++;
            else
                i = 0;

            if (i == 15 && state.equals("loading")) {
                System.out.println("\nBrowser in " + state + " state since last 60 secs. So refreshing browser.");
                Driver.instance.navigate().refresh();
                System.out.print("Waiting for browser loading to complete");
                i = 0;
            } else if (i == 6 && state.equals("interactive")) {
                System.out.println(
                        "\nBrowser in " + state + " state since last 30 secs. So starting with execution.");
                return;
            }

            sleep(4000);
            oldstate = state;

        }
        System.out.println();
        sleep(2000);
    }

    public static void click(WebElement element) {
        if (element != null) {
            waitForElementClickable(element);
            try {
                element.click();
            } catch (Exception e) {
                JavascriptExecutor executor = (JavascriptExecutor) Driver.instance;
                executor.executeScript("arguments[0].click();", element);
            }
        }
    }

    public static void clickAndWait(WebElement element) {
        if (element != null) {
            waitForElementClickable(element);
            try {
                element.click();
            } catch (Exception e) {
                JavascriptExecutor executor = (JavascriptExecutor) Driver.instance;
                executor.executeScript("arguments[0].click();", element);
            }
        }
        waitForPageLoad();
    }

    public static void clear(WebElement element) {
        try {
            element.clear();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) Driver.instance;
            executor.executeScript("arguments[0].value ='';", element);
        }
    }

    public static Boolean isElementPresent(By by) {
        Driver.setElementWait(0);
        try {
            Driver.instance.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            Driver.setElementWait(Configurations.IMPLICITLY_WAIT_TIME);
        }
    }

    public static void verifyElementExists(By by) {
        if(!isElementPresent(by)) {
            Execution.setTestFail("Element " + by + " does not exist");
        }
    }

    public static void waitForElementClickable(WebElement element) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(Driver.instance).withTimeout(Duration.ofMillis(Configurations.ELEMENT_WAIT_TIME))
                    .pollingEvery(Duration.ofMillis(Configurations.POLLING_TIME))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            ReportManager.addReportLog(LogStatus.WARNING, e.getMessage());
        }
    }

    public static void waitForElementPresent(By locator) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(Driver.instance).withTimeout(Duration.ofMillis(Configurations.ELEMENT_WAIT_TIME))
                    .pollingEvery(Duration.ofMillis(Configurations.POLLING_TIME))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            fluentWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            ReportManager.addReportLog(LogStatus.WARNING, e.getMessage());
        }
    }

    public static String getCurrentTimeByTimezoneOffset(int timezoneOffset, String format) {
        ZoneOffset offset = ZoneOffset.ofHours(timezoneOffset);
        OffsetDateTime odt = OffsetDateTime.now(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String output = odt.format(formatter);
        return output;
    }

    public static String readFile(String dataFilePath) {
        String data = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFilePath), "UTF-8"));
            while ((line = br.readLine()) != null) {
                data += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }

    public static void navigateToPage(String url) {
        ReportManager.addReportLog(LogStatus.INFO, "Navigate to url " + url);
        Driver.instance.get(url);
    }

    public static void verifyTest() {
        if (!Execution.result) {
            Assert.assertTrue(false);
        } else {
            Assert.assertTrue(true);
        }
    }

    public static void changeRangeSelector(WebElement element, int offset) {
        waitForElementClickable(element);
        Actions builder = new Actions(Driver.instance);
        builder.moveToElement(element)
                .click()
                .dragAndDropBy(element, offset, 0)
                .build()
                .perform();
    }
}
