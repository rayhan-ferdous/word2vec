        if (has("name")) {

            name = this.get("name");

        }

    }



    public static Player createEmptyZeroLevelPlayer(final String characterName) {

        final Player player = new Player(new RPObject());

        player.setID(RPObject.INVALID_ID);

        player.put("name", characterName);

        player.update();
