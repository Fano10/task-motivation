package uqac.dim.tache.calendrier;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import uqac.dim.tache.CrudTache;
import uqac.dim.tache.R;

public class JourSemaine extends Fragment implements View.OnClickListener {
    CrudTache parent;
    public JourSemaine(){
        super();
    }
    @SuppressLint("ValidFragment")
    public JourSemaine(CrudTache parent){
        super();
        this.parent = parent;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jour_semaine_layout,container,false);
        //les boutons
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonLundi = view.findViewById(R.id.jour_semaine_lundi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonMardi = view.findViewById(R.id.jour_semaine_mardi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonMercredi = view.findViewById(R.id.jour_semaine_mercredi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonJeudi = view.findViewById(R.id.jour_semaine_jeudi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonVendredi = view.findViewById(R.id.jour_semaine_vendredi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonSamedi = view.findViewById(R.id.jour_semaine_samedi);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonDimanche = view.findViewById(R.id.jour_semaine_dimanche);

        buttonLundi.setOnClickListener((View.OnClickListener) this);
        buttonMardi.setOnClickListener((View.OnClickListener) this);
        buttonMercredi.setOnClickListener((View.OnClickListener) this);
        buttonJeudi.setOnClickListener((View.OnClickListener) this);
        buttonVendredi.setOnClickListener((View.OnClickListener) this);
        buttonSamedi.setOnClickListener((View.OnClickListener) this);
        buttonDimanche.setOnClickListener((View.OnClickListener) this);

        return view;


    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String texte = (String)button.getText();
        this.parent.date.setText(texte);
        this.parent.calenderActionOver();
    }
}
