package com.raveena.progressapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.raveena.progressapp.models.MusicDBModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RecordAudioActivity extends AppCompatActivity {
    private ImageButton startbtn, stopbtn;
    private Button playbtn, stopplay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFullFilePath = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private Chronometer time;
    PopupWindow popupWindow;

    private static MusicDBModel musicDBModel;
    private static String filename;
    private static String fullPath;
    private static String projectFolder;
    private static String dateAdded;
    private static boolean isFavorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);

        startbtn = findViewById(R.id.Recordbtn);
        stopbtn = findViewById(R.id.Stopbtn);
        playbtn = findViewById(R.id.Playbtn);
        stopplay = findViewById(R.id.StopPlayingbtn);
        time = findViewById(R.id.record_time);

        stopbtn.setEnabled(false);
        playbtn.setEnabled(false);
        stopplay.setEnabled(false);




        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermissions()) {
                    time.start();
                    startbtn.setBackground(getResources().getDrawable(R.drawable.round_button_on));
                    stopbtn.setEnabled(true);
                    startbtn.setEnabled(false);
                    playbtn.setEnabled(false);
                    stopplay.setEnabled(false);
                    startAudio();
//                    mRecorder = new MediaRecorder();
//                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);


//                    // Creating a subfolder for specific songs or projects
//                    String childFolderName = "Testing";
//                    File mediaStorageDir = new File(getExternalFilesDir("/"), childFolderName);
//                    // if it already exists do not create
//                    if (!mediaStorageDir.exists()) {
//                        if (! mediaStorageDir.mkdirs()) {
//                            Log.d("App", "failed to create");
//                        }
//                    }
//
//                    String recordPath = getExternalFilesDir("/").getAbsolutePath();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault()); // to make unique
//                    Date now = new Date();
//                    String fileName = "filename";
//
//                    mFullFilePath = recordPath + "/" + childFolderName + "/" + fileName + sdf.format(now) + ".aac";
//
//                    // Using this to get the best possible audio
//                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//                    mRecorder.setAudioEncodingBitRate(128000);
//                    mRecorder.setAudioSamplingRate(96000);
//
//                    mRecorder.setOutputFile(mFullFilePath);
//
//                    try {
//                        mRecorder.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mRecorder.start();
//                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();

                } else {
                    RequestPermissions();
                }
            }
        }); // start btn

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.stop();
                time.setFormat(null);
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(true);
                stopplay.setEnabled(true);
                mRecorder.stop();
                mRecorder.release();
//                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording stop " + mFullFilePath, Toast.LENGTH_SHORT).show();
                startbtn.setBackground(getResources().getDrawable(R.drawable.round_button_off));
                popUpActions();
            }
        }); // stop tbn

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(false);
                stopplay.setEnabled(true);
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(mFullFilePath);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "Recording started playing", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }); // play btn

        stopplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.release();
                mPlayer = null;
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                playbtn.setEnabled(true);
                stopplay.setEnabled(false);
                Toast.makeText(getApplicationContext(), "playing audio stopped", Toast.LENGTH_SHORT).show();

            }
        });


    } // end onCreate()

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    /**
     * Method checks if the app has permissions enabled
     * @return if permissions are checked
     */
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests permissions if not checked
     */
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(RecordAudioActivity.this,
                new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    /**
     * This method will take care of PopUp Window display and dismissal
     * When pressing the Delete Button, the popup window closes
     * When pressing the Save Button, the audio file will save to storage and database
     */
    public void popUpActions() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = layoutInflater.inflate(R.layout.save_video_popup, null);

        popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,
                true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // To have the pop up window close without saving data
        ((Button) popupView.findViewById(R.id.deletePopUpBtn))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        popupWindow.dismiss();
                    }
                });

        // To have the popup window close with data saved
        ((Button) popupView.findViewById(R.id.savePopUpBtn))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveAudio("", "");
                        popupWindow.dismiss();
                        mRecorder = null;

//                        musicDBModel = new MusicDBModel(-1, , fullPath,
//                                projectFolder, dateAdded, isFavorite);
                    }
                });

    }

    public void startAudio() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // Using this to get the best possible audio
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioEncodingBitRate(128000);
        mRecorder.setAudioSamplingRate(96000);

        String childFolderName = "Testing";
        File mediaStorageDir = new File(getExternalFilesDir("/"), childFolderName);
        // if it already exists do not create
        if (!mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create");
            }
        }

        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault()); // to make unique
        Date now = new Date();
        filename = "filename" + sdf.format(now) + ".aac";

        mFullFilePath = recordPath + "/" + childFolderName + "/" + filename;

        mRecorder.setOutputFile(mFullFilePath);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
    }

    public void saveAudio(String newFileName, String projectFolderName) {
        // First checking if project folder is created
        File mediaStorageDir = new File(getExternalFilesDir("/"), projectFolderName);
        // if it already exists do not create
        if (!mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create");
            }
        }

        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        File sdcard = new File(recordPath, projectFolderName);
        String fromFullPath = mFullFilePath;
        String toFullPath = "/username.aac";
        // String toFullPath = "/" + newFileName + ".aac";

        File from = new File(sdcard, filename);
        File to = new File(sdcard, toFullPath);
        filename = newFileName;
        fullPath = to.getAbsolutePath();
        boolean test = from.renameTo(to);
        //from.delete();


    }


}
