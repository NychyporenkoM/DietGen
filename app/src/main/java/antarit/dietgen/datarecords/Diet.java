package antarit.dietgen.datarecords;

public class Diet extends DietType {

    public enum Purpose {
        GainMuscle,
        BalancedNutrition,
        FatLoss
    }

    private Integer mUserId;
    private Purpose mPurpose;

    public Diet() {
        super();
    }

    public void setUserId (Integer userId) {
        this.mUserId = userId;
    }

    public void setPurpose (Purpose purpose) {
        this.mPurpose = purpose;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public Purpose getPurpose() {
        return mPurpose;
    }

}