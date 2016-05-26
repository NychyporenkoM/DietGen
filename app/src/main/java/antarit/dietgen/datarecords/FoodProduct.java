package antarit.dietgen.datarecords;

public class FoodProduct extends DatabaseRecord {

    private Integer mCategoryId;
    private String mName;
    private Float mProteins;
    private Float mCarbohydrates;
    private Float mFats;
    private Boolean mSelected;

    public FoodProduct() {
        super();
        setName(null);
        setProteins(0.0f);
        setCarbohydrates(0.0f);
        setFats(0.0f);
    }

    public void setCategoryId(Integer categoryId) {
        this.mCategoryId = categoryId;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setProteins(Float proteins) {
        this.mProteins = proteins;
    }

    public void setCarbohydrates(Float carbohydrates) {
        this.mCarbohydrates = carbohydrates;
    }

    public void setFats(Float fats) {
        this.mFats = fats;
    }

    public void setSelected(Boolean selected) {
        this.mSelected = selected;
    }

    public Integer getCategoryId() {
        return mCategoryId;
    }

    public String getName() {
        return mName;
    }

    public Float getProteins() {
        return mProteins;
    }

    public Float getCarbohydrates() {
        return mCarbohydrates;
    }

    public Float getFats() {
        return mFats;
    }

    public Boolean isSelected() {
        return mSelected;
    }

}