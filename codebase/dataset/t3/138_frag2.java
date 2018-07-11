    public static void main(String[] args) {

        int port = DEFAULT_PORT;

        if (args.length < 1) {

            System.out.println("Usage: DistributedListServer <port>");

        } else {

            try {

                port = Integer.parseInt(args[0]);

            } catch (NumberFormatException e) {

            }

        }

        (new DistributedListServer(port)).run();

    }
