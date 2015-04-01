package com.epam.wt.command;

public enum CommandName {
	ADD("add"), DETETE("delete"), DELETE_ALL("delete_all"), FIND("find"), SORT(
			"sort"), SHOW("show");

	private String commandType;

	private CommandName(String type) {
		commandType = type;
	}

	static public CommandName getType(String cType) {
		for (CommandName type : CommandName.values()) {
			if (type.getTypeValue().equals(cType)) {
				return type;
			}
		}
		return null;
	}

	public String getTypeValue() {
		return commandType;
	}

}
