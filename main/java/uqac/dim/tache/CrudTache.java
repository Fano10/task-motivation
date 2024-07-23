package uqac.dim.tache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import uqac.dim.tache.calendrier.Calendrier;
import uqac.dim.tache.calendrier.JourSemaine;
import uqac.dim.tache.room.Tache;
import uqac.dim.tache.room.AppDatabase;

public class CrudTache extends Activity {
    public int jourCalendrier = 0;
    public int moisCalendrier = 0;
    public int anneeCalendrier = 0;

    public int heure = 0;
    public int minute = 0;

    private boolean boolModification;
    private int tacheId;
    public Button date;
    Button horaire;

    Button valider;
    Button annuler;
    String type = "occasionnel";
    RadioGroup radioGroup;
    Spinner spinnerCategorie;
    EditText nomE;
    EditText descriptionE;
    EditText pointE;
    EditText dureeE;
    TextView textCategorie;
    ArrayAdapter<CharSequence> categorieAdapter;
    private AppDatabase db;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_layout);
        //Recuperer l'intention du parent'
        Intent intent = getIntent();
        //connection avec la base de donnee
        db = AppDatabase.getInstance(this);
        //ajouter le spinner
        spinnerCategorie = findViewById(R.id.insert_spinnerCategorie);
        categorieAdapter = ArrayAdapter.createFromResource(this,R.array.categories_array,android.R.layout.simple_spinner_item);
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(categorieAdapter);
        //definir les boutons et les edits textes
        boolModification = false;
        nomE = findViewById(R.id.insert_nom);
        descriptionE = findViewById(R.id.insert_description);
        pointE = findViewById(R.id.insert_nombre_point);
        dureeE = findViewById(R.id.insert_duree);
        textCategorie = findViewById(R.id.insert_text_categorie);
        date = (Button) findViewById(R.id.insert_date);
        horaire = findViewById(R.id.insert_horaire);
        valider = findViewById(R.id.insert_valider);
        annuler = findViewById(R.id.insert_annuler);
        radioGroup = findViewById(R.id.insert_radio_groupe);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.insert_radio_occasionnel){
                type = "occasionnel";
                this.date.setText("Date");
            }
            else{
                type = "permanent";
                this.date.setText("jour");
            }
        });
        radioGroup.check(R.id.insert_radio_occasionnel); // le type occasionnel est par defaut
        //Verifier si c'est un ajout ou une modification
        if(intent.getStringExtra(ActivityTache.EXTRA_NOM) != null) {
            String nom = intent.getStringExtra(ActivityTache.EXTRA_NOM);
            String description = intent.getStringExtra(ActivityTache.EXTRA_DESCRIPTION);
            String duree = intent.getStringExtra(ActivityTache.EXTRA_DUREE);
            int point = intent.getIntExtra(ActivityTache.EXTRA_POINT,0);
            String type = intent.getStringExtra(ActivityTache.EXTRA_TYPE);
            String categorie = intent.getStringExtra(ActivityTache.EXTRA_CATEGORIE);
            String date = intent.getStringExtra(ActivityTache.EXTRA_DATE);
            String horaire = intent.getStringExtra(ActivityTache.EXTRA_HORAIRE);
            modification(nom, description, duree, point, type, categorie, date, horaire);
            this.boolModification = true;
            this.tacheId = intent.getIntExtra(ActivityTache.EXTRA_ID,0);
            this.valider.setText("Modifier");
        }

        else{
            this.valider.setText("Ajouter");
        }


    }
    public void modification(String nom, String description, String duree,int point,String type,String categorie,String date,String horaire){
        nomE.setText(nom);
        descriptionE.setText(description);
        dureeE.setText(duree);
        pointE.setText(String.valueOf(point));
        this.horaire.setText(horaire);
        if(type.equals("occasionnel")){
            radioGroup.check(R.id.insert_radio_occasionnel);
        }
        else{
            radioGroup.check(R.id.insert_radio_permanent);
        }
        int position = categorieAdapter.getPosition(categorie);
        spinnerCategorie.setSelection(position);
        this.date.setText(date);



    }
    public void ChangerDate(View v){
        comportementView(false,View.GONE);//cacher les views
        if(Objects.equals(this.type, "occasionnel")) {
            Calendrier fragment = new Calendrier(this);
            getFragmentManager().beginTransaction().add(R.id.fragment_calendrier, fragment).commit();
        }
        else{
            JourSemaine jourSemaine = new JourSemaine(this);
            getFragmentManager().beginTransaction().add(R.id.fragment_calendrier, jourSemaine).commit();
        }
    }
    public void calenderActionOver(){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_calendrier);
        if(fragment != null){
            getFragmentManager().beginTransaction().remove(fragment).commit();
            if(type.equals("occasionnel") && jourCalendrier != 0) {
                    String a = String.valueOf(jourCalendrier) + "/" + String.valueOf(moisCalendrier) + "/" + String.valueOf(anneeCalendrier);
                    date.setText(a);

            }
        }
        comportementView(true,View.VISIBLE); // remettre les views
    }

    private void comportementView(boolean enable, int visibility){ // changer le comportement des views selon l'action sur la date
        this.nomE.setEnabled(enable);
        this.descriptionE.setEnabled(enable);
        this.pointE.setEnabled(enable);
        this.dureeE.setEnabled(enable);
        this.horaire.setEnabled(enable);
        this.radioGroup.setVisibility(visibility);
        this.spinnerCategorie.setVisibility(visibility);
        this.valider.setVisibility(visibility);
        this.annuler.setVisibility(visibility);
        this.textCategorie.setVisibility(visibility);
    }
    public void ChangerHoraire(View v){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, hourOfDay, minuteodDay) -> {
            heure = hourOfDay;
            minute = minuteodDay;
            horaire.setText(String.format(Locale.getDefault(),"%02d:%02d",heure,minute));
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,style,onTimeSetListener,heure,minute,true);
        timePickerDialog.show();
    }


    public void insertValider(View v){//Insertion de la nouvelle tache dans la base de donnee
        try {
            String nomS = nomE.getText().toString();
            String descriptionS = descriptionE.getText().toString();
            String dureeS = dureeE.getText().toString();
            int pointI = Integer.parseInt(pointE.getText().toString());
            String categorie = spinnerCategorie.getSelectedItem().toString();
            String heure = horaire.getText().toString();
            String date = this.date.getText().toString();
            if(heure.equals("Horaire")||date.equals("Date")||date.equals("jour")){
                throw new RuntimeException("Champ date ou horaire obligatoire");
            }
            //creation d'une nouvelle tache
            Tache tache = new Tache(nomS, descriptionS, date, heure, this.type, categorie, pointI, dureeS); // s'il y a des valeurs vide alors il y aura un exception
            if (boolModification) {//Si c'est une modification
                tache.id = this.tacheId;
                db.tacheDao().updateTache(tache);//mis a jour de la tache
            } else {//si c'est un insertion
                //insertion de la nouvelle tache
                db.tacheDao().addTache(tache);

            }
            //fermeture de l'activité
            this.finish();
        }
        catch (Exception e){
            Toast.makeText(this,"Champs manquants",Toast.LENGTH_SHORT).show();
        }
    }
    public void insertAnnuler(View v){
        onBackPressed();

    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quitter la page");
        builder.setMessage("Voulez-vous vraiment quitter? les modification ne seront pas sauvegardées");
        builder.setPositiveButton("Quitter", (dialog, which) -> {
            super.onBackPressed();
        });
        builder.setNegativeButton("Rester", (dialog, which) -> {
            //on reste sur la page
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
