package maxim.goy.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import maxim.goy.lab6.Fragment.DetailFragment;
import maxim.goy.lab6.Fragment.ListEventFragment;
import maxim.goy.lab6.Model.Event;

public class MainActivity extends AppCompatActivity implements ListEventFragment.OnFragmentSendDataListener {

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().
                setReorderingAllowed(true).
                replace(R.id.fragment_container_view, ListEventFragment.class, savedInstanceState).
                commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().setReorderingAllowed(true).
                    replace(R.id.fragment_item_event, DetailFragment.class, savedInstanceState).
                    commit();
            fragmentManager.popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this,
                        AddEventActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.asc_order:
                sortedEventsInAsc();
                return true;
            case R.id.desc_order:
                sortedEventsInDesc();
                return true;*/
            default:
                return true;
        }
    }

    @Override
    public void onSendData(Event data) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, ChangeEventActivity.class);
            intent.putExtra("event", data);
            startActivity(intent);
        }

    }
}