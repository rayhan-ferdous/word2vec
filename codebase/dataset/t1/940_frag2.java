    public int[] getAllThreads() throws Exception {

        int count = 0;

        int threadPointer;

        try {

            VM_Field field = bmap.findVMField("VM_Scheduler", "threads");

            int address = mem.readTOC(field.getOffset());

            int arraySize = mem.read(address + VM.ARRAY_LENGTH_OFFSET);

            VM_Field numThreadsField = bmap.findVMField("VM_Scheduler", "numActiveThreads");

            int numThreadsAddress = mem.addressTOC(numThreadsField.getOffset());

            int numThreads = mem.readsafe(numThreadsAddress) + 1;

            int threadArray[] = new int[numThreads];

            int j = 0;

            for (int i = 1; i < arraySize; i++) {

                threadPointer = mem.read(address + i * 4);

                if (threadPointer != 0) {

                    threadArray[j++] = threadPointer;

                    if (j == numThreads) break;

                }

            }

            return threadArray;

        } catch (BmapNotFoundException e) {

            throw new Exception("cannot find VM_Scheduler.threads, has VM_Scheduler.java been changed?");

        }

    }
