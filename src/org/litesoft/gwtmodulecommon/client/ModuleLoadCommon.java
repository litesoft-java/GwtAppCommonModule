package org.litesoft.gwtmodulecommon.client;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.exceptions.*;
import org.litesoft.commonfoundation.problems.*;
import org.litesoft.commonfoundation.typeutils.*;
import org.litesoft.deferred.client.*;
import org.litesoft.externalization.shared.*;


import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public abstract class ModuleLoadCommon implements EntryPoint {

    private native String getNativeDefinedPathToStaticResourcesVersionDir()/*-{
        return $wnd.getPathToStaticResourcesVersionDir();
    }-*/;

    private native String getNativeDefinedPathToRoot()/*-{
        return $wnd.getPathToRoot();
    }-*/;

    private native String getNativeDefinedUnexpectedErrorText()/*-{
        return $wnd.getUnexpectedErrorText();
    }-*/;

    private native String getNativeDefinedHtmlCorruptedText()/*-{
        return $wnd.getHtmlCorruptedText();
    }-*/;

    private native String getNativeDefinedLanguageTablesCorruptedText()/*-{
        return $wnd.getLanguageTablesCorruptedText();
    }-*/;

    private static final String HTML_HOST_CORRUPTED = "HTML Corrupted - Not found: ";

    private final RelativeImageResource[] mImageResources;
    protected String mHtmlCorruptedText, mLanguageTablesCorruptedText;
    protected E13nResolver mE13nResolver;
    protected SystemErrorReporter mSystemErrorReporter;

    private static boolean sAlerted;

    protected ModuleLoadCommon( RelativeImageResource... pImageResources ) {
        mImageResources = pImageResources;
    }

    private abstract static class NativeStringFetcher {
        public String get( String what ) {
            try {
                return getNative();
            }
            catch ( Exception e ) {
                e.printStackTrace();
                // Whatever!
            }
            if ( !sAlerted ) {
                sAlerted = true;
                Window.alert( HTML_HOST_CORRUPTED + what );
            }
            return null;
        }

        abstract protected String getNative();
    }

    @Override
    public final void onModuleLoad() { // GWT: EntryPoint
        new DoublesFactory(); // Force Self Registration! Should probably be first

        final String zPathToStaticResourcesVersionDir = new NativeStringFetcher() {
            @Override
            protected String getNative() {
                return getNativeDefinedPathToStaticResourcesVersionDir();
            }
        }.get( "PathToStaticResourcesVersionDir" );

        final String zPathToRoot = new NativeStringFetcher() {
            @Override
            protected String getNative() {
                return getNativeDefinedPathToRoot();
            }
        }.get( "PathToRoot" );

        final String zUnexpectedErrorText = new NativeStringFetcher() {
            @Override
            protected String getNative() {
                return getNativeDefinedUnexpectedErrorText();
            }
        }.get( "UnexpectedErrorText" );

        mHtmlCorruptedText = new NativeStringFetcher() {
            @Override
            protected String getNative() {
                return getNativeDefinedHtmlCorruptedText();
            }
        }.get( "HtmlCorruptedText" );

        mLanguageTablesCorruptedText = new NativeStringFetcher() {
            @Override
            protected String getNative() {
                return getNativeDefinedLanguageTablesCorruptedText();
            }
        }.get( "LanguageTablesCorruptedText" );

        if ( !sAlerted ) {
            mSystemErrorReporter = new SystemErrorReporter() {
                @Override
                public void reportSystemError( String source, String context, Throwable throwable ) {
                    Window.alert( zUnexpectedErrorText );
                }
            };

            GWT.setUncaughtExceptionHandler( new OurUncaughtExceptionHandler( GWT.getUncaughtExceptionHandler() ) );

            new GWTDeferredRunnerFactoryImpl( new ExceptionOccurredCallback() { // Initialize the DeferredRunnerFactory
                @Override
                public void exceptionOccurred( Object pSource, Exception pException ) {
                    mSystemErrorReporter.reportSystemError( ClassName.simple( pSource ), "DeferredRunnerFactory", pException );
                }
            } ).create( new Runnable() {
                @Override
                public void run() {
                    initModule( zPathToStaticResourcesVersionDir, zPathToRoot );
                }
            } ).schedule( 1 ); // Let the current process end, so that OurUncaughtExceptionHandler will be used!
        }
    }

    abstract protected void initModule( String pPathToStaticResourcesVersionDir, String pPathToRoot );

    protected void setRootLayoutPanelContentsAndClearRootPanelById( Widget pWidget, String pID ) {
        clearOptionalRootPanel( pID );
        RootLayoutPanel.get().add( pWidget );
    }

    protected void replaceRootPanelContents( String pID, Widget pWidget ) {
        RootPanel zRootPanel = RootPanels.get( pID );

        if ( zRootPanel == null ) {
            Window.alert( Strings.replace( mHtmlCorruptedText, "{ID}", pID ) );
            return;
        }
        clearRootPanel( zRootPanel );
        completeRootPanelContentsReplacement( zRootPanel, pWidget );
    }

    protected void completeRootPanelContentsReplacement( RootPanel pRootPanel, Widget pWidget ) {
        pRootPanel.add( pWidget );
    }

    protected static void clearOptionalRootPanel( String pID ) {
        RootPanel zRootPanel = RootPanels.get( pID );
        if ( zRootPanel != null ) {
            clearRootPanel( zRootPanel );
        }
    }

    private static void clearRootPanel( RootPanel pRootPanel ) {
        pRootPanel.clear(); // properly remove GWT widgets
        DOM.setInnerHTML( pRootPanel.getElement(), "" ); // remove static content
    }

    protected void setStaticImageRootPaths( String pPathToStaticResourcesBaseDir ) {
        if ( mImageResources != null ) {
            for ( RelativeImageResource zResource : mImageResources ) {
                zResource.init( pPathToStaticResourcesBaseDir );
            }
        }
    }

    protected void setHelpShower( HelpSupport.Shower pShower ) {
        HelpSupport.init( pShower );
    }

    protected boolean completeE13n( KeyedTextValues pKeyedTextValues ) {
        mE13nResolver = GlobalE13nResolver.set( pKeyedTextValues );

        if ( !checkLanguageLoadedValidationEntries( "LNG_FILE_COMMON", "LNG_FILE_MAIN" ) ) {
            Window.alert( mLanguageTablesCorruptedText );
            return false;
        }
        return true;
    }

    protected boolean checkLanguageLoadedValidationEntries( String... pLanguageValidationEntries ) {
        for ( String zValidationEntry : pLanguageValidationEntries ) {
            if ( !"".equals( GlobalE13nResolver.get().resolveOptionally( zValidationEntry ) ) ) {
                return false;
            }
        }
        return true;
    }

    private class OurUncaughtExceptionHandler implements GWT.UncaughtExceptionHandler,
                                                         Window.ClosingHandler {
        private final GWT.UncaughtExceptionHandler mNext;
        private boolean sWindowClosing = false;

        public OurUncaughtExceptionHandler( GWT.UncaughtExceptionHandler pNext ) {
            mNext = pNext;
        }

        @Override
        public void onWindowClosing( Window.ClosingEvent event ) {
            sWindowClosing = true;
        }

        @Override
        public void onUncaughtException( Throwable e ) {
            if ( mSystemErrorReporter != null ) {
                try {
                    mSystemErrorReporter.reportSystemError( ClassName.simple( this ), null, e );
                }
                catch ( Throwable ignore ) {
                    // Ignore
                }
            }
            if ( !sWindowClosing ) {
                e.printStackTrace(); // new AlertDialog.Factory().from("Main App caught Unexpected Exception of:\n{0}\n{1}", e.getClass().getName(), e.getMessage()).show();
                if ( mNext != null ) {
                    mNext.onUncaughtException( e );
                }
            }
        }
    }
}
