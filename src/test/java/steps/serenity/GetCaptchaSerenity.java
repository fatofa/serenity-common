package steps.serenity;

import gherkin.lexer.Th;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageObject.LoginPage;
import net.thucydides.core.steps.ScenarioSteps;
import utils.GlobalConstant;

import java.io.IOException;

public class GetCaptchaSerenity extends ScenarioSteps {
    WebDriver driver;
    LoginPage loginPage = new LoginPage(driver);

    public void openLoginPage(){
        loginPage.openLoginPage();
    }

    public void enterCaptcha() throws InterruptedException, TesseractException, IOException {
        GlobalConstant.captcha = loginPage.getCaptcha();
        loginPage.enterCaptcha(GlobalConstant.captcha);
        Thread.sleep(5000);
    }

    public void verifyCaptcha(){
        loginPage.verifyCaptcha(GlobalConstant.captcha);
    }

    public void clickBtnRefresh() throws InterruptedException {
        loginPage.clickBtnRefresh();
        Thread.sleep(5000);

    }

    public void enterNewCaptcha() throws InterruptedException, TesseractException, IOException {
        GlobalConstant.newCaptcha = loginPage.getCaptcha();
        loginPage.enterCaptcha(GlobalConstant.newCaptcha);
        Thread.sleep(5000);
    }

    public void verifyNewCaptcha(){
        loginPage.verifyCaptcha(GlobalConstant.newCaptcha);
    }

    public void verifyCaptchaChanged(){
        Assert.assertNotEquals(GlobalConstant.captcha, GlobalConstant.newCaptcha);
    }

    public void closePage(){
        loginPage.closePage();
    }
}
