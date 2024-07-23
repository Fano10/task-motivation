package uqac.dim.tache;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

public class ProfilCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);
        ImageView imageProfile1 = findViewById(R.id.image_profile_1);
        ImageView imageProfile2 = findViewById(R.id.image_profile_2);
        ImageView imageProfile3 = findViewById(R.id.image_profile_3);
        ImageView imageProfile4 = findViewById(R.id.image_profile_4);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imageResId = 0;
                if (v.getId() == R.id.image_profile_1) {
                    imageResId = R.drawable.ic_profile_1;
                } else if (v.getId() == R.id.image_profile_2) {
                    imageResId = R.drawable.ic_profile_2;
                } else if (v.getId() == R.id.image_profile_3) {
                    imageResId = R.drawable.ic_profile_3;
                } else if (v.getId() == R.id.image_profile_4) {
                    imageResId = R.drawable.ic_profile_4;
                }else if (v.getId() == R.id.img) {
                    imageResId = R.drawable.mc;
                }

                // Créer un Intent avec l'ID de la ressource de l'image sélectionnée
                Intent intent = new Intent(ProfilCreation.this, CreerProfilActivity.class);
                intent.putExtra("imageResId", imageResId);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        imageProfile1.setOnClickListener(listener);
        imageProfile2.setOnClickListener(listener);
        imageProfile3.setOnClickListener(listener);
        imageProfile4.setOnClickListener(listener);
    }

}
