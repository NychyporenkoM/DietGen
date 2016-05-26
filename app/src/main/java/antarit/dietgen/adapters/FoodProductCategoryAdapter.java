package antarit.dietgen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import antarit.dietgen.R;
import antarit.dietgen.datarecords.FoodProduct;
import antarit.dietgen.datarecords.FoodProductCategory;

public class FoodProductCategoryAdapter extends BaseExpandableListAdapter {

    LayoutInflater mLayoutInflater;
    ArrayList<FoodProductCategory> mFoodProductCategories;

    public FoodProductCategoryAdapter(Context context, ArrayList<FoodProductCategory> foodProductCategories) {
        setFoodProductCategories(foodProductCategories);
        setLayoutInflater ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    class ExpandableListHolder {
        CheckBox checkBox;
        TextView textView;
        int position;
    }

    @Override
    public int getGroupCount() {
        return getFoodProductCategories().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getFoodProductCategories().get(groupPosition).getFoodProducts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getFoodProductCategories().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getFoodProductCategories().get(groupPosition).getFoodProducts().get(childPosition);
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
            holder.checkBox = (CheckBox) view.findViewById(R.id.row_product_cb_selected);
            holder.textView = (TextView) view.findViewById(R.id.row_product_tv_name);
            holder.position = groupPosition;
            view.setTag(holder);
        }
        else {
            holder = (ExpandableListHolder) view.getTag();
        }

        final FoodProductCategory foodProductCategory = getFoodProductCategories().get(groupPosition);

        holder.textView.setText(foodProductCategory.getName());
        holder.checkBox.setChecked(foodProductCategory.isChecked());
        holder.checkBox.setTag(foodProductCategory);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodProductCategory foodProductCategoryFromTag = (FoodProductCategory) v.getTag();
                foodProductCategoryFromTag.setSelectedFoodProducts(!foodProductCategoryFromTag.isChecked());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        View view = convertView;
        ExpandableListHolder holder;

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.row_product, parent, false);

            holder = new ExpandableListHolder();
            holder.checkBox = (CheckBox) view.findViewById(R.id.row_product_cb_selected);
            holder.textView = (TextView) view.findViewById(R.id.row_product_tv_name);
            view.setTag(holder);
        }
        else {
            holder = (ExpandableListHolder) view.getTag();
        }

        FoodProduct foodProduct = getFoodProductCategories().get(groupPosition).getFoodProducts().get(childPosition);

        holder.textView.setText(foodProduct.getName());
        holder.checkBox.setChecked(foodProduct.isSelected());
        holder.checkBox.setTag(foodProduct);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodProduct foodProductFromTag = (FoodProduct) v.getTag();
                foodProductFromTag.setSelected(!foodProductFromTag.isSelected());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public void setFoodProductCategories(ArrayList<FoodProductCategory> foodProductCategories) {
        this.mFoodProductCategories = foodProductCategories;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public ArrayList<FoodProductCategory> getFoodProductCategories() {
        return mFoodProductCategories;
    }
}