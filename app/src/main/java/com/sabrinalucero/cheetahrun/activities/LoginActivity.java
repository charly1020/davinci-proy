package com.sabrinalucero.cheetahrun.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.sabrinalucero.cheetahrun.Utils.Util;
import com.sabrinalucero.cheetahrun.R;

/**
 * Created by Sabrina on 28/06/2017.
 */

public class LoginActivity  extends AppCompatActivity{
  private SharedPreferences prefs;

  private EditText editTextEmail;
  private EditText editTextPassword;
  private Switch switchRemember;
  private Button btnLogin;

  SharedPreferences.Editor editor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    bindUi();

    //read instance
    prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

    setCredentialsIfExist();

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        //si es true, vuelve al menu principal
        if( login(email,password)){
          goToMain();
          saveOnPreferences(email,password);
        }
      }
    });
  }

  //enlaza los elementos de la vista
  private void bindUi(){

    editTextEmail = (EditText) findViewById(R.id.editTextEmail);
    editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    switchRemember = (Switch) findViewById(R.id.switchRemember);
    btnLogin = (Button)  findViewById(R.id.buttonLogin);

  }

  //si existen los datos de las credenciales
  private void setCredentialsIfExist(){

    //recuperamos los valores guardados
    String email = Util.getUserMailPrefs(prefs);
    String password = Util.getUserPasswordPrefs(prefs);

    //si han sido guardados y no estan vacios se setean en SharedPreferences
    if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
      editTextEmail.setText(email);
      editTextPassword.setText(password);
      switchRemember.setChecked(true);
    }

  }

  //validaciones del login
  private boolean login(String email, String password){
    if(!isValidEmail(email)){
      Toast.makeText(this,"Email not valid, please try again. e.g juanito@correo.com",Toast.LENGTH_LONG).show();
      return false;
    }else if(!isValidPassword(password)) {
      Toast.makeText(this,"Password not valid, 4 characters or more, please try again.",Toast.LENGTH_LONG).show();
      return false;
    }else {
      return true;
    }
  }

  //guarda de info de preferencias
  private void saveOnPreferences(String email, String password){
    if (switchRemember.isChecked()){
      //write instance
      editor = prefs.edit();
      editor.putString("email", email);
      editor.putString("pass", password);
      // editor.commit();
      editor.apply();
    }

  }


  private boolean isValidEmail(String email){
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  private boolean isValidPassword(String password){
    return password.length() >=  4 ;
  }

  private void goToMain(){
    Intent intent = new Intent(this, MainActivity.class);
    //evitamos que vuelva al activity anterior
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }




}
