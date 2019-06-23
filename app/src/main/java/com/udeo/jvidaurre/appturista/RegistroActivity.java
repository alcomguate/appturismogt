package com.udeo.jvidaurre.appturista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udeo.jvidaurre.appturista.R;
import com.udeo.jvidaurre.appturista.activity.DatePickerFragment;
import com.udeo.jvidaurre.appturista.dto.Usuario;

import java.util.Calendar;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etBirthDate, etFirstName, etMiddleName, etLastName, etEmail, etPassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        etBirthDate = (EditText) this.findViewById(R.id.birthDate);
        etBirthDate.setOnClickListener(this);

        etFirstName = (EditText) this.findViewById(R.id.firstName);
        etMiddleName = (EditText) this.findViewById(R.id.middleName);
        etLastName = (EditText) this.findViewById(R.id.lastName);
        etEmail = (EditText) this.findViewById(R.id.userName);
        etPassword = (EditText) this.findViewById(R.id.new_password);
        inittialiceDatabase();

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
                etBirthDate.setText(selectedDate.toString());
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birthDate:
                showDatePickerDialog();
                break;
        }
    }

    private Usuario createNewUser(){
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUid(UUID.randomUUID().toString());
        nuevoUsuario.setEmail(etEmail.getText().toString());
        nuevoUsuario.setFirstName(etFirstName.getText().toString());
        nuevoUsuario.setMiddleName(etMiddleName.getText().toString());
        nuevoUsuario.setLastName(etLastName.getText().toString());
        nuevoUsuario.setBirthDate(etBirthDate.getText().toString());
        nuevoUsuario.setPassword(etPassword.getText().toString());
        return nuevoUsuario;
    }

    private void cleanField() {
        etBirthDate.setText("");
        etFirstName.setText("");
        etMiddleName.setText("");
        etLastName.setText("");
        etPassword.setText("");
        etEmail.setText("");
    }

    private boolean validateField(){
        String firstName = etFirstName.getText().toString();
        String midleName = etMiddleName.getText().toString();
        String lastName = etLastName.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        int errors = 0;
        if (firstName.equals("")) {
            etFirstName.setError("Campo requerido");
            errors++;
        }
        if (midleName.equals("")) {
            etMiddleName.setError("Campo requerido");
            errors++;
        }
        if (lastName.equals("")) {
            etLastName.setError("Campo requerido");
            errors++;
        }

        if (birthDate.equals("")) {
            etBirthDate.setError("Campo requerido");
            errors++;
        }

        if (password.equals("")){
            etPassword.setError("Campo requerido");
            errors++;
        }

        if (email.equals("")){
            etEmail.setError("Campo requerido");
            errors++;
        }
        return (errors == 0);
    }

    public void crearUsuario(View view) {

        if (validateField()){
            // Agregar los datos
            if (validateField()){
                // Agregar los datos
                Usuario nuevoUsuario = createNewUser();
                databaseReference.child("User").child(nuevoUsuario.getUid()).setValue(nuevoUsuario);
                cleanField();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
