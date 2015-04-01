package com.epam.wt.dao;

public enum DaoEnum {
	USING_MEMORY("memory"), USING_FILE("file"), USING_XML("xml"),USING_DB("database");
	private String type;

	private DaoEnum(String type) {
		this.type = type;
	}

	static public DaoEnum getType(String type) {
		for (DaoEnum enumType : DaoEnum.values()) {
			if (enumType.getTypeValue().equals(type)) {
				return enumType;
			}
		}
		return null;
	}

	public String getTypeValue() {
		return type;
	}

}
