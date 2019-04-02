package com.elementsculmyca.ec19_app.UI.SignUpPage;

import android.Manifest;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elementsculmyca.ec19_app.DataSources.DataModels.EventDataModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.ResponseModel;
import com.elementsculmyca.ec19_app.DataSources.DataModels.TicketModel;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.AppDatabase;
import com.elementsculmyca.ec19_app.DataSources.LocalServices.DatabaseInitializer;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiClient;
import com.elementsculmyca.ec19_app.DataSources.RemoteServices.ApiInterface;
import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.UI.HomePage.DayAdapter;
import com.elementsculmyca.ec19_app.UI.LoginScreen.FragmentOtpChecker;
import com.elementsculmyca.ec19_app.UI.LoginScreen.LoginActivity;
import com.elementsculmyca.ec19_app.UI.MainScreen.MainScreenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elementsculmyca.ec19_app.UI.LoginScreen.FragmentOtpChecker.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class SignUpActivity extends AppCompatActivity implements FragmentOtpChecker.otpCheckStatus  {
    TextView login,guest;
    private ApiInterface apiInterface;
    ImageView submit;
    EditText userName,userCollege,userPhone,userEmail;
    private ProgressDialog mProgress;
    private String musername;
    private String muserclg,mUserPhone,mUserEmail;
    SharedPreferences sharedPreferences;
    DatabaseInitializer databaseInitializer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface = ApiClient.getClient().create( ApiInterface.class );
        login=findViewById(R.id.tv_login);
        submit=findViewById(R.id.submit);
        guest=findViewById(R.id.tv_guest);
        userName=findViewById(R.id.name);
        userCollege=findViewById(R.id.college);
        userPhone=findViewById(R.id.phone_number);
        userEmail = findViewById(R.id.email);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Registering you");
        mProgress.setTitle("Please Wait");
        mProgress.setCanceledOnTouchOutside(false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainScreenActivity.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    checkOTP();
                    sharedPreferences=getSharedPreferences("login_details",0);
                    musername = userName.getText().toString();
                    muserclg= userCollege.getText().toString();
                    mUserPhone = userPhone.getText().toString();
                    mUserEmail = userEmail.getText().toString();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void checkOTP() {
        checkAndRequestPermissions();
        if(ContextCompat.checkSelfPermission(SignUpActivity.this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            Bundle bundle = new Bundle();
            bundle.putString("phone", userPhone.getText().toString());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
        mProgress.dismiss();
    }
    private void checkAndRequestPermissions() {
        int receiveSMS = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }
    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            Toast.makeText(SignUpActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName.getText().toString().equals("")) {
            userName.setError("Enter a User Name");
            return false;
        }

        if (userPhone.getText().toString().equals("")) {
            userPhone.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(userPhone.getText().toString()).matches()) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (userPhone.getText().toString().length() != 10) {
            userPhone.setError("Enter a valid Phone Number");
            return false;
        }
        if (userCollege.getText().toString().equals("")) {
            userCollege.setError("Enter a College Name");
            return false;
        }

        if(userEmail.getText().toString().equals("")){
            userEmail.setError("Enter an email address");
            return false;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()) {
//            userEmail.setError("Enter a Valid Email Address");
//            return false;
//        }

        if(!isEmailValid(userEmail.getText().toString())){
            userEmail.setError("Enter valid email");
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    @Override
    public void updateResult(boolean status) {
        if (status) {
            mProgress.show();
            registerUser();
        } else {
            mProgress.dismiss();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){

            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            Bundle bundle = new Bundle();
            bundle.putString("phone", userPhone.getText().toString());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
    }

    void registerUser() {
        Call<ResponseModel> call = apiInterface.registerUser(musername,mUserPhone,mUserEmail,muserclg);
        call.enqueue( new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //TODO YAHAN PE LIST AAEGI API SE UI ME LAGA LENA
                ResponseModel responseModel= response.body();
                if(responseModel.getStatus().equals("error")){
                    Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }else {
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("Username",musername);
                    editor.putString("UserClg",muserclg);
                    editor.putString("UserPhone",mUserPhone);
                    editor.putString("UserEmail",mUserEmail);
                    editor.commit();
                    mProgress.hide();
                    startActivity(new Intent(SignUpActivity.this,MainScreenActivity.class));
                    finishAffinity();
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                mProgress.dismiss();
                Log.e( "Response", call.request().url() + "" + call.request().body());
            }
        } );
    }
}
