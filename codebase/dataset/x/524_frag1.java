                    while (iter_missing.hasNext()) {

                        err_buf.append(iter_missing.next());

                        err_buf.append(";");

                    }

                    fLog.log(Level.SEVERE, err_buf.toString());

                    deleteDir(war_dir);

                    return false;

                }

                if (wmd.getDeSerializationType().equalsIgnoreCase(WarMetaData.DESERIALIZE_FILE)) jar_files.add(serialized_classifier);
