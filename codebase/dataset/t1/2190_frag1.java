            RRset rrset = new RRset(cname);

            addRRset(name, response, rrset, Section.ANSWER, flags);

            if (zone != null && iterations == 0) response.getHeader().setFlag(Flags.AA);

            rcode = addAnswer(response, cname.getTarget(), type, dclass, iterations + 1, flags);

        } else if (sr.isDNAME()) {

            DNAMERecord dname = sr.getDNAME();

            RRset rrset = new RRset(dname);

            addRRset(name, response, rrset, Section.ANSWER, flags);

            Name newname;

            try {

                newname = name.fromDNAME(dname);

            } catch (NameTooLongException e) {

                return Rcode.YXDOMAIN;

            }

            rrset = new RRset(new CNAMERecord(name, dclass, 0, newname));

            addRRset(name, response, rrset, Section.ANSWER, flags);

            if (zone != null && iterations == 0) response.getHeader().setFlag(Flags.AA);

            rcode = addAnswer(response, newname, type, dclass, iterations + 1, flags);

        } else if (sr.isSuccessful()) {

            RRset[] rrsets = sr.answers();

            for (int i = 0; i < rrsets.length; i++) addRRset(name, response, rrsets[i], Section.ANSWER, flags);

            if (zone != null) {

                addNS(response, zone, flags);

                if (iterations == 0) response.getHeader().setFlag(Flags.AA);

            } else addCacheNS(response, getCache(dclass), name);

        }

        return rcode;

    }
