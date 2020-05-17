package automationLibrary.initiations;

public class Configurations {
    public static String CHROMEDRIVER_MACOS_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver";
    public static String CHROMEDRIVER_WINDOWS_FILE_PATH = System.getProperty("user.dir") + "/externals/chromedriver.exe";
    public static String EXTENT_REPORT_PATH = System.getProperty("user.dir") + "/reports/";
    public static String EXTENT_REPORT_CONFIG_FILE = System.getProperty("user.dir") + "/extent-config.xml";
    public static String TEST_DATA_FILE_PATH = System.getProperty("user.dir") + "/testData/testData.json";
    public static int PAGE_WAIT_TIME = 30000;
    public static int IMPLICITLY_WAIT_TIME = 30000;
    public static int ELEMENT_WAIT_TIME = 10000;
    public static int POLLING_TIME = 200;
}
