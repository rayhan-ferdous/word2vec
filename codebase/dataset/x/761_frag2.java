            dir.mkdirs();

            if (dir.isDirectory()) {

                String[] profiles = { "h300.profile", "pmp.profile", "x5.profile", "zvm.profile", "u10.profile" };

                for (int i = 0; i < profiles.length; i++) {

                    writeFile(profiles[i], new File(dir, profiles[i]));
