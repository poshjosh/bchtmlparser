package com.bc.htmlparser;

import java.util.Date;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author poshjosh
 */
public class AttributeSetTest {
    
    private final TestBase testBase = new HtmlparserTestBase();
    
    @Test
    public void testIsEqual() {
        Date now = new Date();
        AttributeSet as0 = this.getSampleAttributeSet("same", "jil", now);
        AttributeSet as1 = this.getSampleAttributeSet("same", "jil", now); 
        AttributeSet as2 = this.getSampleAttributeSet("different", "jil", now);
        assertTrue("Two attributeSets with equal attributes, found to be unequal", as0.isEqual(as1));
//        assertTrue("Two attributeSets with un-equal attributes, found to be equal", as0.isEqual(as2));
    }
    
    private AttributeSet getSampleAttributeSet(String name, String className, Date date){
        SimpleAttributeSet as = new SimpleAttributeSet();
        if(name != null) as.addAttribute("name", name);
        if(className != null) as.addAttribute("class", className);
        as.addAttribute("xmal", this); // simulate uncommon attribute name
        as.addAttribute("date", date);
        testBase.log(this.getClass(), as);
        return as;
    }
}
