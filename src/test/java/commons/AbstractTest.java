package commons;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import utils.GlobalConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTest {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
    private String projectPath = System.getProperty("user.dir");

    public static final Logger logger = Log.getLogger();


    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    protected enum Browser {
        CHROME, FIREFOX, CHROMEHEADLESS, FIREFOXHEADLESS;
    }

    protected WebDriver getBrowserDriver(String browserName, String url) {
        Browser browser = Browser.valueOf(browserName.toUpperCase());

        System.out.println("Browser Name = " + browserName);

        switch (browser) {
            case CHROME:
                //System.setProperty("webdriver.chrome.driver", projectPath +  "/browserDriver/chromedriver.exe");
                WebDriverManager.chromedriver().setup();
                threadDriver.set(new ChromeDriver());
                break;
            case FIREFOX:
                //System.setProperty("webdriver.gecko.driver", projectPath +  "/browserDriver/geckodriver.exe");
                WebDriverManager.firefoxdriver().setup();
                threadDriver.set(new FirefoxDriver());
                break;
            case CHROMEHEADLESS:
                //System.setProperty("webdriver.chrome.driver", projectPath +  "/browserDriver/chromedriver.exe");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("-headless");
                chromeOptions.addArguments("window-size=1920x1080");
                threadDriver.set(new ChromeDriver(chromeOptions));
                break;
            case FIREFOXHEADLESS:
                //System.setProperty("webdriver.gecko.driver", projectPath +  "/browserDriver/geckodriver.exe");
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                firefoxOptions.addArguments("window-size=1920x1080");
                threadDriver.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                throw new RuntimeException("Please choose browser name!");
        }

        threadDriver.get().navigate().to(url);
        threadDriver.get().manage().timeouts().implicitlyWait(GlobalConstant.LONG_TIMEOUT, TimeUnit.SECONDS);

        return threadDriver.get();
    }


    protected void removeBroserDriver() {
        WebDriver driver = threadDriver.get();
        threadDriver.remove();

        String cmd = "";
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            logger.info("OS name = " + osName);

            String driverInstanceName = driver.toString().toLowerCase();
            logger.info("Driver instance name = " + driverInstanceName);

            // Quit driver file
            if (driverInstanceName.contains("chrome")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                } else {
                    cmd = "pkill chromedriver";
                }
            } else if (driverInstanceName.contains("internetexplorer")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driverInstanceName.contains("firefox")) {
                if (osName.contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                } else {
                    cmd = "pkill geckodriver";
                }
            } else if (driverInstanceName.contains("edge")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                } else {
                    cmd = "pkill msedgedriver";
                }
            } else if (driverInstanceName.contains("opera")) {
                if (osName.contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
                } else {
                    cmd = "pkill operadriver";
                }
            } else if (driverInstanceName.contains("safari")) {
                if (osName.contains("mac")) {
                    cmd = "pkill safaridriver";
                }
            }

            if (driver != null) {
                // Quit driver
                driver.manage().deleteAllCookies();
                driver.quit();
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
