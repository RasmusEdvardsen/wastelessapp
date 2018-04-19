package com.example.edvardsen.wastelessclient.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.Codes;
import com.example.edvardsen.wastelessclient.data.UserModel;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.HandlerService;
import com.example.edvardsen.wastelessclient.services.JSONService;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.CAMERA;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    Button Ok;
    TextView barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        // barcode = (TextView) findViewById(R.id.barcode);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }



    // Button button = findViewById(R.id.button);
    //button.setOnClickListener(new View.OnClickListener(){
    //    @Override
    //   public void onClick(View v){

    // }
    //});


    private void requestPermission() {
        ActivityCompat.requestPermissions( this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public static void setVisibility(View view, Integer integer){
        view.setVisibility(integer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }
    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(BarcodeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final String barcodeFormat = result.getBarcodeFormat().toString();  /*barcode.getText().toString(); */
        final String barcode = result.getText();
        final String url = Constants.baseURL + Constants.usersPath;
        final String json = "{\"barcode\": " + "\"" + barcodeFormat + "\","
                + "\"}";
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public Codes codes;
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AsyncTask.execute(new Runnable() {

                    @Override
                    public void run() {
                        Response response = null;
                        try{
                            OkHttpClient client = new OkHttpClient();
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();
                            response = client.newCall(request).execute();
                            if(response == null){
                                HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                            }else{
                                if(response.code() == 200){
                                    Codes.getBarcode(barcodeFormat);
                                    codes.getListofCodes(barcodeFormat,null);
                                    //UserModel.getBarcode(barcode);

                                    HandlerService.makeToast(getBaseContext(), "Success!", Toast.LENGTH_SHORT, 500);
                                    startActivity(new Intent(getBaseContext(), InventoryActivity.class));
                                }else{
                                    HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                                    Log.i("Barcode", String.valueOf(response.code()));
                                }
                            }


                        }catch (Exception e){
                            Log.e("information", e.toString());
                            HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                        }
                    }
                });
                scannerView.stopCameraPreview();
                //scannerView.resumeCameraPreview(BarcodeActivity.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
                startActivity(browserIntent);
            }
        });
        builder.setPositiveButton("Scan More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            // Create URL
                            URL loginURL = new URL(Constants.baseURL + Constants.scrapePath + "/" + barcode);

                            // Create connection
                            HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                            Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                            if(httpURLConnection.getResponseCode() == 200){


                                //TODO: RENAME JSONService to ReaderService, PUT THIS IN TO THAT SERVICE.
                                //TODO: JSONArray ???
                                String test = "";
                                try{
                                    //Read JSON
                                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                                    StringBuilder responseStrBuilder = new StringBuilder();
                                    String inputStr;
                                    while ((inputStr = streamReader.readLine()) != null)
                                        responseStrBuilder.append(inputStr);
                                    test = responseStrBuilder.toString();
                                    Log.i("information", test);
                                }catch (Exception e){
                                    //TODO: Log Exception
                                    Log.i("information", "failed: " + e.toString());
                                }



                            }
                        }catch (Exception e){
                            Log.e("information", e.toString());
                            HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                        }
                    }
                });
                scannerView.resumeCameraPreview(BarcodeActivity.this);

            }
        });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}
