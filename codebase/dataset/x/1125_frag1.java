    private static int quantize24(float sample, float ditherBits) {

        if (ditherBits != 0) {

            sample += random.nextFloat() * ditherBits;

        }

        if (sample >= 8388607.0f) {

            return 8388607;

        } else if (sample <= -8388608.0f) {

            return -8388608;

        } else {

            return (int) (sample < 0 ? (sample - 0.5f) : (sample + 0.5f));

        }

    }



    private static int quantize32(float sample, float ditherBits) {

        if (ditherBits != 0) {

            sample += random.nextFloat() * ditherBits;

        }

        if (sample >= 2147483647.0f) {

            return 2147483647;

        } else if (sample <= -2147483648.0f) {

            return -2147483648;

        } else {

            return (int) (sample < 0 ? (sample - 0.5f) : (sample + 0.5f));

        }

    }



    public static void float2byte(List input, int inOffset, byte[] output, int outByteOffset, int frameCount, AudioFormat format, float ditherBits) {
