package antarit.dietgen;

import android.content.Context;

import antarit.dietgen.datarecords.CompatibilityRecord;
import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.Diet;
import antarit.dietgen.datarecords.FoodProductCategory;
import antarit.dietgen.datarecords.FoodItem;
import antarit.dietgen.datarecords.FoodProduct;
import antarit.dietgen.datarecords.Meal;
import antarit.dietgen.datarecords.Ration;
import antarit.dietgen.datarecords.User;
import antarit.dietgen.helpers.FoodProductCategoryHelper;
import antarit.dietgen.helpers.FoodProductCompatibilityHelper;
import antarit.dietgen.helpers.FoodProductHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Generator {
    public static final Integer BMR_COEFFICIENT_MAN = 5;
    public static final Integer BMR_COEFFICIENT_WOMAN = -161;
    enum Nutrient {Protein, Carbohydrate, Fat}
    Nutrient[] MainNutrient = {Nutrient.Carbohydrate, Nutrient.Protein, Nutrient.Fat};

    private Integer BMR; //basal metabolic rate
    private Integer TDEE; //total daily energy expenditure
    private Random random;
    private User user;
    private Context context;

    public Generator(Context context, User user) {
        this.context = context;
        this.user = user;
        initializeRandom();
        calculateBMR(this.user);
        calculateTDEE(this.user);
    }

    public Diet getDiet() throws EmptyRecordException {
        try {
            Diet diet = this.user.getDiet();
            ArrayList<DatabaseRecord> rations = new ArrayList<>();

            for (Integer week = 7, day = 0; day != week; day++) {
                rations.add(getGeneratedRation(diet.getPurpose()));
            }

            diet.setRecords(rations);

            return diet;
        } catch (EmptyRecordException e) {
            throw new EmptyRecordException(e.getMessage());
        }
    }

    public void initializeRandom() {
        Date currentDate = new Date();
        long seed = currentDate.getTime();
        random = new Random(seed);
    }

    public Integer generatePosition(Integer range) {
        return random.nextInt(range);
    }

    public Boolean generateBoolean(){
        return generatePosition(2) != 1;
    }

    public void calculateTDEE(User user) {
        antarit.dietgen.datarecords.User.PhysicalActivityLevel activityLevel = user.getPhysicalActivityLevel();
        Double activityCoefficient = getPhysicalActivityCoefficient(activityLevel);

        TDEE = (int) (BMR * activityCoefficient);
    }

    public void calculateBMR(User user) {
        Integer age = user.getAge();
        Integer height = user.getHeight();
        Integer weight = user.getWeight();
        User.Gender gender = user.getGender();

        calculateBMR(age, height, weight, gender);
    }

    private void calculateBMR(Integer age, Integer height, Integer weight, User.Gender gender) {
        final Double heightCoefficient = 6.25;
        final Integer ageCoefficient = 5;
        final Integer weightCoefficient = 10;

        Integer genderCoefficient = getGenderCoefficient(gender);

        BMR = (int) ((weightCoefficient * weight) + (heightCoefficient * height) - (ageCoefficient * age) + genderCoefficient);
    }

    public Integer getGenderCoefficient(User.Gender gender) {
        if (gender == User.Gender.Male)
            return BMR_COEFFICIENT_MAN;
        else
            return BMR_COEFFICIENT_WOMAN;
    }

    public Double getPhysicalActivityCoefficient(User.PhysicalActivityLevel physicalActivityLevel) {
        switch (physicalActivityLevel) {
            case Sedentary:
                return 1.2;
            case Light:
                return 1.325;
            case Moderate:
                return 1.55;
            case Active:
                return 1.7;
            case Extreme:
                return 1.9;
            default:
                return 1.0;
        }
    }

    public Integer getBMR() {
        return BMR;
    }

    public Integer getTDEE() {
        return TDEE;
    }

    public Ration getGeneratedRation(Diet.Purpose purpose) throws EmptyRecordException {
        try {
            Ration ration = new Ration();

            Integer totalCalories = getDailyCalories(purpose);
            ration.setNeededCalories(totalCalories);

            Integer[] mealsCalories = getMealsCalories(user.getMealsCount(), totalCalories);
            ArrayList<DatabaseRecord> rationRecords = new ArrayList<>();

            for (Integer iterator = 0, position = 0; iterator != user.getMealsCount(); iterator++, position++) {
                int nutrientPosition;

                if (iterator > 2)
                    nutrientPosition = iterator - 3;
                else
                    nutrientPosition = iterator;

                rationRecords.add(GenerateMeal(MainNutrient[nutrientPosition], mealsCalories[position]));
            }

            ration.setRecords(rationRecords);

            return ration;
        } catch (EmptyRecordException e) {
            throw new EmptyRecordException(e.getMessage());
        }
    }

    public Integer getDailyCalories(Diet.Purpose purpose) {
        return (int)(TDEE * getCaloriesCoefficient(purpose));
    }

    public Float getCaloriesCoefficient(Diet.Purpose purpose) {
        switch (purpose) {
            case GainMuscle: return 1.2f;
            case BalancedNutrition: return 1.0f;
            case FatLoss: return 0.8f;
            default: return 0.0f;
        }
    }

    //Total calories are calories of ration. Ration composed from meals, that's why total calories
    //submitted as 100%. We need to generate meal and we should know how much calories it should
    //keep. So we will add 5% of total calories to each meal while don't used 100% of total calories.
    //And we should take into consideration that fact that we shouldn't eat to much before we go
    //to sleep, and we should take more calories in the morning to refill energy after night. So
    //we take 5% of total calories from last meal and add them to the first one.
    public Integer[] getMealsCalories(Integer mealsCount, Integer totalCalories) {
        Integer[] mealsCalories = new Integer[mealsCount];

        Arrays.fill(mealsCalories, 0);

        Integer calories = totalCalories;
        calories /= 20;

        for (Integer iterator = 20, position = 0; iterator != 0; iterator--, position++) {
            if (position == mealsCount)
                position=0;
            mealsCalories[position]+=calories;
        }

        mealsCalories[0] += calories;
        mealsCalories[mealsCount - 1] -= calories;

        return mealsCalories;
    }

    public Meal GenerateMeal(Nutrient mainNutrient, Integer calories) throws EmptyRecordException {
        try {
            switch (mainNutrient) {
                case Carbohydrate:
                    return getGeneratedMeal(1, calories);
                case Protein:
                    return getGeneratedMeal(2, calories);
                case Fat:
                    return getGeneratedMeal(3, calories);
                default:
                    return new Meal();
            }
        } catch (EmptyRecordException e) {
            throw new EmptyRecordException(e.getMessage());
        }
    }

    public Meal getGeneratedMeal(Integer mainNutrientType, Integer calories) throws EmptyRecordException {
        try {
            Balancer balancer = new Balancer(calories);
            Boolean isEnough;

            do {
                ArrayList<DatabaseRecord> foodItems = new ArrayList<>();
                FoodItem foodItem = getGeneratedFoodItem(mainNutrientType, null, null);

                if (isMixed(mainNutrientType))
                    foodItems.add(getGeneratedFoodItem(mainNutrientType, foodItem, null));
                if (isLowCaloriesFood(foodItem.getProduct().getCategoryId()))
                    foodItems.add(getGeneratedFoodItem(mainNutrientType, null, foodItem));

                foodItems.add(foodItem);

                Meal meal = new Meal();
                meal.setRecords(foodItems);

                balancer.balance(meal);

                isEnough = balancer.isEnough(meal.getCalories());

                if (isEnough) {
                    return meal;
                }
            }
            while (!isEnough);
            return null;
        } catch (EmptyRecordException e) {
            String mainNutrientName;

            switch (mainNutrientType) {
                case 1: mainNutrientName = "carbohydrates";
                    break;
                case 2: mainNutrientName = "proteins";
                    break;
                case 3: mainNutrientName = "fats";
                    break;
                default: mainNutrientName = "";
            }

            String message;
            if (mainNutrientName != "")
                message = "Go to products and select some products with " + mainNutrientName;
            else
                message = "Something went wrong with diet generation";

            throw new EmptyRecordException(message);
        }
    }

    public Boolean isMixed(Integer mainNutrient) {
        if (mainNutrient == 2)
            return Boolean.TRUE;
        else
            return generateBoolean();
    }

    public Boolean isLowCaloriesFood(Integer category_id) {
        if (category_id == 7 || category_id == 8 || category_id == 9)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public FoodItem getGeneratedFoodItem(Integer mainNutrientType, FoodItem compatibleItem, FoodItem lowCaloriesItem) throws EmptyRecordException {
        FoodItem item = new FoodItem();
        FoodProductCategory category;
        FoodProductCategoryHelper categoryHelper = new FoodProductCategoryHelper(context);

        if (compatibleItem != null) {
            FoodProductCompatibilityHelper compatibilityHelper = new FoodProductCompatibilityHelper(context);
            CompatibilityRecord compatibilityRecord = (CompatibilityRecord) compatibilityHelper.getRandomRecord(compatibleItem.getProduct().getCategoryId());
            category = (FoodProductCategory) categoryHelper.getRecordById(compatibilityRecord.getCategoryId());
        } else if (lowCaloriesItem != null)
            category = (FoodProductCategory) categoryHelper.getRecordById(lowCaloriesItem.getProduct().getCategoryId());
        else
            category = (FoodProductCategory) categoryHelper.getRandomRecord(mainNutrientType);

        FoodProductHelper productHelper = new FoodProductHelper(context);
        FoodProduct product = (FoodProduct) productHelper.getRandomRecord(category.getId());

        if (product == null)
            throw new EmptyRecordException();

        item.setProduct(product);

        return item;
    }

    public class EmptyRecordException extends Exception {

        public EmptyRecordException() {}

        public EmptyRecordException(String message) {
            super(message);
        }
    }
}