    public boolean playStream() {

        isStopped = false;

        if (isPlaying()) {

            return true;

        }

        if (openBuffers.size() > 0) {

            for (int i = 0; i < BUFFER_COUNT; i++) {

                int id = openBuffers.remove(openBuffers.size() - 1);

                if (!stream(id)) {

                    openBuffers.add(id);

                    break;

                }

                idBuffer.put(0, id);

                idBuffer.rewind();

                AL10.alSourceQueueBuffers(source.getId(), idBuffer);

            }

        }

        AL10.alSourcePlay(source.getId());

        setStartTime(System.currentTimeMillis());

        return true;

    }
