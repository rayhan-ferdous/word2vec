    static boolean run() throws Exception {

        methodToRun = "sampleRun";

        String[] sampleArgv = { "InvokeReflect", "-h" };

        System.out.println("Running `InvokeReflect InvokeReflect -h'");

        main(sampleArgv);

        return true;

    }
