    public void processArgs(String[] args) {

        Pattern pat = Pattern.compile("-[a-z]");

        System.out.println("\nArguments: " + USeqUtilities.stringArrayToString(args, " ") + "\n");

        for (int i = 0; i < args.length; i++) {

            String lcArg = args[i].toLowerCase();

            Matcher mat = pat.matcher(lcArg);

            if (mat.matches()) {

                char test = args[i].charAt(1);

                try {

                    switch(test) {

                        case 'f':
