package tutorialx.pushnotificationhelper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Button buttonclipboard = findViewById(R.id.buttonclipboard);
    final TextView textViewToken = findViewById(R.id.textViewToken);

    final String fcmToken = FirebaseInstanceId.getInstance().getToken();
    textViewToken.setText(fcmToken);

    buttonclipboard.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        textViewToken.setText(fcmToken);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("token", fcmToken);
        if (clipboard != null) {
          clipboard.setPrimaryClip(clip);
          Toast.makeText(MainActivity.this,"Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
