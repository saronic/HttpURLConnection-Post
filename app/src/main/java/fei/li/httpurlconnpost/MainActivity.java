package fei.li.httpurlconnpost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mInfoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sentPostBtn = (Button) findViewById(R.id.send_post_btn);
        mInfoTV = (TextView) findViewById(R.id.textView);
        sentPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://192.168.31.139:3000");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            //conn.setReadTimeout(15000 /* milliseconds */);
                            //conn.setConnectTimeout(15000 /* milliseconds */);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json");
                            //conn.setDoInput(true);
                            conn.setDoOutput(true);

                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(os, "UTF-8"));
                            String content = "{\"name\":\"zhang\"}";
                            updateUi("Post 发送: " + content);
                            writer.write(content);

                            writer.flush();
                            writer.close();
                            os.close();
                            int responseCode = conn.getResponseCode();
                            Log.d(TAG, "run: responseCode: " + responseCode);
                            updateUi("Response code: " + responseCode);
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                        }
                    }
                }).start();
            }
        });
    }

    private void updateUi(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInfoTV.setText(mInfoTV.getText() + "\n" + info);
            }
        });
    }

}
