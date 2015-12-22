package com.flyingblock.pcm.commands;

import com.flyingblock.pcm.PingHandler;
import com.flyingblock.pcm.animation.PingAnimationSave;
import com.flyingblock.pcm.save.PingAnimationConfig;
import com.flyingblock.pcm.tags.ServerInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class PcmCommand extends FlyguyCommand
{
    public static final String[][] HELP_MESSAGES = {
        {"&fPlayer Count Message Commands&b:",
        "  &6/pcm play [args] &4| &rcommand for editing the player list",
        "  &6/pcm motd [args] &4| &rcommand for editing the motd",
        "  &6/pcm vers [args] &4| &rcommand for editing the version text",
        "  &6/pcm img [args]  &4| &rcommand for editing the server icon",
        "  &6/pcm reload      &4| &rreloads all parts of plugin",
        "  &6/pcm save        &4| &rsaves all part of plugin",
        "&fEach sub command has a respective help command",
        "To view page 2, type &b/pcm help 2"},
        
        {"&fPlayer Count Message Commands&b:",
        "  &6/pcm pinginterval (interval) &4| &rsets the interval (ms) or shows the interval if no argument if provided for (interval)",
        "  &6/pcm length (length) &4| &rsets the animation length (ms) or shows the length if no argument is provided for (length)",
        "  &6/pcm showstaff (true/false) &4| &rSets whether or not to display the staff with %player% tag or checks the current value"
            + "if no argument is provied for (true/false)",
        "  &6/pcm world (world) &4| &rsets world to check for game tags (%ghours%, %weather%...) or views the world if there is no argument",
        "  &6/pcm guest (name) &4| &rsets default guest name or views the current name if there is no argument",
        "To view page 2, type &b/pcm motd help 2"}};
    
    public static final String[] arguments = {"play", "motd", "vers", "img", "reload", "save", "help", "pinginterval", "length", "showstaff"};

    private PingAnimationConfig config;
    private PingAnimationSave anim;

    public PcmCommand(String command, String permission, ArrayList<FlyguyCommand> subCommands, PingAnimationSave anim, PingAnimationConfig config)
    {
        super(command, permission, subCommands);
        this.anim = anim;
        this.config = config;
    }

    @Override
    public void doCommand(CommandSender sender, String[] args)
    {
        if(args.length == 0)
            sender.sendMessage("That isn't a registered command in PlayerCountMessage, try /pcm help for help");
        else if(args[0].equalsIgnoreCase("reload"))
            loadFromConfig(sender, args);
        else if(args[0].equalsIgnoreCase("save"))
            saveConfig(sender, args);
        else if(args[0].equalsIgnoreCase("help"))
            sendHelp(sender, args);
       /* else if(args[0].equalsIgnoreCase("pinginterval"))
        {
            if(args.length == 2)
            {
                try {
                    int interval = Integer.parseInt(args[1]);
                    PingHandler.PING_INTERVAL = interval;
                    sender.sendMessage("Set ping interval to " + interval + " ms");
                }
                catch (NumberFormatException e) {
                    sender.sendMessage("the computer didn't like your number, please try again");
                }
            }
            else
                sender.sendMessage("Ping interval is: " + PingHandler.PING_INTERVAL + " ms");
        }*/
        else if(args[0].equalsIgnoreCase("length"))
        {
            if(args.length == 2)
            {
                try {
                    int length = Integer.parseInt(args[1]);
                    anim.setLength(length);
                    sender.sendMessage("Set animation length to " + length + " ms");
                }
                catch (NumberFormatException e) {
                    sender.sendMessage("the computer didn't like your number, please try again");
                }
            }
            else
                sender.sendMessage("Animation length is: " + anim.getLength() + " ms");
        }
        else if(args[0].equalsIgnoreCase("showstaff"))
        {
            if(args.length == 2 && (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")))
            {
                boolean state = args[1].equalsIgnoreCase("true");
                ServerInfo.includeStaff = state;
                sender.sendMessage("Set include staff to " + state);
            }
            else if(args.length == 2)
            {
                sender.sendMessage("The computer didn't like that boolean, try again wtih \"true\" or \"false\"");
            }
            else
                sender.sendMessage("Show staff is currently: " + ServerInfo.includeStaff);
        }
        else
            sender.sendMessage("That isn't a registered command in PlayerCountMessage, try /pcm help for help");
    }
    
    private boolean loadFromConfig(CommandSender sender, String[] args)
    {
        config.fullReload();
        reloadMessage();
        sender.sendMessage("Reloaded the config");
        return true;
    }
    
    private boolean saveConfig(CommandSender sender, String[] args)
    {
        config.saveToConfig();
        sender.sendMessage("Saved current message to the config");
        return true;
    }

    private boolean sendHelp(CommandSender sender, String[] args)
    {
        if(args.length == 1)
            for(String line : HELP_MESSAGES[0])
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        else if(args.length == 2)
        {
            try {
                int lineNum = Integer.parseInt(args[1]);
                for(String line : HELP_MESSAGES[lineNum-1])
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            catch (NumberFormatException e) {
                sender.sendMessage("the computer didn't like your number, please try again");
            }
            catch (IndexOutOfBoundsException e) {
                sender.sendMessage("that help page (" + args[1] + ") doesn't exist");
            }
        }
        return true;
    }

    public void reloadMessage()
    {
        config.reloadConfig();
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) 
    {
        if(args.length < 2)
            return Arrays.asList(arguments);
        return new ArrayList<>();
    }
}
