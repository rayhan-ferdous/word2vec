        } else if (sr.isSuccessful()) {

            RRset[] rrsets = sr.answers();

            for (int i = 0; i < rrsets.length; i++) addRRset(name, response, rrsets[i], Section.ANSWER, flags);

            if (zone != null) {

                addNS(response, zone, flags);

                if (iterations == 0) response.getHeader().setFlag(Flags.AA);

            } else addCacheNS(response, getCache(dclass), name);

        }

        return rcode;
