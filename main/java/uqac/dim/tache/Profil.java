package uqac.dim.tache;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import uqac.dim.tache.graphique.GraphiqueCirculaire;
import uqac.dim.tache.graphique.GraphiqueLegende;
import uqac.dim.tache.room.Categorie;
import uqac.dim.tache.room.Tache;
import uqac.dim.tache.room.AppDatabase;

public class Profil extends Activity {

    public Categorie professionnel;
    public Categorie sante;
    public Categorie loisir;
    public Categorie developpement;
    public Categorie social;
    public Categorie domestique;

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_layout);
        //prendre l'instance de la base de donnee
        db = AppDatabase.getInstance(this);
        //recuperer toutes les taches
        List<Categorie> categories = db.categorieDao().getAllCategories();


        Categorie[] tabCategorie = {professionnel,sante,loisir,developpement,social,domestique}; //6
        int check = creationCategorie(categories,tabCategorie);
        if(check>0) {
            FrameLayout frameLayout = findViewById(R.id.frameLayout);
            FrameLayout frameLayoutLegende = findViewById(R.id.layoutLegende);
            frameLayout.removeAllViews();
            frameLayoutLegende.removeAllViews();
            dessiner(tabCategorie);
            legende(tabCategorie);
        }
        else {
            TextView textView = new TextView(this);
            ImageView imageView = new ImageView(this);
            textView.setText("Finissez des taches et gagnez du points");
            imageView.setImageResource(R.drawable.tache);
            FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setTextSize(20);
            textParams.setMargins(0,50,0,0);
            textView.setLayoutParams(textParams);
            imageView.setLayoutParams(imageParams);
            FrameLayout frameLayout = findViewById(R.id.frameLayout);
            FrameLayout frameLayoutLegende = findViewById(R.id.layoutLegende);
            frameLayout.addView(imageView);
            frameLayoutLegende.addView(textView);

        }
    }

    private int creationCategorie(List<Categorie> categories, Categorie [] tabCategorie) {
        int pointTotal = 0;
        for(int i=0;i<6;i++){
            tabCategorie[i] = categories.get(i);
            pointTotal += tabCategorie[i].nombreDePoint;
        }
        return pointTotal;
    }

    public void dessiner(Categorie[] tabCategorie){
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        GraphiqueCirculaire graphiqueCirculaire = new GraphiqueCirculaire(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        graphiqueCirculaire.setLayoutParams(layoutParams);
        graphiqueCirculaire.DefinirCategorie(tabCategorie);
        frameLayout.addView(graphiqueCirculaire);
    }

    public void legende(Categorie[] tabCategorie){
        FrameLayout linearLayout = findViewById(R.id.layoutLegende);
        GraphiqueLegende graphiqueLegende = new GraphiqueLegende(this,tabCategorie);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        graphiqueLegende.setLayoutParams(layoutParams);
        linearLayout.addView(graphiqueLegende);
    }
}
