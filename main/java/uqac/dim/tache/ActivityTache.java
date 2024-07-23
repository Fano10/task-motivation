package uqac.dim.tache;

//import static uqac.dim.final_info.R.id.spinnerCategorie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import uqac.dim.tache.listeTache.TacheAdapter;
import uqac.dim.tache.room.AppDatabase;
import uqac.dim.tache.room.Tache;

public class ActivityTache extends Activity {
    //Definition des cle unique pour le bundle
    public final static String EXTRA_ID = "id";
    public final static String EXTRA_NOM = "nom";
    public final static String EXTRA_DESCRIPTION = "description";
    public final static String EXTRA_POINT = "point";
    public final static String EXTRA_DUREE = "duree";
    public final static String EXTRA_HORAIRE = "horaire";
    public final static String EXTRA_DATE = "date";
    public final static String EXTRA_TYPE = "type";
    public final static String EXTRA_CATEGORIE = "categorie";

    //Definition des attributs normals
    private ArrayList<Tache> mesTaches;
    private TacheAdapter adapter;
    // private TacheAdapter adapter;
    private Ringtone ringtone;
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache);
        //Commentaire: instancier la tache adaptater et le bd
        db = AppDatabase.getInstance(this);
        mesTaches = new ArrayList<>();
        //recuperer toutes les taches
        mesTaches = (ArrayList<Tache>) db.tacheDao().getAllTaches();
        adapter = new TacheAdapter(this, mesTaches);


        //Afficher toutes les taches dans la liste
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.Liste);
        listView.setAdapter(adapter);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);



        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) com.google.android.material.floatingactionbutton.FloatingActionButton fab = findViewById(R.id.ajouter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterNouvelleTache();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tache tache = mesTaches.get(position);
                ModificationTache(tache);
                playSound();
                Log.d("ActivityTache", "onCreate");

            }
        });
    }
    private void playSound() {
        if (ringtone != null) {
            ringtone.play(); // Joue le son de notification
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void ajouterNouvelleTache() {
        Intent intent = new Intent(this, CrudTache.class);
        startActivity(intent);
    }
    public void ModificationTache(final Tache tache) {
        Intent intent = new Intent(this, CrudTache.class);
        intent.putExtra(EXTRA_ID,tache.id);
        intent.putExtra(EXTRA_NOM,tache.nom);
        intent.putExtra(EXTRA_DESCRIPTION,tache.description);
        intent.putExtra(EXTRA_TYPE,tache.type);
        intent.putExtra(EXTRA_POINT,tache.nombreDePoint);
        intent.putExtra(EXTRA_DUREE,tache.duree);
        intent.putExtra(EXTRA_CATEGORIE,tache.categorie);
        intent.putExtra(EXTRA_HORAIRE,tache.heure);
        intent.putExtra(EXTRA_DATE,tache.date);
        startActivity(intent);
    }

    @Override
    protected void onRestart() { // reconnection de notre base de donnee et mis a jour de l'interface
        mesTaches = (ArrayList<Tache>) db.tacheDao().getAllTaches();
        adapter = new TacheAdapter(this, mesTaches);
        //Afficher toutes les taches dans la liste
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = findViewById(R.id.Liste);
        listView.setAdapter(adapter);
        super.onRestart();
    }

}
