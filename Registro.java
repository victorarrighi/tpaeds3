public abstract class Registro {
    protected int bytes;
    protected boolean lapide;

    abstract public int getID();

    abstract public int setID(int id);

    abstract public byte[] serialize();

    abstract public void deserialize(byte[] ba);

    public boolean isRecordValid() {
        return this.lapide == false;
    }
}
