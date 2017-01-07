package com.arpaul.avantari_qs2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arpaul.avantari_qs2.common.ApplicationInstance;
import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.LogUtils;

import java.util.ArrayList;

import static com.arpaul.utilitieslib.CalendarUtils.DIFF_TYPE.TYPE_SECONDS;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbLoading;
    private TextView tvTimeTaken;
    private Button btnStart;
    private String startTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseControls();

        bindControls();
    }

    private void bindControls() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrLetter.clear();
                pbLoading.setVisibility(View.VISIBLE);
                startTime = CalendarUtils.getDateinPattern(CalendarUtils.DATE_TIME_FORMAT_T);
                tvTimeTaken.setText("Syncing..");

                downloadAlphabet();
//                startService(new Intent(MainActivity.this, DownloadService.class));
            }
        });
    }

    ArrayList<String> strOne = new ArrayList<>();
    ArrayList<String> strTwo = new ArrayList<>();
    ArrayList<String> strThree = new ArrayList<>();
    ArrayList<String> strFour = new ArrayList<>();

    private void downloadAlphabet() {
        for(char alphabet = 'a'; alphabet <= 'z'; ) {
            ArrayList<String> arrList = null;
            if(alphabet <= 'f') {
                arrList = strOne;
            } else if(alphabet <= 'l') {
                arrList = strTwo;
            } else if(alphabet <= 's') {
                arrList = strThree;
            } else if(alphabet <= 'z') {
                arrList = strFour;
            }
            arrList.add(alphabet + "");
            alphabet += 1;
        }
        startDownload(strOne.get(0));
        startDownload(strTwo.get(0));
        startDownload(strThree.get(0));
        startDownload(strFour.get(0));
    }

    private void startDownload(final String alphabet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new DownloadData().downloadAlphabet(MainActivity.this, alphabet + "");
            }
        }).start();
    }

    ArrayList<String> arrLetter = new ArrayList<>();
    private BroadcastReceiver brDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String letter = intent.getStringExtra("alphabet");
            if(!letter.equalsIgnoreCase("z")) {
                if(strOne.contains(letter)) {
                    int posi = strOne.indexOf(letter);
                    if((posi + 1) <= strOne.size()) {
                        startDownload(strOne.get(posi + 1));
                    }
                } else if(strTwo.contains(letter)) {
                    int posi = strTwo.indexOf(letter);
                    if((posi + 1) <= strTwo.size()) {
                        startDownload(strTwo.get(posi + 1));
                    }
                } else if(strThree.contains(letter)) {
                    int posi = strThree.indexOf(letter);
                    if((posi + 1) <= strThree.size()) {
                        startDownload(strThree.get(posi + 1));
                    }
                } else if(strFour.contains(letter)) {
                    int posi = strFour.indexOf(letter);
                    if((posi + 1) <= strFour.size()) {
                        startDownload(strFour.get(posi + 1));
                    }
                }
            } else {
                String endTime = CalendarUtils.getDateinPattern(CalendarUtils.DATE_TIME_FORMAT_T);
                LogUtils.debugLog("Duration", "startTime: " + startTime);
                LogUtils.debugLog("Duration", "endTime: " + endTime);
                String timeTaken = CalendarUtils.getDiffBtwDatesPattern(startTime, endTime, TYPE_SECONDS, CalendarUtils.DATE_TIME_FORMAT_T) + "";

                pbLoading.setVisibility(View.GONE);
                tvTimeTaken.setText(timeTaken);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(brDownload, new IntentFilter(ApplicationInstance.ACTION_DOWNLOAD));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(brDownload);
    }

    private void initialiseControls(){
        pbLoading       = (ProgressBar) findViewById(R.id.pbLoading);
        tvTimeTaken     = (TextView) findViewById(R.id.tvTimeTaken);
        btnStart        = (Button) findViewById(R.id.btnStart);

        pbLoading.setVisibility(View.GONE);
    }
}
