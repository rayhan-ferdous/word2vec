    void sendDirectory(WorkingDirectory workingDirectory) throws IOException, CVSException {

        sendDirectory(workingDirectory.getDirectory(), workingDirectory.getAbsoluteRepositoryPath());

        if (!m_WorkingDirectoriesStateHasBeenSentFor.contains(workingDirectory)) {

            if (m_CurrentRequest.getSendQuestionableCommands()) {

                File[] files = workingDirectory.getDirectory().listFiles(m_IgnoreFileFilter);

                Arrays.sort(files);

                for (int i = 0; i < files.length; i++) {

                    File file = files[i];

                    if (workingDirectory.getEntry(file) == null) sendQuestionable(file.getName());

                }

            }

            if (workingDirectory.isStaticDirectory()) sendStaticDirectory();

            String sStickyTag = workingDirectory.getStickyTagSpec();

            if (sStickyTag != null) sendSticky(sStickyTag);

            String sCheckinProg = workingDirectory.getCheckinProgram();

            if (sCheckinProg != null) sendCheckinProg(sCheckinProg);

            String sUpdateProg = workingDirectory.getUpdateProgram();

            if (sUpdateProg != null) sendUpdateProg(sUpdateProg);

            m_WorkingDirectoriesStateHasBeenSentFor.add(workingDirectory);

        }

    }
