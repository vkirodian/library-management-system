package com.rapps.utility.learning.lms.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class holding the generic output that the application sends out.
 * 
 * @author vkirodian
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericOutput {

	private OutputStatus outputStatus;
	private Object data;

	public OutputStatus getOutputStatus() {
		return outputStatus;
	}

	public void setOutputStatus(OutputStatus outputStatus) {
		this.outputStatus = outputStatus;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}