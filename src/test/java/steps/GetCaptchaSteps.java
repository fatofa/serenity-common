package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.sourceforge.tess4j.TesseractException;
import net.thucydides.core.annotations.Steps;
import steps.serenity.GetCaptchaSerenity;

import java.io.IOException;

public class GetCaptchaSteps {
    @Steps
    GetCaptchaSerenity getCaptchaSerenity;
    @Given("Open login page")
    public void openHomePage() {
        getCaptchaSerenity.openLoginPage();
    }

    @When("Input captcha on textbox")
    public void inputCaptchaOnTextbox() throws InterruptedException, TesseractException, IOException {
        getCaptchaSerenity.enterCaptcha();
    }

    @And("Verify the captcha input is right")
    public void verifyCaptcha(){
        getCaptchaSerenity.verifyCaptcha();
    }

    @When("Click on button refresh")
    public void clickBtnRefresh() throws InterruptedException {
        Thread.sleep(5000);
        getCaptchaSerenity.clickBtnRefresh();
    }

    @And("Input new captcha on textbox")
    public void inputNewCaptchaOnTextbox() throws InterruptedException, TesseractException, IOException {
        getCaptchaSerenity.enterNewCaptcha();
    }

    @And("Verify new captcha input is right")
    public void verifyNewCaptcha(){
        getCaptchaSerenity.verifyNewCaptcha();
    }

    @And("Verify the captcha is changed")
    public void verifyCaptchaChanged(){
        getCaptchaSerenity.verifyCaptchaChanged();
    }

    @Then("Close page")
    public void closepage(){getCaptchaSerenity.closePage();}
}
