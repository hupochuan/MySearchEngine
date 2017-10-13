/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Model;

/**
 *
 * @author Administrator
 */
public class Wenda {

	public Wenda(){
		
	}
    public Wenda( String noun, String belongto, int type) {
		super();
		
		this.noun = noun;
		this.belongto = belongto;
		this.type = type;
	}
	private int id;
    private String noun;
    private String belongto;
    private int type;
	public String getNoun() {
		return noun;
	}
	public void setNoun(String noun) {
		this.noun = noun;
	}
	public String getBelongto() {
		return belongto;
	}
	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getWord() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
