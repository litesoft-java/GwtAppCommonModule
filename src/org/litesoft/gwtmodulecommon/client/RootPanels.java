package org.litesoft.gwtmodulecommon.client;

import org.litesoft.commonfoundation.typeutils.*;

import com.google.gwt.user.client.ui.*;

import java.util.*;

public class RootPanels {
    private static final Map<String, RootPanel> ROOT_PANELS = Maps.newHashMap();

    private static RootPanel update( String pID, RootPanel pRootPanel ) {
        if ( pRootPanel != null ) {
            ROOT_PANELS.put( pID, pRootPanel );
        }
        return pRootPanel;
    }

    public static RootPanel get( String pID ) {
        return update( pID, RootPanel.get( pID ) );
    }

    public static RootPanel get() {
        return update( null, RootPanel.get() );
    }

    public static Collection<RootPanel> getAll() {
        if ( ROOT_PANELS.isEmpty() ) {
            get(); // Force the registration of at least the default one!
        }
        return ROOT_PANELS.values();
    }
}
