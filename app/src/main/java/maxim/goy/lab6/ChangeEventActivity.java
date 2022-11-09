package maxim.goy.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.Model.EventsList;
import maxim.goy.lab6.Model.Image;

public class ChangeEventActivity extends AppCompatActivity {
    TextView name, description;
    DatePicker date;
    TimePicker time;
    ImageView image;
    Event event;

    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.calendar);
        time = findViewById(R.id.time);

        event = (Event) getIntent().getExtras().getSerializable("event");
        Image.getInstance().loadImageFromStorage(image, event.pathImages);
        name.setText(event.name);
        description.setText(event.description);
        date.updateDate(event.calendar.get(Calendar.YEAR),
                event.calendar.get(Calendar.MONTH),
                event.calendar.get(Calendar.DATE));
        time.setHour(event.calendar.get(Calendar.HOUR));
        time.setMinute(event.calendar.get(Calendar.MINUTE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                changeEvent();
                goToMainActivity();
                return true;
            case R.id.cancel:
                goToMainActivity();
                return true;
            default:
                return true;
        }
    }

    public void setImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Pick_image);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        try {
            final Uri imageUri = imageReturnedIntent.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeEvent() {
        EventsList eventsList = new EventsList(this);
        eventsList.RemoveEvent(event);
        event.name = name.getText().toString();
        event.description = description.getText().toString();
        event.calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                time.getHour(), time.getMinute());
        try {
            event.pathImages = Image.getInstance().saveToInternalStorage(
                    ((BitmapDrawable) image.getDrawable()).getBitmap(), this, event.getNameImage());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        eventsList.AddEvent(event);
    }
}
