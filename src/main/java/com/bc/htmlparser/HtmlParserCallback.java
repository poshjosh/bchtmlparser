package com.bc.htmlparser;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;

public class HtmlParserCallback 
        extends HTMLEditorKitParserCallbackImpl implements Filter<HTML.Tag>, Formatter<char []> {

    private static final transient Logger logger = Logger.getLogger(HtmlParserCallback.class.getName());
    
    private boolean enabled = false;
    
    private Filter<HTML.Tag> tagFilter = null;
    
    private Filter<AttributeSet> attributeSetFilter = null;
    
    private Formatter<char []> formatter = null;
    
    private final List<HTML.Tag> visitedTags;
    
    public HtmlParserCallback() { 
        this(new StringBuilder());
    }

    public HtmlParserCallback(StringBuilder buffer) { 
        super(buffer);
        this.visitedTags = new ArrayList<>();
    }

    /**
     * Resets this object to the state it was in before the most recent parse operation
     */
    @Override
    public void reset() {
logger.log(Level.FINER, "{0}. Reset", logger.getName());    
        super.reset();
        enabled = false;
        if(visitedTags != null) {
            visitedTags.clear();
        }
    }
    
    /**
     * Resets this object to its default state. As if it had been newly created
     */
    @Override
    public void resetToDefaults() {
logger.log(Level.FINER, "{0}. Reset to defaults", logger.getName());        
        super.resetToDefaults();
        tagFilter = null;
        attributeSetFilter = null;
        formatter = null;
    }

    public boolean isWithin(HTML.Tag tag) {
        return this.visitedTags.contains(tag);
    }

    @Override
    protected void appendSeparator() {
        if(this.isOnlyPlainText()) {
            super.appendSeparator();
        }else{
            if(this.isWithin(HTML.Tag.BODY)) {
                super.appendSeparator();
            }
        }
    }
    
    @Override
    public final char [] format(char [] chars) {
        return formatter != null ? formatter.format(chars) : chars;
    }

    @Override
    public boolean accept(HTML.Tag t) {
        return tagFilter != null ? tagFilter.accept(t) : true;
    }

    public boolean accept(MutableAttributeSet a) {
        return attributeSetFilter != null ? attributeSetFilter.accept(a) : true;
    }
    
    @Override
    protected void appendData(char data[]) {

        if (data != null) {
            
if(logger.isLoggable(Level.FINER))            
logger.log(Level.FINER, "{0}. BEFORE Format:{1}", new Object[] {logger.getName(), CharBuffer.wrap(data)});

            data = this.format(data);
            
if(logger.isLoggable(Level.FINER))            
logger.log(Level.FINER, "{0}. AFTER Format:{1}", new Object[] {logger.getName(), CharBuffer.wrap(data)});
        }
        
        super.appendData(data);
    }

    @Override
    public void handleStartTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {
//System.out.println("========================================================"+this.tagFilter);        
        final boolean acceptTag = this.accept(t);
        
        final boolean acceptAttributeSet = this.accept(a);
        
        this.enabled = (acceptTag && acceptAttributeSet);

if(logger.isLoggable(Level.FINEST))                    
logger.log(Level.FINEST, "{0}. Start tag: {1}. tag accepted: {2}, atribute set accepted: {3}.", 
new Object[] {logger.getName(), t, acceptTag, acceptAttributeSet});
        
        visitedTags.add(t);
        
        if (this.enabled) {
            
            super.handleStartTag(t, a, pos);
        }
    }

    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        
        int start = visitedTags.lastIndexOf(t);
        if(start != -1) {
            visitedTags.subList(start, visitedTags.size()).clear();
        }
        
        if (this.enabled) {
            super.handleEndTag(t, pos);
        }
    }

    @Override
    public void handleComment(char data[], int pos) {
        
        if (this.enabled) {
            super.handleComment(data, pos);
        }
    }

    @Override
    public void handleSimpleTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {
//System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooo "+this.attributeSetFilter);
        final boolean acceptTag = this.accept(t);
        
        final boolean acceptAttributeSet = this.accept(a);
        
        this.enabled = (acceptTag && acceptAttributeSet);
        
        visitedTags.add(t);
        
        if (this.enabled) {
            
            super.handleSimpleTag(t, a, pos);
        }
    }

    @Override
    public void handleError(String errorMsg, int pos) {
        if (this.enabled) {
            super.handleError(errorMsg, pos);
        }
    }

    @Override
    public void handleEndOfLineString(String eol) {
        if (this.enabled) {
            super.handleEndOfLineString(eol);
        }
    }

    @Override
    public void handleText(char data[], int pos) {
        if (this.enabled) {
            super.handleText(data, pos);
        }
    }

    public Filter<AttributeSet> getAttributSetFilter() {
        return attributeSetFilter;
    }

    public void setAttributeSetFilter(Filter<AttributeSet> filter) {
        this.attributeSetFilter = filter;
    }

    public Filter<HTML.Tag> getTagFilter() {
        return tagFilter;
    }

    public void setTagFilter(Filter<HTML.Tag> filter) {
        this.tagFilter = filter;
    }

    public Formatter<char []> getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter<char []> formatter) {
        this.formatter = formatter;
    }
}
