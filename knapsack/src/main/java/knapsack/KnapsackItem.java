package knapsack;

public class KnapsackItem {
    
    private int weigth;
    private int value;
    
    public KnapsackItem(int weigth, int value) {
	
	setWeigth(weigth);
	setValue(value);
	
    }

    public int getWeigth() {
	return weigth;
    }

    public void setWeigth(int weigth) {
	this.weigth = weigth;
    }

    public int getValue() {
	return value;
    }

    public void setValue(int value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return "{weigth="+this.weigth+",value="+this.value+"}";
    }
    
}
