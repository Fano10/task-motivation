package uqac.dim.tache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;
import uqac.dim.tache.room.Tache;
import uqac.dim.tache.room.AppDatabase;


import androidx.core.content.ContextCompat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Schedule extends Activity {

    private AppDatabase db;

    String [] tableauJour = {"lundi","mardi","mercredi","jeudi","vendredi","samedi","dimanche"};
    ArrayList<String>[] jourDeLaSemaine;
    ArrayList<Boolean>[] boolJourDeLaSemaine;

    HorizontalScrollView horizontalScrollView;
    TextView textOccasionnel;
    TextView textPermanent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);
        db = AppDatabase.getInstance(this);
        horizontalScrollView = findViewById(R.id.schedule_scrollbar);
        textOccasionnel = findViewById(R.id.schedule_occasionnel);
        textPermanent = findViewById(R.id.schedule_permanent);
        List<Tache> taches = db.tacheDao().getAllTaches();
        jourDeLaSemaine = new ArrayList[7];
        boolJourDeLaSemaine = new ArrayList[7];
        for (int i=0 ; i<7 ;i++){
            jourDeLaSemaine[i] = new ArrayList<String>();
            boolJourDeLaSemaine[i] = new ArrayList<Boolean>();
        }
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView semaineDe = findViewById(R.id.semaineDe);
        DateEmploisDuTemps(semaineDe);
        CompleterSemaine();
        int nombreTache = completerToutesLesJoursDeLaSemaine(taches);
        if(nombreTache>0) {
            CompleterTacheSemaine();
        }
        else{
            horizontalScrollView.setVisibility(View.GONE);
            textPermanent.setVisibility(View.GONE);
            textOccasionnel.setVisibility(View.GONE);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(params);
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.reposer);
            TextView textView1 = new TextView(this);
            textView1.setText("Relaxez!!!");
            textView1.setTextSize(18);
            TextView textView2 = new TextView(this);
            textView2.setText("Aucune tache pour cette semaine");
            textView2.setTextSize(18);
            LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(
                    600,
                    600
            );
            paramsImage.gravity=Gravity.CENTER;
            LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsText.gravity = Gravity.CENTER;
            image.setLayoutParams(paramsImage);
            textView1.setLayoutParams(paramsText);
            textView2.setLayoutParams(paramsText);
            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            LinearLayout layoutPrincipal = findViewById(R.id.schedule_layout);
            layoutPrincipal.addView(image);
            layoutPrincipal.addView(linearLayout);
        }
    }
    void DateEmploisDuTemps(TextView textView){//cette fonction permet de savoir la semaine du mois et de changer le titre
        LocalDate lundi = LundiSemaine();
        String[] mois = {"Janvier","Fevrier","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Decembre"};
        int dateMois =lundi.getMonthValue() - 1;
        int dateJour = lundi.getDayOfMonth();
        int dateAnnee = lundi.getYear();

        String value = "Semaine de "+ dateJour + " "+ mois[dateMois]+ " "+ dateAnnee;
        textView.setText(value);
        textView.setTextSize((float)30);
    }
    void EnTeteAffichage(String dateTexte){ //cette fonction s'occupe juste de l'affichage sur l'enTete du TROW
        TableRow rowJour = findViewById(R.id.jourSemaine); //Selectionne notre ligne pour l'en tete voir jour de la semaine
        TextView jour = new TextView(this);
        jour.setText(dateTexte);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        rowParams.setMargins(0,0,40,0);
        jour.setLayoutParams(rowParams);
        int couleurVerte = ContextCompat.getColor(this, R.color.green);
        int couleurBlanc = ContextCompat.getColor(this, R.color.white);
        jour.setBackgroundColor(couleurVerte);
        jour.setTextColor(couleurBlanc);
        jour.setTextSize((float)24);
        rowJour.addView(jour);
    }
    LinearLayout CreerLinearLayout(){ // cette fonction s'occupe de mettre une linear layout dans chaque colonne de notre tableau
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0,25,0,0);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    TextView CreerTextView(String texte,boolean permanent){//cette fonction permet de creer un textView
        TextView textView = new TextView(this);
        textView.setText(texte);
        textView.setTextSize((float)24);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0,0,40,35);
        textView.setLayoutParams(layoutParams);
        int couleurBleu = ContextCompat.getColor(this, R.color.bleu);
        int couleurNoir = ContextCompat.getColor(this, R.color.black);
        int couleurRose = ContextCompat.getColor(this,R.color.rose);
        if(permanent){
            textView.setBackgroundColor(couleurBleu);
        }
        else{
            textView.setBackgroundColor(couleurRose);
        }
        textView.setTextColor(couleurNoir);
        return textView;
    }


    LocalDate LundiSemaine(){ // cette fonction s'occupe de chercher le premier jour de la semaine c-a- Lundi
        LocalDate dateToday = LocalDate.now();
        while(dateToday.getDayOfWeek() != DayOfWeek.MONDAY){
            dateToday = dateToday.minusDays(1);
        }
        return dateToday;
    }

    void CompleterSemaine(){ // cette fonction s'occupe de chercher et afficher toute les jours de la semaines(appeller dans le main)
        LocalDate monday = LundiSemaine();
        String[] jourDeLaSemaine = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
        String jour;
        for(int i=0 ; i<7 ; i++) {
            jour = String.valueOf(monday.getDayOfMonth()) + " " + jourDeLaSemaine[i];
            EnTeteAffichage(jour);
            monday = monday.plusDays(1);
        }
    }

    void CompleterTacheSemaine(){ // cette fonction complete complete notre tache.
        TableRow tableRow = (TableRow) findViewById(R.id.contenuSemaine);
        int size = 0;
        for(int i=0;i<7;i++){
            LinearLayout layout = CreerLinearLayout();
            tableRow.addView(layout);
            size = this.jourDeLaSemaine[i].size();
            for(int a=0;a<size;a++){
                TextView textView = CreerTextView(this.jourDeLaSemaine[i].get(a),this.boolJourDeLaSemaine[i].get(a));
                layout.addView(textView);
            }
        }
    }
    int completerToutesLesJoursDeLaSemaine(List<Tache> taches){
        LocalDate monday = LundiSemaine();
        LocalDate intermediaire;
        LocalDate dateTache;
        int size = taches.size();
        int nombreTache = 0;
        Tache tache;
        for(int i=0; i<size;i++){
            tache = taches.get(i);
            if(Objects.equals(tache.type, "occasionnel")) { // si la tache est occasionnel
                String[] dateParties = tache.date.split("/");
                dateTache = LocalDate.of(Integer.parseInt(dateParties[2]), Integer.parseInt(dateParties[1]), Integer.parseInt(dateParties[0]));//yyyy//mm/jj
                for (int a = 0; a < 7; a++) {
                    intermediaire = monday.plusDays(a);
                    if (intermediaire.isEqual(dateTache)) {
                        nombreTache +=1;
                        this.jourDeLaSemaine[a].add(tache.heure + " " + tache.nom);
                        this.boolJourDeLaSemaine[a].add(false);
                    }
                }
            }
            else{ // si la tache est permanent
                for (int a = 0; a < 7; a++) {
                    if(Objects.equals(tache.date, tableauJour[a])){
                        nombreTache +=1;
                        this.jourDeLaSemaine[a].add(tache.heure + " " + tache.nom);
                        this.boolJourDeLaSemaine[a].add(true);
                    }
                }
            }
        }
        return nombreTache;
    }
}
