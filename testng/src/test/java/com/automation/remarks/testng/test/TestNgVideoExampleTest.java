package com.automation.remarks.testng.test;

import com.automation.remarks.pages.MainPage;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.RecordingMode;
import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.automation.remarks.pages.MainPage.URL;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by sergey on 29.06.16.
 */
@Listeners(VideoListener.class)
public class TestNgVideoExampleTest {

    @BeforeClass
    public void setUp() {
        // Default video folder is ${user.dir}/video. Could be changed by:
        VideoConfiguration.VIDEO_FOLDER = "custom_folder";
        // Video could be disabled globally. Set to "true"
        VideoConfiguration.VIDEO_ENABLED = "true";
        // There is two recording modes ANNOTATED AND ALL
        // Annotated is default and works only with methods annotated by @Video
        VideoConfiguration.MODE = RecordingMode.ANNOTATED;
    }

    @Test
    @Video(name = "checkPostsNumberPerPage")
    // Video file name could be changed
    public void shouldBe10PostsAtPage() {
        open(URL, MainPage.class).
                posts.shouldHaveSize(9);
    }

    @Test
    @Video(enabled = false)
    // Video recording could be disabled for single test
    public void shouldBeAllPostsAtPage() {
        open(URL, MainPage.class)
                .posts.shouldHaveSize(9);
    }

    @Test
    @Video
    // If test fails, video will be saved with method name
    public void shouldBeALotOfVisitors() {
        open(URL, MainPage.class)
                .userCounter.shouldHave(text("36567"));
    }
}