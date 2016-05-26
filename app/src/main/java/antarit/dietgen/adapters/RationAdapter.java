package antarit.dietgen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
//import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import antarit.dietgen.R;
import antarit.dietgen.datarecords.FoodItem;
import antarit.dietgen.datarecords.Meal;
import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;

public class RationAdapter extends BaseExpandableListAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<DietType> mRations;

    public RationAdapter(Context context, ArrayList<DatabaseRecord> rations) {
        setContext(context);
        setLayoutInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setRations(new ArrayList<DietType>());

        for (DatabaseRecord record : rations) {
            DietType dietType = (DietType) record;
            getRations().add(dietType);
        }
    }

    class ExpandableListHolder {
        TextView textView;
        int position;
    }

    @Override
    public int getGroupCount() {
        return getRations().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getRations().get(groupPosition).getRecords().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getRations().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getRations().get(groupPosition).getRecords().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        ExpandableListHolder holder;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.row_product, parent, false);
            holder = new ExpandableListHolder();
            holder.textView = (TextView) view.findViewById(R.id.row_product_tv_name);
            holder.position = groupPosition;
            view.setTag(holder);
        } else {
            holder =(ExpandableListHolder) view.getTag();
        }

        String text = "Ration #" + String.valueOf(groupPosition + 1);
        holder.textView.setText(text);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        View view = convertView;
        ExpandableListHolder holder;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.row_product, parent, false);
            holder = new ExpandableListHolder();
            holder.textView = (TextView) view.findViewById(R.id.row_product_tv_name);
            view.setTag(holder);
        } else {
            holder =(ExpandableListHolder) view.getTag();
        }

        Meal meal = (Meal) getRations().get(groupPosition).getRecords().get(childPosition);
        String text = "Meal #" + String.valueOf(childPosition + 1) + " " + '\n' + getFoodProducts(meal) + '\n' + "Итого " + meal.getCalories().toString() + "ккал";
        holder.textView.setText(text);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String getFoodProducts(Meal meal) {
        String productsString = "";

        for (DatabaseRecord record : meal.getRecords()) {
            FoodItem foodItem = (FoodItem) record;
            productsString += foodItem.getProduct().getName() + " " + foodItem.getWeight().toString() + "г" + '\n';
        }

        return productsString;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public void setRations(ArrayList<DietType> rations) {
        this.mRations = rations;
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public ArrayList<DietType> getRations() {
        return mRations;
    }
}