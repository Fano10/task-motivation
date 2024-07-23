package uqac.dim.tache.listeTache;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton; // Ajout de cette ligne pour ImageButton
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.tache.ActivityTache;
import uqac.dim.tache.R;
import uqac.dim.tache.room.Categorie;
import uqac.dim.tache.room.Tache;
import uqac.dim.tache.room.AppDatabase;

public class TacheAdapter extends ArrayAdapter<Tache> {


    private final Context context;
    private AppDatabase db;


    public TacheAdapter(Context context, List<Tache> taches) {
        super(context, 0, taches);
        this.context = context; // Ajoutez une variable membre pour le contexte dans votre adaptateur
        db = AppDatabase.getInstance(this.context);
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Obtenir l'objet Tache pour cette position
        final Tache tache = getItem(position);

        // Vérifie si une vue existante est réutilisée, sinon inflate la vue
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nouvelle_tache, parent, false);
        }

        // Recherche de la vue pour la data
        CheckBox checkBoxTache = convertView.findViewById(R.id.Cocher);
        ImageButton imageButtonModifier = convertView.findViewById(R.id.Modifier); // Ajoutez cette ligne
        TextView textViewType = convertView.findViewById(R.id.typeTextView);
        // Remplir la vue
        assert tache != null;
        checkBoxTache.setText(tache.nom);
        textViewType.setText(tache.nom);
        checkBoxTache.setChecked(tache.estComplete);
        RelativeLayout layout = convertView.findViewById(R.id.nouvelleTache);
        //Adapter la couleur du layout en fonction du Type
        if ("Permanent".equals(tache.type)) {
            layout.setBackgroundColor(context.getResources().getColor(R.color.bleu));
        } else if ("Occasionnel".equals(tache.type)) {
            layout.setBackgroundColor(context.getResources().getColor(R.color.black));
            textViewType.setTextColor(context.getResources().getColor(android.R.color.white));
            checkBoxTache.setTextColor(context.getResources().getColor(android.R.color.white));

        }
        // Configuration de l'écouteur sur le CheckBox pour la supprimer
        checkBoxTache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // la tache est realiser
                    //Avant de supprimer la tache ajouter du point dans la table categorie
                    String categorieNom = tache.categorie;
                    int point = tache.nombreDePoint;
                    Categorie categorie = db.categorieDao().getTacheByNom(categorieNom);
                    categorie.nombreDePoint += point;
                    db.categorieDao().updateCategorie(categorie);
                    //supprimer la tache de la bd puisqu'il est realiser
                    db.tacheDao().deleteTache(tache);
                    remove(tache); // Supprime la tâche de la liste
                    notifyDataSetChanged();//rafraichier l'affichage
                    Toast.makeText(context,"Félicitation. Tache réalisée",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuration de l'écouteur pour l'ImageButton
        imageButtonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getContext() instanceof ActivityTache) {
                    ((ActivityTache) getContext()).ModificationTache(tache);
                }
            }
        });

        return convertView;
    }
}
