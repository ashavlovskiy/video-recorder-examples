package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by sergey on 13.07.16.
 */
public class AllureListener extends VideoListener {

    private static final Logger log = Logger.getLogger(AllureListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        Video video = result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Video.class);
        if (VideoRecorder.conf().isVideoEnabled() && video != null)
            try {
                attachment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        super.onTestFailedButWithinSuccessPercentage(result);
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
            return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
        } catch (IOException e) {
            log.warning("Allure listener exception" + e);
            return new byte[0];
        }
    }
}
