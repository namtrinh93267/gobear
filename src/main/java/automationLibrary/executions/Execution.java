package automationLibrary.executions;

import org.testng.Reporter;

import java.lang.reflect.Method;
import java.util.List;

public class Execution {
    public static Method method;
    public static boolean result;

    public static void setTestFail(String log) {
        Reporter.log(log);
        if (method != null) {
            System.out.println("[FAILED] " + method.getDeclaringClass().getName() + "." + method.getName() + " >>> " + log);
        } else {
            System.out.println("[FAILED] >>> " + log);
        }
        result = false;
    }
}
