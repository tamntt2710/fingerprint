package com.example.use_fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private final Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

        Toast.makeText(context, "Fingerprint Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Fingerprint Authentication failed.", Toast.LENGTH_LONG).show();
        this.update("Fingerprint Authentication failed.", false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Fingerprint Authentication succeeded.", Toast.LENGTH_LONG).show();
        this.update("Fingerprint Authentication succeeded.", true);
    }

    public void update(String e, Boolean success){
        TextView textView = ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
    }
}
