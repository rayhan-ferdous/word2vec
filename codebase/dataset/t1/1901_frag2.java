    public Object callReadResolve(Object result) {

        if (result == null) {

            return null;

        } else {

            Method readResolveMethod = getMethod(result.getClass(), "readResolve", null, true);

            if (readResolveMethod != null) {

                try {

                    return readResolveMethod.invoke(result, EMPTY_ARGS);

                } catch (IllegalAccessException e) {

                    throw new ObjectAccessException("Could not call " + result.getClass().getName() + ".readResolve()", e);

                } catch (InvocationTargetException e) {

                    throw new ObjectAccessException("Could not call " + result.getClass().getName() + ".readResolve()", e.getTargetException());

                }

            } else {

                return result;

            }

        }

    }



    public Object callWriteReplace(Object object) {

        if (object == null) {

            return null;

        } else {

            Method writeReplaceMethod = getMethod(object.getClass(), "writeReplace", null, true);

            if (writeReplaceMethod != null) {

                try {

                    Object[] EMPTY_ARGS = new Object[0];

                    return writeReplaceMethod.invoke(object, EMPTY_ARGS);

                } catch (IllegalAccessException e) {

                    throw new ObjectAccessException("Could not call " + object.getClass().getName() + ".writeReplace()", e);

                } catch (InvocationTargetException e) {

                    throw new ObjectAccessException("Could not call " + object.getClass().getName() + ".writeReplace()", e.getTargetException());

                }

            } else {

                return object;

            }

        }

    }



    public boolean supportsReadObject(Class type, boolean includeBaseClasses) {

        return getMethod(type, "readObject", new Class[] { ObjectInputStream.class }, includeBaseClasses) != null;

    }



    public void callReadObject(Class type, Object object, ObjectInputStream stream) {

        try {

            Method readObjectMethod = getMethod(type, "readObject", new Class[] { ObjectInputStream.class }, false);

            readObjectMethod.invoke(object, new Object[] { stream });

        } catch (IllegalAccessException e) {

            throw new ConversionException("Could not call " + object.getClass().getName() + ".readObject()", e);

        } catch (InvocationTargetException e) {

            throw new ConversionException("Could not call " + object.getClass().getName() + ".readObject()", e.getTargetException());

        }

    }



    public boolean supportsWriteObject(Class type, boolean includeBaseClasses) {

        return getMethod(type, "writeObject", new Class[] { ObjectOutputStream.class }, includeBaseClasses) != null;

    }



    public void callWriteObject(Class type, Object instance, ObjectOutputStream stream) {

        try {

            Method readObjectMethod = getMethod(type, "writeObject", new Class[] { ObjectOutputStream.class }, false);

            readObjectMethod.invoke(instance, new Object[] { stream });

        } catch (IllegalAccessException e) {

            throw new ConversionException("Could not call " + instance.getClass().getName() + ".writeObject()", e);

        } catch (InvocationTargetException e) {

            throw new ConversionException("Could not call " + instance.getClass().getName() + ".writeObject()", e.getTargetException());

        }

    }



    private Method getMethod(Class type, String name, Class[] parameterTypes, boolean includeBaseclasses) {
