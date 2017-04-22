package com.example.andres.maps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
    }


        public void enviarCoordenadas(View v){
        String n1 = num1.getText().toString();
        String n2 = num2.getText().toString();


            //Creating firebase object
            Firebase ref = new Firebase(Config.FIREBASE_URL);
            //Getting values to store


            //Creating Coordenadas object
            final Coordenadas coordenadas = new Coordenadas();

            coordenadas.setLatitud(Float.parseFloat(n1));
            coordenadas.setLongitud(Float.parseFloat(n2));

            ref.child("Coordenadas").setValue(coordenadas);

            ref.addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        //Getting the data from snapshot
                        Coordenadas coordenadas = postSnapshot.getValue(Coordenadas.class);

                        Intent in = new Intent(MainActivity.this, MapsActivity.class);
                        in.putExtra("latitud",coordenadas.getLatitud());
                        in.putExtra("longitud",coordenadas.getLongitud());
                        startActivity(in);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

    }


}
