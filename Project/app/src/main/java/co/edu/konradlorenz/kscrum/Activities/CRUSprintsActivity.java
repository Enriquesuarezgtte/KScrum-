package co.edu.konradlorenz.kscrum.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.Entities.Sprint;
import co.edu.konradlorenz.kscrum.R;

public class CRUSprintsActivity extends AppCompatActivity {

    private final String TAG = "CRUSprintsActivity";

    private View view;

    public final static int SEND_IMAGE_REQUEST = 150;

    private String lastImageUrl;

    private String chargedImage;

    private Uri selectedFile;

    private TextView title;

    private TextInputEditText sprintStartDate;

    private TextInputEditText sprintEndDate;

    private TextInputEditText sprintExtraInfo;

    private ImageView sprintImage;

    private String sprintTitle;

    private Button selectDocumentButton;

    private Button createSprintButton;

    private String projectUUID;

    private String sprintUUID;

    private String currentNumberSprint;


    private Boolean isCreate = false;

    private Boolean isUpdate = false;


    private FirebaseAuth auth;

    private CollectionReference dbCollection;

    private FirebaseStorage storage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crusprints);
        getLayoutComponents();
        validateActivityIncoming();
        initializeFirebaseComponents();
        createListeners();

    }


    private void createListeners() {
        selectDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });


        createSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToExecute();
            }
        });


        sprintStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("startDate");
            }
        });

        sprintEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("endDate");
            }
        });
    }

    private void showDatePickerDialog(final String typeDate){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month) + " / " + year;

                if(typeDate.equals("startDate")){
                    sprintStartDate.setText(selectedDate);
                }else{
                    sprintEndDate.setText(selectedDate);
                }

            }
        });
        newFragment.show(getSupportFragmentManager(), typeDate.equals("startDate")? "Select an start date" : "Select and End date");

    }


    private void tryToExecute() {

        String sprntStartDate = sprintStartDate.getText().toString();
        String sprntEndDate = sprintEndDate.getText().toString();
        String imageUrl = chargedImage != null && !chargedImage.isEmpty() ? chargedImage : lastImageUrl;
        String extras = sprintExtraInfo.getText().toString();

        View focus = null;
        if (sprntStartDate == null || sprntStartDate.isEmpty()) {
            sprintStartDate.setError("please put an start date for the sprint");
            focus = sprintStartDate;
        }

        if (sprntEndDate == null || sprntEndDate.isEmpty()) {
            sprintEndDate.setError("please put an end date for the sprint");

            focus = sprintEndDate;

        }
        if (extras == null || extras.isEmpty()) {
            sprintExtraInfo.setError("please add a litle description");

            focus = sprintExtraInfo;

        }


        if (focus != null) {
            focus.requestFocus();
        } else {

            Sprint sprint = buildObj(sprntStartDate, sprntEndDate, extras, imageUrl);

            saveData(sprint, isUpdate);

        }

    }


    private void saveData(Sprint sprint, boolean isUpdate) {


        if (isUpdate) {
            dbCollection.document(sprintUUID).set(sprint).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), ProjectsContainerActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "updated Succesfully", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            dbCollection.document().set(sprint).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), ProjectsContainerActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "created Succesfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public Sprint buildObj(String startDate, String endDate, String extras, String urlImage) {
        Sprint sprint = new Sprint();

        sprint.setProjectId(projectUUID);
        sprint.setId(sprintUUID);
        sprint.setTitle(generateIncrementalName());
        sprint.setCreationDate(formatDate(startDate));
        sprint.setLastDate(formatDate(endDate));
        sprint.setExtraInfo(extras);
        sprint.setPercentage("0%");

        return sprint;
    }

    public Date formatDate(String date){
            String val =date.replaceAll(" " , "");
        try {
            Date rdate = new SimpleDateFormat("dd/MM/yyyy").parse(val);
            return rdate;
        } catch (ParseException e) {
            Log.e(TAG, "formatDate: exception", e );
        }

        return null;
    }


    public String generateIncrementalName(){
        if(currentNumberSprint != null && !currentNumberSprint.isEmpty()){
            int newInt = Integer.parseInt(currentNumberSprint) +1;
            String returnTitle = "Sprint " + String.valueOf(newInt);
            return returnTitle;
        }else{
            return "";
        }
    }


    private void choseImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpg");
        startActivityForResult(Intent.createChooser(intent, "Add a project Photo"), SEND_IMAGE_REQUEST);
    }


    public void getLayoutComponents() {

        title = findViewById(R.id.cruPtitle);
        sprintStartDate = findViewById(R.id.sprint_start_date);
        sprintEndDate = findViewById(R.id.sprint_end_date);
        sprintExtraInfo = findViewById(R.id.sprint_extra_info);
        selectDocumentButton = findViewById(R.id.select_sprint_image_button);
        createSprintButton = findViewById(R.id.createSprintBtn);
        sprintImage = findViewById(R.id.sprint_selected_view);
    }

    private void validateActivityIncoming() {
        String projUUID = getIntent().getStringExtra("projectUUID");
        String sprntUUID = getIntent().getStringExtra("sprintUUID");
        String currSize =  getIntent().getStringExtra("currentSize" );


        if (sprntUUID != null && !sprntUUID.isEmpty()) {
            isUpdate = true;
            sprintUUID = sprntUUID;
            currentNumberSprint = currSize;
            projectUUID=projUUID ;
        } else {
            isCreate = true;
            sprintUUID = "";
            currentNumberSprint = currSize;
            projectUUID=projUUID;
        }


    }


    private void initializeFirebaseComponents() {
        auth = FirebaseAuth.getInstance();
        dbCollection = FirebaseFirestore.getInstance().collection("sprints");
        storage = FirebaseStorage.getInstance();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_IMAGE_REQUEST && resultCode == RESULT_OK) {
            selectedFile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                sprintImage.setImageBitmap(bitmap);

                //Todo firestore load here
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (isUpdate) {
            updateUI();
        }
    }


    public void updateUI() {
        dbCollection.document(sprintUUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    Sprint sprint = new Sprint();

                    if (doc != null) {
                        sprint.setPercentage(doc.getString("percentage"));
                        sprint.setExtraInfo(doc.getString("extraInfo"));
                        sprint.setTitle(doc.getString("title"));
                        sprint.setImagen(doc.getString("imagen"));
                        sprint.setId(doc.getString("id"));
                        sprint.setProjectId(doc.getString("projectId"));
                        sprint.setCreationDate(doc.getDate("creationDate"));
                        sprint.setLastDate(doc.getDate("lastDate"));

                        loadParams(sprint);


                    } else {
                        Log.d(TAG, "No such document");
                    }


                } else {
                    Log.e(TAG, "" + task.getException());
                }
            }
        });
    }

    public void loadParams(Sprint sprint) {
        //Save previous url image (if not update)
        lastImageUrl = sprint.getImagen();
        //Setting parameters for update
        Glide.with(getApplicationContext()).load(sprint.getImagen()).into(sprintImage);

        sprintStartDate.setText(sprint.getCreationDate().toString());

        sprintEndDate.setText(sprint.getLastDate().toString());

        sprintExtraInfo.setText(sprint.getExtraInfo());

        createSprintButton.setText("update Sprint");

        title.setText("Update your sprint");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getContext(), listener, year, month, day);
        }

    }
}
