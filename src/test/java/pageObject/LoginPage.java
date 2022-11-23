package pageObject;

import commons.AbstractWeb;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import pageUI.LoginPageUI;
import utils.GlobalConstant;

import java.io.File;
import java.io.IOException;


public class LoginPage extends AbstractWeb {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void openLoginPage(){
        openUrl(GlobalConstant.URL_LOGIN);
    }

    public String getCaptcha() throws IOException, TesseractException {
        WebElement imageCaptcha = findElementByXpath(LoginPageUI.IMAGE_CAPTCHA);
        File src = imageCaptcha.getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File(GlobalConstant.PATH_IMAGE_CAPTCHA));
        ITesseract image = new Tesseract();
        String captcha = image.doOCR(new File(GlobalConstant.PATH_IMAGE_CAPTCHA));
        return captcha.replaceAll(" ", "").replaceAll("\n", "");
    }

    public void enterCaptcha(String captcha){
       sendKeyToElement(LoginPageUI.TEXTBOX_CODE, captcha);
    }

    public void verifyCaptcha(String captcha){
        Assert.assertEquals(getInputText(LoginPageUI.TEXTBOX_CODE), captcha);
    }

    public void clickBtnRefresh(){
        clickToElement(LoginPageUI.BTN_REFRESH);
    }
    public void closePage(){
        close();
    }
}
