        static Map buildElement2ViewMap(View parentView, Map element2ViewMap, Set elements) {

            if (elements.size() == element2ViewMap.size()) return element2ViewMap;

            if (parentView.isSetElement() && !element2ViewMap.containsKey(parentView.getElement()) && elements.contains(parentView.getElement())) {

                element2ViewMap.put(parentView.getElement(), parentView);

                if (elements.size() == element2ViewMap.size()) return element2ViewMap;

            }

            for (Iterator it = parentView.getChildren().iterator(); it.hasNext(); ) {

                buildElement2ViewMap((View) it.next(), element2ViewMap, elements);

                if (elements.size() == element2ViewMap.size()) return element2ViewMap;

            }

            for (Iterator it = parentView.getSourceEdges().iterator(); it.hasNext(); ) {

                buildElement2ViewMap((View) it.next(), element2ViewMap, elements);

                if (elements.size() == element2ViewMap.size()) return element2ViewMap;

            }

            for (Iterator it = parentView.getSourceEdges().iterator(); it.hasNext(); ) {

                buildElement2ViewMap((View) it.next(), element2ViewMap, elements);

                if (elements.size() == element2ViewMap.size()) return element2ViewMap;

            }

            return element2ViewMap;

        }
