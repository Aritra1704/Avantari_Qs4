package com.arpaul.avantari_qs2.webservices;

import com.arpaul.utilitieslib.LogUtils;

import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ARPaul on 07-01-2017.
 */

public class XMLServiceCalls {

    private final String TAG = "RestServiceCalls";
    private String url = "";
    private final int TIMEOUT = 10000;
    private WebServiceResponse responseDo;

    public XMLServiceCalls(String url){
        this.url                = url;

        responseDo = new WebServiceResponse();

        sendRequest();
    }

    public WebServiceResponse getData(){
        return responseDo;
    }

    private void sendRequest() {

        String param = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient();

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
            clientBuilder.readTimeout(30, TimeUnit.SECONDS);
//            clientBuilder.writeTimeout(30, TimeUnit.SECONDS);
            okHttpClient = clientBuilder.build();

            Request.Builder builder = new Request.Builder();
            builder.url(url);

            Request request = builder.build();

            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {

                int status = response.code();
                switch (status) {
                    case WebServiceConstant.STATUS_SUCCESS:
                        responseDo.setResponseCode(WebServiceResponse.ResponseType.SUCCESS);
                        responseDo.setResponseMessage(response.body().string());
                        break;

                    case WebServiceConstant.STATUS_FAILED:
                    default:
                        responseDo.setResponseCode(WebServiceResponse.ResponseType.FAILURE);
                        responseDo.setResponseMessage(response.body().string());
                        break;
                }
                LogUtils.debugLog(TAG, "" + response.code());
            }
        } catch (ProtocolException ex) {
            ex.printStackTrace();
        } finally {
            return;
        }
    }
}
