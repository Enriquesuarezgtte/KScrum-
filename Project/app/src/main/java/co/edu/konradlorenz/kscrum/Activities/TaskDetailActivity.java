package co.edu.konradlorenz.kscrum.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.edu.konradlorenz.kscrum.R;

public class TaskDetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        findMaterialElements();
        fabHandler();
    }

    public void fabHandler() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(TaskDetailActivity.this, PBIActivity.class);
                startActivity(newIntent);
                TaskDetailActivity.this.finish();
            }
        });
    }

    public void findMaterialElements() {
        floatingActionButton = findViewById(R.id.taskDetailFAB);
    }

}
