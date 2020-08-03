package com.raveena.progressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raveena.progressapp.databases.MusicDatabaseHandler;
import com.raveena.progressapp.models.MusicDBModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProjectActivity extends AppCompatActivity {

    EditText projectNameEt, descriptionEt;
    String projectName, description = "";
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        confirm = findViewById(R.id.addProjectBtn);
        projectNameEt = findViewById(R.id.projectNameEt);
        descriptionEt = findViewById(R.id.descriptionEt);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save project name and description (if necessary)
                projectName = projectNameEt.getText().toString();
                description = descriptionEt.getText().toString();

                MusicDBModel newModel = new MusicDBModel(projectName);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                Date now = new Date();
                String dateAdded = sdf.format(now);
                newModel.setDateAdded(dateAdded);

                // Check if there is a description
                if (description != null || description.length() != 0) {
                    newModel.setDescription(description);
                }

                // Make a project folder in storage
                File mediaStorageDir = new File(getExternalFilesDir("/"), projectName);
                // if it already exists do not create
                if (!mediaStorageDir.exists()) {
                    if (! mediaStorageDir.mkdirs()) {
                        Log.d("App", "failed to create");
                    }
                } else {
                    Log.d("App", "there was a folder already");
                    Toast.makeText(getApplicationContext(), "Already project folder with " +
                            "that name", Toast.LENGTH_SHORT).show();
                }

                MusicDatabaseHandler db = new MusicDatabaseHandler(getApplicationContext());
                boolean status = db.addMusicToDBWithProjectName(newModel);
                if (status) {
                    Toast.makeText(getApplicationContext(), "Successfully Added",
                            Toast.LENGTH_SHORT).show(); // DEBUG
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add",
                            Toast.LENGTH_SHORT).show(); // DEBUG
                }

                // Return back to
                Intent goBackHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(goBackHome);

            }
        });


    }
}
