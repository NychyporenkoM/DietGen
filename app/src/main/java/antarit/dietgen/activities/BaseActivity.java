package antarit.dietgen.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import antarit.dietgen.R;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public enum SelectedItem {
        Diets,
        Users,
        Products,
        NotSelected
    }

    private static SelectedItem selectedItem;

    public static void setSelectedItem(SelectedItem item) {
        selectedItem = item;
    }

    public static void initializeSelectedItem() {
        if(selectedItem == null)
            selectedItem = SelectedItem.NotSelected;
    }

    private View mActivityContent;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout mContainerView;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSelectedItem();
        setContentView(R.layout.base_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.base_act_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDrawerLayout((DrawerLayout) findViewById(R.id.base_act_drawer));
        setDrawerToggle(new ActionBarDrawerToggle(this, getDrawerLayout(), R.string.drawer_open, R.string.drawer_close));
        getDrawerLayout().addDrawerListener(getDrawerToggle());

        setContainerView((FrameLayout) findViewById(R.id.base_act_view_container));

        setNavigationView((NavigationView) findViewById(R.id.base_act_navigation));
        customizeNavigationView();
        getNavigationView().setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_diets:
                navigate(item, SelectedItem.Diets, new DietsActivity());
                break;
            case R.id.nav_products:
                navigate(item, SelectedItem.Products, new FoodProductsActivity());
                break;
            case R.id.nav_users:
                navigate(item, SelectedItem.Users, new UsersActivity());
                break;
            default:
                getDrawerLayout().closeDrawers();
                break;
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return getDrawerToggle().onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void setActivityContent(int resId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mActivityContent = inflater.inflate(resId, getDrawerLayout(), false);
        getContainerView().addView(mActivityContent);
    }

    public void setDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }

    public void setDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
        this.mDrawerToggle = mDrawerToggle;
    }

    public void setContainerView(FrameLayout mContainerView) {
        this.mContainerView = mContainerView;
    }

    public void setNavigationView(NavigationView mNavigationView) {
        this.mNavigationView = mNavigationView;
    }

    public View getActivityContent() {
        return mActivityContent;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    public FrameLayout getContainerView() {
        return mContainerView;
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    public void navigate(MenuItem item, SelectedItem selectedItem, Activity activity) {
        setSelectedItem(selectedItem);
        item.setChecked(true);
        getDrawerLayout().closeDrawers();
        startActivity(new Intent(this, activity.getClass()));
    }

    public void processException (String exceptionMessage) {
        Snackbar snackbar = Snackbar.make(getActivityContent(), exceptionMessage, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snackbar.show();
    }

    public void customizeNavigationView() {
        switch (selectedItem) {
            case Diets:
                getNavigationView().getMenu().findItem(R.id.nav_diets).setChecked(true);
                break;
            case Users:
                getNavigationView().getMenu().findItem(R.id.nav_users).setChecked(true);
                break;
            case Products:
                getNavigationView().getMenu().findItem(R.id.nav_products).setChecked(true);
                break;
            default:
                break;
        }
    }

    public void setIndicatorBounds(ExpandableListView expandableListView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
            expandableListView.setIndicatorBounds(widthPixels - GetPixelFromDensityIndependentPixels(50), widthPixels - GetPixelFromDensityIndependentPixels(10));
        else
            expandableListView.setIndicatorBoundsRelative(widthPixels - GetPixelFromDensityIndependentPixels(50), widthPixels - GetPixelFromDensityIndependentPixels(10));
    }

    public int GetPixelFromDensityIndependentPixels(float densityIndependentPixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (densityIndependentPixels * scale + 0.5f);
    }
}