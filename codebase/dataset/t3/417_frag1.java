    public void write_attributes_3(fr.esrf.Tango.AttributeValue[] values) throws fr.esrf.Tango.MultiDevFailed, fr.esrf.Tango.DevFailed {

        while (true) {

            if (!this._is_local()) {

                org.omg.CORBA.portable.InputStream _is = null;

                try {

                    org.omg.CORBA.portable.OutputStream _os = _request("write_attributes_3", true);

                    fr.esrf.Tango.AttributeValueListHelper.write(_os, values);

                    _is = _invoke(_os);

                    return;

                } catch (org.omg.CORBA.portable.RemarshalException _rx) {

                } catch (org.omg.CORBA.portable.ApplicationException _ax) {

                    String _id = _ax.getId();

                    if (_id.equals("IDL:Tango/MultiDevFailed:1.0")) {

                        throw fr.esrf.Tango.MultiDevFailedHelper.read(_ax.getInputStream());

                    } else if (_id.equals("IDL:Tango/DevFailed:1.0")) {

                        throw fr.esrf.Tango.DevFailedHelper.read(_ax.getInputStream());

                    } else throw new RuntimeException("Unexpected exception " + _id);

                } finally {

                    this._releaseReply(_is);

                }

            } else {

                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("write_attributes_3", _opsClass);

                if (_so == null) throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");

                Device_4Operations _localServant = (Device_4Operations) _so.servant;

                try {

                    _localServant.write_attributes_3(values);
