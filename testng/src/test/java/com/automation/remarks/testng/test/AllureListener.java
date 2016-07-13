package com.automation.remarks.testng.test;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        if (VideoRecorder.conf().isVideoEnabled())
            attachment();
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


    @Attachment(value = "video", type = "video/avi")
    private byte[] attachment() {
        try {
            String lastRecordingPath = VideoRecorder.getLastRecordingPath();
            return Files.readAllBytes(Paths.get(lastRecordingPath));
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
