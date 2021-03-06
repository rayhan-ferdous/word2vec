    public void writer__handleArgs(String[] rArgs) {

        String argName_OutputFolder = writer__ARGUMENT_CONTROL_PREFIX + writer__ARGUMENT_OutputFolder;

        String argName_BackupFolder = writer__ARGUMENT_CONTROL_PREFIX + writer__ARGUMENT_BackupFolder;

        String argName_NoBackup = writer__ARGUMENT_CONTROL_PREFIX + writer__ARGUMENT_NoBackup;

        int numArgs = rArgs.length;

        for (int argI = 0; argI < numArgs; argI++) {

            if (rArgs[argI].startsWith(argName_OutputFolder)) {

                _setOutputFolder(rArgs[argI].substring(argName_OutputFolder.length()));

            } else if (rArgs[argI].startsWith(argName_BackupFolder)) {

                _setBackupFolder(rArgs[argI].substring(argName_BackupFolder.length()));

            } else if (argName_NoBackup.equals(rArgs[argI])) {

                _backup(false);

            }

        }

        writer__setArgs(rArgs.length, rArgs);

    }
