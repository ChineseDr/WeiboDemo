package com.iotek.myweibo.entity;

public class SelfItem {
	/*个人界面listview每一项*/
	private boolean isShowTopDivider;//分割线
	private int leftImg;//小图标
	private String subhead;//标题
	private String caption;//说明
	public SelfItem(boolean isShowTopDivider, int leftImg, String subhead,
			String caption) {
		this.isShowTopDivider = isShowTopDivider;
		this.leftImg = leftImg;
		this.subhead = subhead;
		this.caption = caption;
	}
	
	public boolean isShowTopDivider() {
		return isShowTopDivider;
	}
	
	public void setShowTopDivider(boolean isShowTopDivider) {
		this.isShowTopDivider = isShowTopDivider;
	}
	
	public int getLeftImg() {
		return leftImg;
	}
	
	public void setLeftImg(int leftImg) {
		this.leftImg = leftImg;
	}
	
	public String getSubhead() {
		return subhead;
	}
	
	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	

}
