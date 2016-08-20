/*
 * Copyright 2016 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.htmlparser;

import java.util.List;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 20, 2016 8:47:22 PM
 * @param <E>
 */
public abstract class CompositeFilter<E> implements Filter<E> {
    
    /**
     * The predicates that are to be composited together;
     */
    protected Filter<E>[] mPredicates;

    /**
     * Creates a new instance of an CompositeFilter.
     * With no predicates, this would always answer <code>true</code>
     * to {@link #accept}.
     * @see #setPredicates
     */
    public CompositeFilter () {
        setPredicates ((Filter<E>[])null);
    }

    /**
     * Creates an CompositeFilter that comprises of the input predicate filters
     * @param predicates The list of filters. 
     */
    public CompositeFilter(Filter<E>... predicates) {
        setPredicates (predicates);
    }

    public CompositeFilter(List<Filter<E>> predicates) {
        setPredicates (predicates.toArray(new Filter[0]));
    }
    //
    // NodeFilter interface
    //

    @Override
    public abstract boolean accept (E e);
    
    /**
     * Get the predicates used by this CompositeFilter.
     * @return The predicates currently in use.
     */
    public Filter<E> [] getPredicates () {
        return (mPredicates);
    }

    /**
     * Set the predicates for this CompositeFilter.
     * @param predicates The list of predidcates to use in {@link #accept}.
     */
    public void setPredicates (Filter<E>... predicates) {
        if (null == predicates)
            predicates = new Filter[0];
        mPredicates = predicates;
    }
}

