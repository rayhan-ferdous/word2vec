    private String evalString(StreamTokenizer st) throws IOException {

        int token = st.nextToken();

        switch(token) {

            case StreamTokenizer.TT_WORD:

                if (st.sval.equals("@UPPER")) return evalUpper(st); else if (st.sval.equals("@LOWER")) return evalLower(st); else if (st.sval.equals("@PAD")) return evalPad(st); else if (st.sval.equals("@SUBST")) return evalSubst(st); else if (st.sval.equals("@IF")) return evalIf(st); else if (st.sval.equals("@LENGTH")) break; else return st.sval;

            case '"':

                return st.sval;

            default:

                break;

        }

        st.pushBack();

        return Integer.toString(evalExpr(st));

    }
