package com.automation.remarks.testng.test;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.await;

/**
 * Created by sergey on 13.07.16.
 */
public class AllureListener implements ITestListener {

    private static final Logger log = Logger.getLogger(AllureListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Video video = result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Video.class);
        if (VideoRecorder.conf().isVideoEnabled() && (video != null && video.enabled()))
            try {
                attachment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }


    @Attachment(value = "video", type = "video/mp4")
    private byte[] attachment() throws InterruptedException {
        try {
            File video = VideoRecorder.getLastRecording();
            await().atMost(5, TimeUnit.SECONDS)
                    .pollDelay(1, TimeUnit.SECONDS)
                    .ignoreExceptions()
                    .until(() -> video != null);

            return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
        } catch (IOException e) {
            log.warning("Allure listener exception" + e);
            return new byte[0];
        }
    }
}
