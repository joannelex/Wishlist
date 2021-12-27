package persistence;

// This class references code from CPSC210/JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import org.json.JSONObject;

// Writable interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
