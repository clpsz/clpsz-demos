package com.clpsz.java.example.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class NoPool {

    public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
        final String url = "http://nuc:80/api/echo-name?first_name=Alan&last_name=Turing";
        final CloseableHttpClient httpclient = HttpClients.createSystem();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpGet httpGet = new HttpGet(url);
                        CloseableHttpResponse response = httpclient.execute(httpGet);
                        HttpEntity entity = response.getEntity();
                        EntityUtils.consume(entity);

                        response.close();
                        System.out.println("no pool finish");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        Thread.sleep(100000);
    }
}