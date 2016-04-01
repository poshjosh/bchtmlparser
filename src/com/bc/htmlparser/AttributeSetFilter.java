package com.bc.htmlparser;

import java.util.List;
import javax.swing.text.AttributeSet;

/**
 * This class subclasses the default {@link com.bc.htmlparser.FilterImpl}
 * specifically to use our own custom #equals method as the #equals method 
 * used in {@link com.bc.htmlparser.FilterImpl FilterImpl} does not work
 * in this case, likewise the AttributeSet#isEqual 
 * 
 * @author poshjosh
 */
public class AttributeSetFilter extends FilterImpl<AttributeSet> {
    
    public AttributeSetFilter() { }

    public AttributeSetFilter(AttributeSet acceptable) { 
        super(acceptable);
    }

    public AttributeSetFilter(AttributeSet... acceptables) { 
        super(acceptables);
    }
    
    public AttributeSetFilter(List<AttributeSet> acceptables) { 
        super(acceptables);
    }

    @Override
    public synchronized boolean contains(AttributeSet e) {
        boolean output;
        Object [] acceptables = this.getAcceptables();
        if(acceptables == null) {
            output = true;
        }else{
            output = false;
            for(Object acceptable:acceptables) {
                if(this.equals(acceptable, e)) {
                    output = true;
                    break;        
                }
            }
        }
        return output;
    }
    
    // This is the main reason for this subclass;
    // i.e To use the our own custom #equals method as the AttributeSet#isEquals 
    // does not often work, likewise the AttributeSet#equals 
    // 
    // If you create an attribute set manually and use it, only 
    // attributeSet0#toString#equals(attributeSet1#toString) works
    // SimpleAttributeSet attributes = new SimpleAttributeSet();
    // attributes.addAttribute("id", "page-data");
    // attributes.addAttribute("class", "content-main");
    //
    private boolean equals(Object as0, AttributeSet as1) {
        return as0.toString().equals(as1.toString()) || ((AttributeSet)as0).isEqual(as1);
    }
}
