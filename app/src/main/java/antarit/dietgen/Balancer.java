package antarit.dietgen;

import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;
import antarit.dietgen.datarecords.FoodItem;

public class Balancer {
    public static final Integer THIRTY_FIVE = 50;
    public static final Integer TWENTY = 20;
    public static final Integer TEN = 10;
    public static final Integer HUNDRED_GRAMS = 100;
    public static final Integer FIFTY_GRAMS = 50;
    public static final Integer TWENTY_GRAMS = TWENTY;
    public static final Integer TEN_GRAMS = TEN;
    public static final Integer FIVE_GRAMS = 5;
    public static final Integer FIFTY_CALORIES = 50;

    enum State {
        HighIncreasingGrams,
        MiddleIncreasing,
        LowIncreasing,
        Balancing
    }

    enum Act {
        Addition,
        Deduction
    }

    private State mState;
    private Integer mMinimumCalories;
    private Integer mMaximumCalories;
    private Integer mLoopCount;

    static Integer MEALS_COUNT = 0;

    //rightCalories or necessaryCalories... don't know how to name this variable.
    public Balancer (Integer rightCalories){
        setMinimumCalories(rightCalories - FIFTY_CALORIES);
        setMaximumCalories(rightCalories + FIFTY_CALORIES);
        mLoopCount = 0;
        MEALS_COUNT++;
    }

    public void setState(State state) {
        this.mState = state;
    }

    public void setMinimumCalories(Integer minimumCalories) {
        this.mMinimumCalories = minimumCalories;
    }

    public void setMaximumCalories(Integer maximumCalories) {
        this.mMaximumCalories = maximumCalories;
    }

    public State getState() {
        return mState;
    }

    public Integer getMinimumCalories() {
        return mMinimumCalories;
    }

    public Integer getMaximumCalories() {
        return mMaximumCalories;
    }

    public Boolean isEnough(Integer calories) {
        return ((calories >= getMinimumCalories()) && (calories <= getMaximumCalories())) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Integer getDifference(Integer bigger, Integer smaller){
        return bigger - smaller;
    }

    public void balance(DietType dietType) {
        do {
            setState();
            for(DatabaseRecord record: dietType.getRecords()) {
                FoodItem item = (FoodItem) record;
                balanceCalories(getAct(dietType.getCalories()), getBalanceGrams(dietType.getCalories()), item);
                dietType.updateNutritionData();
                if(isEnough(dietType.getCalories())){
                    checkFoodItems(dietType.getRecords());
                    break;
                }
            }
            mLoopCount++;
            /*
            Add function.. hmmm something like removeSomeGramsFromFoodItem and
            add some test of mealCalories to be between (calories+50) and (calories - 50)
            */
        }
        while (!isEnough(dietType.getCalories()));
    }

    public Act getAct(Integer currentCalories) {
        if (currentCalories > getMinimumCalories())
            return Act.Deduction;
        if (currentCalories < getMaximumCalories())
            return Act.Addition;
        else
            return null;
    }

    public void setState() {
        if (mLoopCount < TEN)
            setState(State.HighIncreasingGrams);
        else if (mLoopCount < TWENTY)
            setState(State.MiddleIncreasing);
        else if (mLoopCount < THIRTY_FIVE)
            setState(State.LowIncreasing);
        else
            setState(State.Balancing);
    }

    public Integer getBalanceGrams(Integer currentCalories) {
        Integer difference = getDifference(currentCalories);

        if (difference > 100)
            return getBalancedGramsByState(HUNDRED_GRAMS, FIFTY_GRAMS, TWENTY_GRAMS, TEN_GRAMS);
        else if(difference < 100 && difference > 0)
            return getBalancedGramsByState(FIFTY_GRAMS, TWENTY_GRAMS, TEN_GRAMS, FIVE_GRAMS);
        else
            return 0;
    }

    public Integer getDifference(Integer currentCalories){
        if (currentCalories > getMaximumCalories())
            return getDifference(currentCalories, getMaximumCalories());
        else if (currentCalories < getMinimumCalories())
            return getDifference(getMinimumCalories(), currentCalories);
        else
            return 0;
    }

    public Integer getBalancedGramsByState(Integer caseHigh, Integer caseMiddle, Integer caseLow, Integer caseBalanced) {
        switch (getState()) {
            case HighIncreasingGrams:
                return caseHigh;
            case MiddleIncreasing:
                return caseMiddle;
            case LowIncreasing:
                return caseLow;
            case Balancing:
                return caseBalanced;
            default:
                return 0;
        }
    }

    public void balanceCalories(Act act, Integer balanceCalories, FoodItem item) {
        item.setWeight(item.getWeight() + action(act) * balanceCalories);
    }

    public Integer action(Act act) {
        switch (act) {
            case Addition:
                return 1;
            case Deduction:
                return -1;
            default:
                return 0;
        }
    }

    public void checkFoodItems(ArrayList<DatabaseRecord> foodItems) {
        ArrayList<DatabaseRecord> checkedFoodItems = (ArrayList<DatabaseRecord>) foodItems.clone();

        for (DatabaseRecord record : checkedFoodItems) {
            FoodItem foodItem = (FoodItem) record;
            if (foodItem.getWeight() == 0) {
                foodItems.remove(record);
            }
        }
    }
}