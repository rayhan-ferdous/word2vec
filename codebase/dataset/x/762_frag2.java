    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeInt(pid);

        out.writeInt(eid);

        out.writeObject(quorumWeaks);

        out.writeObject(writeSet);

    }
