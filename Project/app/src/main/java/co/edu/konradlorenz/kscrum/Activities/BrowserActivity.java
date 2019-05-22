package co.edu.konradlorenz.kscrum.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Message;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import co.edu.konradlorenz.kscrum.Entities.AccessToken;
import co.edu.konradlorenz.kscrum.Entities.LoginRequest;
import co.edu.konradlorenz.kscrum.Entities.Usuario;
import co.edu.konradlorenz.kscrum.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BrowserActivity extends AppCompatActivity {
    private String currentRequest;
    private WebView webView;
    private FirebaseAuth  mAuth;
    private FirebaseFirestore db;
    private LinearLayout progressView;

    public final static String TAG="BrowserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_browser);
        initViewParams();
        initializeWebView();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public void initViewParams(){
        this.currentRequest = getIntent().getStringExtra("reqUrl");
        this.webView = findViewById(R.id.web_v);
        this.progressView=  findViewById(R.id.loadingView);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showPView(final boolean show){
        if(show){
            webView.setVisibility(View.GONE);
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation(slideUp);
        }else {
            progressView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }

    public void initializeWebView(){

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

            public final String TAG="myWebViweClient";
            public final String myCallback = "kscrum://callback";


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }




            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if ( request.getUrl().getQueryParameter("code") != null){
                    showPView(true);
                    String transitCode = request.getUrl().getQueryParameter("code");
                    generatePostGithubRequest(transitCode);
                }
                return false;
            }







            public void generatePostGithubRequest(String code) {

                OkHttpClient client = new OkHttpClient();
                LoginRequest request = new LoginRequest(getString(R.string.gitHub_ClientId), getString(R.string.gitHub_ClientSecret), code);

                Gson gson = new Gson();

                String req = gson.toJson(request);

                Request requestGH = new Request.Builder()
                        .url("https://github.com/login/oauth/access_token")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), req)).
                                build();

                client.newCall(requestGH).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: " + call );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e(TAG , "onResponse: " + response);
                        String responseBody = response.body().string();
                        String values[] =  responseBody.split("&");
                        AccessToken  myToken = new AccessToken();

                        myToken.setAccess_token(values[0].split("=")[1]);
                        myToken.setScope(values[1].split("=")[1]);
                        myToken.setToken_type(values[2].split("=")[1]);

                        authWithGithub(myToken);
                    }
                });




            }

        });
        webView.loadUrl(currentRequest);

    }

    public void authWithGithub(AccessToken mytoken){
        String token = mytoken.getAccess_token();
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        FirebaseUser user = mAuth.getCurrentUser();
                        Usuario newUser = new Usuario("", user.getEmail(), user.getPhotoUrl().toString(), user.getUid());
                        db.collection("Users").document(user.getUid()).set(newUser);
                        Intent i = new Intent(BrowserActivity.this, ProjectsContainerActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPView(false);
    }


}
