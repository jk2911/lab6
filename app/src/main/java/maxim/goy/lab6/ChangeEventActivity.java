package maxim.goy.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import maxim.goy.lab6.Model.Event;

public class ChangeEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);
        ((TextView)findViewById(R.id.a)).setText(
                ((Event)getIntent().getExtras().getSerializable("event")).name
        );
    }
}