package model;

import java.util.Calendar;
import java.util.Date;

// This class references code from CPSC210/AlarmSystem
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// Represents a Wishlist Event
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // EFFECT: Creates an event with given description, current date and time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date and time of this event
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
