    public boolean submitSolution(GridModel grid, int gridScore) {

        int targetScore = grid.countConnections();

        synchronized (this) {

            if (stats != null) {

                stats.recordScore(gridScore);

            }

            if (bestScore < gridScore) {

                grid.copyTo(bestSolution);

                bestScore = gridScore;

                logMessage(String.format("Best score: %3d/%d", bestScore, targetScore));

                writeResultToFile(grid, targetScore);

                notifyListeners(bestScore);

            }

        }

        if (!solutionFound && gridScore == targetScore) {

            solutionFound = true;

            logMessage("Solution found !");

        }

        return solutionFound;

    }
