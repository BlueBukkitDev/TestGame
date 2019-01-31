package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.ui.InputField;

public class InputFieldManager {
	private List<InputField> fields = new ArrayList<InputField>();
	

	public void update() {
		for(InputField each: fields) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(InputField each:fields) {
			each.render(g);
		}
	}
	
	public void registerField(InputField field) {
		fields.add(field);
	}
	
	public void unregisterField(InputField field) {
		fields.remove(field);
	}

	public List<InputField> getInputFields() {
		return fields;
	}
	
	public void clear() {
		this.fields.clear();
	}
}
