package uqac.dim.tache.calendrier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import java.util.Calendar;

import uqac.dim.tache.CrudTache;
import uqac.dim.tache.R;

public class Calendrier extends Fragment implements CalendarView.OnDateChangeListener,View.OnClickListener {
    CrudTache parent;
    CalendarView calendarView;
    Calendar calendar;

    int year;
    int month;
    int day;

    Button annuler;
    Button ok;

    public Calendrier(){
        super();
        year = 0;
        month=0;
        day = 0;
    }
    @SuppressLint("ValidFragment")
    public Calendrier(CrudTache parent){
        super();
        year = 0;
        month=0;
        day = 0;
        this.parent = parent;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewCalendrier = inflater.inflate(R.layout.calendrier_layout,container,false);
        this.annuler = viewCalendrier.findViewById(R.id.bouton_annuler);
        this.ok = viewCalendrier.findViewById(R.id.bouton_ok);
        this.calendarView = viewCalendrier.findViewById(R.id.calendarView);
        this.annuler.setOnClickListener((View.OnClickListener) this);
        this.ok.setOnClickListener((View.OnClickListener) this);
        this.calendarView.setOnDateChangeListener((CalendarView.OnDateChangeListener) this);
        return viewCalendrier;
    }
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
    }
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.bouton_ok){
            if(this.day !=0 && this.year!=0) { // si rien n'a ete selectionner
                parent.jourCalendrier = this.day;
                parent.moisCalendrier = this.month + 1;
                parent.anneeCalendrier = this.year;
            }
            else{
                Calendar calendar = Calendar.getInstance();
                parent.jourCalendrier = calendar.get(Calendar.DAY_OF_MONTH);
                parent.moisCalendrier = calendar.get(Calendar.MONTH) + 1;
                parent.anneeCalendrier = calendar.get(Calendar.YEAR);
            }
        }
        parent.calenderActionOver();
    }
}
