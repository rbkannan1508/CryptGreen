package rbk.com.cryptfinal2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class AboutActivity extends AppCompatActivity {
    private Button send_mail,ok_about;
   // public String gappname;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.about);
       // send_mail = (Button)findViewById(R.id.sendmail);
        ok_about = (Button)findViewById(R.id.okabout);
       // gappname = getResources().getString(R.string.app_name);

        /*send_mail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent smintent = new Intent(Intent.ACTION_SEND);
                smintent.putExtra(Intent.EXTRA_EMAIL, "support@sparklesolutions.net");
                smintent.putExtra(Intent.EXTRA_SUBJECT, gappname);
                smintent.putExtra(Intent.EXTRA_TEXT, gappname);
                smintent.setDataAndType(Uri.parse("support@sparklesolutions.net"), "text/plain");
                startActivity(Intent.createChooser(smintent, "Choice email App"));
                finish();
            }
        });
        */
        ok_about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
