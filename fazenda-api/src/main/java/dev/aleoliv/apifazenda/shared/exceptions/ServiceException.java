package dev.aleoliv.apifazenda.shared.exceptions;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	public ServiceException(String msg) {
		super(msg);
	}
}
