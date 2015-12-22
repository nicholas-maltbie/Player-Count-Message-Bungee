package com.flyingblock.pcm.commands;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.event.EventHandler;

abstract public class CommandHandler implements Listener
{
    private ArrayList<FlyguyCommand> commands = new ArrayList<FlyguyCommand>();
    private Plugin plugin;
    
    public CommandHandler(Plugin plugin)
    {
        this.plugin = plugin;
        setupCommands(plugin);
        setup(plugin);
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    abstract public void setupCommands(Plugin plugin);

    public void addCommand(FlyguyCommand cmd)
    {
        commands.add(cmd);
    }
	
    public void close(Plugin plugin)
    {
        for(FlyguyCommand flyCmd : commands)
        {
            plugin.getProxy().getPluginManager().unregisterCommand(flyCmd);
        }
        plugin.getProxy().getPluginManager().unregisterListener(this);
    }
	
    public final void setup(Plugin plugin)
    {
        for(FlyguyCommand flyCmd : commands)
        {
            plugin.getProxy().getPluginManager().registerCommand(plugin, flyCmd);
        }
    }
    
    @EventHandler
    public void onTab(TabCompleteEvent e)
    {
        List<String> completions = e.getSuggestions();
        if(!(e.getCursor().split(" ").length > 1) || !(e.getSender()instanceof CommandSender))
            return;
        String cmd = e.getCursor().split(" ")[0];
        String[] args = new String[e.getCursor().split(" ").length -1];
        for(int i = 1; i < args.length; i++)
            args[i-1] = e.getCursor().split(" ")[i];
        for(FlyguyCommand command : commands)
        {
            if(command.getCommand().equals(e))
                completions.addAll(command.getValidArguments((CommandSender)e.getSender(), args));
        }
    }
}
