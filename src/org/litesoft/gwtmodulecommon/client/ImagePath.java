package org.litesoft.gwtmodulecommon.client;

public class ImagePath extends RelativeImageResource {

    private static String sPathToImages;

    public ImagePath( String... pPrefetchSubPaths ) {
        super( "common/icons/", pPrefetchSubPaths );
    }

    @Override
    protected void setInstance( String pFullPath ) {
        sPathToImages = pFullPath;
    }

    public static String to( String pSubPath ) {
        return sPathToImages + pSubPath;
    }
}
