package com.bc.htmlparser;

import java.io.IOException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;

/**
 * @author USER
 */
public class ReadMe {

    public static void main(String [] args) {
        
        try{
            
            // The HTML content to parse
            //
            final String html = 
            "<html>\n<head><title>This is the Title</title><link rel=\"_rel\" href=\"_href\"></head>" + 
            "\n<body>\n<h2>This is the Heading</h2>" +
            "\n<div id=\"page-data\" class=\"content-main\">This is some content</div>" +
            "\n<a href=\"_href\"/><!-- This is some comment -->\n</body>\n</html>";
            
            System.out.println("Input html");
            System.out.println(html);
            
            ParseJob parseJob = new ParseJob();
            
            // Extract plain text
            //
            StringBuilder text_plain = parseJob.plainText(true).separator(" ").parse(html);
            
            System.out.println("\nExtracted plain text");
            System.out.println(text_plain);
            
            // Extract all html content except comments
            //
            parseJob.resetToDefaults();
            StringBuilder text_html = parseJob.comments(false).separator("\n").parse(html);
            
            System.out.println("\nExtracted less comments");
            System.out.println(text_html);
            
            // Extract all HTML tags except A tag
            //
            parseJob.resetToDefaults();
            StringBuilder no_A_html = parseJob.reject(HTML.Tag.A).separator("\n").parse(html);
            
            System.out.println("\nExtracted less 'a' tags");
            System.out.println(no_A_html);

            SimpleAttributeSet attributes = new SimpleAttributeSet();
            attributes.addAttribute("id", "page-data");
            attributes.addAttribute("class", "content-main");
            
            // Extract the div tag with the specified attributes, and all its children
            //
            parseJob.resetToDefaults();
            StringBuilder div_html = parseJob.accept(HTML.Tag.DIV).accept(attributes).parse(html);
            
            System.out.println("\nExtract specific div tag");
            System.out.println(div_html);
            
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
