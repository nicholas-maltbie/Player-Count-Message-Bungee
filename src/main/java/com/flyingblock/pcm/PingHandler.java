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

/**
 *
 * @author Nick_Pro
 */
public class PingHandler //implements StatusListener
{
   /* private Tag[] tags = {new CurrentTag()};
    public static int PING_INTERVAL = 100;
    
    private static PingAnimationSave save;
    private PingAnimation animation;
    private long lastTime;
    private int millis;
    
    public static void setup(PingAnimationSave save)
    {
        PingHandler.save = save;
        PingManager.setStopAfter(save.getLength()/1000+1);
    }
    
    public PingHandler()
    {
        super();
        lastTime = System.currentTimeMillis();
        animation = save.develop(PingThingy.getLatestPing());
    }
    
    @Override
    public ServerData update()
    {
        millis += System.currentTimeMillis() - lastTime;
        
        PingData data;
        if(animation.isOver(millis) || millis/1000+2> PingManager.getStopAfter())
            data = animation.getLastPing();
        else
            data = animation.getPing(millis);
        
        lastTime = System.currentTimeMillis();
        for(Tag t : tags)
        {
            data = data.getAppliedTo(t);
        }
        return data;
    }
    */
}
