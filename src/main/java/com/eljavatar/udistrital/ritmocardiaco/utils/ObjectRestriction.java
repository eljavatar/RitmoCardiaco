package com.eljavatar.udistrital.ritmocardiaco.utils;

import java.io.Serializable;

public class ObjectRestriction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8085870613201849762L;

	private String parameterName;
	private RelationalOperatorJpaEnum operator;
	private Object[] values;
	
	public ObjectRestriction(String parameterName, RelationalOperatorJpaEnum operator, Object... values) {
		super();
		this.parameterName = parameterName;
		this.operator = operator;
		this.values = values;
	}
	
	public ObjectRestriction(String parameterName, RelationalOperatorJpaEnum operator) {
		this(parameterName, operator, (Object[]) null);
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public RelationalOperatorJpaEnum getOperator() {
		return operator;
	}

	public void setOperator(RelationalOperatorJpaEnum operator) {
		this.operator = operator;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object... values) {
		this.values = values;
	}
	
}
