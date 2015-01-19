package com.auburn.comp3710.serializable;

/**

 * Created by Robby Pocase  on 4/7/2014.
 */


public class KeyboardPress extends BluetoothSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int characterCode;
	
	public KeyboardPress() {
		super();
	}
	
	public KeyboardPress(int characterCode) {
		super();
		this.characterCode = characterCode;
	}
	
	public void setCharacterCode(int characterCode) {
		this.characterCode = characterCode;
	}
	
	public int getCharacterCode() {
		return this.characterCode;
	}

}
