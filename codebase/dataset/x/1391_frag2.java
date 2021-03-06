    private boolean isDenied(SecurityManager sm, Runnable code) {

        exceptionCaught = false;

        System.setSecurityManager(sm);

        try {

            code.run();

        } catch (SecurityException se) {

            exceptionCaught = true;

        }

        return exceptionCaught;

    }
