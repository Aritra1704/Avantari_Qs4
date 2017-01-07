package com.arpaul.avantari_qs2;

import android.content.Context;

import com.arpaul.avantari_qs2.common.ApplicationInstance;
import com.arpaul.avantari_qs2.webservices.DictionaryParser;
import com.arpaul.avantari_qs2.webservices.WebServiceResponse;
import com.arpaul.avantari_qs2.webservices.XMLServiceCalls;


/**
 * Created by ARPaul on 07-01-2017.
 */

public class DownloadData {

    private WebServiceResponse response;

    public void downloadAlphabet(Context context, String alphabet) {
        System.out.println(alphabet);
        response = new XMLServiceCalls(ApplicationInstance.URL_HEADER + alphabet + ApplicationInstance.URL_FOOTER).getData();

        if(response != null && response.getResponseCode() == WebServiceResponse.ResponseType.SUCCESS)
            new DictionaryParser(response, context).fetchDictionary(alphabet);
    }
}
