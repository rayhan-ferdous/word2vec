    public DataEvent(DataEventAction eventSource, int batchId, String channelId, boolean error, int numberOfRowsAffected) {

        this.eventSource = eventSource;

        this.batchId = batchId;

        this.channelId = channelId;

        this.error = error;

        this.numberOfRowsAffected = numberOfRowsAffected;

    }
