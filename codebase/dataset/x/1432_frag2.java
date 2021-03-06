package bluetoothgateway;



import java.io.InvalidClassException;

import java.util.Date;

import java.util.Vector;



/**

 *

 * @author Clemens Lombriser <lombriser@ife.ee.ethz.ch>

 */

public class DataPacket {



    private Date m_timestamp;



    private Date m_original_timestamp;



    private Vector m_data;



    private String m_sensorname;



    private String[] m_channels;



    public DataPacket(Date timestamp, Vector data, String sensorname, String[] channels) {

        m_original_timestamp = m_timestamp = timestamp;

        m_data = data;

        m_sensorname = sensorname;

        m_channels = channels;

    }



    public String getSensorname() {

        return m_sensorname;

    }



    public String[] getChannels() {

        return m_channels;

    }



    public Vector getValues() {

        return m_data;

    }



    public long getTimestamp() {

        return m_timestamp.getTime();

    }



    public void setTimestamp(Date timestamp) {

        m_timestamp = timestamp;

    }



    /**

    * Returns the timestamp that was given initially to the DataPacket. This 

    * timestamp may have changed 

    * @return

    */

    public Date getOriginalTimestamp() {
