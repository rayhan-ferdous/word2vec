        if (to == null) return;

        for (int b = 0; b < numBehaviors(); b++) {

            Behavior B = fetchBehavior(b);

            if ((B != null) && (B.ID().equals(to.ID()))) return;

        }

        to.startBehavior(this);
