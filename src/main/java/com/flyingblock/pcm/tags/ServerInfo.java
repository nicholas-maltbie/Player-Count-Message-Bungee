/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flyingblock.pcm.tags;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 *
 * @author Nicholas
 */
public class ServerInfo 
{
    public static boolean includeStaff = false;
    private static ProxyServer server;
    
    public static final String PCM_STAFF_PERM = "pcm.staff";
    
    private static final String[] dummyPlayers = {"Escmo", "Flyguy23ndm"};
    private static final String[] staff = {"Staff1"};
    
    public static String[] getOnlinePlayerNames()
    {
        try{
            List<String> names = new ArrayList<>();
            for(ProxiedPlayer player : server.getPlayers()) {
                if(player.hasPermission(PCM_STAFF_PERM))
                {
                    if(includeStaff)
                        names.add(player.getName());
                }
                else
                    names.add(player.getName());
            }
            return names.toArray(new String[names.size()]);
        }
        catch (NullPointerException e)
        {
            return dummyPlayers;
        }
    }
    
    public static int getPlayersOnline()
    {
        return ProxyServer.getInstance().getOnlineCount();
    }
    
    public static void setup(ProxyServer server)
    {
        ServerInfo.server = server;
    }
    
    public static String[] getOnlineStaffNames()
    {
        try {
            List<String> names = new ArrayList<>();
            for(ProxiedPlayer player : server.getPlayers()) {
                if(player.hasPermission(PCM_STAFF_PERM))
                    names.add(player.getName());
            }
            return names.toArray(new String[names.size()]);
        }
        catch (NullPointerException e)
        {
            return staff;
        }
    }
    
}
