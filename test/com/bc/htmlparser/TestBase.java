package com.bc.htmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

/**
 * @author poshjosh
 */
public interface TestBase {

    File promptUserToSelectSource(boolean exitOnCancel);
    
    String getContents(File file) throws IOException;
    
    Reader getReader(File file) throws FileNotFoundException;
    
    void log(Class sourceClass, Object msg);

    void log(Class sourceClass, String format, Object... formatArgs);

    void log(Class sourceClass, Throwable t);

    void log(Class sourceClass, Object msg, Throwable t);
    
}
