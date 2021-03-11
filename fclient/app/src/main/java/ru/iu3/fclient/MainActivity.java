package ru.iu3.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("mbedcrypto");
        initRng();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        byte[] key = randomBytes(16);
//        byte[] data = "Text__Encryption".getBytes();
//        byte[] EncryptData = encrypt(key, data);
//        byte[] DecryptData = decrypt(key, EncryptData);
//        String EncryptText = new String(EncryptData, StandardCharsets.UTF_8);
//        String DecryptText = new String(DecryptData, StandardCharsets.UTF_8);
//        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI() + '\n' + "EncryptText: " + EncryptText + '\n'
//                + "DecryptText: " + DecryptText);

        Button button = findViewById(R.id.btnClickMe);
        button.setOnClickListener((View v) -> { showToast(v);});
        Button button2 = findViewById(R.id.btnInternet);
        button2.setOnClickListener((View v) -> { showToastInternet(v);});

//        int res = initRng();
//        Log.i("fclient", "Init Rng = " + res);
//        byte[] v = randomBytes(10);

    }

    public static byte[] StringToHex(String s) {

        byte[] hex;
        try {
            hex = Hex.decodeHex(s.toCharArray());
        }
        catch (DecoderException ex) {
            hex = null;
        }
        return hex;
    }

    public void showToast(View v) {
//        A.
//        Log.i("fapptag",  "Clicked");
//        Toast toast = Toast.makeText(MainActivity.this, stringFromJNI(), Toast.LENGTH_SHORT);
//        toast.show();
//        B.
//
//        byte[] key = StringToHex("1234567812345678");
//        byte[] enc = encrypt(key, StringToHex("Text__Encryption"));
//        byte[] dec = decrypt(key, enc);
//        String s = new String(Hex.encodeHex(dec)).toUpperCase();
//        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//        C.
        Intent it = new Intent(MainActivity.this, PinpadActivity.class);
        startActivityForResult(it, 0);

    }

    public void showToastInternet(View view) {
        TestHttpClient();
    }

    protected void TestHttpClient()
    {
        new Thread(() -> {
            try {
                HttpURLConnection uc = (HttpURLConnection) (new URL("https://www.wikipedia.org").openConnection());
                InputStream inputStream = uc.getInputStream();
                String html = IOUtils.toString(inputStream);
                String title = getPageTitle(html);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                });
            } catch (Exception ex) {
                Log.e("fapptag", "Http client fails", ex);
            }
        }).start();
    }


    protected String getPageTitle(String html) {
        Pattern pattern = Pattern.compile( "<title>(.+?)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String p;
        if (matcher.find())
            p = matcher.group(1);
        else
            p = "Not found";
        return p;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK || data != null) {
                String pin = data.getStringExtra("pin");
                Toast.makeText(MainActivity.this, pin, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int initRng();
    public static native byte[] randomBytes(int no);
    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);

}