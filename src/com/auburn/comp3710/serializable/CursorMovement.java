package com.auburn.comp3710.serializable;

/**

 * Created by Robby Pocase  on 4/7/2014.
 */


public class CursorMovement extends BluetoothSerializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float xValue;
    private float yValue;

    public CursorMovement() {
    	super();
    }

    public CursorMovement(float xDirection, float yDirection) {
    	super();
        this.xValue = xDirection;
        this.yValue = yDirection;
    }
    
    public String toString() {
    	return "X Value: " + xValue + "\nY Value: " + yValue + "\n";
    }

    public float getxValue() {
        return xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }
    
    public static void main(String[] args) {
    	CursorMovement movement = new CursorMovement((float)12.123, (float)123.123);
    	System.out.println(movement);
    }

}
