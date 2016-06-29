package com.automation.remarks.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by sergey on 29.06.16.
 */
public class MainPage{
    public static final String URL = "http://automation-remarks.com";

    @FindBy(css = ".post-link")
    public ElementsCollection posts;
    @FindBy(css = ".users")
    public SelenideElement userCounter;
}
