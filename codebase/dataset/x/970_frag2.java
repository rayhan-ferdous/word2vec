    private void genLoop(JCStatement loop, JCStatement body, JCExpression cond, List<JCExpressionStatement> step, boolean testFirst) {

        Env<GenContext> loopEnv = env.dup(loop, new GenContext());

        int startpc = code.entryPoint();

        if (testFirst) {

            CondItem c;

            if (cond != null) {

                code.statBegin(cond.pos);

                c = genCond(TreeInfo.skipParens(cond), CRT_FLOW_CONTROLLER);

            } else {

                c = items.makeCondItem(goto_);

            }

            Chain loopDone = c.jumpFalse();

            code.resolve(c.trueJumps);

            genStat(body, loopEnv, CRT_STATEMENT | CRT_FLOW_TARGET);

            code.resolve(loopEnv.info.cont);

            genStats(step, loopEnv);

            code.resolve(code.branch(goto_), startpc);

            code.resolve(loopDone);

        } else {

            genStat(body, loopEnv, CRT_STATEMENT | CRT_FLOW_TARGET);

            code.resolve(loopEnv.info.cont);

            genStats(step, loopEnv);

            CondItem c;

            if (cond != null) {

                code.statBegin(cond.pos);

                c = genCond(TreeInfo.skipParens(cond), CRT_FLOW_CONTROLLER);

            } else {

                c = items.makeCondItem(goto_);

            }

            code.resolve(c.jumpTrue(), startpc);

            code.resolve(c.falseJumps);

        }

        code.resolve(loopEnv.info.exit);

    }
