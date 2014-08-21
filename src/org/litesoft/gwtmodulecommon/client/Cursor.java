package org.litesoft.gwtmodulecommon.client;

public class Cursor {
    public static void busy() {
        cursorTo( "wait" );
    }

    public static void regular() {
        cursorTo( "auto" );
    }

    private static void cursorTo( String pValue ) {
        RootPanels.get().getElement().getStyle().setProperty( "cursor", pValue );
    }
}
