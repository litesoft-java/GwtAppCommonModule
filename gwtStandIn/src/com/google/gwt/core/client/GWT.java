package com.google.gwt.core.client;

@SuppressWarnings("UnusedParameters")
public class GWT {
    public interface UncaughtExceptionHandler {
        void onUncaughtException(Throwable e);
    }

    public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
        throw new IllegalStateException();
    }

    public static void setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        throw new IllegalStateException();
    }
}
