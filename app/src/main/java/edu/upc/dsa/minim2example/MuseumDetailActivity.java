package edu.upc.dsa.minim2example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.minim2example.models.Element;

public class MuseumDetailActivity extends AppCompatActivity {
    private Context context;

    private Element museum;
    private TextView direccion;
    private TextView descripcion;
    private TextView grupdirec;
    private TextView codpostal;
    private TextView municipio;
    private TextView email;
    private TextView telefono;
    private TextView hab;
    private TextView extension;
    private TextView altitud;


    private ImageView escudo;
    private ImageView bandera;

    // private Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Definimos el nombre del layput relacionado con este activity
        setContentView(R.layout.museum_detail);

        direccion = (TextView) findViewById(R.id.nomdireccion);
        descripcion = (TextView) findViewById(R.id.descripcion);
        grupdirec = (TextView) findViewById(R.id.direcciongrupo);
        codpostal = (TextView) findViewById(R.id.codigopostal);
        municipio = (TextView) findViewById(R.id.nommunicipio);
        email = (TextView) findViewById(R.id.email);
        telefono = (TextView) findViewById(R.id.telefono);
        hab = (TextView) findViewById(R.id.habitantes);
        extension = (TextView) findViewById(R.id.extension);
        altitud = (TextView) findViewById(R.id.altitud);

        escudo = (ImageView) findViewById(R.id.imageescudo);
        bandera = (ImageView) findViewById(R.id.imagebandera);

        getInfo();
    }
    //User Notifier Handler using Toast
    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(MuseumDetailActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If login activity closed means the user has logged in
        if (requestCode == 2) {
                if (data != null) {
                    museum = data.getParcelableExtra("Museum");
                    Log.w("s", "Test");
                } else {
                    NotifyUser("Could not retreive data");
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
        }
    }

    public void getInfo() {
        direccion.append(museum.getAdrecaNom());
        descripcion.append(museum.getDescripcio());
        grupdirec.append(museum.getGrupAdreca().getAdreca());
        codpostal.append(museum.getGrupAdreca().getCodiPostal());
        municipio.append(museum.getRelMunicipis().getMunicipiNom());
        email.append(museum.getEmail().get(0));
        telefono.append(museum.getTelefonContacte().get(0));
        hab.append(museum.getRelMunicipis().getNombreHabitants());
        extension.append(museum.getRelMunicipis().getExtensio());
        altitud.append(museum.getRelMunicipis().getAltitud());

        Picasso.get().load(museum.getRelMunicipis().getMunicipiEscut()).into(escudo);
        Picasso.get().load(museum.getRelMunicipis().getMunicipiBandera()).into(bandera);
    }
}
