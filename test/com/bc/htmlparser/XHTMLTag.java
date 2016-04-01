package com.bc.htmlparser;

import javax.swing.text.html.HTML;

/**
 * @author poshjosh
 */
public class XHTMLTag extends HTML.Tag {
    
    public static final XHTMLTag XML = new XHTMLTag("?xml", false, false);
    
    private XHTMLTag(String id, boolean causesBreak, boolean isBlock) {
        super(id, causesBreak, isBlock);
    }
}
