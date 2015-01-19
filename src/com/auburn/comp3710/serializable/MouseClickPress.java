package com.auburn.comp3710.serializable;

/**
 * Created by Robby Pocase on 4/7/2014.
 */


public class MouseClickPress extends BluetoothSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    //This is a combination of integer mask values as defined in the Robot api
    private int buttonMask;

    public MouseClickPress() { super(); };

    public MouseClickPress(int buttonMask) {
        super();
        this.buttonMask = buttonMask;
    }

    public void setButtonMask(int buttonMask) {
        this.buttonMask = buttonMask;
    }

    public int getButtonMask() {
        return buttonMask;
    }

}
