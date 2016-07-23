package com.automation.remarks.testng.test;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Created by sergey on 13.07.16.
 */
public class AllureListener implements ITestListener {
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
                    .pollDelay(30, TimeUnit.MILLISECONDS)
                    .ignoreExceptions()
                    .until(() ->video != null);

            return Files.readAllBytes(video.toPath());
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
