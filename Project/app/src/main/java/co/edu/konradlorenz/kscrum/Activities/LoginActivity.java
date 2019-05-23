package co.edu.konradlorenz.kscrum.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import co.edu.konradlorenz.kscrum.Entities.LoginRequest;
import co.edu.konradlorenz.kscrum.Entities.Project;
import co.edu.konradlorenz.kscrum.Entities.Usuario;
import co.edu.konradlorenz.kscrum.Fragments.PasswordRecoveryFragment;
import co.edu.konradlorenz.kscrum.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar loginProgressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private Button googleSignButton;
    private View.OnClickListener buttonGoogle;
    private Button githubSignInButton;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button loginBtn;
    private CollectionReference cr;
    private ListenerRegistration registration;
    private Context context;
    private boolean login;
    private  ConnectivityManager cm;


    public final String myCallback = "kscrum://callback";

    private FirebaseAuth mAuth;

    private static final String TAG = "LoginActivity";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final int RC_SIGN_IN = 101;
    private FirebaseFirestore db;
    private String flag_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login =false;
        findMaterialElements();
        context=this;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
         cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);


        addGithubListener();
        addSimpleLoginListener();


    }

    public void addSimpleLoginListener(){
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View FocusView = null;
                    String mail =  usernameInput.getText().toString();
                    String pwd =   passwordInput.getText().toString();

                    if(mail  != null && !mail.isEmpty()){
                        if(!validateMail(mail)) {
                            usernameInput.setError("Please enter an valid email");
                            usernameInput.requestFocus();
                        }else if(!validateDomain(mail)){
                            usernameInput.setError("Please enter an valid email");
                            usernameInput.requestFocus();
                        }else{

                            if (pwd != null && !pwd.isEmpty()){
                                LogInWithEmailAndPassword(mail , pwd);
                            }else {
                                passwordInput.setError("Please enter a password");
                                passwordInput.requestFocus();
                            }

                        }
                    }else {
                        usernameInput.setError("Please enter an email");
                        loginProgressBar.requestFocus();
                    }





                }
            });
    }

    private boolean validateDomain(String mail) {

        //return false if mail is not valid
        String domain=""; boolean a =false;
        for (int i =0; i<mail.length();i++){
            if((mail.charAt(i)+"").equals(".")){
                a=false;
            }
            if(a){
                domain += mail.charAt(i)+"";
            }

            if((mail.charAt(i)+"").equals("@")){
                a=true;
            }
            if((mail.charAt(i)+"").equals(".")){
                a=false;
            }

        }
        if(domain.equals("gmail")){
            return true;
        }
        return false;
    }

    public void LogInWithEmailAndPassword(String mail , String pwd){
        loginProgressBar.setVisibility(View.VISIBLE);
        this.mAuth.signInWithEmailAndPassword(mail , pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        doLogin();
                    }else {
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
                        if(!isConnected){
                            Toast.makeText(context, "No Internet connection", Toast.LENGTH_LONG).show();
                            loginProgressBar.setVisibility(View.GONE);
                        }else {
                            loginProgressBar.setVisibility(View.GONE);
                            logInConectionFailed();
                        }
                    }
            }
        });
    }

    private void doLogin(){
        FirebaseUser user = mAuth.getCurrentUser();

        Usuario newUser = new Usuario(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), user.getUid());

         db.collection("Users").document(user.getUid()).set(newUser);
        mAuth.getCurrentUser().reload();
        if(mAuth.getCurrentUser().isEmailVerified()){
            Intent i = new Intent(LoginActivity.this, ProjectsContainerActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            loginProgressBar.setVisibility(View.GONE);
            startActivity(i);
            cleanFields();
        }else{
            Toast.makeText(context, "Please verify your email",  Toast.LENGTH_LONG).show();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent newIntent = new Intent(context, LoginActivity.class);
            newIntent.putExtra("LogOut", "logout");
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            cleanFields();
        }





    }

    public void cleanFields(){
        this.usernameInput.setText("");
        this.passwordInput.setText("");
    }

    public static boolean validateMail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }


    public void addGithubListener() {

        githubSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGithubLogin();
            }
        });

    }


    public void startGithubLogin() {
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            Toast.makeText(context, "No Internet connection", Toast.LENGTH_LONG).show();
            loginProgressBar.setVisibility(View.GONE);
        }else {
            String request = generateGitHubRequest();
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra("reqUrl", request);
            startActivity(intent);
        }
    }


    public String generateGitHubRequest() {
        String result = "";

        result += "https://github.com/login/oauth/authorize" + "?client_id=" + getString(R.string.gitHub_ClientId) + "&scope=user" + "&allow_signup=true" + "&redirect_uri=" + myCallback;

        return result;
    }

    public void findMaterialElements() {
        FirebaseApp.initializeApp(this);
        loginProgressBar = findViewById(R.id.loading_spinner);
        googleSignButton = findViewById(R.id.google_login_button);
        githubSignInButton = findViewById(R.id.github_btn);
        googleSignButton.setOnClickListener(this);
        usernameInput =  findViewById(R.id.username_text_login);
        passwordInput = findViewById(R.id.password_text_input);
        loginBtn = findViewById(R.id.email_sign_in_button);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        try {
            flag_logout = getIntent().getExtras().getString("LogOut");
        } catch (Exception e) {
            Log.e("LOGOUT", "No se puede cerrar sesión");
        }
    }


    public void signUpHandler(View view) {
        Intent newIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(newIntent);
    }

    public void openDialogFragment(View view) {
        Fragment fragment = new PasswordRecoveryFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.login_layout, fragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (flag_logout != null) {
            signOut();
        }

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                googleSignIn();
                break;
        }
    }

    private void googleSignIn() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        credentialFirebaseSingIn(credential);
    }

    private void credentialFirebaseSingIn(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            doLogin();
                        } else {
                            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                            boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();
                            if(!isConnected){
                                Toast.makeText(context, "No Internet connection", Toast.LENGTH_LONG).show();

                            }else{
                                logInConectionFailed();
                            }
                        }
                    }
                });
    }

    private void logInConectionFailed() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onResume() {
        super.onResume();



    }

}
