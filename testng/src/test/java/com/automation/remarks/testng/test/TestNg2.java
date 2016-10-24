package com.automation.remarks.testng.test;

import com.automation.remarks.pages.MainPage;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.codeborne.selenide.Configuration;
import com.listener.TestListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.automation.remarks.pages.MainPage.URL;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by sepi on 05.10.16.
 */
@Listeners({VideoListener.class})
public class TestNg2 {

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/sepi/chromedriver");
        Configuration.browser = "chrome";
        // Default video folder is ${user.dir}/video. Could be changed by:
        VideoRecorder.conf().withVideoFolder("custom_folder")
                // Video could be disabled globally. Set to "true"
                .videoEnabled(true)
                .withRecorderType(RecorderType.FFMPEG)
                // There is two recording modes ANNOTATED AND ALL
                // Annotated is default and works only with methods annotated by @Video
                .withRecordMode(RecordingMode.ANNOTATED)
                .withVideoSaveMode(VideoSaveMode.FAILED_ONLY)
                .withFrameRate(1);
    }

    @Test
    @Video(name = "checkPostsNumberPerPage")
    // Video file name could be changed
    public void test2() throws InterruptedException {
        open(URL, MainPage.class).
                posts.shouldHaveSize(10);
    }

    @Test
    @Video()
    // Video recording could be disabled for single test
    public void test1() throws InterruptedException {
        open(URL, MainPage.class)
                .posts.shouldHaveSize(9);

    }

    @Test
    @Video
    // If test fails, video will be saved with method name
    public void test3() {
        open(URL, MainPage.class)
                .userCounter.scrollTo().shouldHave(text("36567"));
    }

}
