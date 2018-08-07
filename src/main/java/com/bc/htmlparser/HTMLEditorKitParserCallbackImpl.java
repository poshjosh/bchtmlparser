package com.bc.htmlparser;

import java.nio.CharBuffer;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;

public class HTMLEditorKitParserCallbackImpl extends HTMLEditorKit.ParserCallback {

    private static final transient Logger logger = Logger.getLogger(HTMLEditorKitParserCallbackImpl.class.getName());
    
    private boolean onlyPlainText = false;
    
    private boolean includeComments = true;
    
    private int separatorsAppended = 0;
    
    private int maxSeparatorsBetweenText = 2;
    
    private String separator = null;
    
    private StringBuilder buffer = null;

    public HTMLEditorKitParserCallbackImpl() { 
        this(new StringBuilder());
    }

    public HTMLEditorKitParserCallbackImpl(StringBuilder buffer) { 
        this.buffer = buffer;
        HTMLEditorKitParserCallbackImpl.this.resetToDefaults();
    }

    /**
     * Resets this object to the state it was in before the most recent parse operation
     */
    public void reset() {
        
        separatorsAppended = 0;

//        text.setLength(0);text.trimToSize(); // External references may exist
        buffer = new StringBuilder();
    }

    /**
     * Resets this object to its default state. As if it had been newly created
     */
    public void resetToDefaults() {
        this.reset();
        onlyPlainText = false;
        includeComments = true;
        maxSeparatorsBetweenText = 2;
        separator = null;
    }

    @Override
    public void flush() throws BadLocationException { }

    @Override
    public void handleComment(char data[], int pos) {
        
if(logger.isLoggable(Level.FINER))        
logger.log(Level.FINER, "{0}. Comment: {1}, at {2}", new Object[] {logger.getName(), CharBuffer.wrap(data), pos});
        
        if (includeComments) {
            
            // Append 7 chars. <!-- (4 chars) before and --> (3 chars) after    
            buffer.append("<!--");
            appendData(data);
            buffer.append("-->");
            
            this.resetSeparatorsAppended();
            
            this.appendSeparator();
        }
    }

    @Override
    public void handleStartTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {
        
if(logger.isLoggable(Level.FINER))        
logger.log(Level.FINER, "{0}. Start tag: {1}, at {2}", new Object[] {logger.getName(), t, pos});

        if (!onlyPlainText) {
            
            buffer.append('<');
            buffer.append(t);
            appendAttributes(a);
            buffer.append('>');
            
            this.resetSeparatorsAppended();
            
            if(t.breaksFlow()) {
                this.appendSeparator();
            }
        }
    }

    @Override
    public void handleEndTag(javax.swing.text.html.HTML.Tag t, int pos) {

//System.out.println("Found end tag: "+t+", breaks flow: "+t.breaksFlow()+", is block: "+t.isBlock());    
if(logger.isLoggable(Level.FINER))            
logger.log(Level.FINER, "{0}. End tag: {1}, at {2}", new Object[] {logger.getName(), t, pos});

        if (!onlyPlainText) {
            
            if(t.breaksFlow()) {
//System.out.println("Found end tag: "+t+", breaks flow: "+t.breaksFlow()+", is block: "+t.isBlock());    
                this.appendSeparator();
            }
            
            buffer.append("</");
            buffer.append(t);
            buffer.append('>');
            
            this.resetSeparatorsAppended();

            if(t.breaksFlow()) {
                this.appendSeparator();
            }
        }
    }

    @Override
    public void handleSimpleTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {

//System.out.println("Found simple tag: "+t+", breaks flow: "+t.breaksFlow()+", is block: "+t.isBlock());    
if(logger.isLoggable(Level.FINER))            
logger.log(Level.FINER, "{0}. Simple tag: {1}, at {2}", new Object[] {logger.getName(), t, pos});

        if (!onlyPlainText) {
            buffer.append('<');
            buffer.append(t);
            appendAttributes(a);
            buffer.append("/>");
            
            this.resetSeparatorsAppended();

            if(t.breaksFlow()) {
                this.appendSeparator();
            }
        }
    }

    @Override
    public void handleError(String errorMsg, int pos) {
if(logger.isLoggable(Level.FINE))            
logger.log(Level.FINE, "{0}. ERROR: {1} at {2}.", new Object[] {logger.getName(), errorMsg, pos});
    }

    @Override
    public void handleEndOfLineString(String eol) {
if(logger.isLoggable(Level.FINER))            
logger.log(Level.FINER, "{0}. End of line: {1}", new Object[] {logger.getName(), eol});
        this.appendSeparator();
    }

    @Override
    public void handleText(char []data, int pos) {
        
//        System.out.println("Text: " +CharBuffer.wrap(data) + ", at pos: " + pos);
        
        if(logger.isLoggable(Level.FINER)) {           
            logger.log(Level.FINER, "Text: {0}, at {1}", 
            new Object[] {CharBuffer.wrap(data), pos});
        }

        appendData(data);
        
        this.appendSeparator();
    }

    protected void appendAttributes(AttributeSet a) {
        for (Enumeration en = a.getAttributeNames(); en.hasMoreElements(); buffer.append('"')) {
            Object name = en.nextElement();
            Object value = a.getAttribute(name);
            buffer.append(' ');
            buffer.append(name);
            buffer.append('=');
            buffer.append('"');
            if(value != null) buffer.append(value);
        }
    }

    protected void appendData(char [] data) {
        if (data != null) {
            logger.finer(() -> "Appending: " + CharBuffer.wrap(data));
            buffer.append(data);
        }
    }

    protected void resetSeparatorsAppended() {
        separatorsAppended = 0;
    }
    
    protected void appendSeparator() {
        this.appendSeparator(false);
    }
    
    protected void appendSeparator(boolean log) {
        
        final boolean mayAppend = separator != null && separatorsAppended < maxSeparatorsBetweenText; 
        
        if(logger.isLoggable(Level.FINER)) {                
                logger.log(Level.FINER, "Will append separator: {0}, separator: {1}, seperators appended: {2}, content: ...{3}", 
                new Object[]{mayAppend, separator, separatorsAppended, buffer.subSequence(buffer.length()<30?0:buffer.length()-30, buffer.length())});
        } 
        
if(log) System.out.println("Will append separator: "+mayAppend+", separator: "+separator+
        ", seperators appended: "+separatorsAppended+
        ", content: ..."+buffer.subSequence(buffer.length()<30?0:buffer.length()-30, buffer.length()));

        if (mayAppend) {
            buffer.append(separator);
            ++separatorsAppended;
        }
    }

    public StringBuilder getBuffer() {
        return buffer;
    }

    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public int getMaxSeparatorsBetweenText() {
        return maxSeparatorsBetweenText;
    }

    public void setMaxSeparatorsBetweenText(int maxSeparatorsBetweenText) {
        this.maxSeparatorsBetweenText = maxSeparatorsBetweenText;
    }

    public boolean isIncludeComments() {
        return includeComments;
    }

    public void setIncludeComments(boolean includeComments) {
        this.includeComments = includeComments;
    }

    public boolean isOnlyPlainText() {
        return onlyPlainText;
    }

    public void setOnlyPlainText(boolean onlyPlainText) {
        this.onlyPlainText = onlyPlainText;
    }
}
