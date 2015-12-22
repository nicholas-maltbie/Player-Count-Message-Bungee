/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flyingblock.pcm.tags.after;

import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author Nick_Pro
 */
public class PlayerAfterTag extends AfterTag
{
    public static String guestName = "Guest";
    public static final String USER_TAG = "%user%";
    
    public static final String[] PLAYER_AFTER_TAGS = {USER_TAG};
    
    public PlayerAfterTag()
    {
        super(Arrays.asList(PLAYER_AFTER_TAGS));
    }

    @Override
    public String applyTo(String str, String player) 
    {
        if(player == null)
            player = guestName;
        
        while(this.containsTarget(str))
        {
            if(str.contains(USER_TAG))
                str = str.replaceFirst(USER_TAG, player);
        }
        return str;
    }

    @Override
    public boolean reset() 
    {
        return false;
    }

    @Override
    public boolean removeLine(String str) 
    {
        return false;
    }   
}
