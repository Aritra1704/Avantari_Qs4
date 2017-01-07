package com.arpaul.avantari_qs2;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ARPaul on 07-01-2017.
 */

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        for(char alphabet = 'a'; alphabet <= 'z'; ) {
            new DownloadData().downloadAlphabet(this, alphabet + "");

            alphabet += 2;
        }

        for(char alphabet = 'b'; alphabet <= 'z'; ) {
            new DownloadData().downloadAlphabet(this, alphabet + "");

            alphabet += 2;
        }
    }
}
