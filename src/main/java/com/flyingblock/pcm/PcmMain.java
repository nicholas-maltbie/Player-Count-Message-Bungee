/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flyingblock.pcm;

import com.flyingblock.pcm.animation.PingAnimationSave;
import com.flyingblock.pcm.commands.MyCommandHandler;
import com.flyingblock.pcm.save.PingAnimationConfig;
import com.flyingblock.pcm.tags.ServerInfo;
import com.flyingblock.pcm.tags.after.PlayerInfoIP;
import net.md_5.bungee.api.plugin.Plugin;

/**
 *
 * @author Nicholas
 */
public class PcmMain extends Plugin
{
    private static Plugin plugin;
    private static PingAnimationConfig config;
    private PingAnimationSave saveAnimation;
    private MyCommandHandler cmdHandler;
    private PingThingy thingy;
    
    @Override
    public void onEnable()
    {
        config = new PingAnimationConfig(this);
        saveAnimation = config.getPingAnimation();
        PlayerInfoIP.setup(this);
        ServerInfo.setup(this.getProxy());
        MyCommandHandler.setAnimation(saveAnimation);
        MyCommandHandler.setConfig(config);
        cmdHandler = new MyCommandHandler(this);
        PcmMain.plugin = this;
        thingy = new PingThingy();
        thingy.enable(this, saveAnimation);
    }
    
    @Override
    public void onDisable()
    {
        PlayerInfoIP.close(this);
        cmdHandler.close(plugin);
        config.saveToConfig();
        config.saveConfig();
        thingy.close();
    }
    
}
