            if (KeyCode == KeyEvent.VK_SLASH) {

                if (e.isControlDown()) {

                    SM.setMessage(ShortMessage.CHANNEL_PRESSURE, channelNum, getCharValue(KeyCode, (Integer) jSpinner1.getValue()), 0);

                } else if (e.isAltDown()) {

                    int value = getCharValue(KeyCode, 546);

                    byte[] ab = separete7bit(value);

                    SM.setMessage(ShortMessage.PITCH_BEND, channelNum, ab[0], ab[1]);

                } else {

                    SM.setMessage(ShortMessage.NOTE_ON, channelNum, 60 + ((Integer) jSpinner1.getValue() * 15), jSlider1.getValue());
