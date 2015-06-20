package com.lymno.cabinet;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashChooser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences cache = getSharedPreferences("cache", MODE_PRIVATE);
        String storedToken = cache.getString("IDToken", "");


        Context context = SplashChooser.this;
        if (storedToken.equals("")) {
            Intent singIntent = new Intent(context, SignInOrRegister.class);
            context.startActivity(singIntent);
            this.finishActivity(0);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finishActivity(0);
        }

    }
}