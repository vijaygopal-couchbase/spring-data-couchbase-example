package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.example.entity.Brewery;
import org.example.entity.ChangeFeed;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChangeFeedConsumer {

    String BASE_URL = "https://rpzfzetjx6threef.apps.cloud.couchbase.com:4984/ag2go/";

    ObjectMapper mapper = new ObjectMapper();
    public void consumeStreamingApi(String apiUrl){
        getrequest(apiUrl);
    }

    private void getrequest(String apiUrl) {
        try{
            CloseableHttpClient httpClient = getHttpClient();

            HttpGet request = new HttpGet(apiUrl);
            CloseableHttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            if(entity!=null){
                processResponse(entity);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
                {
                    public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                    {
                        return true;
                    }
                }).build()).setDefaultCredentialsProvider(getBasicCredentials()).build();
        return httpClient;
    }

    private void processResponse(HttpEntity entity) {
        try{
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<ChangeFeed> changeFeedsList = new ArrayList<ChangeFeed>();
            reader.lines().forEach(item->{
                //System.out.println(item);
                try {
                    if(!StringUtil.isNullOrEmpty(item)) {
                        ChangeFeed cfeed = mapper.readValue(item.getBytes(), ChangeFeed.class);
                        changeFeedsList.add(cfeed);
                        if(cfeed.getId().contains("cafe")){
                            try {
                                CloseableHttpClient httpClient = getHttpClient();
                                String url = BASE_URL+"/"+cfeed.getId();
                                HttpGet request = new HttpGet(url);
                                CloseableHttpResponse response = httpClient.execute(request);
                                HttpEntity responseEntity = response.getEntity();
                                if(responseEntity!=null){
                                    InputStream is = responseEntity.getContent();
                                    String result = IOUtils.toString(is, StandardCharsets.UTF_8);
                                    Brewery brewery = mapper.readValue(result.getBytes(), Brewery.class);
                                    brewery.setAbv(6);
                                    String PUT_URL = BASE_URL+"/"+brewery.getId();
                                    HttpPut updateReq = new HttpPut(PUT_URL);
                                    updateReq.setHeader("Accept", "application/json");
                                    updateReq.setHeader("Content-type", "application/json");
                                    StringEntity updatedBrewery = new StringEntity(mapper.writeValueAsString(brewery));
                                    updateReq.setEntity(updatedBrewery);
                                    httpClient.execute(updateReq);
                                }
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            } catch (KeyManagementException e) {
                                throw new RuntimeException(e);
                            } catch (KeyStoreException e) {
                                throw new RuntimeException(e);
                            } catch (ClientProtocolException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

//            String line;
//            while((line = reader.readLine()) != null){
//                System.out.println(line);
//            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHost getHttpHost(){
        HttpHost targetHost = new HttpHost("rpzfzetjx6threef.apps.cloud.couchbase.com",4984);
        return targetHost;
    }

    private AuthScope getAuthScope(){
        return new AuthScope(getHttpHost());
    }

    private BasicCredentialsProvider getBasicCredentials(){
        BasicCredentialsProvider bcp = new BasicCredentialsProvider();
        bcp.setCredentials(getAuthScope(), new UsernamePasswordCredentials("test","Pa55w0rd$"));
        return bcp;
    }
}
