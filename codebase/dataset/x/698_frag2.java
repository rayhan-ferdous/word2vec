    private org.omg.CORBA.portable.OutputStream _OB_op_try_pull_structured_event(org.omg.CORBA.portable.InputStream in, org.omg.CORBA.portable.ResponseHandler handler) {

        org.omg.CORBA.portable.OutputStream out = null;

        try {

            org.omg.CORBA.BooleanHolder _ob_ah0 = new org.omg.CORBA.BooleanHolder();

            org.omg.CosNotification.StructuredEvent _ob_r = try_pull_structured_event(_ob_ah0);

            out = handler.createReply();

            org.omg.CosNotification.StructuredEventHelper.write(out, _ob_r);

            out.write_boolean(_ob_ah0.value);

        } catch (org.omg.CosEventComm.Disconnected _ob_ex) {

            out = handler.createExceptionReply();

            org.omg.CosEventComm.DisconnectedHelper.write(out, _ob_ex);

        }

        return out;

    }
