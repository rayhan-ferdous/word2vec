            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            din = AudioSystem.getAudioInputStream(decodedFormat, in);

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
