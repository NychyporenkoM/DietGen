package antarit.dietgen.datarecords;

import java.util.ArrayList;

import antarit.dietgen.helpers.DietTypeHelper;

public abstract class DietType extends DatabaseRecord {

    public static final Integer HUNDRED_GRAMS = 100;
    public static final Float CALORIES_PER_GRAM = 4.5f;

    private Float mProteins;
    private Float mCarbohydrates;
    private Float mFats;
    private DietType mParent;
    private ArrayList<DatabaseRecord> mChildRecords;

    public DietType() {
        super();
        setRecords(new ArrayList<DatabaseRecord>());
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

    public void setParent(DietType parent) {
        this.mParent = parent;
    }

    public void setRecords(ArrayList<DatabaseRecord> records) {
        this.mChildRecords = records;

        updateNutritionData();

        for (DatabaseRecord record : getRecords()) {
            DietType dietType = (DietType) record;
            dietType.setParent(this);
        }
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

    public DietType getParent() {
        return mParent;
    }

    public ArrayList<DatabaseRecord> getRecords() {
        return mChildRecords;
    }

    public void updateNutritionData() {
        setDefaultNutritionData();

        for (DatabaseRecord record : getRecords()) {
            setUpdatedNutritionData((DietType) record);
        }
    }

    public void setUpdatedNutritionData(DietType dietType) {
        setProteins(getProteins() + dietType.getProteins());
        setCarbohydrates(getCarbohydrates() + dietType.getCarbohydrates());
        setFats(getFats() + dietType.getFats());
    }

    public void setDefaultNutritionData() {
        setProteins(0.0f);
        setCarbohydrates(0.0f);
        setFats(0.0f);
    }

    public Integer getCalories() {
        Float proteinCalories = getProteins() * CALORIES_PER_GRAM;
        Float carbohydrateCalories = getCarbohydrates() * CALORIES_PER_GRAM;
        Float fatCalories = getFats() * 2 * CALORIES_PER_GRAM;

        if (proteinCalories == 0.0f && carbohydrateCalories == 0.0f && fatCalories == 0.0f)
            return 0;
        return (int)(proteinCalories + carbohydrateCalories + fatCalories);
    }

    public void saveRecords(DietTypeHelper dietTypeHelper) {
        for (DatabaseRecord record : getRecords()) {
            dietTypeHelper.saveRecord(record);
        }
    }
}