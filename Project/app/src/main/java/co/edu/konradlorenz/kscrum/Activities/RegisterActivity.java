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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.konradlorenz.kscrum.Entities.Usuario;
import co.edu.konradlorenz.kscrum.R;


public class RegisterActivity extends AppCompatActivity {

    private Button chooseImageButton;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri selectedImage;
    private ImageView imageRegisterInput;
    private TextInputEditText user_name;
    private TextInputEditText user_last_name;
    private TextInputEditText user_email;
    private TextInputEditText user_password;
    private Button signUpButton;
    private ProgressBar prog;
    private Context context;
    private  ConnectivityManager cm;



    private FirebaseAuth  auth;
    private FirebaseFirestore dbCollection;
    private FirebaseFirestore storage;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$" , Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findMaterialElements();
        initializeFirebaseELements();

        addListeners();
    }

    private  void initializeFirebaseELements(){
        auth = FirebaseAuth.getInstance();
        dbCollection = FirebaseFirestore.getInstance();
        storage =  FirebaseFirestore.getInstance();
    }

    private void findMaterialElements() {
        chooseImageButton = findViewById(R.id.select_image_button);
        imageRegisterInput = findViewById(R.id.image_selected_view);
        user_name =  findViewById(R.id.username_text_registry);
        user_last_name = findViewById(R.id.lastname_text_registry);
        user_email = findViewById(R.id.email_text_registry);
        user_password = findViewById(R.id.password_text_registry);
        signUpButton = findViewById(R.id.sign_up_button);
        prog = findViewById(R.id.user_registration_progressbar);
        context=this;
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectedImage = data.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imageRegisterInput.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }



    private void addListeners(){
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
                if(!isConnected){
                    Toast.makeText(context, "No Internet connection", Toast.LENGTH_LONG).show();
                }else {
                    tryToRegistry();
                }
            }
        });
    }


    private void tryToRegistry() {
        String username = user_name.getText().toString();
        String lastname = user_last_name.getText().toString();
        String  email = user_email.getText().toString();
        String password = user_password.getText().toString();
        String completeName = username + " " +  lastname;
        ImageView imagen = imageRegisterInput;


        boolean execute = true;

        View errView = null;

        if(username == null  || username.isEmpty()){
            user_name.setError("Please insert a name");
            execute = false;
            errView = user_name;
        }
        if(selectedImage==null){
            execute=false;
            user_name.setError("Please select a photo");
        errView =user_name;
        }

        if(lastname == null ||  lastname.isEmpty()){
            user_last_name.setError("Please insert a last name");
            execute = false;

            errView = user_last_name;

        }

        if(email == null || email.isEmpty()){
            user_email.setError("Please insert a mail");
            execute = false;

            errView = user_email;

        }else if (!validateMail(email)){

                user_email.setError("Please enter a valid mail");
                execute = false;

                errView = user_email;

        }else if(!validateDomain(email)){
            user_email.setError("Please enter a valid mail");
            execute = false;

            errView = user_email;
        }


        if(password == null || password.isEmpty()){
            user_password.setError("Need a password");
            execute = false;

            errView = user_password;

        }else if (!validatePass(password)){
            user_password.setError("Please enter a valid password , more than 6 values and an special character");
            execute = false;

            errView = user_password;

        }

        registryWithFirebase(errView , execute ,  completeName , email , password);








    }


    private void registryWithFirebase(View errorView , boolean haveToExecute, final String completeName , String email , String pwd) {
        if (!haveToExecute){
            errorView.requestFocus();
        }else {

            prog.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email , pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();

                        UserProfileChangeRequest update =  new UserProfileChangeRequest.Builder()
                                .setDisplayName(completeName)
                                .setPhotoUri(selectedImage)
                                .build();


                        user.updateProfile(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    LoginSuccessfull();
                                }
                            }
                        });
                    }

                }
            });
        }
    }


    private void LoginSuccessfull(){
        FirebaseUser user = auth.getCurrentUser();
        Usuario newUser = new Usuario(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), user.getUid());
        dbCollection.collection("Users").document(user.getUid()).set(newUser);

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context,"Email sent. Please verify your account", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        signUpHandler();
    }

    public static boolean validatePass(String passStr) {
        Matcher matcher = PASSWORD_PATTERN .matcher(passStr);
        return matcher.find();
    }

    public static boolean validateMail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private boolean validateDomain(String mail) {
            boolean c=false;
            String fullDomain="";
        //return false if mail is not valid
        String domain=""; boolean a =false;
        for (int i =0; i<mail.length();i++){
            if((mail.charAt(i)+"").equals(".")&&a){
                a=false;
            }
            if(a){
                domain += mail.charAt(i)+"";
            }else if(c){
                fullDomain += mail.charAt(i)+"";
            }

            if((mail.charAt(i)+"").equals("@")){
                a=true;
                c=true;
            }


        }
        if(domain.equals("gmail")||(domain+fullDomain).equals("konradlorenz.edu.co")){
           return true;
        }else if(domain.equals("163")||domain.equals("126")||domain.equals("qq")|(
                domain+fullDomain).equalsIgnoreCase("WWW.SINA.COM")
        ||fullDomain.contains("cn")){
        return true;
        }
        return false;
    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Send a photo"), PICK_IMAGE_REQUEST);
    }

    public void signUpHandler() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent newIntent = new Intent(context, LoginActivity.class);
        newIntent.putExtra("LogOut", "logout");
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        prog.setVisibility(View.GONE);
        startActivity(newIntent);
        cleanFields();

    }

    public void cleanFields(){
        this.user_name.setText("");
        this.user_last_name.setText("");
        this.user_email.setText("");
        this.user_password.setText("");
    }
}
