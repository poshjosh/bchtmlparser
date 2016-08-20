package com.bc.htmlparser;

import java.util.List;

/**
 * @author poshjosh
 * @param <E>
 */
public class AndFilter<E> extends CompositeFilter<E> {
    
    /**
     * Creates a new instance of an AndFilter.
     * With no predicates, this would always answer <code>true</code>
     * to {@link #accept}.
     * @see #setPredicates
     */
    public AndFilter () {
        super();
    }

    /**
     * Creates an AndFilter that accepts nodes acceptable to all given filters.
     * @param predicates The list of filters. 
     */
    public AndFilter (Filter<E>... predicates) {
        super(predicates);
    }
    
    public AndFilter(List<Filter<E>> predicates) {
        super(predicates);
    }
    
    /**
     * Accept nodes that are acceptable to all of its predicate filters.
     * @param e The node to check.
     * @return <code>true</code> if all the predicate filters find the node
     * is acceptable, <code>false</code> otherwise.
     */
    @Override
    public boolean accept (E e) {
        
        boolean ret = true;

        for (int i = 0; ret && (i < mPredicates.length); i++) {
            
            if (!mPredicates[i].accept (e)) {
                ret = false;
            }    
        }    

        return (ret);
    }
}
