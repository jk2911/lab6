package maxim.goy.lab6.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.R;

public class DetailFragment extends Fragment {
    TextView name;

    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment, container, false);
        name = view.findViewById(R.id.name);
        return view;
    }

    public void setSelectedItem(Event event) {
        name.setText(event.getNameImage());
    }
}
