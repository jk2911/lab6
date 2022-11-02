package maxim.goy.lab6.Model;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventsList {
    private final String fileName = "events";
    public List<Event> events;
    JSONHelper<Event> jsonHelper;
    static Context context;

    public EventsList(Context context) {
        this.context = context;
        jsonHelper = new JSONHelper<>();
        Type typeClass = new TypeToken<List<Event>>() {
        }.getType();
        events = new ArrayList<>(jsonHelper.importFromJSON(this.context, fileName, typeClass));
        if (events == null)
            events = new ArrayList<>();

    }

    public void AddEvent(Event newEvent) {
        events.add(newEvent);
        Type typeClass = new TypeToken<List<Event>>() {
        }.getType();
        jsonHelper.exportToJSON(this.context, events, fileName, typeClass);
    }
    public void RemoveEvent(Event event) {
        events.remove(event);
        Type typeClass = new TypeToken<List<Event>>() {
        }.getType();
        jsonHelper.exportToJSON(this.context, events, fileName, typeClass);
    }
}
