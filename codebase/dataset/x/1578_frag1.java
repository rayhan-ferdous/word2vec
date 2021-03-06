    private synchronized List<Thread> tryUpdateBlock(Block oldblock, Block newblock) throws IOException {

        final ActiveFile activefile = ongoingCreates.get(oldblock);

        if (activefile != null && !activefile.threads.isEmpty()) {

            for (Iterator<Thread> i = activefile.threads.iterator(); i.hasNext(); ) {

                final Thread t = i.next();

                if (!t.isAlive()) {

                    i.remove();

                }

            }

            if (!activefile.threads.isEmpty()) {

                return new ArrayList<Thread>(activefile.threads);

            }

        }

        File blockFile = findBlockFile(oldblock.getBlockId());

        if (blockFile == null) {

            throw new IOException("Block " + oldblock + " does not exist.");

        }

        File oldMetaFile = findMetaFile(blockFile);

        long oldgs = parseGenerationStamp(blockFile, oldMetaFile);

        File tmpMetaFile = new File(oldMetaFile.getParent(), oldMetaFile.getName() + "_tmp" + newblock.getGenerationStamp());

        if (!oldMetaFile.renameTo(tmpMetaFile)) {

            throw new IOException("Cannot rename block meta file to " + tmpMetaFile);

        }

        if (oldgs > newblock.getGenerationStamp()) {

            throw new IOException("Cannot update block (id=" + newblock.getBlockId() + ") generation stamp from " + oldgs + " to " + newblock.getGenerationStamp());

        }

        if (newblock.getNumBytes() > oldblock.getNumBytes()) {

            throw new IOException("Cannot update block file (=" + blockFile + ") length from " + oldblock.getNumBytes() + " to " + newblock.getNumBytes());

        }

        if (newblock.getNumBytes() < oldblock.getNumBytes()) {

            truncateBlock(blockFile, tmpMetaFile, oldblock.getNumBytes(), newblock.getNumBytes());

        }

        File newMetaFile = getMetaFile(blockFile, newblock);

        if (!tmpMetaFile.renameTo(newMetaFile)) {

            throw new IOException("Cannot rename tmp meta file to " + newMetaFile);

        }

        updateBlockMap(ongoingCreates, oldblock, newblock);

        updateBlockMap(volumeMap, oldblock, newblock);

        validateBlockMetadata(newblock);

        return null;

    }
