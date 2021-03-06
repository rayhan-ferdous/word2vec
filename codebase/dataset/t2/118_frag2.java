        } catch (Exception e) {

            logger.logError("unable to lock file " + this.file + ": " + e.getMessage());

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        org.apache.xerces.parsers.DOMParser parser = new org.apache.xerces.parsers.DOMParser();

        try {

            parser.parse(this.file);

        } catch (Exception e) {

            logger.logError("cannot parse sequence file " + this.file + ": " + e.getMessage());

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        } finally {

            try {

                lock.release();

                channel.close();

            } catch (java.io.IOException ie) {

                logger.logError("cannot release lock on file " + this.file + ": " + ie.getMessage());

                throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

            }

        }

        org.w3c.dom.Document doc = parser.getDocument();

        org.w3c.dom.NodeList nodes = doc.getElementsByTagName("sequence");

        if (nodes == null) {

            logger.logError("no id node, error in parsing " + this.file);

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        if (nodes.getLength() != 1) {

            logger.logError("multiple nodes, error in parsing " + this.file);

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        org.w3c.dom.Element element = (org.w3c.dom.Element) nodes.item(0);

        String value = element.getFirstChild().getNodeValue();

        int ret;

        try {

            ret = Integer.parseInt(value.trim());

        } catch (NumberFormatException nfe) {

            logger.logError("unable to parse: " + value);

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        if (ret < 0) {

            logger.logError("bad number: " + ret);

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        return (ret);

    }



    private void setSequence(long sequence) throws org.osid.id.IdException {

        if (this.file == null) {

            logger.logError("no sequence file specified");

            throw new org.osid.id.IdException(org.osid.id.IdException.CONFIGURATION_ERROR);

        }

        java.io.File file;

        java.nio.channels.FileChannel channel;

        java.nio.channels.FileLock lock;

        try {

            file = new java.io.File(this.file);

            channel = new java.io.RandomAccessFile(file, "r").getChannel();

            lock = channel.lock(0, Integer.MAX_VALUE, true);

        } catch (Exception e) {

            logger.logError("unable to lock file " + this.file + ": " + e.getMessage());

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss Z");

        java.util.Date date = new java.util.Date();

        try {

            java.io.BufferedWriter out = new java.io.BufferedWriter(new java.io.FileWriter(file));

            out.write("<?xml version=\"1.0\"?>\n");

            out.write("<IdManager impl=\"edu.mit.osidimpl.id.local\">\n");

            out.write("    <id assignedDate=\"" + sdf.format(date) + "\">\n");

            if (this.prefix != null) {

                out.write("        <prefix>\n");

                out.write("            " + this.prefix + "\n");

                out.write("        </prefix>\n");

            }

            out.write("        <sequence>\n");

            out.write("            " + sequence + "\n");

            out.write("        </sequence>\n");

            out.write("    </id>\n");

            out.write("</IdManager>\n");

            out.close();

        } catch (java.io.IOException ie) {

            logger.logError("unable to write identifier to " + this.file + ": " + ie.getMessage());

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        try {

            lock.release();

            channel.close();

        } catch (java.io.IOException ie) {

            logger.logError("cannot release lock on file " + this.file + ": " + ie.getMessage());

            throw new org.osid.id.IdException(org.osid.id.IdException.OPERATION_FAILED);

        }

        return;

    }

}
