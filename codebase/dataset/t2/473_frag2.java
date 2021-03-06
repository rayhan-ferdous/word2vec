    private int evalExpr(StreamTokenizer st) throws IOException {

        int val = evalTerm(st);

        int token = st.nextToken();

        while (token == '+' || token == '-' || token == '|' || token == '^') {

            int t = evalTerm(st);

            switch(token) {

                case '+':

                    val += t;

                    break;

                case '-':

                    val -= t;

                    break;

                case '|':

                    val |= t;

                    break;

                case '^':

                    val ^= t;

                    break;

                default:

                    throw new IOException("Invalid token");

            }

            token = st.nextToken();

        }

        st.pushBack();

        return val;

    }
