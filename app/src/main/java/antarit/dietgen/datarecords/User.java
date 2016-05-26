package antarit.dietgen.datarecords;

public class User extends DatabaseRecord {

    public enum PhysicalActivityLevel {
        Sedentary,
        Light,
        Moderate,
        Active,
        Extreme,
        UnChosen
    }

    public enum Gender {
        Male,
        Female,
        UnChosen
    }

    private String mName;
    private Integer mAge;
    private Integer mHeight;
    private Integer mWeight;
    private Integer mMealsCount;
    private Gender mGender;
    private PhysicalActivityLevel mPhysicalActivityLevel;
    private Diet mDiet;

    public User() {
        super();
        setName(null);
        setAge(0);
        setHeight(0);
        setWeight(0);
        setMealsCount(0);
        setGender(Gender.UnChosen);
        setPhysicalActivityLevel(PhysicalActivityLevel.UnChosen);
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setAge(Integer age) {
        this.mAge = age;
    }

    public void setHeight(Integer height) {
        this.mHeight = height;
    }

    public void setWeight(Integer weight) {
        this.mWeight = weight;
    }

    public void setMealsCount(Integer mealsCount) {
        this.mMealsCount = mealsCount;
    }

    public void setGender(Gender gender) {
        this.mGender = gender;
    }

    public void setPhysicalActivityLevel(PhysicalActivityLevel PhysicalActivityLevel) {
        this.mPhysicalActivityLevel = PhysicalActivityLevel;
    }

    public void setDiet(Diet diet) {
        this.mDiet = diet;
        getDiet().setUserId(getId());
    }

    public String getName() {
        return mName;
    }

    public Integer getAge() {
        return mAge;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public Integer getWeight() {
        return mWeight;
    }

    public Integer getMealsCount() {
        return mMealsCount;
    }

    public Gender getGender() {
        return mGender;
    }

    public PhysicalActivityLevel getPhysicalActivityLevel() {
        return mPhysicalActivityLevel;
    }

    public Diet getDiet() {
        return mDiet;
    }
}