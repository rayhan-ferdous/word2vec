        if (st.nextToken() != ',') throw new IOException("Missing ','");

        if (st.nextToken() != '"') throw new IOException("Invalid string");

        String newc = st.sval;

        if (st.nextToken() != ')') throw new IOException("Missing ')'");

        for (int i = 0; i < val.length(); i++) {
