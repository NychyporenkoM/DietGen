package antarit.dietgen.datarecords;

public class CompatibilityRecord extends DatabaseRecord {

    private Integer mCategoryId;

    public CompatibilityRecord() {
        super();
        setCategoryId(null);
    }

    public void setCategoryId (Integer categoryId) {
        this.mCategoryId = categoryId;
    }

    public Integer getCategoryId() {
        return mCategoryId;
    }

}