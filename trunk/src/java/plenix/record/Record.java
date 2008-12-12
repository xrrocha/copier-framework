package plenix.record;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Record {
	private Map<String, Object> fields = new HashMap<String, Object>();

	public void setField(String name, Object value) {
		fields.put(name, value);
	}
	public Object getField(String name) {
		return fields.get(name);
	}
	
	public void removeField(String name) {
		fields.remove(name);
	}
	
	public void clear() {
		fields.clear();
	}
	
	public Set<String> fieldNames() {
		return fields.keySet();
	}
	public void copyTo(Record other) {
		for (String name: fields.keySet()) {
			other.setField(name, fields.get(name));
		}
	}
	
	public void copyFrom(Record other) {
		for (String name: other.fields.keySet()) {
			this.setField(name, other.fields.get(name));
		}
	}
	public String toString() {
		return fields.toString();
	}
}
