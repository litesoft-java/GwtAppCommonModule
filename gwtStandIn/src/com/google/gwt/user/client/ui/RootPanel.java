package com.google.gwt.user.client.ui;

@SuppressWarnings("UnusedParameters")
public class RootPanel {
    public static RootPanel get() {
        throw new IllegalStateException();
    }

    public static RootPanel get( String pID ) {
        throw new IllegalStateException();
    }

    public Element getElement() {
        throw new IllegalStateException();
    }

    public void clear() {
        throw new IllegalStateException();
    }

    public void add( Widget pWidget ) {
        throw new IllegalStateException();
    }

    public class Element {
        public Style getStyle() {
            throw new IllegalStateException();
        }
    }

    public class Style {
        public void setProperty( String pAttribute, String pValue ) {
            throw new IllegalStateException();
        }
    }
}
