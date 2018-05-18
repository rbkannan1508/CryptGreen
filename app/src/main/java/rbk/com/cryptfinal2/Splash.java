package rbk.com.cryptfinal2;

import android.content.Intent;
import android.os.Bundle;



public class Splash extends MainActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread mythread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent myintent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(myintent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        mythread.start();
    };
};
