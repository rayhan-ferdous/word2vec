        private EmitterSet[] makeSplit(SplitRecord split) {

            int arg = split.argument;

            int test = split.test;

            EmitterSet yes = new EmitterSet();

            EmitterSet no = new EmitterSet();

            Iterator i = emitters.iterator();

            while (i.hasNext()) {

                EmitterDescriptor ed = (EmitterDescriptor) i.next();

                if (ed.argMatchesEncoding(arg, test)) yes.add(ed); else no.add(ed);

            }

            return new EmitterSet[] { yes, no };

        }
