    private static CommandLine parse(String[] args) {

        Options opts = new Options();

        OptionBuilder.withArgName("name");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("set device name, use DCMQR by default");

        opts.addOption(OptionBuilder.create("device"));

        OptionBuilder.withArgName("aet[@host][:port]");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("set AET, local address and listening port of local" + "Application Entity, use device name and pick up any valid " + "local address to bind the socket by default");

        opts.addOption(OptionBuilder.create("L"));

        OptionBuilder.withArgName("username");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("enable User Identity Negotiation with specified username and " + " optional passcode");

        opts.addOption(OptionBuilder.create("username"));

        OptionBuilder.withArgName("passcode");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("optional passcode for User Identity Negotiation, " + "only effective with option -username");

        opts.addOption(OptionBuilder.create("passcode"));

        opts.addOption("uidnegrsp", false, "request positive User Identity Negotation response, " + "only effective with option -username");

        OptionBuilder.withArgName("NULL|3DES|AES");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("enable TLS connection without, 3DES or AES encryption");

        opts.addOption(OptionBuilder.create("tls"));

        OptionGroup tlsProtocol = new OptionGroup();

        tlsProtocol.addOption(new Option("tls1", "disable the use of SSLv3 and SSLv2 for TLS connections"));

        tlsProtocol.addOption(new Option("ssl3", "disable the use of TLSv1 and SSLv2 for TLS connections"));

        tlsProtocol.addOption(new Option("no_tls1", "disable the use of TLSv1 for TLS connections"));

        tlsProtocol.addOption(new Option("no_ssl3", "disable the use of SSLv3 for TLS connections"));

        tlsProtocol.addOption(new Option("no_ssl2", "disable the use of SSLv2 for TLS connections"));

        opts.addOptionGroup(tlsProtocol);

        opts.addOption("noclientauth", false, "disable client authentification for TLS");

        OptionBuilder.withArgName("file|url");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("file path or URL of P12 or JKS keystore, resource:tls/test_sys_1.p12 by default");

        opts.addOption(OptionBuilder.create("keystore"));

        OptionBuilder.withArgName("password");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("password for keystore file, 'secret' by default");

        opts.addOption(OptionBuilder.create("keystorepw"));

        OptionBuilder.withArgName("password");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("password for accessing the key in the keystore, keystore password by default");

        opts.addOption(OptionBuilder.create("keypw"));

        OptionBuilder.withArgName("file|url");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("file path or URL of JKS truststore, resource:tls/mesa_certs.jks by default");

        opts.addOption(OptionBuilder.create("truststore"));

        OptionBuilder.withArgName("password");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("password for truststore file, 'secret' by default");

        opts.addOption(OptionBuilder.create("truststorepw"));

        OptionBuilder.withArgName("aet");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("retrieve instances of matching entities by C-MOVE to specified destination.");

        opts.addOption(OptionBuilder.create("cmove"));

        opts.addOption("cget", false, "retrieve instances of matching entities by C-GET.");

        OptionBuilder.withArgName("cuid[:ts]");

        OptionBuilder.hasArgs();

        OptionBuilder.withDescription("negotiate support of specified Storage SOP Class and Transfer " + "Syntaxes. The Storage SOP Class may be specified by its UID " + "or by one of following key words:\n" + "CR  - Computed Radiography Image Storage\n" + "CT  - CT Image Storage\n" + "MR  - MRImageStorage\n" + "US  - Ultrasound Image Storage\n" + "NM  - Nuclear Medicine Image Storage\n" + "PET - PET Image Storage\n" + "SC  - Secondary Capture Image Storage\n" + "XA  - XRay Angiographic Image Storage\n" + "XRF - XRay Radiofluoroscopic Image Storage\n" + "DX  - Digital X-Ray Image Storage for Presentation\n" + "MG  - Digital Mammography X-Ray Image Storage for Presentation\n" + "PR  - Grayscale Softcopy Presentation State Storage\n" + "KO  - Key Object Selection Document Storage\n" + "SR  - Basic Text Structured Report Document Storage\n" + "The Transfer Syntaxes may be specified by a comma " + "separated list of UIDs or by one of following key " + "words:\n" + "IVRLE - offer only Implicit VR Little Endian " + "Transfer Syntax\n" + "LE - offer Explicit and Implicit VR Little Endian " + "Transfer Syntax\n" + "BE - offer Explicit VR Big Endian Transfer Syntax\n" + "DEFL - offer Deflated Explicit VR Little " + "Endian Transfer Syntax\n" + "JPLL - offer JEPG Loss Less Transfer Syntaxes\n" + "JPLY - offer JEPG Lossy Transfer Syntaxes\n" + "MPEG2 - offer MPEG2 Transfer Syntax\n" + "NOPX - offer No Pixel Data Transfer Syntax\n" + "NOPXD - offer No Pixel Data Deflate Transfer Syntax\n" + "If only the Storage SOP Class is specified, all " + "Transfer Syntaxes listed above except No Pixel Data " + "and No Pixel Data Delflate Transfer Syntax are " + "offered.");

        opts.addOption(OptionBuilder.create("cstore"));

        OptionBuilder.withArgName("dir");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("store received objects into files in specified directory <dir>." + " Do not store received objects by default.");

        opts.addOption(OptionBuilder.create("cstoredest"));

        opts.addOption("ivrle", false, "offer only Implicit VR Little Endian Transfer Syntax.");

        OptionBuilder.withArgName("maxops");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("maximum number of outstanding C-MOVE-RQ " + "it may invoke asynchronously, 1 by default.");

        opts.addOption(OptionBuilder.create("async"));

        OptionBuilder.withArgName("maxops");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("maximum number of outstanding storage operations performed " + "asynchronously, unlimited by default.");

        opts.addOption(OptionBuilder.create("storeasync"));

        opts.addOption("noextneg", false, "disable extended negotiation.");

        opts.addOption("rel", false, "negotiate support of relational queries and retrieval.");

        opts.addOption("datetime", false, "negotiate support of combined date and time attribute range matching.");

        opts.addOption("fuzzy", false, "negotiate support of fuzzy semantic person name attribute matching.");

        opts.addOption("retall", false, "negotiate private FIND SOP Classes " + "to fetch all available attributes of matching entities.");

        opts.addOption("blocked", false, "negotiate private FIND SOP Classes " + "to return attributes of several matching entities per FIND " + "response.");

        opts.addOption("vmf", false, "negotiate private FIND SOP Classes to " + "return attributes of legacy CT/MR images of one series as " + "virtual multiframe object.");

        opts.addOption("pdv1", false, "send only one PDV in one P-Data-TF PDU, pack command and data " + "PDV in one P-DATA-TF PDU by default.");

        opts.addOption("tcpdelay", false, "set TCP_NODELAY socket option to false, true by default");

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for TCP connect, no timeout by default");

        opts.addOption(OptionBuilder.create("connectTO"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("delay in ms for Socket close after sending A-ABORT, 50ms by default");

        opts.addOption(OptionBuilder.create("soclosedelay"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("period in ms to check for outstanding DIMSE-RSP, 10s by default");

        opts.addOption(OptionBuilder.create("reaper"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for receiving C-FIND-RSP, 60s by default");

        opts.addOption(OptionBuilder.create("cfindrspTO"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for receiving C-MOVE-RSP and C-GET RSP, 600s by default");

        opts.addOption(OptionBuilder.create("cmoverspTO"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for receiving C-GET-RSP and C-MOVE RSP, 600s by default");

        opts.addOption(OptionBuilder.create("cgetrspTO"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for receiving A-ASSOCIATE-AC, 5s by default");

        opts.addOption(OptionBuilder.create("acceptTO"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("timeout in ms for receiving A-RELEASE-RP, 5s by default");

        opts.addOption(OptionBuilder.create("releaseTO"));

        OptionBuilder.withArgName("KB");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("maximal length in KB of received P-DATA-TF PDUs, 16KB by default");

        opts.addOption(OptionBuilder.create("rcvpdulen"));

        OptionBuilder.withArgName("KB");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("maximal length in KB of sent P-DATA-TF PDUs, 16KB by default");

        opts.addOption(OptionBuilder.create("sndpdulen"));

        OptionBuilder.withArgName("KB");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("set SO_RCVBUF socket option to specified value in KB");

        opts.addOption(OptionBuilder.create("sorcvbuf"));

        OptionBuilder.withArgName("KB");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("set SO_SNDBUF socket option to specified value in KB");

        opts.addOption(OptionBuilder.create("sosndbuf"));

        OptionBuilder.withArgName("KB");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("minimal buffer size to write received object to file, 1KB by default");

        opts.addOption(OptionBuilder.create("filebuf"));

        OptionGroup qrlevel = new OptionGroup();

        OptionBuilder.withDescription("perform patient level query, multiple " + "exclusive with -S and -I, perform study level query " + "by default.");

        OptionBuilder.withLongOpt("patient");

        opts.addOption(OptionBuilder.create("P"));

        OptionBuilder.withDescription("perform series level query, multiple " + "exclusive with -P and -I, perform study level query " + "by default.");

        OptionBuilder.withLongOpt("series");

        opts.addOption(OptionBuilder.create("S"));

        OptionBuilder.withDescription("perform instance level query, multiple " + "exclusive with -P and -S, perform study level query " + "by default.");

        OptionBuilder.withLongOpt("image");

        opts.addOption(OptionBuilder.create("I"));

        OptionBuilder.withArgName("cuid");

        OptionBuilder.hasArgs();

        OptionBuilder.withDescription("negotiate addition private C-FIND SOP " + "class with specified UID");

        opts.addOption(OptionBuilder.create("cfind"));

        opts.addOptionGroup(qrlevel);

        OptionBuilder.withArgName("[seq/]attr=value");

        OptionBuilder.hasArgs();

        OptionBuilder.withValueSeparator('=');

        OptionBuilder.withDescription("specify matching key. attr can be " + "specified by name or tag value (in hex), e.g. PatientName " + "or 00100010. Attributes in nested Datasets can " + "be specified by including the name/tag value of " + "the sequence attribute, e.g. 00400275/00400009 " + "for Scheduled Procedure Step ID in the Request " + "Attributes Sequence");

        opts.addOption(OptionBuilder.create("q"));

        OptionBuilder.withArgName("attr");

        OptionBuilder.hasArgs();

        OptionBuilder.withDescription("specify additional return key. attr can " + "be specified by name or tag value (in hex).");

        opts.addOption(OptionBuilder.create("r"));

        OptionBuilder.withArgName("num");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("cancel query after receive of specified " + "number of responses, no cancel by default");

        opts.addOption(OptionBuilder.create("C"));

        OptionBuilder.withArgName("aet");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("retrieve matching objects to specified " + "move destination.");

        opts.addOption(OptionBuilder.create("cmove"));

        opts.addOption("evalRetrieveAET", false, "Only Move studies not allready stored on destination AET");

        opts.addOption("lowprior", false, "LOW priority of the C-FIND/C-MOVE operation, MEDIUM by default");

        opts.addOption("highprior", false, "HIGH priority of the C-FIND/C-MOVE operation, MEDIUM by default");

        OptionBuilder.withArgName("num");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("repeat query (and retrieve) several times");

        opts.addOption(OptionBuilder.create("repeat"));

        OptionBuilder.withArgName("ms");

        OptionBuilder.hasArg();

        OptionBuilder.withDescription("delay in ms between repeated query (and retrieve), no delay by default");

        opts.addOption(OptionBuilder.create("repeatdelay"));

        opts.addOption("reuseassoc", false, "Reuse association for repeated query (and retrieve)");

        opts.addOption("closeassoc", false, "Close association between repeated query (and retrieve)");

        opts.addOption("h", "help", false, "print this message");

        opts.addOption("V", "version", false, "print the version information and exit");

        CommandLine cl = null;

        try {

            cl = new GnuParser().parse(opts, args);

        } catch (ParseException e) {

            throw new RuntimeException("unreachable");

        }

        return cl;

    }
