                value[2] = summary.cytosineMethyLevel;

            } else if (tmpKey[0].equalsIgnoreCase("CG")) {

                value[2] = BAC.autoEstimateCpg ? summary.cpgMethyLevel : BAC.forceCpg;

            } else if (tmpKey[0].equalsIgnoreCase("CH")) {

                value[2] = BAC.autoEstimateCph ? summary.cphMethyLevel : BAC.forceCph;

            } else if (tmpKey[0].equalsIgnoreCase("GCH")) {

                value[2] = BAC.autoEstimateGch ? summary.gchMethyLevel : BAC.forceGch;

            } else if (tmpKey[0].equalsIgnoreCase("CCH")) {

                value[2] = BAC.autoEstimateCch ? summary.cchMethyLevel : BAC.forceCch;

            } else if (tmpKey[0].equalsIgnoreCase("WCH")) {

                value[2] = BAC.autoEstimateWch ? summary.wchMethyLevel : BAC.forceWch;

            } else if (tmpKey[0].equalsIgnoreCase("GCG")) {

                value[2] = BAC.autoEstimateGcg ? summary.gcgMethyLevel : BAC.forceGcg;

            } else if (tmpKey[0].equalsIgnoreCase("CCG")) {
