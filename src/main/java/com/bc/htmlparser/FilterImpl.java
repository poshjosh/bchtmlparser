package com.bc.htmlparser;

import java.util.List;

/**
 * @author poshjosh
 * @param <E> The type to which this filter may be applied
 */
public class FilterImpl<E> implements Filter<E> {
    
    private Object [] acceptables;
    
    public FilterImpl() { }
    
    public FilterImpl(E acceptable) { 
        this.acceptables = new Object[]{acceptable};
    }

    public FilterImpl(E... acceptables) { 
        this.acceptables = acceptables;
    }

    public FilterImpl(List<E> acceptables) { 
        this.acceptables = acceptables.toArray();
    }

    @Override
    public boolean accept(E e) {
        return this.contains(e);
    }
    
    public synchronized boolean contains(E e) {
        boolean output;
        if(acceptables == null) {
            output = true;
        }else{
            output = false;
            for(Object acceptable:acceptables) {
                if(acceptable.equals(e)) {
                    output = true;
                    break;        
                }
            }
        }
        return output;
    }

    public Object[] getAcceptables() {
        return acceptables;
    }

    public void setAcceptables(E... acceptables) {
        this.acceptables = acceptables;
    }
}
