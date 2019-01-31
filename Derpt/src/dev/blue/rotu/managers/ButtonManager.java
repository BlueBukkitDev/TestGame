package dev.blue.rotu.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dev.blue.rotu.ui.Button;

public class ButtonManager {
	private List<Button> buttons = new ArrayList<Button>();
	private List<Button> hiddenButtons = new ArrayList<Button>();
	
	public void update() {
		for(Button each: buttons) {
			each.update();
		}
	}
	
	public void render(Graphics g) {
		for(int i = buttons.size(); i > 0; i--) {
			buttons.get(i-1).render(g);
		}
	}
	
	public void registerButton(Button button) {
		button.getWhileDownAnim().run();
		button.getWhileUpAnim().run();
		buttons.add(0, button);
	}
	
	public void unregisterButton(Button button) {
		button.getWhileDownAnim().cancel();
		button.getWhileUpAnim().cancel();
		buttons.remove(button);
	}

	public List<Button> getButtons() {
		return buttons;
	}
	
	public void clear() {
		this.buttons.clear();
	}
	
	public void hideButton(String ID) {
		Button inQuestion = null;
		for(Button each:buttons) {
			if(each.getId() == ID) {
				inQuestion = each;
			}
		}
		if(inQuestion != null) {
			if(!hiddenButtons.contains(inQuestion)) {
				buttons.remove(inQuestion);
				hiddenButtons.add(inQuestion);
			}
		}
	}
	
	public void revealButton(String ID) {
		Button inQuestion = null;
		for(Button each:hiddenButtons) {
			if(each.getId() == ID) {
				inQuestion = each;
			}
		}
		if(inQuestion != null) {
			if(!buttons.contains(inQuestion)) {
				hiddenButtons.remove(inQuestion);
				buttons.add(inQuestion);
			}
		}
	}
}
