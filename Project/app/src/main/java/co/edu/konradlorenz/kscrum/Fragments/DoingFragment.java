package co.edu.konradlorenz.kscrum.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import co.edu.konradlorenz.kscrum.Activities.TaskDetailActivity;
import co.edu.konradlorenz.kscrum.Entities.Pbi;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;


public class DoingFragment extends Fragment {
    private RecyclerView pbiRecycler;
    private PBIAdapter sprintAdapter;
    private View view;
    private MaterialCardView materialCardView;
    private Sprint sprint;

    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private ArrayList<Pbi> sprints;
    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doing, container, false);

        findMaterialElements();
        cardHandler();

        return view;
    }

    public void cardHandler() {
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), TaskDetailActivity.class);
                startActivity(newIntent);
            }
        });
    }

    public void findMaterialElements() {
        materialCardView = view.findViewById(R.id.doingCard);
        sprint = (Sprint) getArguments().getSerializable("sprint");

    }
}
