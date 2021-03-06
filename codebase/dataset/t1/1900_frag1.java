    public void postSign(byte[] signatureValue, List<X509Certificate> signingCertificateChain) {

        LOG.debug("postSign");

        HttpServletRequest httpServletRequest;

        try {

            httpServletRequest = (HttpServletRequest) PolicyContext.getContext("javax.servlet.http.HttpServletRequest");

        } catch (PolicyContextException e) {

            throw new RuntimeException("JACC error: " + e.getMessage());

        }

        String signatureValueStr = new String(Hex.encodeHex(signatureValue));

        HttpSession session = httpServletRequest.getSession();

        session.setAttribute("SignatureValue", signatureValueStr);

        session.setAttribute("SigningCertificateChain", signingCertificateChain);

    }
