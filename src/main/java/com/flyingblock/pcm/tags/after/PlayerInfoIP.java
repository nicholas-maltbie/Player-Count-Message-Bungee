package com.flyingblock.pcm.tags.after;

import com.flyingblock.pcm.save.SerFile;
import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PlayerInfoIP
{
    public static final String SAVE_FILE_NAME = "PlayerNameMap.ser";
    
    private static File nameDir;
    private static File uuidDir;
    private static Map<InetAddress, String> playerNames;
    private static Listener playerJoinHandler;
    public static Plugin plugin;
    
    private PlayerInfoIP()
    {
        //you can't do this, hahahahahaha
    }
    
    public static String getPlayerName(InetAddress key)
    {
        return playerNames.get(key);
    }
    
    public static String getPlayerName(ProxiedPlayer player)
    {
        return getPlayerName(player.getAddress().getAddress());
    }
    
    public static void setup(Plugin plugin)
    {
        PlayerInfoIP.plugin = plugin;
        nameDir = new File(plugin.getDataFolder(), SAVE_FILE_NAME);
        SerFile nameSave = new SerFile(nameDir);
        if(!nameDir.exists())
            nameSave.save(new HashMap<>());
        nameSave.read();
        if(!(nameSave.getData() instanceof HashMap))
        {
            nameDir.delete();
            nameSave.save(new HashMap<>());
            nameSave.read();
        }
        playerNames = (Map<InetAddress, String>) nameSave.getData();
        plugin.getLogger().log(Level.INFO, "Enabled the PlayerInfoIP listener");
        playerJoinHandler = new PlayerInfoHandler();
        plugin.getProxy().getPluginManager().registerListener(plugin, playerJoinHandler);
    }
    
    public static void close(Plugin plugin)
    {
        plugin.getProxy().getPluginManager().unregisterListener(playerJoinHandler);
    }
    
    public static class PlayerInfoHandler implements Listener
    {
        @EventHandler
        public void onJoin(PostLoginEvent event)
        {
            playerNames.put(event.getPlayer().getAddress().getAddress(), event.getPlayer().getName());
            SerFile nameSave = new SerFile(nameDir);
            nameSave.save(playerNames);
        }
    }
    
}
