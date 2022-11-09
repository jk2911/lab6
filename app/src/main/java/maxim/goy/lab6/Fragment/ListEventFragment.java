package maxim.goy.lab6.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import maxim.goy.lab6.Adapter.EventAdapter;
import maxim.goy.lab6.ChangeEventActivity;
import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.Model.EventsList;
import maxim.goy.lab6.R;

public class ListEventFragment extends Fragment {
    ListView events;
    EventsList eventsList;
    Event selectedItem;
    EventAdapter adapter;
    final int DIALOG_DELETE = 1;

    View view;

    public ListEventFragment() {
        super(R.layout.list_events_fragment);
    }

    public interface OnFragmentSendDataListener {
        void onSendData(Event data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.list_events_fragment, container, false);
        events = view.findViewById(R.id.events);
        setListEvents();
        return view;
    }

    public void setListEvents() {
        eventsList = new EventsList(getContext());
        adapter = new EventAdapter(getContext(), R.layout.list_item, eventsList.events);
        events.setAdapter(adapter);
        registerForContextMenu(events);
        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // по позиции получаем выбранный элемент
                Event selectedItem = (Event) events.getItemAtPosition(position);
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
    }

    public static final int IDM_OPEN = 101;
    public static final int IDM_REMOVE = 102;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Открыть");
        menu.add(Menu.NONE, IDM_REMOVE, Menu.NONE, "Удалить");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedItem = (Event) events.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case IDM_OPEN:
                InfoEvent();
                return true;
            case IDM_REMOVE:
                showDialog();
            /*case R.id.change:
                ChangeEvent();
                return true;
            case R.id.delete:
                showDialog(DIALOG_DELETE);
                return true;*/
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void InfoEvent() {
        Intent intent = new Intent(getContext(), ChangeEventActivity.class);
        intent.putExtra("event", selectedItem);
        startActivity(intent);
    }

    protected void showDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(getContext());
        al.setTitle("Удаление");
        al.setMessage("Вы действительно хотите удалить мероприятие?");
        al.setPositiveButton("Да", clickListener);
        al.setNegativeButton("Нет", clickListener);
        al.show();
    }

    DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    eventsList.RemoveEvent(selectedItem);
                    adapter.notifyDataSetChanged();
                    return;
                case DialogInterface.BUTTON_NEGATIVE:
                    return;

            }
        }
    };
}
