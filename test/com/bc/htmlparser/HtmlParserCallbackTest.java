package com.bc.htmlparser;

import com.bc.testutil.TestBase;
import java.io.File;
import java.io.IOException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import org.junit.Test;

/**
 * @author poshjosh
 */
public class HtmlParserCallbackTest {
    
    private final boolean exitOnDialogCanel = true;
    
    private final boolean userSelectSource = true;
        
//    private final HTML.Tag [] tagsToAccept = {
//        HTML.Tag.HTML, HTML.Tag.HEAD, HTML.Tag.TITLE, HTML.Tag.LINK, HTML.Tag.META, HTML.Tag.BODY,
//        HTML.Tag.BR, HTML.Tag.DIV, HTML.Tag.H1, HTML.Tag.H2, HTML.Tag.H3, HTML.Tag.H4, 
//        HTML.Tag.I, HTML.Tag.IMG, HTML.Tag.LI, HTML.Tag.OL, HTML.Tag.P, HTML.Tag.SMALL, HTML.Tag.SPAN, HTML.Tag.STRONG,
//        HTML.Tag.TABLE, HTML.Tag.TD, HTML.Tag.TH, HTML.Tag.TR, HTML.Tag.UL
//    };
//    private final HTML.Tag [] tagsToAccept = {
//        HTML.Tag.HTML, HTML.Tag.HEAD, HTML.Tag.TITLE, HTML.Tag.BODY,
//        HTML.Tag.DIV, 
//        HTML.Tag.BR, HTML.Tag.P, HTML.Tag.SPAN, 
//    };
    private final HTML.Tag [] tagsToAccept = null;
    private final HTML.Tag [] tagsToReject = { XHTMLTag.XML, HTML.Tag.SCRIPT, HTML.Tag.STYLE };
    
    private final TestBase testBase = new HtmlparserTestBase();
    
    public HtmlParserCallbackTest() { }

//    @Test
    public void testHtmlWithComments() throws IOException {
        
        testBase.log(this.getClass(), "testHtmlWithComments");
        
        String source;
        if(userSelectSource) {
            File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
            if(file == null) {
                return;
            }
            source = testBase.getContents(file);
        }else{
            source = this.getSampleSource(); 
        }
        
        ParseJob instance = new ParseJob(new ParserCallbackImpl());
        
        instance.accept(this.tagsToAccept).reject(this.tagsToReject);
        instance.comments(true).maxSeparators(1);
        instance.separator("\n\n");
        
        StringBuilder result = instance.parse(source);
        
        testBase.log(this.getClass(), "==================== SOURCE ======================\n%s", source);

        testBase.log(this.getClass(), "\n==================== RESULT ======================\n%s", result);
    }

    @Test
    public void testPlainText() throws IOException {
        
        testBase.log(this.getClass(), "testPlainText");
        
        String source;
        if(userSelectSource) {
            File file = testBase.promptUserToSelectSource(this.exitOnDialogCanel);
            if(file == null) {
                return;
            }
            source = testBase.getContents(file);
        }else{
            source = this.getSampleSource(); 
        }
        
        ParseJob instance = new ParseJob(new ParserCallbackImpl());
        
        instance.comments(false).plainText(true).maxSeparators(1);
        instance.separator("\n\n");
        
        StringBuilder result = instance.parse(source);
        
        testBase.log(this.getClass(), "==================== SOURCE ======================\n%s", source);

        testBase.log(this.getClass(), "\n==================== RESULT ======================\n%s", result);
    }
    
