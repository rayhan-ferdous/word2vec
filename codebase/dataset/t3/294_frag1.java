    private org.omg.CORBA.portable.OutputStream _OB_op_set_qos(org.omg.CORBA.portable.InputStream in, org.omg.CORBA.portable.ResponseHandler handler) {

        org.omg.CORBA.portable.OutputStream out = null;

        try {

            org.omg.CosNotification.Property[] _ob_a0 = org.omg.CosNotification.QoSPropertiesHelper.read(in);

            set_qos(_ob_a0);

            out = handler.createReply();

        } catch (org.omg.CosNotification.UnsupportedQoS _ob_ex) {

            out = handler.createExceptionReply();

            org.omg.CosNotification.UnsupportedQoSHelper.write(out, _ob_ex);

        }

        return out;

    }
