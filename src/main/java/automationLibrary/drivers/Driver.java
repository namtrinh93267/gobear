package automationLibrary.drivers;

import automationLibrary.executions.Execution;
import automationLibrary.initiations.Configurations;
import automationLibrary.utils.VideoRecorder;
import org.apache.commons.lang3.SystemUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.media.VideoFormatKeys.QualityKey;

public class Driver {
    public static WebDriver instance;
    public static VideoRecorder videoRecorder;

    public static void initBrowser(String browser, String environment, boolean isMobileEmulation) {
        switch (browser) {
            case "chrome":
                if(SystemUtils.IS_OS_MAC_OSX) {
                    System.setProperty("webdriver.chrome.driver", Configurations.CHROMEDRIVER_MACOS_FILE_PATH);
                } else if(SystemUtils.IS_OS_WINDOWS) {
                    System.setProperty("webdriver.chrome.driver", Configurations.CHROMEDRIVER_WINDOWS_FILE_PATH);
                }
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions chromeOptions = new ChromeOptions();

                if(isMobileEmulation) {
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", "iPhone X");
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                }
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                instance = new ChromeDriver(capabilities);
                break;
        }
        setWaitTime();
        maximizeWindow();
        deleteCookies();
    }

    public static void setElementWait(int millisecond) {
        try {
            instance.manage().timeouts().implicitlyWait(millisecond, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            Execution.setTestFail(e.getMessage());
        }
    }

    public static void setPageWait(int millisecond) {
        try {
            instance.manage().timeouts().pageLoadTimeout(millisecond, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            Execution.setTestFail(e.getMessage());
        }
    }

    public static void setWaitTime() {
        setElementWait(Configurations.IMPLICITLY_WAIT_TIME);
        setPageWait(Configurations.PAGE_WAIT_TIME);
    }

    public static void maximizeWindow() {
        if(SystemUtils.IS_OS_MAC_OSX) {
            instance.manage().window().fullscreen();
        } else if(SystemUtils.IS_OS_WINDOWS) {
            instance.manage().window().maximize();
        }
    }

    public static void deleteCookies() {
        if (instance == null) {
            return;
        }
        instance.manage().deleteAllCookies();
    }

    public static void closeBrowser() {
        instance.quit();
    }

    public static void startRecord(String videoFolder, String videoName) {
        try {
            org.openqa.selenium.Dimension screenSize = instance.manage().window().getSize();
            org.openqa.selenium.Point point = instance.manage().window().getPosition();
            int width = screenSize.width;
            int height = screenSize.height;
            int x = point.x;
            int y = point.y;


            GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            Rectangle captureSize = new Rectangle(x, y, width, height);
            videoRecorder = new VideoRecorder(graphicsConfiguration, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                            Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, new File(videoFolder), videoName);
            videoRecorder.start();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    public static void stopRecord() {
        try {
            videoRecorder.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
