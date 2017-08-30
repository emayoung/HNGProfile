package androiddegree.udacity.ememobong.hngprofile;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.phoneNumber;

public class MainActivity extends AppCompatActivity {

    TextView callTv;
    TextView mailTv;
    TextView slackTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callTv = (TextView) findViewById(R.id.call_text);
        mailTv = (TextView) findViewById(R.id.mail_text);
        slackTv = (TextView) findViewById(R.id.slack_text);
    }
    public void mailMe(View v){
        popMailDialog();
    }


    public void callMe(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + callTv.getText()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void popMailDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.email_form);
        dialog.setTitle("Contact Me");

        // set the custom dialog components - text, image and button
        final EditText email = (EditText) dialog.findViewById(R.id.email_edittext);
        final EditText subject = (EditText) dialog.findViewById(R.id.subject_edittext);
        final EditText body = (EditText) dialog.findViewById(R.id.body_edittext);
        Button sendBut = (Button) dialog.findViewById(R.id.send_button);

        // if button is clicked, close the custom dialog
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mailTv.getText().toString(), email.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT   , body.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(dialog.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();

    }
}
