        private String getConstantValue(String constantName) {

            String value = "0";

            Object top = this.digester.peek();

            if (top != null) {

                Class clazz = top.getClass();

                try {

                    java.lang.reflect.Field field = clazz.getField(constantName);

                    Object fieldValue = field.get(top);

                    if (fieldValue != null) {

                        value = fieldValue.toString();

                    }

                } catch (Exception e) {

                }

            }

            return value;

        }
