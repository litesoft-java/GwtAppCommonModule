package org.litesoft.gwtmodulecommon.client;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import com.google.gwt.user.client.ui.*;

public class LoadingPopUp {
    private final PopupPanel mPopupPanel = new PopupPanel( false, true );
    private boolean mShowing;

    public LoadingPopUp() {
        mPopupPanel.setWidget( new Image( IconPath.to( "small/loading.gif" ) ) );
        mPopupPanel.setGlassEnabled( true );
    }

    public void show( int p1to9 ) {
        mPopupPanel.setGlassStyleName( ClassName.simple( this ) + Integers.constrainBetween( 1, 9, p1to9 ) );
        if ( !mShowing ) {
            mShowing = true;
            mPopupPanel.center();
            mPopupPanel.show();
        }
    }

    public void hide() {
        if ( mShowing ) {
            mShowing = false;
            mPopupPanel.hide();
        }
    }
}
