package com.bc.htmlparser;

import com.bc.testutil.TestBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
    @BeforeClass
    public static void setUpClass() { }
    
    @AfterClass
    public static void tearDownClass() { }
    
    @Before
    public void setUp() { }
    
    @After
    public void tearDown() { }

    /**
     * Test of reset method, of class ParseJob.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testReset() throws FileNotFoundException, IOException {
        testBase.log(this.getClass(), "reset");
        
        File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
        if(file == null) {
            return;
        }
        try(Reader source = testBase.getReader(file)) {
        
            ParseJob instance = new ParseJob();
            instance.accept(this.tagsToAccept).reject(this.tagsToReject);
            final String original_separator = "<BR class=\"original_separator\"/>";
            final String replacement_separator = "<BR class=\"replacement_separator\"/>";
            instance.comments(false).formatter(new Formatter<char[]>(){
                @Override
                public char[] format(char [] chars) {
                    return new String(chars).replace(original_separator, replacement_separator).toCharArray();
                }
            });
            instance.maxSeparators(1).separator(original_separator);

            StringBuilder result_0 = instance.parse(source);

            instance.reset();

            StringBuilder result_1 = instance.parse(source);

            assertEquals("Parse result before reset and that after reset are not equal", result_0, result_1);
        }
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testParse_Reader() throws FileNotFoundException, IOException {
        testBase.log(this.getClass(), "parse(Reader)");
        
        File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
        if(file == null) {
            return;
        }
        try(Reader reader = testBase.getReader(file)) {
        
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
        
        File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
        if(file == null) {
            return;
        }
        String source = testBase.getContents(file);
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
        
        File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
        if(file == null) {
            return;
        }
        String source = testBase.getContents(file);
        ParseJob instance = new ParseJob();
        instance.comments(false).plainText(true).maxSeparators(2);
        instance.separator("\n");

        StringBuilder result = instance.parse(source, false);

        testBase.log(this.getClass(), "\n%s", result);
    }

    /**
     * Test of parse method, of class ParseJob.
     * @throws java.io.IOException
     */
    @Test
    public void testParse_Reader_boolean() throws IOException {
        testBase.log(this.getClass(), "parse(Reader, boolean)");
        
        File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
        if(file == null) {
            return;
        }
        try(Reader source = testBase.getReader(file)) {
            ParseJob instance = new ParseJob();
            instance.comments(false).plainText(true).maxSeparators(1);
            instance.separator("\n");

            StringBuilder result = instance.parse(source, true);

            testBase.log(this.getClass(), "\n%s", result);
        }
    }
}
