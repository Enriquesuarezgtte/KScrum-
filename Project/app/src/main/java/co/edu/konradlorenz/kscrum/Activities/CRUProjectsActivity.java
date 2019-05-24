package co.edu.konradlorenz.kscrum.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;

import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.R;

public class CRUProjectsActivity extends AppCompatActivity {

    private final String TAG ="CRUProjectsActivity";

    private View view;

    public final static int SEND_IMAGE_REQUEST = 150;

    private String lastImageUrl;

    private String chargedImage;

    private Uri selectedFile;

    private TextView title;

    private TextInputEditText projectName;

    private TextInputEditText projectLanguaje;

    private TextInputEditText projectDescription;

    private ImageView projectImage;

    private Button selectDocumentButton;

    private Button createProjectButton;

    private String UUID;
    private ConnectivityManager cm;


    private Boolean isCreate = false;

    private Boolean isUpdate = false;


    private FirebaseAuth auth;

    private CollectionReference dbCollection;

    private FirebaseStorage storage;

    private Context contex;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cru_projects);
        contex=this;
        getLayoutComponents();
        UUID = validateActivityIncoming();

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


        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    Toast.makeText(contex, "No Internet connecticontexton. Try later", Toast.LENGTH_LONG).show();
                }else {
                    tryToExecute();
                }
            }
        });
    }


    private void tryToExecute() {

        String projectNameObj =  projectName.getText().toString();
        String projectDesc = projectDescription.getText().toString();
        String imageUrl = chargedImage != null  && !chargedImage.isEmpty()? chargedImage : lastImageUrl;
        String projectLang = projectLanguaje.getText().toString();

        View focus = null;
        if(projectNameObj == null || projectNameObj.isEmpty()){
            projectName.setError("please put a name for your project");
            focus = projectName;
        }

        if (projectDesc == null || projectDesc.isEmpty()){
            projectDescription.setError("please put a description for your project");

            focus = projectDescription;

        }
        if(projectLang == null || projectLang.isEmpty()){
            projectLanguaje.setError("please put a languaje for your project");

            focus = projectLanguaje;

        }


        if (focus != null){
            focus.requestFocus();
        }else {

            saveData(projectNameObj , projectDesc , projectLang , imageUrl , isUpdate);

        }

    }


    private void saveData(String projectName , String projectDesc , String projectLang , String imageUrl , boolean isUpdate){
        Project project = new Project();
        project.setProjectDisplayName(projectName);
        project.setProjectLanguaje(projectLang);
        project.setProjectDescription(projectDesc);
        project.setProjectPhotoURL(imageUrl);
        project.setId(UUID);


        if (isUpdate){
            dbCollection.document(UUID).set(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext() , ProjectsContainerActivity.class );
                        startActivity(intent);
                        Toast.makeText(getApplicationContext() ,"updated Succesfully" , Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }else {
            dbCollection.document().set(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext() , ProjectsContainerActivity.class );
                        startActivity(intent);
                        Toast.makeText(getApplicationContext() ,"created Succesfully" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }







    private void choseImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/jpg");
        startActivityForResult(Intent.createChooser(intent, "Add a project Photo"), SEND_IMAGE_REQUEST);
    }


    public void getLayoutComponents() {

        title = findViewById(R.id.cruPtitle);
        projectName = findViewById(R.id.projectName_text_input);
        projectLanguaje = findViewById(R.id.projectLanguaje_text_input);
        projectDescription = findViewById(R.id.project_desc);
        selectDocumentButton =findViewById(R.id.select_image_button);
        createProjectButton = findViewById(R.id.createProjectBtn);
        projectImage = findViewById(R.id.image_selected_view);
        cm = (ConnectivityManager)contex.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    private String validateActivityIncoming() {
        String id = getIntent().getStringExtra("UUID");

        if (id != null && !id.isEmpty()) {
            isUpdate = true;
            return id;
        } else {
            isCreate = true;
            id = "";
            return id;
        }


    }


    private void initializeFirebaseComponents() {
        auth = FirebaseAuth.getInstance();
        dbCollection = FirebaseFirestore.getInstance().collection("projects");
        storage = FirebaseStorage.getInstance();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_IMAGE_REQUEST && resultCode == RESULT_OK) {
            selectedFile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                projectImage.setImageBitmap(bitmap);

                //Todo firestore load here
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(isUpdate){
            updateUI();
        }
    }


    public void  updateUI(){
       dbCollection.document(UUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot doc =  task.getResult();
                   Project  project = new Project();

                   if (doc != null){
                       project.setProjectDescription(doc.getString("projectDescription"));
                       project.setProjectDisplayName(doc.getString("projectDisplayName"));
                       project.setProjectLanguaje(doc.getString("projectLanguaje"));
                       project.setProjectPhotoURL(doc.getString("projectPhotoURL"));
                       project.setId(UUID);
                       loadParams(project);


                   }else{
                       Log.d(TAG , "No such document");
                   }


               }else {
                   Log.e(TAG , "" + task.getException());
               }
           }
       });
    }

    public void loadParams(Project project){
        //Save previous url image (if not update)
        lastImageUrl =  project.getProjectPhotoURL();
        //Setting parameters for update
        Glide.with(getApplicationContext()).load(project.getProjectPhotoURL()).into(projectImage);

        projectName.setText(project.getProjectDisplayName());

        projectLanguaje.setText(project.getProjectLanguaje());

        projectDescription.setText(project.getProjectDescription());

        createProjectButton.setText("update Project");

        title.setText("Update your project");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
