    public Response serveFile(String uri, Properties header, File homeDir, boolean allowDirectoryListing) {

        if (!homeDir.isDirectory()) return new Response(HTTP_INTERNALERROR, MIME_PLAINTEXT, "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");

        uri = uri.trim().replace(File.separatorChar, '/');

        if (uri.indexOf('?') >= 0) uri = uri.substring(0, uri.indexOf('?'));

        if (uri.startsWith("..") || uri.endsWith("..") || uri.indexOf("../") >= 0) return new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Won't serve ../ for security reasons.");

        File f = new File(homeDir, uri);

        if (!f.exists()) return new Response(HTTP_NOTFOUND, MIME_PLAINTEXT, "Error 404, file not found.");

        if (f.isDirectory()) {

            if (!uri.endsWith("/")) {

                uri += "/";

                Response r = new Response(HTTP_REDIRECT, MIME_HTML, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri + "</a></body></html>");

                r.addHeader("Location", uri);

                return r;

            }

            if (new File(f, "index.html").exists()) f = new File(homeDir, uri + "/index.html"); else if (new File(f, "index.htm").exists()) f = new File(homeDir, uri + "/index.htm"); else if (allowDirectoryListing) {

                String[] files = f.list();

                String msg = "<html><body><h1>Directory " + uri + "</h1><br/>";

                if (uri.length() > 1) {

                    String u = uri.substring(0, uri.length() - 1);

                    int slash = u.lastIndexOf('/');

                    if (slash >= 0 && slash < u.length()) msg += "<b><a href=\"" + uri.substring(0, slash + 1) + "\">..</a></b><br/>";

                }

                for (int i = 0; i < files.length; ++i) {

                    File curFile = new File(f, files[i]);

                    boolean dir = curFile.isDirectory();

                    if (dir) {

                        msg += "<b>";

                        files[i] += "/";

                    }

                    msg += "<a href=\"" + encodeUri(uri + files[i]) + "\">" + files[i] + "</a>";

                    if (curFile.isFile()) {

                        long len = curFile.length();

                        msg += " &nbsp;<font size=2>(";

                        if (len < 1024) msg += curFile.length() + " bytes"; else if (len < 1024 * 1024) msg += curFile.length() / 1024 + "." + (curFile.length() % 1024 / 10 % 100) + " KB"; else msg += curFile.length() / (1024 * 1024) + "." + curFile.length() % (1024 * 1024) / 10 % 100 + " MB";

                        msg += ")</font>";

                    }

                    msg += "<br/>";

                    if (dir) msg += "</b>";

                }

                return new Response(HTTP_OK, MIME_HTML, msg);

            } else {

                return new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: No directory listing.");

            }

        }

        try {

            String mime = null;

            int dot = f.getCanonicalPath().lastIndexOf('.');

            if (dot >= 0) mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());

            if (mime == null) mime = MIME_DEFAULT_BINARY;

            long startFrom = 0;

            String range = header.getProperty("Range");

            if (range != null) {

                if (range.startsWith("bytes=")) {

                    range = range.substring("bytes=".length());

                    int minus = range.indexOf('-');

                    if (minus > 0) range = range.substring(0, minus);

                    try {

                        startFrom = Long.parseLong(range);

                    } catch (NumberFormatException nfe) {

                    }

                }

            }

            FileInputStream fis = new FileInputStream(f);

            fis.skip(startFrom);

            Response r = new Response(HTTP_OK, mime, fis);

            r.addHeader("Content-length", "" + (f.length() - startFrom));

            r.addHeader("Content-range", "" + startFrom + "-" + (f.length() - 1) + "/" + f.length());

            return r;

        } catch (IOException ioe) {

            return new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");

        }

    }
