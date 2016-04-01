package com.bc.htmlparser;

/**
 * @author poshjosh
 */
public class AndFilter<E> implements Filter<E> {
    
    /**
     * The predicates that are to be and'ed together;
     */
    protected Filter<E>[] mPredicates;

    /**
     * Creates a new instance of an AndFilter.
     * With no predicates, this would always answer <code>true</code>
     * to {@link #accept}.
     * @see #setPredicates
     */
    public AndFilter ()
    {
        setPredicates (null);
    }

    /**
     * Creates an AndFilter that accepts nodes acceptable to both filters.
     * @param left One filter.
     * @param right The other filter.
     */
    public AndFilter (Filter<E> left, Filter<E> right)
    {
        Filter<E> [] predicates;

        predicates = new Filter[2];
        predicates[0] = left;
        predicates[1] = right;
        setPredicates (predicates);
    }
    
    /**
     * Creates an AndFilter that accepts nodes acceptable to all given filters.
     * @param predicates The list of filters. 
     */
    public AndFilter (Filter<E> [] predicates)
    {
        setPredicates (predicates);
    }

    /**
     * Get the predicates used by this AndFilter.
     * @return The predicates currently in use.
     */
    public Filter<E> [] getPredicates ()
    {
        return (mPredicates);
    }

    /**
     * Set the predicates for this AndFilter.
     * @param predicates The list of predidcates to use in {@link #accept}.
     */
    public void setPredicates (Filter<E> [] predicates)
    {
        if (null == predicates)
            predicates = new Filter[0];
        mPredicates = predicates;
    }

    //
    // NodeFilter interface
    //

    /**
     * Accept nodes that are acceptable to all of its predicate filters.
     * @param e The node to check.
     * @return <code>true</code> if all the predicate filters find the node
     * is acceptable, <code>false</code> otherwise.
     */
    @Override
    public boolean accept (E e)
    {
        boolean ret;

        ret = true;

        for (int i = 0; ret && (i < mPredicates.length); i++)
            if (!mPredicates[i].accept (e))
                ret = false;

        return (ret);
    }
}
