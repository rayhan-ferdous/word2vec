                for (int i = 0; i < rrsets.length; i++) addRRset(name, response, rrsets[i], sigonly);

            }

        }

        addAuthority(response, name, zone);

        addAdditional(response);

        if (queryTSIG != null) {

            try {

                if (tsig != null) tsig.apply(response, queryTSIG);

            } catch (IOException e) {

            }

        }

        try {

            byte[] out = response.toWire();

            if (out.length > maxLength) {

                truncate(response, out.length, maxLength);

                if (tsig != null) tsig.apply(response, queryTSIG);

            }

        } catch (IOException e) {

        }

        return response;

    }
