package com.example.lenovo.wirelesskeyboard;

import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
//    ConstraintLayout layout=(ConstraintLayout)findViewById(R.id.csl);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void keyb(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    char ccc;
String baa;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
        String s =KeyEvent.keyCodeToString( keyCode);
        int kunicode= event.getUnicodeChar(event.getMetaState());
        //ccc=(char)kunicode;

       // Toast.makeText(this, (char)kunicode+" "+s, Toast.LENGTH_SHORT).show();

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
  //      Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
       String s =KeyEvent.keyCodeToString( keyCode);
       baa=s;
       int kunicode= event.getUnicodeChar(event.getMetaState());
       ccc=(char)kunicode;

      //  Toast.makeText(this, ccc+" "+s, Toast.LENGTH_SHORT).show();
        AsyncTask asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                runTcpClient();

                return null;
            }
        };
        asyncTask.execute();
        return super.onKeyDown(keyCode, event); }

    ///////////////////////////////////////////////////////////
    private void runTcpClient() {
        try {
            int TCP_SERVER_PORT=1234;
            Socket s = new Socket(InetAddress.getByName("192.168.43.254"), TCP_SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //send output msg
//            String outMsg = "TCP connecting to " + TCP_SERVER_PORT + System.getProperty("line.separator");
//            String outMsg ="a";
            String outMsg =""+ccc+","+baa;
            out.write(outMsg);
            out.flush();
            Log.i("TcpClient", "sent: " + outMsg);
            //accept server response
            //  String inMsg = in.readLine() + System.getProperty("line.separator");
            // Log.i("TcpClient", "received: " + inMsg);
            //close connection
            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////



}
