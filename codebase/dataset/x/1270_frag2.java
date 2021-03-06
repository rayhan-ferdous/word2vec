    public OBJWriter(Writer out, String header, int maximumFractionDigits) throws IOException {

        super(out);

        if (maximumFractionDigits >= 0) {

            this.numberFormat = NumberFormat.getNumberInstance(Locale.US);

            this.numberFormat.setMinimumFractionDigits(0);

            this.numberFormat.setMaximumFractionDigits(maximumFractionDigits);

        } else {

            this.numberFormat = null;

        }

        this.header = header;

        writeHeader(this.out);

    }
