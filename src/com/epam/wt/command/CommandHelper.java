package com.epam.wt.command;

import java.util.HashMap;
import java.util.Map;

import com.epam.wt.command.impl.AddCommand;
import com.epam.wt.command.impl.DeleteAllCommand;
import com.epam.wt.command.impl.DeleteCommand;
import com.epam.wt.command.impl.FindNoteCommand;
import com.epam.wt.command.impl.ShowCommand;
import com.epam.wt.command.impl.SortCommand;



public final class CommandHelper {
	private Map<CommandName, Command> commands = new HashMap<>();

    public CommandHelper(){ 
                    commands.put(CommandName.ADD, new AddCommand());
                    commands.put(CommandName.DETETE, new DeleteCommand());
                    commands.put(CommandName.DELETE_ALL, new DeleteAllCommand());
                    commands.put(CommandName.FIND, new FindNoteCommand());
                    commands.put(CommandName.SORT, new SortCommand());
                    commands.put(CommandName.SHOW, new ShowCommand());
                    
    }
    
    public Command getCommand(CommandName nameCommand){
                    Command command = commands.get(nameCommand);
                    return command;
    }

}
