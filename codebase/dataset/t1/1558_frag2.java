    public SeabirdParser() {

        super();

        initMonthMap();

        channelNames = new String[8];

        channelNames[0] = "Temperature";

        channelNames[1] = "Conductivity";

        channelNames[2] = "Pressure";

        channelNames[3] = "Salinity";

        channelNames[4] = "Voltage 0";

        channelNames[5] = "Voltage 1";

        channelNames[6] = "Voltage 2";

        channelNames[7] = "Voltage 3";

        units = new String[channelNames.length];

        units[0] = "C";

        units[1] = "S/M";

        units[2] = "dbar";

        units[3] = "psu";

        units[4] = "V";

        units[5] = "V";

        units[6] = "V";

        units[7] = "V";

        metadataChannels = new String[2];

        metadataChannels[0] = "Model";

        metadataChannels[1] = "Serial Number";

    }
