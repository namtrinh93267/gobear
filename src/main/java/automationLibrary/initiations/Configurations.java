package automationLibrary.initiations;

public class Configurations {
    public static String CHROMEDRIVER_MACOS_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver";
    public static String CHROMEDRIVER_WINDOWS_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver.exe";
    public static String CHROMEDRIVER_LINUX64_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver_linux64";
    public static String CHROMEDRIVER_LINUX32_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver_linux32";
    public static String TEST_DATA_FILE_PATH = System.getProperty("user.dir") + "/testData/testData.json";
    public static int ELEMENT_WAIT_TIME = 10000;
    public static int POLLING_TIME = 200;
}
