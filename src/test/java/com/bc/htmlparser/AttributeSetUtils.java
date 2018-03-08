package com.bc.htmlparser;

import java.util.Enumeration;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;

public final class AttributeSetUtils {

    private AttributeSetUtils() { }

    public static boolean equals(AttributeSet a0, AttributeSet a1) {
        if(a0 == a1) {
            return true;
        }
        if(a0 != null && a1 == null) {
            return false;
        }
        if(a1 != null && a0 == null) {
            return false;
        }
        if(a0 == null && a1 == null) {
            return true;
        }
        
        String s0 = a0.toString().trim();
        String s1 = a1.toString().trim();
        
        if(s0.length() != s1.length()) {
            return false;
        }
        
        return s0.equalsIgnoreCase(s1);
    }
    
    public static String get(AttributeSet a, String attributeName) {
        for (Enumeration en = a.getAttributeNames(); en.hasMoreElements();) {
            Object name = en.nextElement();
            if (name.toString().equalsIgnoreCase(attributeName)) {
                return a.getAttribute(name).toString();
            }    
        }
        return null;
    }

    public static void add(MutableAttributeSet a, String attributeName, String attributeValue) {
        Enumeration en = a.getAttributeNames();
        do {
            if (!en.hasMoreElements()){
                break;
            }    
            Object name = en.nextElement();
            if (name.toString().equalsIgnoreCase(attributeName)) {
                a.addAttribute(name, attributeValue);
            }    
        } while (true);
    }

    public static void remove(MutableAttributeSet a, String attributeName) {
        Enumeration en = a.getAttributeNames();
        do {
            if (!en.hasMoreElements()) {
                break;
            }    
            Object name = en.nextElement();
            if (name.toString().equalsIgnoreCase(attributeName)) {
                a.removeAttribute(name);
            }    
        } while (true);
    }

    public static void replace(MutableAttributeSet a, String attributeName, String attributeValue) {
        Enumeration en = a.getAttributeNames();
        do {
            if (!en.hasMoreElements()) {
                break;
            }    
            Object name = en.nextElement();
            if (name.toString().equalsIgnoreCase(attributeName)) {
                a.removeAttribute(name);
                a.addAttribute(name, attributeValue);
            }
        } while (true);
    }

    public static String toString(AttributeSet a) {
        StringBuilder builder = new StringBuilder("[");
        Enumeration en = a.getAttributeNames();
        do {
            if (!en.hasMoreElements()) {
                break;
            }    
            Object name = en.nextElement();
            builder.append(name).append("=").append(a.getAttribute(name));
            if (en.hasMoreElements()) {
                builder.append(", ");
            }    
        } while (true);
        builder.append("]");
        return builder.toString();
    }
}
