    public static Vector retrieve(String usrlogin) throws DbException {

        Db db = null;

        String sql = "";

        try {

            db = new Db();

            Statement stmt = db.getStatement();

            SQLRenderer r = new SQLRenderer();

            r.add("tab_id");

            r.add("tab_title");

            r.add("display_type");

            r.add("user_login", usrlogin);

            sql = r.getSQLSelect("tab_template", "sequence");

            ResultSet rs = stmt.executeQuery(sql);

            Vector v = new Vector();

            while (rs.next()) {

                String id = rs.getString("tab_id");

                String title = rs.getString("tab_title");

                String displaytype = rs.getString("display_type");

                v.addElement(new Tab(id, title, displaytype));

            }
