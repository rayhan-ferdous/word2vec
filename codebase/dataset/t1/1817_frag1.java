    public boolean pstepLine(int thread, int printMode) {

        boolean stillrunning = true;

        boolean lookingForNextLine = true;

        breakpoint bpSaved = bpset.lookup(reg.hardwareIP());

        int orig_thread = reg.hardwareTP();

        int curr_thread = -1;

        boolean success = threadstepLine.setStepLineBreakpoint(thread);

        if (!success) {

            return stillrunning;

        }

        if (bpSaved != null) bpset.clearBreakpoint(bpSaved);

        while (lookingForNextLine) {

            stillrunning = continueCheckingForSignal(thread, printMode, false);

            if (stillrunning) {

                int curr_addr = reg.hardwareIP();

                curr_thread = reg.hardwareTP();

                if (threadstep.isAtStepLineBreakpoint(thread, curr_addr) && (curr_thread != orig_thread)) {

                    threadstep.setStepBreakpoint(thread, false, false);

                    threadstepLine.temporaryClearStepBreakpoint(thread);

                    stillrunning = continueCheckingForSignal(thread, printMode, false);

                    threadstep.clearStepBreakpoint(thread);

                    threadstepLine.restoreStepBreakpoint(thread);

                } else {

                    lookingForNextLine = false;

                }

            } else {

                return stillrunning;

            }

        }

        if (stillrunning) {

            threadstepLine.clearStepBreakpoint(thread);

            if (bpSaved != null) bpset.setBreakpoint(bpSaved);

        }

        return stillrunning;

    }
