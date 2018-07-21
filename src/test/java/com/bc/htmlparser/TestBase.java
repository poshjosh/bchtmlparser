package com.bc.htmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import javax.swing.JFileChooser;

/**
 * @author poshjosh
 */
public interface TestBase {
    
    JFileChooser getFileChooser();

    String[] getAcceptedFileExtensions();

    void setAcceptedFileExtensions(String[] acceptedFileExtensions);

    default Path findAnyPathEndingWith(String suffix, Path outputIfNone) throws IOException {
        final int skip = (int)(Math.random() * 10); 
        return this.findAnyPathEndingWith(suffix, skip, Integer.MAX_VALUE, outputIfNone);
    }
    
    Path findAnyPathEndingWith(String suffix, int offset, int limit, Path outputIfNone) throws IOException;
        
    File promptUserToSelectSource(boolean exitOnCancel);
    
    String getContents(File file) throws IOException;
    
    Reader getReader(File file) throws FileNotFoundException;
    
    void log(Class sourceClass, Object msg);

    void log(Class sourceClass, String format, Object... formatArgs);

    void log(Class sourceClass, Throwable t);

    void log(Class sourceClass, Object msg, Throwable t);
    
}
