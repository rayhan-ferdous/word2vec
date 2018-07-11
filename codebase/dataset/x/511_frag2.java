        if (b0 == 0x00 && b1 == 0x3C && b2 == 0x00 && b3 == 0x3F) {

            return "UTF-16BE";

        }

        if (b0 == 0x3C && b1 == 0x00 && b2 == 0x3F && b3 == 0x00) {

            return "UTF-16LE";

        }

        if (b0 == 0x4C && b1 == 0x6F && b2 == 0xA7 && b3 == 0x94) {

            return "CP037";

        }

        return null;
