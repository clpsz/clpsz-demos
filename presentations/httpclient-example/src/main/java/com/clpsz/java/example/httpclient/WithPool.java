package com.clpsz.java.example.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class WithPool {

    public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
        byte[] buffer = new byte[100];

        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setDefaultMaxPerRoute(1);
        pool.setMaxTotal(1);
        final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(pool).build();

        String url = "http://nuc:80/api/echo-name?first_name=Alan&last_name=Turing";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream instream = entity.getContent();
        instream.read(buffer);
        String s = new String(buffer, StandardCharsets.UTF_8);
        System.out.println(s);

        instream.close();
        response.close();


        url = "http://nuc:80/api/echo-name?first_name=Peter&last_name=Turing";
        httpGet = new HttpGet(url);
        response = httpclient.execute(httpGet);
//        entity = response.getEntity();
//        instream = entity.getContent();
//        instream.read(buffer);
//        s = new String(buffer, StandardCharsets.UTF_8);
//
//        System.out.println(s);
//
//        instream.close();
//        response.close();



        url = "http://nuc:80/api/echo-name?first_name=Alan&last_name=Turing";
        httpGet = new HttpGet(url);
        response = httpclient.execute(httpGet);
        entity = response.getEntity();
        instream = entity.getContent();
        instream.read(buffer);
        s = new String(buffer, StandardCharsets.UTF_8);

        System.out.println(s);

        instream.close();
        response.close();

        Thread.sleep(100000);
    }
}