package com.auburn.comp3710.serializable;

/**
 * Created by Robby Pocase on 4/7/2014.
 */

public class VerticalScroll extends BluetoothSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    //indicates the number of scroll clicks that should occur
    private int scrollClicks;

    public VerticalScroll() { super(); }

    public VerticalScroll(int scrollClicks) {
        this.scrollClicks = scrollClicks;
    }

    public void setScrollClicks(int scrollClicks) {
        this.scrollClicks = scrollClicks;
    }

    public int getScrollClicks() {
        return scrollClicks;
    }


}
