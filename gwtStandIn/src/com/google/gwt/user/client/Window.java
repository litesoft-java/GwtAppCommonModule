package com.google.gwt.user.client;

import com.google.gwt.event.shared.*;

@SuppressWarnings("UnusedParameters")
public class Window {
    public static void alert( String s ) {
        throw new IllegalStateException();
    }

    public interface ClosingHandler extends EventHandler {
        void onWindowClosing( Window.ClosingEvent event );
    }

    public static class ClosingEvent {
    }
}
