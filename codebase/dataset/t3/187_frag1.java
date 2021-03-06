    public boolean pstep(int thread, int printMode, boolean skip_prolog) {

        boolean stillrunning;

        int addr = reg.hardwareIP();

        boolean over_branch = false;

        breakpoint bpSaved = bpset.lookup(addr);

        threadstep.setStepBreakpoint(thread, over_branch, skip_prolog);

        if (bpSaved != null) bpset.clearBreakpoint(bpSaved);

        stillrunning = continueCheckingForSignal(thread, printMode, false);

        threadstep.clearStepBreakpoint(thread);

        if (stillrunning && bpSaved != null) bpset.setBreakpoint(bpSaved);

        printCurrentStatus(printMode);

        return stillrunning;

    }
