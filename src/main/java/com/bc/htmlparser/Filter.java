/**
 * @(#)Filter.java   25-Mar-2010 16:50:32
 *
 * Copyright 2009 BC Enterprise, Inc. All rights reserved.
 * BCE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.bc.htmlparser;

import java.io.Serializable;

/**
 * @param <E>
 * @author  chinomso bassey ikwuagwu
 * @version 1.0
 * @since   1.0
 */
public interface Filter<E> extends Serializable {
    boolean accept(E e);
}//~END
