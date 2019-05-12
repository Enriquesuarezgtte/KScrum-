package co.edu.konradlorenz.kscrum.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.edu.konradlorenz.kscrum.R;

public class ProjectDetailPlaceholderFragment extends Fragment {

    private View actContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        actContext = inflater.inflate(R.layout.project_detail_placeholder, container, false);

        return actContext;
    }
}
