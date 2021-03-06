    public static boolean isSelfSigned(Certificate cert, String sigProvider) throws CertificateException {

        if (isSelfIssued(cert)) {

            try {

                if (sigProvider == null) {

                    cert.verify(cert.getPublicKey());

                } else {

                    cert.verify(cert.getPublicKey(), sigProvider);

                }

                return true;

            } catch (Exception e) {

            }

        }

        return false;

    }
