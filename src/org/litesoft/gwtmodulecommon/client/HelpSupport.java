package org.litesoft.gwtmodulecommon.client;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

public class HelpSupport {

    public interface Shower {
        /**
         * Show the pertinent Help Text.
         *
         * @param pPageReference    !empty
         * @param pSubPageReference !null
         */
        void show( String pPageReference, String pSubPageReference );
    }

    private static Shower sShower;

    static /* package friendly */ void init( Shower pShower ) {
        sShower = Confirm.isNotNull( "Shower", pShower );
    }

    public static void show( String pPageReference, String pSubPageReference ) {
        sShower.show( Strings.assertNotEmptyIdentifier( "PageReference", pPageReference ),
                      ConstrainTo.notNull( Strings.assertOptionalIdentifier( "SubPageReference", pSubPageReference ) ) );
    }
}
