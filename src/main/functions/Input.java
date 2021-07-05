package main.functions;

/**
 * @author  Mingeon Sung
 * date:    12/20/2020
 */

public class Input {

    private int index;
    private double length;
    private int quantity;

    public Input(int index) {
        this(index, 0, 0);
    }

    public Input(int num, double length, int quantity) {
        this.index = num;
        this.length = length;
        this.quantity = quantity;
    }

    public String getIndex() {
        return Integer.toString(index);
    }
    public void setIndex(String index) {
        this.index = Integer.parseInt(index);
    }
    public String getLength() {
        if(length == 0)
            return "";
        return Double.toString(length);
    }
    public void setLength(String length) {
        if(!length.equals("")) {
            try {
                this.length = Double.parseDouble(length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public String getQuantity() {
        if(quantity == 0)
            return "";
        return Integer.toString(quantity);
    }
    public void setQuantity(String quantity) {
        if(!quantity.equals("")) {
            try {
                this.quantity = Integer.parseInt(quantity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String toString() {
        return index + "- " + length + ", " + quantity;
    }
}
