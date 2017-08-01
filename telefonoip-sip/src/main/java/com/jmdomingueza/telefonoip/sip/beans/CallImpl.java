package com.jmdomingueza.telefonoip.sip.beans;

public class CallImpl implements Call {

	private static final long serialVersionUID = 4052189764202224153L;

	
	private Count count;
	
	private String number;
	
	private String tagFrom;
	
	private String tagTo;
	
	private String branchVia;
	
	private String contentType;
	
	private String contentSubType;
	
	private Integer sessionDescriptionMode;
	
	private State state;
	
	private Direction direction;

	@Override
	public Count getCount() {
		return count;
	}

	@Override
	public void setCount(Count count) {
		this.count = count;
	}
	
	@Override
	public String getNumber() {
		return number;
	}
	
	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getTagFrom() {
		return tagFrom;
	}

	@Override
	public void setTagFrom(String tagFrom) {
		this.tagFrom = tagFrom;
	}

	@Override
	public String getTagTo() {
		return tagTo;
	}

	@Override
	public void setTagTo(String tagTo) {
		this.tagTo = tagTo;
	}

	@Override
	public String getBranchVia() {
		return branchVia;
	}
	
	@Override
	public void setBranchVia(String branchVia) {
		this.branchVia = branchVia;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getContentSubType() {
		return contentSubType;
	}

	@Override
	public void setContentSubType(String contentSubType) {
		this.contentSubType = contentSubType;
	}

	@Override
	public Integer getSessionDescriptionMode() {
		return sessionDescriptionMode;
	}

	@Override
	public void setSessionDescriptionMode(Integer sessionDescriptionMode) {
		this.sessionDescriptionMode = sessionDescriptionMode;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state;	
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
		
	}	
	
}
