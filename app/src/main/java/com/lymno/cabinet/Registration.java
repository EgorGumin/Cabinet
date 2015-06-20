package com.lymno.cabinet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends ActionBarActivity implements View.OnClickListener{
    EditText emailEdit;
    EditText passwordEdit;
    EditText confirmPass;
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText middleNameEdit;
    Button registerBtn;
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        emailEdit      = (EditText) findViewById(R.id.reg_email);
        passwordEdit   = (EditText) findViewById(R.id.reg_password);
        confirmPass    = (EditText) findViewById(R.id.confirm_password);
        firstNameEdit  = (EditText) findViewById(R.id.firstName);
        lastNameEdit   = (EditText) findViewById(R.id.lastName);
        middleNameEdit = (EditText) findViewById(R.id.middleName);

        registerBtn = (Button) findViewById(R.id.reg_btn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Кнопка регистрации. Нужно запретить использование некоторых знаков в логине и пароле.
            //Например, точку с запятой.
            case R.id.reg_btn:
                email      = emailEdit.getText().toString();
                password   = passwordEdit.getText().toString();
                firstName  = firstNameEdit.getText().toString();
                lastName   = lastNameEdit.getText().toString();
                middleName = middleNameEdit.getText().toString();
                if (email.equals("") || password.equals("") || firstName.equals("") || lastName.equals("")) {
                    Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
                    break;
                }
                if (!password.equals(confirmPass.getText().toString())){
                    Toast.makeText(this, "Пароли не совпадают.", Toast.LENGTH_LONG).show();
                    break;
                }

                String allQuery = "api/users/registration?FirstName="+firstName+"&SecondName="+lastName+"&MiddleName="+middleName+"&Email="+email+"&Password="+password;
                new Register().execute(allQuery);
                break;
        }
    }

    public class Register extends BasicRequest{
        protected void onPostExecute(String res) {
            String method = "";
            String result = "";
            try {
                JSONObject dataJsonObj = new JSONObject(res);
                result = dataJsonObj.getString("Result");
                method = dataJsonObj.getString("Function");
                if ("registration Fail".equals(method +" "+ result)) {
                    Toast.makeText(getBaseContext(), "Такой логин уже занят или произошла ошибка.", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences cache = getSharedPreferences("cache", MODE_PRIVATE);
                    SharedPreferences.Editor ed = cache.edit();
                    ed.putString("IDToken", result);
                    ed.putString("firstName", firstName);
                    ed.putString("lastName", lastName);
                    ed.putString("middleName", firstName);
                    ed.putString("email", email);
                    ed.apply();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
