package model;

public class ErrorObject {
	
	boolean isError;
	String message;
	
	public ErrorObject() {
		isError = false;
		message = "";
	}
	
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}