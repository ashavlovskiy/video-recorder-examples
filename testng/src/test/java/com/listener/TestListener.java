package com.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by sepi on 05.10.16.
 */
public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        List<String> listeners = result.getTestContext().getCurrentXmlTest().getSuite().getListeners();

        if (listeners.contains(TestListener.class.getName()) || shouldIntercept(result.getTestClass().getRealClass()))
            System.out.println("Test!!!");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

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

    boolean shouldIntercept(Class testClass) {
        Listeners listenersAnnotation = getListenersAnnotation(testClass);
        return listenersAnnotation != null && asList(listenersAnnotation.value()).contains(TestListener.class);

    }

    Listeners getListenersAnnotation(Class testClass) {
        Annotation annotation = testClass.getAnnotation(Listeners.class);
        return annotation != null ? (Listeners) annotation :
                testClass.getSuperclass() != null ? getListenersAnnotation(testClass.getSuperclass()) : null;
    }
}
