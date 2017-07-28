package com.example.moadd.poststringvolley;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new HttpRequestTask().execute();

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, LoginForm> {
        @Override
        protected LoginForm doInBackground(Void... params) {
            try {
                //The link on which we have to POST data and in return it will return some data
                String URL = "http://192.168.0.102:8081/Moaddi8/loginfrom.htm";
                //Create and set object 'l' of bean class LoginForm,which we will POST then
                LoginForm l = new LoginForm();
                l.setUsername("Umar");
                l.setPassword("Farooq007");
                //Use RestTemplate to POST(within Asynctask)
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //postforobject method POSTs data to server and brings back LoginForm object format data.
                LoginForm lf = restTemplate.postForObject(URL, l, LoginForm.class);
                return lf;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(LoginForm lf) {
            //The returned object of LoginForm that we recieve from postforobject in doInBackground is displayed here.
         tv.setText(lf.toString());
        }

    }
}
