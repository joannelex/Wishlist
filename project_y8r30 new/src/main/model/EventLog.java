package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// This class references code from CPSC210/AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// Represents a log of Wishlist events
/**
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: returns the instance of EventLog; creates it if it does not already exist
    //          (Singleton Design Pattern)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the eventLog
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the eventLog and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
