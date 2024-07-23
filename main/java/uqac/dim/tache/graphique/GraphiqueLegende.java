package uqac.dim.tache.graphique;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import uqac.dim.tache.room.Categorie;

public class GraphiqueLegende extends View {

    public Categorie[] tabCategorie;
    public Paint[] tabPaint;
    public GraphiqueLegende(Context context,Categorie[] tabCategorie){
        super(context);
        this.tabCategorie = tabCategorie;
        tabPaint = new Paint[6];
        for(int i=0; i<6;i++){
            tabPaint[i] = new Paint();
            tabPaint[i].setColor(Color.parseColor(tabCategorie[i].couleur));
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(50);

        int left = 100;
        int top = 0;
        int right = 300;
        int bottom = 0;

        String texte;
        float xText = (float)right + (float)10;
        float yText = 0 ;

        for(int i=0; i<6;i++){
            top = bottom + 15;
            bottom = top + 100;
            yText = bottom -50;
            texte = tabCategorie[i].nom;
            canvas.drawRect(left, top, right, bottom, tabPaint[i]);
            canvas.drawText(texte, xText, yText, paintText);
        }
    }
}
