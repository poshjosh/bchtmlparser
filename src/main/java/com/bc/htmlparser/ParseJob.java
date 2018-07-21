package com.bc.htmlparser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.parser.ParserDelegator;


/**
 * @(#)ParseJob.java   25-Mar-2015 12:18:18
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * <p>Some usages.</p>
 * <pre>
        ParseJob parseJob = new ParseJob();
        
        String htmlToParse = null; // PUT HTML CONTENT HERE

        // To extract all DIV tags
        try{
            StringBuilder output = parseJob.html(true)
                .comments(false).accept(Tag.DIV)
                .maxSeparators(2).separator("&lt;BR/>\n")
                .parse(htmlToParse);
        }catch(IOException e) {
            e.printStackTrace(); 
        }
        
        // To extract plain text
        parseJob.reset();
        try{
            StringBuilder output = 
            parseJob.plainText(true).comments(false);
            .maxSeparators(2).separator("\n")
            .parse(htmlToParse);
        }catch(IOException e) {
            e.printStackTrace(); 
        }
 * </pre>
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class ParseJob extends ParserDelegator {
    
    private List<HTML.Tag> tagsToAccept;
    private List<HTML.Tag> tagsToReject;

    private List<AttributeSet> attributeSetsToAccept;
    private List<AttributeSet> attributeSetsToReject;
    
    private HtmlParserCallback parserCallback;

    public ParseJob() {  
        this(new HtmlParserCallback()); 
    }
    
    public ParseJob(StringBuilder buffer) {  
        this(new HtmlParserCallback(buffer));
    }

    public ParseJob(HtmlParserCallback parserCallback) {  
        this.parserCallback = Objects.requireNonNull(parserCallback);
    }
    
    /**
     * Resets this object to the state it was in before the most recent parse operation
     * @return this instance
     */
    public ParseJob reset() {
        if(parserCallback != null) {
            parserCallback.reset();
        }
        tagsToAccept = null;
        tagsToReject = null;
        attributeSetsToAccept = null;
        attributeSetsToReject = null;
        return this;
    }
    
    /**
     * Resets this object to its default state. As if it had been newly created
     * @return this instance
     */
    public ParseJob resetToDefaults() {
        if(parserCallback != null) {
            parserCallback.resetToDefaults();
        }
        this.reset();
        return this;
    }

    public StringBuilder parse(Reader reader) throws IOException {
        
        return parse(reader, true);
    }

    public StringBuilder parse(String input) throws IOException {
        
        return parse(input, true);
    }
    
    public StringBuilder parse(String input, boolean ignoreCharset) throws IOException {
        
        try(Reader reader = new StringReader(input)) {
            
            return this.parse(reader, ignoreCharset);
        }
    }
    
    public StringBuilder parse(Reader reader, boolean ignoreCharset) throws IOException {
        
        Filter<HTML.Tag> tagFilter = this.buildTagFilter();
        this.tagFilter(tagFilter);
        
        Filter<AttributeSet> asFilter = this.buildAttributeSetFilter();
        this.attributesFilter(asFilter);
        
        StringBuilder output;
        try{    
            
            this.parse(reader, parserCallback, ignoreCharset);
            
            output = parserCallback.getBuffer();
            
        }catch(RuntimeException e) {
            
            output = null;
            
            throw e;
            
        }finally{
            // We call reset here to ensure vital resources are released
            this.reset();
        }
        
        return output;
    }
    
    public ParseJob callback(HtmlParserCallback callback) {
        this.setParserCallback(callback);
        return this;
    } 

    /**
     * Tells the Parser to accept this tag and all its children
     * @param tag The tag to accept
     * @return this instance
     */
    public ParseJob accept(HTML.Tag tag) {
        // Assigment very important
        this.tagsToAccept = this.add(tagsToAccept, tag);
        return this;
    }
    
    /**
     * Tells the Parser to accept these tags and all their children
     * @param tags The tags to accept
     * @return this instance
     */
    public ParseJob accept(HTML.Tag... tags) {
        // Assigment very important
        this.tagsToAccept = this.addAll(tagsToAccept, tags);
        return this;
    }
    
    /**
     * Tells the Parser to reject this tag and all its children
     * @param tag The tag to reject
     * @return this instance
     */
    public ParseJob reject(HTML.Tag tag) {
        // Assigment very important
        this.tagsToReject = this.add(tagsToReject, tag);
        return this;
    }
    
    /**
     * Tells the Parser to reject these tags and all their children
     * @param tags The tags to reject
     * @return this instance
     */
    public ParseJob reject(HTML.Tag... tags) {
        // Assigment very important
        this.tagsToReject = this.addAll(tagsToReject, tags);
        return this;
    }

    /**
     * Tells the Parser to accept this <code>AttributeSet</code> and all its children
     * @param attributeSet The <code>AttributeSet</code> to accept
     * @return this instance
     */
    public ParseJob accept(AttributeSet attributeSet) {
        // Assigment very important
        this.attributeSetsToAccept = this.add(attributeSetsToAccept, attributeSet);
        return this;
    }
    
    /**
     * Tells the Parser to accept these <code>AttributeSets</code> and all their children
     * @param attributeSets The <code>AttributeSets</code> to accept
     * @return this instance
     */
    public ParseJob accept(AttributeSet... attributeSets) {
        // Assigment very important
        this.attributeSetsToAccept = this.addAll(attributeSetsToAccept, attributeSets);
        return this;
    }
    
    /**
     * Tells the Parser to reject this <code>AttributeSet</code> and all its children
     * @param attributeSet The <code>AttributeSet</code> to reject
     * @return this instance
     */
    public ParseJob reject(AttributeSet attributeSet) {
        // Assigment very important
        this.attributeSetsToReject = this.add(attributeSetsToReject, attributeSet);
        return this;
    }
    
    /**
     * Tells the Parser to reject these <code>AttributeSets</code> and all their children
     * @param attributeSets The <code>AttributeSets</code> to reject
     * @return this instance
     */
    public ParseJob reject(AttributeSet... attributeSets) {
        // Assigment very important
        this.attributeSetsToReject = this.addAll(attributeSetsToReject, attributeSets);
        return this;
    }

    public ParseJob tagFilter(Filter<HTML.Tag> filter) {
        parserCallback.setTagFilter(filter);
        return this;
    }

    public ParseJob attributeSetFilter(Filter<AttributeSet> filter) {
        return this.attributesFilter(filter);
    }

    public ParseJob attributesFilter(Filter<AttributeSet> filter) {
        parserCallback.setAttributeSetFilter(filter);
        return this;
    }
    
    public ParseJob formatter(Formatter<char []> formatter) {
        parserCallback.setFormatter(formatter);
        return this;
    }
    
    public ParseJob maxSeparators(int n) {
        parserCallback.setMaxSeparatorsBetweenText(n);
        return this;
    }

    public ParseJob comments(boolean comments) {
        parserCallback.setIncludeComments(comments);
        return this;
    }

    public ParseJob plainText(boolean plainText) {
        return this.plainText(plainText, false, false);
    }

    public ParseJob plainText(boolean plainText, boolean acceptScriptText, boolean acceptStyleText) {
        parserCallback.setOnlyPlainText(plainText);
        if(!acceptScriptText) {
            // Assigment very important
            this.tagsToReject = this.add(tagsToReject, HTML.Tag.SCRIPT);
        }
        if(!acceptStyleText) {
        // Assigment very important
        this.tagsToReject = this.add(tagsToReject, HTML.Tag.STYLE);
        }
        return this;
    }

    public ParseJob separator(String separator) {
        parserCallback.setSeparator(separator);
        return this;
    }
    
    private Filter<HTML.Tag> buildTagFilter() {
        
        List<Filter<HTML.Tag>> filterList = new LinkedList();
        
        if(this.tagsToAccept != null) {
            filterList = this.add(filterList, new FilterImpl(this.tagsToAccept));
        }
        
        if(this.tagsToReject != null) {
            filterList = this.add(filterList, new FilterNegationImpl(this.tagsToReject));
        }
        
        if(this.parserCallback.getTagFilter() != null) {
            filterList = this.add(filterList, this.parserCallback.getTagFilter());
        }
        
        return and(filterList);
    }

    private Filter<AttributeSet> buildAttributeSetFilter() {
        
        List<Filter<AttributeSet>> filterList = new LinkedList();
        
        if(this.attributeSetsToAccept != null) {
            filterList = this.add(filterList, new AttributeSetFilter(this.attributeSetsToAccept));
        }
        
        if(this.attributeSetsToReject != null) {
            filterList = this.add(filterList, new AttributeSetNegationFilter(this.attributeSetsToReject));
        }
        
        if(this.parserCallback.getAttributSetFilter() != null) {
            filterList = this.add(filterList, this.parserCallback.getAttributSetFilter());
        }
        
        return and(filterList);
    }
    
    private <E> List<E> add(List<E> buffer, E toAdd) {
        if(toAdd == null) {
            return buffer;
        }
        if(buffer == null) {
            buffer = new LinkedList();
        }
        buffer.add(toAdd);
        return buffer;
    }

    private <E> List<E> addAll(List<E> buffer, E[] toAdd) {
        if(toAdd == null) {
            return buffer;
        }
        if(buffer == null) {
            buffer = new LinkedList();
        }
        buffer.addAll(Arrays.asList(toAdd));
        return buffer;
    }
    
    private <T> Filter<T> and(List<Filter<T>> filterList) {
        return filterList.isEmpty() ? (t) -> false : filterList.size() == 1 ?
                filterList.get(0) : new AndFilter(filterList);
    }
    
    private <T> Filter<T> or(List<Filter<T>> filterList) {
        return filterList.isEmpty() ? (t) -> false : filterList.size() == 1 ?
                filterList.get(0) : new OrFilter(filterList);
    }

    public void setParserCallback(HtmlParserCallback callback) {
        parserCallback = callback;
    }
    
    public HtmlParserCallback getParserCallback() {
        return parserCallback;
    }
}
