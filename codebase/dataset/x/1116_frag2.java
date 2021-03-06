    public void format(Reader source, Writer target) {

        this.startTime = System.currentTimeMillis();

        BufferedReader reader = new BufferedReader(source);

        PrintWriter printer = new PrintWriter(new BufferedWriter(target));

        this.lineCount = 0;

        this.beforeFormatingSource(printer);

        try {

            while (reader.ready()) {

                String line = reader.readLine();

                if (line == null) {

                    break;

                }

                this.sourceLine = line;

                this.remainingLine = line;

                this.targetLine = new StringBuilder();

                this.lineCount++;

                this.beforeFormatingLine(printer);

                while (this.remainingLine != null && this.remainingLine.length() > 0) {

                    SourceSelectionProcessor foMode = this.selectionStack.peek();

                    foMode.process(this, null);

                }

                printer.println(this.targetLine.toString());

            }

            this.afterFormatingSource(printer);

            printer.flush();

            printer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
