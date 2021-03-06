package com.bc.htmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * @author poshjosh
 */
public class TestBaseImpl implements TestBase {
    
    private final String [] acceptedExtensions = {"html", "htm", "xhtml", "xht", "xml", "jsp", "php", "jspx", "jspf"};
    
    private final JFileChooser fileChooser = new JFileChooser();
    
    private File lastSelectedFile;
    
    public TestBaseImpl() { 
        fileChooser.setDialogTitle("Select content to parse");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setDragEnabled(true);
        fileChooser.setFileFilter(new TestBaseImpl.FileFilterImpl(acceptedExtensions));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
    }
    
    @Override
    public File promptUserToSelectSource(boolean exitOnCancel) {
        if(this.lastSelectedFile != null) {
            this.fileChooser.setCurrentDirectory(this.lastSelectedFile.getParentFile());
        }
        int result = fileChooser.showDialog(null, "Select Source");
        switch(result) {
            case JFileChooser.APPROVE_OPTION:
                this.lastSelectedFile = fileChooser.getSelectedFile();
                break;
            default:
                if(exitOnCancel) {
                    System.exit(0);
                }
                this.lastSelectedFile = null;
        }
        if(this.lastSelectedFile == null) {
            log(this.getClass(), "You did not select any file");
        }
        return this.lastSelectedFile;
    } 
    
    @Override
    public String getContents(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath())); 
    }

    @Override
    public Reader getReader(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }
    
    @Override
    public void log(Class sourceClass, Object msg) {
        System.out.println(sourceClass.getSimpleName()+(msg==null?"":". "+msg));
    }
    
    @Override
    public void log(Class sourceClass, String format, Object... formatArgs) {
        log(sourceClass, String.format(format, formatArgs));
    }
    
    @Override
    public void log(Class sourceClass, Throwable t) {
        log(sourceClass, null, t);
    }
    
    @Override
    public void log(Class sourceClass, Object msg, Throwable t) {
        log(sourceClass, msg);
        t.printStackTrace();;
    }

    private static class FileFilterImpl extends FileFilter {
        
        private final String [] acceptedExtensions;
        
        private FileFilterImpl(String [] acceptedExtensions) {
            if(acceptedExtensions == null) {
                throw new NullPointerException();
            }
            this.acceptedExtensions = acceptedExtensions;
        }

        @Override
        public boolean accept(File f) {
            boolean output;
            if(acceptedExtensions == null) {
                output = true;
            }else{
                output = false;
                for(String acceptedExtension:acceptedExtensions) {
                    if(f.isDirectory() || f.getName().toLowerCase().endsWith(acceptedExtension.toLowerCase())) {
                        output = true;
                        break;
                    }
                }
            }
            return output;
        }

        @Override
        public String getDescription() {
            return acceptedExtensions == null ? "All files" : "Files of type: "+Arrays.toString(acceptedExtensions);
        }
    }
}
