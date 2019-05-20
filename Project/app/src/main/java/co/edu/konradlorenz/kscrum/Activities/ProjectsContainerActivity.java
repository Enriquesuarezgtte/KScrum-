package co.edu.konradlorenz.kscrum.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import co.edu.konradlorenz.kscrum.Fragments.ProjectDetailPlaceholderFragment;
import co.edu.konradlorenz.kscrum.Fragments.ProjectsFragment;
import co.edu.konradlorenz.kscrum.R;

public class ProjectsContainerActivity extends AppCompatActivity {

    private boolean twoPanes;
    private Bundle startMainBundle;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMainBundle = savedInstanceState;
        setContentView(R.layout.main_layout);

        setUpFragmentManager();
        findMaterialElements();
        fillLayout();
    }


    private void fillLayout() {

        if (startMainBundle == null) {
            fragmentManager.beginTransaction().add(R.id.projects_fragment, new ProjectsFragment()).commit();
        }

        Boolean aBoolean = getResources().getBoolean(R.bool.has_two_panes);

        if (getResources().getBoolean(R.bool.has_two_panes)) {
            fragmentManager.beginTransaction().add(R.id.tablet_detail_project_fragment, new ProjectDetailPlaceholderFragment()).commit();
        }

    }


    private void findMaterialElements() {
        FrameLayout tabletDetailFragment = findViewById(R.id.tablet_detail_project_fragment);
    }

    private void setUpFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }


}
