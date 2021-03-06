        public CustomWeight(Searcher searcher) throws IOException {

            this.similarity = getSimilarity(searcher);

            this.subQueryWeight = subQuery.weight(searcher);

            this.subQueryWeight = subQuery.weight(searcher);

            this.valSrcWeights = new Weight[valSrcQueries.length];

            for (int i = 0; i < valSrcQueries.length; i++) {

                this.valSrcWeights[i] = valSrcQueries[i].createWeight(searcher);

            }

            this.qStrict = strict;

        }