    private String getSampleSource() {
        String html = 
        "<!DOCTYPE html>"        
        +"<html _implied=\"true\">"
            +"\n<head>"
                +"\n<title>"
                    +"\nThis is the title"
                +"\n</title>"
                +"\n<!-- Some comment in head section containing greater than sign <meta> -->"
//                +"\n<meta charset=\"utf-8\">"
//                +"\n<meta id=\"viewport\" name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">"
                +"\n<meta name=\"description\" content=\"Latest EPL News from official news sites and supporter blogs\"/>"
                +"\n<link rel=\"apple-touch-icon-precomposed\" href=\"/themes/eplfeeds/images/apple-touch-icon.png\">"
                
                +"<script type=\"text/javascript\">\n" +
                " var _gaq = _gaq || [];\n" +
                " _gaq.push(['_setAccount', 'UA-36914132-1']);\n" +
                " _gaq.push(['_trackPageview']);\n" +
                " (function() {\n" +
                "   var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n" +
                "   ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n" +
                "   var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n" +
                "  })();\n" +
                "</script>\n"
                
            +"\n</head>"
            +"\n<body>"
                +"\n<content class='content_class_name'>"
                    +"\n<p>"
                        +"\nProperly terminated paragraph contents"
                    +"\n</p>"
                    +"\n<p>"
                        +"\nImproperly terminated paragraph contents"
                    +"\n<div>"
                        +"\nThis is the main &emsp; contents of the web page &nbsp; "
                        +"\n<span>"
                            +"\nThis is the second line in the main contents of the web page"
                        +"\n</span>"
                    +"\n</div>"
                    +"\n<span>"
                        +"\n<!-- Some comment in body section containing greater than sign </span> -->"
                        +"\nThis is some content after the main content, this contains greater than here: span>"
                    +"\n</span>"
                +"\n</content>"
                
                +"<script type=\"text/javascript\">\n" +
"\n" +
"    /* Calculate the width of available ad space */\n" +
"    ad = document.getElementById('google-ads-56f9a1d7484fd');\n" +
"\n" +
"    if (ad.getBoundingClientRect().width) {\n" +
"        adWidth = ad.getBoundingClientRect().width; // for modern browsers\n" +
"    } else {\n" +
"        adWidth = ad.offsetWidth; // for old IE\n" +
"    }\n" +
"\n" +
"    /* Replace ca-pub-XXX with your AdSense Publisher ID */\n" +
"    google_ad_client = \"ca-pub-0976269057382624\";\n" +
"\n" +
"    /* Replace 1234567890 with the AdSense Ad Slot ID */\n" +
"    google_ad_slot = \"4600533424\";\n" +
"\n" +
"    /* Do not change anything after this line */\n" +
"    if ( adWidth >= 728 )\n" +
"        google_ad_size = [\"728\", \"90\"];  /* Leaderboard 728x90 */\n" +
"    else if ( adWidth >= 468 )\n" +
"        google_ad_size = [\"468\", \"60\"];  /* Banner (468 x 60) */\n" +
"    else if ( adWidth >= 336 )\n" +
"        google_ad_size = [\"336\", \"280\"]; /* Large Rectangle (336 x 280) */\n" +
"    else if ( adWidth >= 300 )\n" +
"        google_ad_size = [\"300\", \"250\"]; /* Medium Rectangle (300 x 250) */\n" +
"    else if ( adWidth >= 250 )\n" +
"        google_ad_size = [\"250\", \"250\"]; /* Square (250 x 250) */\n" +
"    else if ( adWidth >= 200 )\n" +
"        google_ad_size = [\"200\", \"200\"]; /* Small Square (200 x 200) */\n" +
"    else if ( adWidth >= 180 )\n" +
"        google_ad_size = [\"180\", \"150\"]; /* Small Rectangle (180 x 150) */\n" +
"    else\n" +
"        google_ad_size = [\"125\", \"125\"]; /* Button (125 x 125) */\n" +
"\n" +
"    document.write (\n" +
"        '<ins class=\"adsbygoogle\" style=\"display:inline-block;width:'\n" +
"        + google_ad_size[0] + 'px;height:'\n" +
"        + google_ad_size[1] + 'px\" data-ad-client=\"'\n" +
"        + google_ad_client + '\" data-ad-slot=\"'\n" +
"        + google_ad_slot + '\"></ins>'\n" +
"    );\n" +
"\n" +
"    (adsbygoogle = window.adsbygoogle || []).push({});\n" +
"\n" +
"</script>\n" +
"<script async src=\"http://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>\n"
                
            +"\n</body>"
        +"\n</html>";
        return html;
    }
    
    private class ParserCallbackImpl extends HtmlParserCallback {
        
        @Override
        public void handleStartTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {
            super.handleStartTag(t, a, pos);
            // testBase.log(this.getClass(), "<"+t+">\tAccepted: "+this.accept(t));
        }

        @Override
        public void handleEndTag(javax.swing.text.html.HTML.Tag t, int pos) {
            super.handleEndTag(t, pos);
            // testBase.log(this.getClass(), "</"+t+">\tAccepted: "+this.accept(t));
        }

        @Override
        public void handleSimpleTag(javax.swing.text.html.HTML.Tag t, MutableAttributeSet a, int pos) {
            super.handleSimpleTag(t, a, pos);
            // testBase.log(this.getClass(), "<"+t+"/>\tAccepted: "+this.accept(t));
        }
    }
}
