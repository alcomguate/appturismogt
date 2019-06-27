package com.udeo.jvidaurre.appturista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udeo.jvidaurre.appturista.R;
import com.udeo.jvidaurre.appturista.activity.DatePickerFragment;
import com.udeo.jvidaurre.appturista.dto.Usuario;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    // Variables para guardar imagenes
    private final String ROOT_DIR = "AppTuristaGT/";
    private final String IMAGE_DIR = ROOT_DIR + "Galery";
    private String path = "";
    ImageView imagenVista;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        inittialiceDatabase();

        imagenVista = (AppCompatImageView) findViewById(R.id.img_preview);

    }

    private void inittialiceDatabase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                System.out.println(selectedDate.toString());
                // etBirthDate.setText(selectedDate.toString());
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            // rESULT DE LA CAMARA
            MediaScannerConnection.scanFile(this, new String[] {path}, null, new MediaScannerConnection.OnScanCompletedListener(){

                @Override
                public void onScanCompleted(String s, Uri uri) {
                    Log.i("Ruta de almacenamiento", "Path: " + path);
                }
            });

            Bitmap bitMap = BitmapFactory.decodeFile(path);

        }
    }

    private void cleanField() {
    }

    public void openCamera(View view) {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), IMAGE_DIR);
        boolean isCreated = fileImagen.exists();
        String imageName = "";
        if (!isCreated) {
            isCreated = fileImagen.mkdirs();
        } else {
            imageName = (System.currentTimeMillis()/1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() + File.separator +
                IMAGE_DIR + File.separator + imageName;

        File imageFile = new File(path);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile((imageFile)));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {

    }



    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                MediaScannerConnection.scanFile(this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String s, Uri uri) {

                    }

                });

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                imagenVista.setImageBitmap(bitmap);
                break;
        }
    }
}
