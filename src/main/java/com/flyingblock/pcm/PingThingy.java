/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flyingblock.pcm;

import com.flyingblock.pcm.animation.PingAnimation;
import com.flyingblock.pcm.animation.PingAnimationSave;
import com.flyingblock.pcm.tags.CurrentTag;
import com.flyingblock.pcm.tags.Tag;
import com.flyingblock.pcm.tags.after.PlayerInfoIP;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author Nick_Pro
 */
public class PingThingy implements Listener
{
    private Plugin plugin;
    
    private Tag[] tags = {new CurrentTag()};
    public static int PING_INTERVAL = 100;
    
    private PingAnimationSave save;
    private PingAnimation animation;
    private long lastTime;
    private int millis;
    
    public void enable(Plugin plugin, PingAnimationSave save)
    {
        this.save = save;
        this.plugin = plugin;
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
        plugin.getLogger().log(Level.INFO, "Enabled Ping Listener");
    }
    
    @EventHandler
    public void onPing(ProxyPingEvent event)
    {
        ServerPing response = event.getResponse();
        PingAnimation anim = save.develop(PlayerInfoIP.getPlayerName(event.getConnection().getAddress().getAddress()));
        PingData ping = anim.getPing(0);
        response.setDescription(ping.getMotd());
        response.setFavicon(ping.getFavicon());
        List<String> players = ping.getPlayers();
        List<PlayerInfo> info = new ArrayList<>();
        for(String player : players)
            info.add(new PlayerInfo(player, "0"));
        response.setPlayers(new Players(-1, ProxyServer.getInstance().getOnlineCount(), info.toArray(new PlayerInfo[info.size()])));
    }
    
    public void close()
    {
        plugin.getProxy().getPluginManager().unregisterListener(this);
    }
}
