package automationLibrary.drivers;

import automationLibrary.utils.VideoRecorder;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.media.VideoFormatKeys.QualityKey;

public abstract class DriverManager {

    public WebDriver driver;
    public VideoRecorder videoRecorder;

    protected abstract void createDriver(boolean isMobileEmulation);

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver(boolean isMobileEmulation) {
        if (driver == null) {
            createDriver(isMobileEmulation);
        }
        return driver;
    }

    public void startRecord(String videoFolder, String videoName) {
        try {
            org.openqa.selenium.Dimension screenSize = driver.manage().window().getSize();
            org.openqa.selenium.Point point = driver.manage().window().getPosition();
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

    public void stopRecord() {
        try {
            videoRecorder.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}