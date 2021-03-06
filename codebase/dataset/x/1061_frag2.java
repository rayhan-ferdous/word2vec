    private String getTaskList(ScheduleItem item) {

        StringBuffer buff = new StringBuffer(1024);

        HashMap<String, TaskCommand> tasks = store.getTaskList();

        String selectedTask = store.getProperty("Tasks.DefTask");

        if (item != null) {

            selectedTask = item.getPostTask();

        }

        String[] keys = (String[]) tasks.keySet().toArray(new String[0]);

        Arrays.sort(keys);

        if (selectedTask.length() == 0) buff.append("<label><input type='radio' name='task' value='none' checked>none</label><br>\n"); else buff.append("<label><input type='radio' name='task' value='none'>none</label><br>\n");

        for (int x = 0; x < keys.length; x++) {

            if (keys[x].equals(selectedTask)) buff.append("<label><input type='radio' name='task' value='" + keys[x] + "' checked>" + keys[x] + "</label><br>\n"); else buff.append("<label><input type='radio' name='task' value='" + keys[x] + "'>" + keys[x] + "</label><br>\n");

        }

        return buff.toString();

    }
