package ru.iu3.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;

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

        byte[] key = randomBytes(16);
        byte[] data = "Text__Encryption".getBytes();
        byte[] EncryptData = encrypt(key, data);
        byte[] DecryptData = decrypt(key, EncryptData);
        String EncryptText = new String(EncryptData, StandardCharsets.UTF_8);
        String DecryptText = new String(DecryptData, StandardCharsets.UTF_8);
        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI() + '\n' + "EncryptText: " + EncryptText + '\n'
                   + "DecryptText: " + DecryptText);
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