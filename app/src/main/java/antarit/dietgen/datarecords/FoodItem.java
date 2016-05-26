package antarit.dietgen.datarecords;

public class FoodItem extends DietType {

    private Integer mWeight;
    private FoodProduct mProduct;

    public FoodItem() {
        super();
        setWeight(0);
    }

    @Override
    public void updateNutritionData() {
        setDefaultNutritionData();

        if (getProduct() != null) {
            Float coefficient = (float) getWeight() / HUNDRED_GRAMS;
            setProteins(getProduct().getProteins() * coefficient);
            setCarbohydrates(getProduct().getCarbohydrates() * coefficient);
            setFats(getProduct().getFats() * coefficient);
        }
    }

    public void setWeight(Integer weight) {
        this.mWeight = weight;
        updateNutritionData();
    }

    public void setProduct(FoodProduct product) {
        this.mProduct = product;
    }

    public Integer getWeight() {
        return mWeight;
    }

    public FoodProduct getProduct() {
        return mProduct;
    }

}