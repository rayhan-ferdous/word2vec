    public static Application foldApplication3(final Value p1, final Value p2, final Value p3, final Application app) {

        assert p1 != null : "null p1";

        assert p2 != null : "null p2";

        assert p3 != null : "null p3";

        assert app != null : "null app";

        app.params[app.next--] = p3;

        app.params[app.next--] = p2;

        app.params[app.next--] = p1;

        return app;

    }
