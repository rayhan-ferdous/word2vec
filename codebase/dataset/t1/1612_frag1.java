        OPT_Options opts = state.getOptions();

        if (!opts.INLINE) return OPT_InlineDecision.NO("inlining not enabled");

        OPT_InlineDecision d = staticOracle.shouldInline(state);

        if (!d.isNO()) return d;

        VM_Method caller = state.getMethod();

        int bcX = state.getBytecodeIndex();

        VM_Method[] targets = plan.getTargets(caller, bcX);

        if (targets == null) {

            return d;

        } else if (targets.length == 1) {

            VM_Method callee = targets[0];

            VM_Method computedTarget = state.getComputedTarget();

            if (computedTarget != null && callee != computedTarget) {

                recordRefusalToInlineHotEdge(state.getCompiledMethodId(), caller, bcX, callee);

                return OPT_InlineDecision.NO("AI: mismatch between computed target and profile data");

            }

            if (!viableCandidate(caller, callee, state)) {

                recordRefusalToInlineHotEdge(state.getCompiledMethodId(), caller, bcX, callee);

                return OPT_InlineDecision.NO("AI: candidate judged to be nonviable");

            }

            if (computedTarget != null) {

                return OPT_InlineDecision.YES(computedTarget, "AI: hot edge matches computed target");

            }

            VM_Method staticCallee = state.obtainTarget();

            if (candidateNeedsGuard(caller, staticCallee, state)) {
