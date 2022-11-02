package maxim.goy.lab6.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.R;

public class DetailFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }
    public void setSelectedItem(Event event){

    }
}
