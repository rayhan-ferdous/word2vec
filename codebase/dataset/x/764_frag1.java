            if (verbose) System.err.println("load:" + name + "\t" + total + "bytes");

            in.close();

            in = null;

            return defineClass(name, data, 0, total);

        } catch (IOException e) {

            e.printStackTrace();

            throw e;

        } finally {

            if (in != null) {
