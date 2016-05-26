package antarit.dietgen.datarecords;

import java.util.ArrayList;

public class FoodProductCategory extends DatabaseRecord {

    private String mName;
    private Boolean mChecked;
    public ArrayList<FoodProduct> mFoodProducts;

    public FoodProductCategory() {
        super();
    }

    public void setName (String name) {
        this.mName = name;
    }

    public void setChecked (Boolean checked) {
        this.mChecked = checked;
    }

    public void setFoodProducts (ArrayList<FoodProduct> foodProducts) {
        this.mFoodProducts = foodProducts;
    }

    public String getName() {
        return mName;
    }

    public Boolean isChecked() {
        return mChecked;
    }

    public ArrayList<FoodProduct> getFoodProducts() {
        return mFoodProducts;
    }

    public void setSelectedFoodProducts (Boolean selected) {
        setChecked(selected);
        for (FoodProduct foodProduct : getFoodProducts()){
            foodProduct.setSelected(isChecked());
        }
    }

}