package org.litesoft.gwtmodulecommon.client;

import com.google.gwt.user.client.ui.*;

public abstract class RelativeImageResource {

    private final String mRelativePath;
    private final String[] mPrefetchSubPaths;

    protected RelativeImageResource( String pRelativePath, String[] pPrefetchSubPaths ) {
        mRelativePath = pRelativePath;
        mPrefetchSubPaths = pPrefetchSubPaths;
    }

    /* package friendly */ void init( String pPathToRoot ) {
        String zFullPath = pPathToRoot + mRelativePath;
        setInstance( zFullPath );
        prefetch( zFullPath );
    }

    abstract protected void setInstance( String pFullPath );

    private void prefetch( String pFullPath ) {
        if ( mPrefetchSubPaths != null ) {
            for ( String zSubPath : mPrefetchSubPaths ) {
                Image.prefetch( pFullPath + zSubPath );
            }
        }
    }
}
