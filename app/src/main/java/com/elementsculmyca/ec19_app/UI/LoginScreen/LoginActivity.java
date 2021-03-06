package com.elementsculmyca.ec19_app.UI.LoginScreen;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.ResponseModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.UserModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.MainScreen.MainScreenActivity;
import com.elementsculmyca.ec19_app.UI.SignUpPage.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity implements FragmentOtpChecker.otpCheckStatus {
    TextView guestLogin,signUp;
    EditText phoneNumber;
    private ProgressDialog mProgress;
    private ApiInterface apiInterface;
    ImageView submit;
    String phone;
    SharedPreferences sharedPreferences;
    FragmentManager fm;
    FragmentOtpChecker otpChecker;
    DatabaseInitializer databaseInitializer;
    private int REQUEST_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );

        phoneNumber = findViewById(R.id.phone_number);
        submit = findViewById(R.id.submit);
        signUp=findViewById(R.id.sign_up);
        sharedPreferences=getSharedPreferences("login_details",0);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Registering You");
        mProgress.setTitle("Please Wait");
        mProgress.setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        String color = "#0f0f0f";
        window.setStatusBarColor(Color.parseColor(color));

        checkAndRequestPermissions( REQUEST_ID );
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    phone = phoneNumber.getText().toString();
                    checkOTP();
                        }
            }
        });
        guestLogin = findViewById(R.id.button_guest);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
            }
        });
    }

    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            phoneNumber.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(phoneNumber.getText().toString()).matches()) {
            phoneNumber.setError("Enter a valid Phone Number");
            return false;
        }
        if (phoneNumber.getText().toString().length() != 10) {
            phoneNumber.setError("Enter a valid Phone Number");
            return false;
        }

        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void checkOTP() {
        checkAndRequestPermissions( REQUEST_ID );
        FragmentManager fm = getFragmentManager();
        FragmentOtpChecker otpChecker = new FragmentOtpChecker();
        Bundle bundle = new Bundle();
        bundle.putString("phone", phoneNumber.getText().toString());
        otpChecker.setArguments(bundle);
        otpChecker.show(fm, "otpCheckerFragment");
        mProgress.dismiss();
    }

    private void checkAndRequestPermissions(int CALL_REQUEST_ID) {

        int storage = ContextCompat.checkSelfPermission( this,
                Manifest.permission.READ_EXTERNAL_STORAGE );
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    CALL_REQUEST_ID );

        }
    }
    @Override
    public void updateResult(boolean status) {
        if (status) {
            mProgress.show();
            registerUser();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    void registerUser() {
        Call<ResponseModel> call = apiInterface.getLoginStatus(phone);
        call.enqueue( new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //TODO YAHAN PE LIST AAEGI API SE UI ME LAGA LENA
                try {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getStatus().equals("User Not Exist")) {
                        Toast.makeText(LoginActivity.this, "You need to sign up first", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    } else {
                        UserModel user = response.body().getUser();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username", user.getName());
                        editor.putString("UserClg", user.getCollege());
                        editor.putString("UserPhone", user.getPhone());
                        editor.putString("UserEmail", user.getEmail());
                        editor.commit();
                        getAllTickets();
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e( "Response", call.request().url() + "" + call.request().body() );

            }

        } );
    }

    void getAllTickets(){
        Call<ArrayList<TicketModel>> call = apiInterface.getTickets(sharedPreferences.getString("UserPhone",""));
        call.enqueue( new Callback<ArrayList<TicketModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TicketModel>> call, Response<ArrayList<TicketModel>> response) {
                //TODO YAHAN PE LIST AAEGI API SE UI ME LAGA LENA
                ArrayList<TicketModel> ticketList= response.body();
                databaseInitializer.populateTicketSync(AppDatabase.getAppDatabase(LoginActivity.this),ticketList);
                mProgress.hide();
                startActivity(new Intent(LoginActivity.this,MainScreenActivity.class));
                finishAffinity();
            }

            @Override
            public void onFailure(Call<ArrayList<TicketModel>> call, Throwable t) {
            }

        } );

    }
}
