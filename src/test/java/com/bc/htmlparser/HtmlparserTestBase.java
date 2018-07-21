package com.bc.htmlparser;

/**
 * @author USER
 */
public class HtmlparserTestBase extends TestBaseImpl {

    public HtmlparserTestBase() {
        this.setAcceptedFileExtensions(new String[]{"html", "htm", "xhtml", "xht", "xml", "jsp", "php", "jspx", "jspf"});
    }
}
