# bchtmlparser
A lightweight java library (10 classes) for parsing HTML tags of type javax.swing.text.html.HTML.Tag

            // The HTML content to parse
            //
            String html = 
            "<html><head><title>This is the Title</title><link rel=\"_rel\" href=\"_href\"></head>" + 
            "<body><h2>This is the Heading</h2>" +
            "<div id=\"page-data\" class=\"content-main\">This is some content</div>" +
            "<a href=\"_href\"/><!-- This is some comment --></body></html>";
            
            ParseJob parseJob = new ParseJob();
            
            // Extract plain text
            //
            StringBuilder text_plain = parseJob.plainText(true).separator(" ").parse(html);
            
            System.out.println(text_plain);
            
            // Extract all html content except comments
            //
            parseJob.resetToDefaults();
            StringBuilder text_html = parseJob.comments(false).separator("\n").parse(html);
            
            System.out.println(text_html);
            
            // Extract all HTML tags except A tag
            //
            parseJob.resetToDefaults();
            StringBuilder no_A_html = parseJob.reject(HTML.Tag.A).separator("\n").parse(html);
            
            System.out.println(no_A_html);

            SimpleAttributeSet attributes = new SimpleAttributeSet();
            attributes.addAttribute("id", "page-data");
            attributes.addAttribute("class", "content-main");
            
            // Extract the div tag with the specified attributes, and all its children
            //
            parseJob.resetToDefaults();
            StringBuilder div_html = parseJob.accept(HTML.Tag.DIV).accept(attributes).parse(html);

