package com.flyingblock.pcm.commands;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

abstract public class FlyguyCommand extends Command
{
    private ArrayList<FlyguyCommand> subCommands;

    public FlyguyCommand(String command, String permission, ArrayList<FlyguyCommand> subCommands)
    {
        super(command, permission);
        this.subCommands = subCommands;	
    }

    public void addSubCommand(FlyguyCommand cmd)
    {
            subCommands.add(cmd);
    }

    public String getCommand()
    {
        return super.getName();
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(getPermission() == null || sender.hasPermission(getPermission()))
        {
            for(FlyguyCommand subCmd : subCommands)
            {
                if(args.length > 0 && subCmd.getCommand().equalsIgnoreCase(args[0]))
                {
                    String[] newArgs = new String[args.length-1];
                    for(int i = 0; i < newArgs.length; i++)
                        newArgs[i] = args[i+1];
                    subCmd.execute(sender, newArgs);
                    return;
                }
            }
            doCommand(sender,args);			
        }
    }

    public List<String> getValidArguments(CommandSender sender, String[] args)
    {
            for(FlyguyCommand subCmd : subCommands)
            {
                    String[] newArgs = new String[args.length-1];
                    for(int i = 0; i < newArgs.length; i++)
                            newArgs[i] = args[i+1];
                    if(args[0].equalsIgnoreCase(subCmd.getCommand()) && (subCmd.getPermission() == null || sender.hasPermission(subCmd.getPermission())))
                            return subCmd.onTabComplete(sender, newArgs);
            }
            ArrayList<String> tabs = new ArrayList<>();
            for(FlyguyCommand subCmd : subCommands)
                    if(subCmd.getPermission() == null || sender.hasPermission(subCmd.getPermission()))
                            tabs.add(subCmd.getCommand());
            tabs.addAll(onTabComplete(sender, args));

            int i = 0;
            while(i < tabs.size())
            {
                    String tab = tabs.get(i);
                    if(args[args.length-1].length() > tab.length())
                            tabs.remove(tab);
                    else if(!tab.substring(0, args[args.length-1].length()).equalsIgnoreCase(args[args.length-1]))
                            tabs.remove(tab);
                    else
                            i++;
            }
            return tabs;
    }

    abstract public void doCommand(CommandSender sender, String[] args);
    abstract public List<String> onTabComplete(CommandSender sender, String[] args);
}
