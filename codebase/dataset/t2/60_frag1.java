    public static void main(String[] args) {

        if (args.length == 0) {

            System.out.println("Usage: java MOL2Impl inputfile");

            System.exit(0);

        }

        MOL2 mol2 = null;

        try {

            URL url = new URL(Util.makeAbsoluteURL(args[0]));

            BufferedReader bReader = new BufferedReader(new InputStreamReader(url.openStream()));

            int idx = args[0].indexOf(".");

            String id = (idx == -1) ? args[0] : args[0].substring(0, idx);

            idx = id.lastIndexOf("\\");

            if (idx != -1) id = id.substring(idx + 1);

            mol2 = new MOL2Impl(bReader, id);

            CMLMolecule mol = mol2.getMolecule();

            StringWriter sw = new StringWriter();

            mol.debug(sw);

            System.out.println(sw.toString());

            SpanningTree sTree = new SpanningTreeImpl(mol);

            System.out.println(sTree.toSMILES());

            Writer w = new OutputStreamWriter(new FileOutputStream(id + ".xml"));

            PMRDelegate.outputEventStream(mol, w, PMRNode.PRETTY, 0);

            w.close();

            w = new OutputStreamWriter(new FileOutputStream(id + "-new.mol"));

            mol2.setOutputCMLMolecule(mol);

            mol2.output(w);

            w.close();

        } catch (Exception e) {

            System.out.println("MOL2 failed: " + e);

            e.printStackTrace();

            System.exit(0);

        }

    }

}