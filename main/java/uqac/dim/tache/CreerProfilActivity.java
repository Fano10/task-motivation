package uqac.dim.tache;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import java.io.ByteArrayOutputStream;
import android.util.Base64;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CreerProfilActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ImageView profileIcon;
    private EditText profileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creer_profil);

        profileIcon = findViewById(R.id.profileIcon);
        profileName = findViewById(R.id.profileName);
        Button chooseButton = findViewById(R.id.chooseButton);
        Button saveButton = findViewById(R.id.saveButton);

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreerProfilActivity.this, ProfilCreation.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir le nom de l'utilisateur
                String nom = profileName.getText().toString();
                // Obtenir l'image de l'utilisateur
                // Ici, nous obtenons la ressource d'image actuellement définie dans profileIcon
                Drawable imageDrawable = profileIcon.getDrawable();
                // Convertir Drawable en Bitmap
                Bitmap imageBitmap = ((BitmapDrawable) imageDrawable).getBitmap();
                // Convertir Bitmap en chaîne pour le stocker dans les préférences partagées
                // Nous utilisons une méthode appelée bitmapToString pour cela
                String image = bitmapToString(imageBitmap);

                // Sauvegarder le nom et l'image dans les préférences partagées
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nom", nom);
                editor.putString("image", image);
                editor.apply();

                // Retourner à MainActivity
                Intent intent = new Intent(CreerProfilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int imageResId = data.getIntExtra("imageResId", 0);
            if (imageResId != 0) {
                profileIcon.setImageResource(imageResId);
            }
        }
    }

    // Méthode pour convertir Bitmap en chaîne
    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
