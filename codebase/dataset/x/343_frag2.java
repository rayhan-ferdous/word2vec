                        this.error(0, "No data contained in '" + this.url.toString() + "'");

                        return;

                    }

                    try {

                        if (!filename.toLowerCase().endsWith(".torrent")) {

                            TOTorrent torrent = TorrentUtils.readFromFile(file, false);

                            String name = TorrentUtils.getLocalisedName(torrent) + ".torrent";

                            File new_file = new File(directoryname, name);
