    public static XMLEncoder getXMLEncoder(String encoding) {

        XMLEncoder encoder = null;

        try {

            encoder = XMLEncoderFactory.getEncoder(encoding);

        } catch (Exception e) {

            log.error(Messages.getMessage("exception00"), e);

            encoder = XMLEncoderFactory.getDefaultEncoder();

        }

        return encoder;

    }
