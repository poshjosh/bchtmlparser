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
 * @author Chinomso Bassey Ikwuagwu on Jul 20, 2016 8:54:25 PM
 * @param <E>
 */
public class XorFilter<E> extends CompositeFilter<E> {

    public XorFilter() {
        super();
    }

    public XorFilter(Filter<E>... predicates) {
        super(predicates);
    }

    public XorFilter(List<Filter<E>> predicates) {
        super(predicates);
    }

    @Override
    public boolean accept (E e) {
        
        boolean ret = false;

        for (int i = 0; ret && (i < mPredicates.length); i++) {
            
            if (mPredicates[i].accept (e)) {
                ret = true;
                break;
            }
        }    

        return (ret);
    }
}
