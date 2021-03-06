    @Override

    public List<WSConcept> getConceptsOfContext(Long graphId, Integer contextConceptId) {

        ONDEXGraph graph = ONDEXGraphRegistry.graphs.get(graphId);

        List<WSConcept> list = new ArrayList<WSConcept>();

        ONDEXConcept concept = graph.getConcept(contextConceptId);

        ONDEXView<ONDEXConcept> concepts = graph.getConceptsOfContext(concept);

        while (concepts.hasNext()) {

            list.add(new WSConcept(concepts.next()));

        }

        return list;

    }
