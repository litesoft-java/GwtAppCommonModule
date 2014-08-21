package org.litesoft.gwtmodulecommon.client;

public class IconPath extends RelativeImageResource {

    private static String sPathToIcons;

    public IconPath( String... pPrefetchSubPaths ) {
        super( "common/icons/", pPrefetchSubPaths );
    }

    @Override
    protected void setInstance( String pFullPath ) {
        sPathToIcons = pFullPath;
    }

    public static String to( String pSubPath ) {
        return sPathToIcons + pSubPath;
    }

    public static String toTreeUpURL() {
        return to( "small/tree-up.png" );
    }

    public static String toTreeDownURL() {
        return to( "small/tree-down.png" );
    }
}
