    protected void processLinks(String content, Crawler crawler) {

        String data = content;

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<a href=\"([^\"]+)\">.*</a>(.*)");

        java.util.LinkedList<String> list = new java.util.LinkedList<String>();

        java.util.regex.Matcher match = pattern.matcher(data);

        while (match.matches()) {

            list.add(match.group(0));

            data = list.get(1);

            match = pattern.matcher(data);

        }

        java.util.Iterator<String> cursor = list.iterator();

        while (cursor.hasNext()) {

            try {

                crawler.crawl(cursor.next());

            } catch (MalformedURLException ex) {

                Logger.getLogger(BaseHTMLParser.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(BaseHTMLParser.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }
