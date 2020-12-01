package automationLibrary.actions;

import automationLibrary.initiations.Configurations;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseAction {

    public WebDriver driver;
    public SoftAssert softAssert;

    public BaseAction(WebDriver driver, SoftAssert softAssert) {
        this.driver = driver;
        this.softAssert = softAssert;
    }

    public WebElement getElement(By by) {
        waitForElementPresent(by);
        WebElement element = null;
        try {
            element = driver.findElement(by);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
        return element;
    }

    public List<WebElement> getElements(By by) {
        waitForElementPresent(by);
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(by);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
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

    public void waitForPageLoad() {
        String state = null;
        String oldstate = null;
        int i = 0;
        while (i < 5) {
            sleep(1000);
            state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
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
            state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
            if (state.equals("complete"))
                break;

            if (state.equals(oldstate))
                i++;
            else
                i = 0;

            if (i == 15 && state.equals("loading")) {
                System.out.println("\nBrowser in " + state + " state since last 60 secs. So refreshing browser.");
                driver.navigate().refresh();
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

    public void click(WebElement element) {
        if (element != null) {
            waitForElementClickable(element);
            try {
                element.click();
            } catch (Exception e) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            }
        }
    }

    public void clickAndWait(WebElement element) {
        if (element != null) {
            waitForElementClickable(element);
            try {
                element.click();
            } catch (Exception e) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
            }
        }
        waitForPageLoad();
    }

    public void clear(WebElement element) {
        try {
            element.clear();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].value ='';", element);
        }
    }

    public Boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
        }
    }

    public void waitForElementClickable(WebElement element) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Configurations.ELEMENT_WAIT_TIME, TimeUnit.MILLISECONDS)
                    .pollingEvery(Configurations.POLLING_TIME, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            fluentWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            Reporter.log(e.getMessage());
        }
    }

    public void waitForElementPresent(By locator) {
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Configurations.ELEMENT_WAIT_TIME, TimeUnit.MILLISECONDS)
                    .pollingEvery(Configurations.POLLING_TIME, TimeUnit.MILLISECONDS)
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            fluentWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            Reporter.log(e.getMessage());
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

    public void changeRangeSelector(WebElement element, int offset) {
        waitForElementClickable(element);
        Actions builder = new Actions(driver);
        builder.moveToElement(element)
                .click()
                .dragAndDropBy(element, offset, 0)
                .build()
                .perform();
    }
}