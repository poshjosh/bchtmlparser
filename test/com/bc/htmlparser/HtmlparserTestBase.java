package com.bc.htmlparser;

import com.bc.testutil.TestBaseImpl;

/**
 * @author USER
 */
public class HtmlparserTestBase extends TestBaseImpl {

    public HtmlparserTestBase() {
        this.setAcceptedFileExtensions(new String[]{"html", "htm", "xhtml", "xht", "xml", "jsp", "php", "jspx", "jspf"});
    }
}
