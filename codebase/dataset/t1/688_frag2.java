        public void run() {

            try {

                InputStream is = mySocket.getInputStream();

                if (is == null) return;

                BufferedReader in = new BufferedReader(new InputStreamReader(is));

                StringTokenizer st = new StringTokenizer(in.readLine());

                if (!st.hasMoreTokens()) sendError(HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");

                String method = st.nextToken();

                if (!st.hasMoreTokens()) sendError(HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");

                String uri = decodePercent(st.nextToken());
