package uqac.dim.tache.graphique;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import uqac.dim.tache.room.Categorie;

public class GraphiqueCirculaire extends View {

    public int rayon;
    public Categorie[] tabCategorie;


    public GraphiqueCirculaire(Context context, AttributeSet attrs){
        super(context,attrs);
        rayon = 250;
    }
    public GraphiqueCirculaire(Context context){
        super(context);
        rayon = 250;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = rayon; // Rayon du demi-cercle

        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(100);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);

        Paint [] tabPaint = new Paint[6];
        for(int i=0; i<6;i++){
            tabPaint[i] = new Paint();
            tabPaint[i].setColor(Color.parseColor(tabCategorie[i].couleur));
        }

        //int totalPoints = domestique.points + professionelle.points + loisir.points+social.points+developpement.points+sante.points;
        int totalPoints = 0;
        for(int i=0; i<6;i++){
            totalPoints += tabCategorie[i].nombreDePoint;
        }
        float facteurDegre = (float)360/(float)totalPoints;
        float startAngle = 0;
        float sweepAngle = 0;
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        String points = "Points: " + totalPoints;
        canvas.drawText(points,(centerX-rayon),(centerY-rayon-100),paintText);
        for(int i=0 ;i<6;i++) {
            sweepAngle = tabCategorie[i].nombreDePoint * facteurDegre;
            canvas.drawArc(rectF, startAngle, sweepAngle, true, tabPaint[i]);
            startAngle += sweepAngle;
        }

    }

    public void DefinirCategorie(Categorie[] tabCategorie){
        this.tabCategorie = tabCategorie;
    }
}
