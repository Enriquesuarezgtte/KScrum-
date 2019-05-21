package co.edu.konradlorenz.kscrum.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.Fragments.DoingFragment;
import co.edu.konradlorenz.kscrum.Fragments.DoneFragment;
import co.edu.konradlorenz.kscrum.Fragments.ToDoFragment;
import co.edu.konradlorenz.kscrum.R;

public class PBIActivity extends AppCompatActivity {


    private TabItem tab1;

    //------ TABS
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_notes,
            R.drawable.ic_projects,
            R.drawable.ic_sprints
    };
    private Sprint sprint;
    //------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbi);


        findMaterialElements();
        setSupportActionBar(toolbar);
        tabsSetUp();
        setupTabIcons();
    }

    public void findMaterialElements() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        sprint = (Sprint)getIntent().getSerializableExtra("sprint");
    }

    public void tabsSetUp() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ToDoFragment(), "To Do");
        adapter.addFrag(new DoingFragment(), "Doing");
        adapter.addFrag(new DoneFragment(), "Done");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("sprint", sprint);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }


}
