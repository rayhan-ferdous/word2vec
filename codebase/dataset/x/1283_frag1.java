    public static float cvs2f(String str) {

        float f;

        try {

            Float F = new Float(str);

            f = F.floatValue();

        } catch (NumberFormatException e) {

            f = defaultFloat;

        }

        return (f);

    }
