package com.flyingblock.pcm.commands;

import com.flyingblock.pcm.animation.PingAnimationSave;
import com.flyingblock.pcm.save.PingAnimationConfig;
import java.util.ArrayList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

public class MyCommandHandler extends CommandHandler
{
    private static PingAnimationConfig config;
    private static PingAnimationSave anim;
    
    private PcmCommand pcm;
    private MotdCommand motd;
    private VersCommand vers;
    private ImgCommand img;
    private PlayCommand play;

    public static void setConfig(PingAnimationConfig config)
    {
        MyCommandHandler.config = config;
    }
    
    public static void setAnimation(PingAnimationSave anim)
    {
        MyCommandHandler.anim = anim;
    }
    
    public MyCommandHandler(Plugin plugin)
    {
        super(plugin);
    }

    @Override
    public void setupCommands(Plugin plugin)
    {
        play = new PlayCommand("play", "pcm.play", new ArrayList<FlyguyCommand>(), anim, config);
        motd = new MotdCommand("motd", "pcm.motd", new ArrayList<FlyguyCommand>(), anim, config);
        vers = new VersCommand("vers", "pcm.vers", new ArrayList<FlyguyCommand>(), anim, config);
        img = new ImgCommand("img", "pcm.img", new ArrayList<FlyguyCommand>(), anim, config);
        ArrayList<FlyguyCommand> subCmds = new ArrayList<>();
        subCmds.add(play);
        subCmds.add(motd);
        subCmds.add(vers);
        subCmds.add(img);
        
        pcm = new PcmCommand("pcm", "pcm.pcm", subCmds, anim, config);
        this.addCommand(pcm);
    }
}
