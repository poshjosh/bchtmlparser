package com.bc.htmlparser;

import java.util.List;

/**
 * @author poshjosh
 * @param <E> The type to which this filter may be applied
 */
public class FilterNegationImpl<E> extends FilterImpl<E> {
    
    public FilterNegationImpl() { }
    
    public FilterNegationImpl(E acceptable) { 
        super(acceptable);
    }

    public FilterNegationImpl(E... acceptables) { 
        super(acceptables);
    }

    public FilterNegationImpl(List<E> acceptables) { 
        super(acceptables);
    }

    @Override
    public boolean accept(E e) {
        return !this.contains(e);
    }
}
