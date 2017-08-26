package com.example.dikadhitama.themoviedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private AlertDialog.Builder alert;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);
    }

    protected void showAlertMessage(String title, String message) {
        alert.setTitle(title)
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    protected void showDialog(String msg) {
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    protected boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork == null){
            return false;
        }
        return (activeNetwork == null ? false : activeNetwork.isConnectedOrConnecting());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.cancel();
        }
    }
}
