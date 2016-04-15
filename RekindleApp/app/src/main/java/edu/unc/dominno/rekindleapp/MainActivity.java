package edu.unc.dominno.rekindleapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
// Add this to the header of your file:
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


//implements FacebookCallback

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    private TextView fbLoginInfo;
    private LoginButton loginButton; //FB Login button
    private CallbackManager callbackManager; //manages callbacks used in FB
    private AccessTokenTracker accessTokenTracker; //the current access token & profile
    private ProfileTracker profileTracker; //tracks current profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        setContentView(R.layout.activity_main);

        /* tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("Fourth Tab");

        tab1.setIndicator("Home");
        Intent i = new Intent(this, Ratings.class);
        tab1.setContent(i);
        startActivity(i);

        tab2.setIndicator("My Ratings");
        tab2.setContent(new Intent(this, ShowRatings.class));

        tab3.setIndicator("", getResources().getDrawable(R.drawable.micon));
        tab3.setContent(new Intent(this, Messages.class));

        tab4.setIndicator("FAQ");
        tab4.setContent(new Intent(this, FAQ.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4); */

        //FB Login Integration
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                //will be called when there's a change in the Current Access Token (will only help if user's already logged in )
                //updateWithToken(newAccessToken);

            }

        };
        fbLoginInfo = (TextView) findViewById(R.id.fbInfo);
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbLoginInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                fbLoginInfo.setText("Login attempt canceled");
            }

            @Override
            public void onError(FacebookException error) {

                fbLoginInfo.setText("Login attempt failed: " + error.getMessage());
            }
        });
        //Used to check if a user is currently logged in to FB
        //updateWithToken(AccessToken.getCurrentAccessToken());

    }

    /* private void updateWithToken(AccessToken currentAccessToken) {
        if (isLoggedIn()) {
            Intent i = new Intent()
        } else {

        }
    } */

    //checks if a user is currently logged in to FB
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
