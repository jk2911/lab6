package maxim.goy.lab6.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import maxim.goy.lab6.Adapter.EventAdapter;
import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.Model.EventsList;
import maxim.goy.lab6.R;

public class ListEventFragment extends Fragment {
    ListView events;
    EventsList eventsList;

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
        EventAdapter adapter = new EventAdapter(getContext(), R.layout.list_item, eventsList.events);
        events.setAdapter(adapter);
        //registerForContextMenu(events);
        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // по позиции получаем выбранный элемент
                Event selectedItem = (Event) events.getItemAtPosition(position);
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
    }

}
