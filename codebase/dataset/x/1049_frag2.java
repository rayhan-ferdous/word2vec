        public LinearFeatureModel() {

            float hue = random.nextFloat();

            Color color = Color.getHSBColor(hue, 1f, 1f);

            setColor(color);

            setClusterNumber(clusterCounter++);

            rhoFilter = new LowpassFilter();

            thetaFilter = new LowpassFilter();

            rhoFilter.setTauMs(tauMs);

            thetaFilter.setTauMs(tauMs);

        }
