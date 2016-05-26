package antarit.dietgen.datarecords;

public abstract class DatabaseRecord{

    private Integer mId;

    DatabaseRecord() {
        setId(null);
    }

    public void setId (Integer id) {
        this.mId = id;
    }

    public Integer getId() {
        return mId;
    }

}
