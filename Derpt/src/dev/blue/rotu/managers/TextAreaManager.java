package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.ui.TextArea;

public class TextAreaManager {
	private List<TextArea> fields = new ArrayList<TextArea>();
	
	public void update() {
		for(TextArea each: fields) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(int i = fields.size(); i > 0; i--) {
			fields.get(i-1).render(g);//i-1 because it's using fields.size instead of an array
		}
	}
	
	public void registerField(TextArea field) {
		fields.add(0, field);
	}
	
	public void unregisterField(TextArea field) {
		fields.remove(field);
	}

	public List<TextArea> getTextAreas() {
		return fields;
	}
	
	public void clear() {
		this.fields.clear();
	}
}
