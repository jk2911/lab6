package maxim.goy.lab6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import maxim.goy.lab6.Model.Event;
import maxim.goy.lab6.Model.Image;
import maxim.goy.lab6.R;

public class EventAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private int layout;
    private List<Event> events;

    public EventAdapter(@NonNull Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = events.get(position);

        viewHolder.nameView.setText(event.name);
        viewHolder.date.setText(event.getStringDateForListView());
        Image.getInstance().loadImageFromStorage(viewHolder.imageView, event.pathImages);

        return convertView;
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView nameView, date;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
            nameView = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
        }
    }
}
