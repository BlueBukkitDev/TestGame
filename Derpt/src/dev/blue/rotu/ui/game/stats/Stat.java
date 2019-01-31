package dev.blue.rotu.ui.game.stats;

public abstract class Stat {
	protected String ID;
	protected double value;
	protected double increase;
	protected double decrease;
	protected double max;
	protected double min;
	
	/**
	 * <p>
	 * <strong>Parameters:</strong></br>
	 * <strong>String id </strong>(an ID by which this stat can be identified)</br>
	 * <strong>int increase </strong>(the amount that the value will increase per tick)</br>
	 * <strong>int decrease </strong>(the amount that the value will decrease per tick)</br>
	 * <strong>int max </strong>(the number that represents the limit of growth in value)</br>
	 * <strong>int min </strong>(the number that represents the limit of decline in value)</br>
	 * <strong>int start </strong>(the initial value, limited to within the max and the min values)</br>
	 * </p>
	 **/
	
	public Stat(String ID, double increase, double decrease, double max, double min, double start) {
		this.ID = ID;
		this.increase = increase;
		this.decrease = decrease;
		this.max = max;
		this.min = min;
		this.value = start;
	}
	
	public abstract void onMinimum();
	public abstract void onMaximum();
	public abstract void onIncrease();
	public abstract void onDecrease();

	public String getID() {
		return ID;
	}

	public double getValue() {
		return value;
	}

	public double getIncrease() {
		return increase;
	}

	public double getDecrease() {
		return decrease;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setIncrease(double increase) {
		this.increase = increase;
	}

	public void setDecrease(double decrease) {
		this.decrease = decrease;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public void setMin(double min) {
		this.min = min;
	}
	
}
