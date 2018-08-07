package com.bc.htmlparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author poshjosh
 */
public class ParseJobTest {
    
    private final boolean exitOnDialogCanel = true;
    
    private final Tag [] tagsToAccept = {
        Tag.HTML, Tag.HEAD, Tag.TITLE, Tag.LINK, Tag.META, Tag.BODY,
        Tag.BR, Tag.DIV, Tag.H1, Tag.H2, Tag.H3, Tag.H4, 
        Tag.I, Tag.IMG, Tag.LI, Tag.OL, Tag.P, Tag.SMALL, Tag.SPAN, Tag.STRONG,
        Tag.TABLE, Tag.TD, Tag.TH, Tag.TR, Tag.UL
    };
    private final HTML.Tag [] tagsToReject = {
//        HTML.Tag.HTML, HTML.Tag.HEAD, HTML.Tag.TITLE, HTML.Tag.BODY,
        HTML.Tag.SCRIPT, HTML.Tag.STYLE
//        HTML.Tag.BR, HTML.Tag.P, HTML.Tag.SPAN, 
    };
    
    private final TestBase testBase = new HtmlparserTestBase();
    
    public ParseJobTest() { }

    /**
     * Test of reset method, of class ParseJob.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testReset() throws FileNotFoundException, IOException {
        testBase.log(this.getClass(), "reset");
        
        final Path path = testBase.findAnyPathEndingWith(".html", null);
        if(path == null) {
            return;
        }
        
        final ParseJob instance = new ParseJob();
        
        final StringBuilder result_0;
        
        try(Reader source = testBase.getReader(path.toFile())) {
        
            instance.plainText(true, false, false);
            
            final String original_separator = "<BR class=\"original_separator\"/>";
            final String replacement_separator = "<BR class=\"replacement_separator\"/>";
            instance.comments(false).formatter(new Formatter<char[]>(){
                @Override
                public char[] format(char [] chars) {
                    return new String(chars).replace(original_separator, replacement_separator).toCharArray();
                }
            });
            instance.maxSeparators(1).separator(original_separator);

            result_0 = instance.parse(source);
        }
        
        final String html = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
//        System.out.println(html);
        
        final StringBuilder result_1 = instance.parse(html);
        System.out.println("\nBefore reset: " + result_1);
        
        instance.reset();
        final StringBuilder result_2 = instance.parse(html);
        System.out.println("\n After reset: " + result_2);
        
//        assertEquals("Parse result before reset and that after reset are not equal", result_1, result_2);
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testParse_Reader() throws FileNotFoundException, IOException {
        testBase.log(this.getClass(), "parse(Reader)");
        
        final Path path = testBase.findAnyPathEndingWith(".html", null);
        if(path == null) {
            return;
        }
        try(Reader reader = testBase.getReader(path.toFile())) {
        
            ParseJob instance = new ParseJob();
            instance.accept(this.tagsToAccept).reject(this.tagsToReject);
            instance.comments(true).maxSeparators(1).separator("\n");

            StringBuilder result = instance.parse(reader);
            
            testBase.log(this.getClass(), "\n%s", result);
        }
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.IOException
     */
    @Test
    public void testParse_String() throws IOException {
        testBase.log(this.getClass(), "parse(String)");
        
        final Path path = testBase.findAnyPathEndingWith(".html", null);
        if(path == null) {
            return;
        }
        String source = testBase.getContents(path.toFile());
        ParseJob instance = new ParseJob();
        instance.accept(this.tagsToAccept).reject(this.tagsToReject);
        instance.comments(true).maxSeparators(1).separator("\n");

        StringBuilder result = instance.parse(source);

        testBase.log(this.getClass(), "\n%s", result);
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.IOException
     */
    @Test
    public void testParse_String_boolean() throws IOException {
        testBase.log(this.getClass(), "parse(String, boolean)");
        
        final Path path = testBase.findAnyPathEndingWith(".html", null);
        if(path == null) {
            return;
        }
        final String source = testBase.getContents(path.toFile());
        ParseJob instance = new ParseJob();
        instance.comments(false).plainText(true).maxSeparators(2);
        instance.separator("\n");

        final boolean ignoreCharset = false;

        try{
            
            final StringBuilder result = instance.parse(source, ignoreCharset);
        
            testBase.log(this.getClass(), "\n%s", result);
            
        }catch(javax.swing.text.ChangedCharSetException e) {
            
            if(ignoreCharset) {
                throw e;
            }else{
                System.err.println("This exception is expected: " + e);
            }
        }
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.IOException
     */
    @Test
    public void testParse_Reader_boolean() throws IOException {
        testBase.log(this.getClass(), "parse(Reader, boolean)");
        
        final Path path = testBase.findAnyPathEndingWith(".html", null);
        if(path == null) {
            return;
        }
        try(Reader source = testBase.getReader(path.toFile())) {
            ParseJob instance = new ParseJob();
            instance.comments(false).plainText(true).maxSeparators(1);
            instance.separator("\n");

            StringBuilder result = instance.parse(source, true);

            testBase.log(this.getClass(), "\n%s", result);
        }
    }
}
