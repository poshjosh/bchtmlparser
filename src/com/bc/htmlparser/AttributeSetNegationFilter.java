package com.bc.htmlparser;

import java.util.List;
import javax.swing.text.AttributeSet;

/**
 * @author poshjosh
 * @see com.bc.htmlparser.AttributeSetFilter
 */
public class AttributeSetNegationFilter extends AttributeSetFilter {
    
    public AttributeSetNegationFilter() { }

    public AttributeSetNegationFilter(AttributeSet acceptable) { 
        super(acceptable);
    }

    public AttributeSetNegationFilter(AttributeSet... acceptables) { 
        super(acceptables);
    }

    public AttributeSetNegationFilter(List<AttributeSet> acceptables) { 
        super(acceptables);
    }

    @Override
    public boolean accept(AttributeSet e) {
        return !this.contains(e);
    }
}
