import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/GetCaptcha.feature"},
        tags = {""},
        glue = "steps"
)

public class EntryPoint {
    private static ExtentSparkReporter htmlReporter;
    private static ExtentReports extent;
    @BeforeClass
    public static void beforeclass(){

        htmlReporter = new ExtentSparkReporter("Spark.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //To add system or environment info by using the setSystemInfo method.
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "browser");

        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

    }

    public static ExtentSparkReporter getHtmlReporter() {
        return htmlReporter;
    }

    public static ExtentReports getExtent() {
        return extent;
    }

    @AfterClass
    public static void afterClass(){
        extent.flush();
    }
}
