package antarit.dietgen.datarecords;

public class Ration extends DietType {

    private Integer mNeededCalories;

    public Ration() {
        super();
    }

    public void setNeededCalories (Integer neededCalories) {
        this.mNeededCalories = neededCalories;
    }

    public Integer getNeededCalories() {
        return mNeededCalories;
    }

}