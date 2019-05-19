package co.edu.konradlorenz.kscrum.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.edu.konradlorenz.kscrum.Activities.PBIActivity;
import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.R;


public class ProjectTabletDetailFragment extends Fragment {

    private TextView projectTitle;
    private ImageView projectImage;
    private TextView percentageCompleted;
    private Project projectSelected;
    private View actContext;
    private Bundle projectData;
    private FloatingActionButton fabProjectDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        actContext = inflater.inflate(R.layout.project_tablet_detail, container, false);

        findMaterialElements();
        getBundleData();
        setUpLayout();
        setUpFAB();

        return actContext;
    }

    private void setUpFAB() {
        fabProjectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SprintFragment sprintFragment= new SprintFragment();
                sprintFragment.setArguments(projectData);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.remove(manager.findFragmentById(R.id.projects_fragment));
                fragmentTransaction.replace(R.id.projects_fragment,sprintFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



                //Intent intent = new Intent(getContext(), PBIActivity.class);
                //startActivity(intent);
                /*
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.fragment1, fragment2);
    fragmentTransaction.commit();
                 */
            }
        });
    }

    private void getBundleData() {

        projectData = this.getArguments();
        projectSelected = (Project) projectData.getSerializable("PROJECT");
    }


    private void setUpLayout() {
        projectTitle.setText(projectSelected.getProjectDisplayName());
        Glide.with(this).load(projectSelected.getProjectPhotoURL()).into(projectImage);
        percentageCompleted.setText(projectSelected.getProjectDescription());
    }


    private void findMaterialElements() {
        projectTitle = actContext.findViewById(R.id.detail_project_title);
        projectImage = actContext.findViewById(R.id.detail_project_image);
        percentageCompleted = actContext.findViewById(R.id.detail_project_percentage_completed);
        fabProjectDetail = actContext.findViewById(R.id.fab_project_detail);
    }
}
