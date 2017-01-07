package com.arpaul.avantari_qs2.webservices;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.arpaul.avantari_qs2.DownloadData;
import com.arpaul.avantari_qs2.common.ApplicationInstance;
import com.arpaul.avantari_qs2.dataaccess.CPConstants;
import com.arpaul.avantari_qs2.dataobjects.ParserDO;
import com.arpaul.avantari_qs2.dataobjects.WordsDO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ARPaul on 07-01-2017.
 */

public class DictionaryParser {

    private WebServiceResponse response;
    private Context context;

    public DictionaryParser (WebServiceResponse response, Context context) {
        this.response = response;
        this.context = context;
    }

    public boolean fetchDictionary(String alphabet) {

        ParserDO parseDO = readLoginJSONData(response.getResponseMessage());
        if(parseDO != null && parseDO.linkHash != null && parseDO.linkHash.containsKey(ParserDO.ParseType.TYPE_DCTIONARY_DATA)) {
            ContentValues[] contentValues = null;
            ArrayList<WordsDO> arrWordsDO = (ArrayList<WordsDO>) parseDO.linkHash.get(ParserDO.ParseType.TYPE_DCTIONARY_DATA);
            if(arrWordsDO != null && arrWordsDO.size() > 0) {
                contentValues = new ContentValues[arrWordsDO.size()];
                for(int i = 0; i < arrWordsDO.size(); i++) {
                    contentValues[i] = new ContentValues();

                    contentValues[i].put(WordsDO.ALPHABET, alphabet);
                    contentValues[i].put(WordsDO.MEANING, arrWordsDO.get(i).Meaning);
                }

                int numInsert = context.getContentResolver().bulkInsert(CPConstants.CONTENT_URI_WORDS, contentValues);
                if(numInsert > 0) {
                    Intent intent = new Intent(ApplicationInstance.ACTION_DOWNLOAD);
                    intent.putExtra("alphabet", alphabet);
                    context.sendBroadcast(intent);
                }
            }
        } else {
            new DownloadData().downloadAlphabet(context, alphabet + "");
        }

        return true;
    }

    private ParserDO readLoginJSONData(String data) {
        ParserDO parseDO = new ParserDO();

        ArrayList<WordsDO> arrWordsDO = new ArrayList<>();
        WordsDO objWordsDO = null;
        try {
            if(!TextUtils.isEmpty(data)) {
                InputStream stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
                Document doc = Jsoup.parse(stream, "UTF-8", "http://example.com/");

                Elements subDatas = doc.getElementsByTag("P");
                for (Element subdata : subDatas) {
                    objWordsDO = new WordsDO();
                    String data_B = subdata.attr("b");
                    String dataText = subdata.text();

                    objWordsDO.Word = data_B;
                    objWordsDO.Meaning = dataText;

                    arrWordsDO.add(objWordsDO);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(arrWordsDO != null)
            parseDO.linkHash.put(ParserDO.ParseType.TYPE_DCTIONARY_DATA, arrWordsDO);

        return parseDO;
    }
}
