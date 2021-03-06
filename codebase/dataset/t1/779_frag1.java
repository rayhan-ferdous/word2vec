    public void execute(String[] args, int offset) throws InterruptedException, IOException, GeneralSecurityException {

        switch(mode) {

            case ECHO:

                echo();

                break;

            case SEND:

                send(args, offset);

                break;

            default:

                throw new RuntimeException("Illegal mode: " + mode);

        }

    }
