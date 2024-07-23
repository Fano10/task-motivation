package uqac.dim.tache;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uqac.dim.tache.room.Categorie;
import uqac.dim.tache.room.Tache;
import uqac.dim.tache.room.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialisation de la base de données
        db = AppDatabase.getInstance(this);
        initialisationDb();
        TextView textViewQuitter = findViewById(R.id.Quitter);
        textViewQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pour fermer seulement l'activité courante
                finish();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout creerProfilLayout = findViewById(R.id.creation_profil);
        creerProfilLayout.setOnClickListener(v -> {
            // Créer un Intent pour démarrer CreerProfilActivity
            Intent intent = new Intent(MainActivity.this, CreerProfilActivity.class);
            startActivity(intent);
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout tacheAFaireLayout = findViewById(R.id.tache);
        tacheAFaireLayout.setOnClickListener(v -> {
            // Créer un Intent pour démarrer ActivityTache
            Intent intent = new Intent(MainActivity.this, ActivityTache.class);
            startActivity(intent);
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout pointCumule = findViewById(R.id.point_accumuler);
        pointCumule.setOnClickListener(v -> {
            // Créer un Intent pour démarrer ActivityTache
            Intent intent = new Intent(MainActivity.this, Profil.class);
            startActivity(intent);
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout shedule = findViewById(R.id.calendrier);
        shedule.setOnClickListener(v -> {
            // Créer un Intent pour démarrer ActivityTache
            Intent intent = new Intent(MainActivity.this, Schedule.class);
            startActivity(intent);
        });
    }

    protected void onResume() {
        super.onResume();

        // Récupérer le nom et l'image de l'utilisateur des préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String nom = sharedPreferences.getString("nom", "Mamoudou Camara");
        String image = sharedPreferences.getString("image", ""); // Si image est vide, utilisez une image par défaut

        // Mettre à jour le TextView et l'ImageView avec le nom et l'image de l'utilisateur
        TextView textViewNom = findViewById(R.id.nom_principal);
        ImageView imageView = findViewById(R.id.img);
        textViewNom.setText(nom);

        if (!image.equals("")) {
            // Convertir la chaîne "image" en Bitmap
            Bitmap imageBitmap = stringToBitmap(image);
            // Définir l'image de l'utilisateur comme source de l'ImageView
            imageView.setImageBitmap(imageBitmap);
        } else {
            // Définir l'image par défaut
            imageView.setImageResource(R.drawable.mc);
        }
    }


    // Méthode pour convertir une chaîne en Bitmap
    private Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    public void initialisationDb(){
        List<Categorie> categories = db.categorieDao().getAllCategories();
        if(categories.size()==0){
            Categorie professionnel = new Categorie("activite professionnel",0,"#FF0000");
            Categorie sante  = new Categorie("activite sante",0,"#00FF00");
            Categorie loisir  = new Categorie("loisir et divertissement",0,"#0000FF");
            Categorie developpement  = new Categorie("developpement personnelle",0,"#FF00FF");
            Categorie social  = new Categorie("activite social",0,"#FFFF00");
            Categorie domestique  = new Categorie("activite domestique",0,"#00FFFF");
            //inserer les categories
            db.categorieDao().addCategorie(professionnel);
            db.categorieDao().addCategorie(sante);
            db.categorieDao().addCategorie(loisir);
            db.categorieDao().addCategorie(developpement);
            db.categorieDao().addCategorie(social);
            db.categorieDao().addCategorie(domestique);

        }
    }
}