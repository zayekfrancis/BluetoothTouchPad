package com.auburn.comp3710.serializable;

/**
 * Created by Robby Pocase on 4/7/2014.
 */


public class MouseClickRelease extends BluetoothSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private int buttonMask;

    public MouseClickRelease() { super(); };

    public MouseClickRelease(int buttonMask) {
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
