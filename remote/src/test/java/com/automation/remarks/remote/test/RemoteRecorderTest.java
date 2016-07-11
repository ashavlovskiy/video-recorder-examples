package com.automation.remarks.remote.test;

import com.automation.remarks.pages.MainPage;
import com.automation.remarks.remote.StartGrid;
import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import static com.automation.remarks.pages.MainPage.URL;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by sepi on 11.07.16.
 */
@Listeners(RemoteVideoListener.class)
public class RemoteRecorderTest {

    @BeforeClass
    public static void setUp() throws Exception {
        String[] args = {};
        StartGrid.main(args);
        Thread.sleep(1000);
    }

    @Test
    @Video
    public void shouldBeALotOfArticles(){
        Configuration.remote = "http://localhost:4444/wd/hub";
        open(URL, MainPage.class).
                posts.shouldHaveSize(9);
    }

}
