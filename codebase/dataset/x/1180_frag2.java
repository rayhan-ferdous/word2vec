    private void undoCreateResources() {

        if (this.fileSpeicher == null) {

            return;

        }

        Iterator it = this.fileSpeicher.iterator();

        while (it.hasNext()) {

            ResourceTreeItem item = (ResourceTreeItem) it.next();

            this.undoCommands.add(new RemoveResourceItemCommand(item));

        }

    }
