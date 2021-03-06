        private synchronized void writeToFile(String line) {

            try {

                PrintWriter out = new PrintWriter(new FileOutputStream(this.tf, true));

                out.println(line);

                out.close();

            } catch (IOException ex) {

                System.out.println("\tTestClient.writeToFile(" + line + ") ~ FAILED, IOException: " + ex.getMessage());

                ex.printStackTrace(System.out);

                return;

            }

            System.out.print(".");

            if (this.currentMessageNumber++ == this.maxMessageNumber + 1) {

                this.cbInterface.finished(this.jobId);

            }

        }
