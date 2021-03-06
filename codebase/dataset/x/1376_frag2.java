    public KorgWavestationSinglePatchDriver() {

        super("Single Patch", "Gerrit Gehnen");

        sysexID = "F0423*2840";

        sysexRequestDump = new SysexHandler("F0 42 @@ 28 10 *bankNum* *patchNum* F7");

        trimSize = 852 + 9;

        patchNameStart = 0;

        patchNameSize = 0;

        deviceIDoffset = 0;

        checksumStart = 7;

        checksumEnd = 852 + 6;

        checksumOffset = 852 + 7;

        bankNumbers = new String[] { "RAM1", "RAM2", "ROM1", "CARD", "RAM3" };

        patchNumbers = new String[] { "01-", "02-", "03-", "04-", "05-", "06-", "07-", "08-", "09-", "10-", "11-", "12-", "13-", "14-", "15-", "16-", "17-", "18-", "19-", "20-", "21-", "22-", "23-", "24-", "25-", "26-", "27-", "28-", "29-", "30-", "31-", "32-", "33-", "34-", "35-" };

    }
