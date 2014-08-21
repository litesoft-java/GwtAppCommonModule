package org.litesoft.gwtmodulecommon.client;

import org.litesoft.commonfoundation.typeutils.*;

import com.google.gwt.i18n.client.*;

import java.util.*;

public class DoublesFactory extends Doubles.Factory {
    private final Map<String, Doubles> mDoublesByPatterns = Maps.newHashMap();

    @Override
    public Doubles getFormat( String pPattern ) {
        Doubles zDoubles = mDoublesByPatterns.get( pPattern );
        if ( zDoubles == null ) {
            mDoublesByPatterns.put( pPattern, zDoubles = new GWT_Doubles( pPattern ) );
        }
        return zDoubles;
    }

    private static class GWT_Doubles extends Doubles {
        public final NumberFormat mFormatter;

        private GWT_Doubles( String pPattern ) {
            mFormatter = NumberFormat.getFormat( pPattern );
        }

        @Override
        public String format( double pValue ) {
            return mFormatter.format( pValue );
        }
    }
}
