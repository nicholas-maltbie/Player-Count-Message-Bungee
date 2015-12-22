/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flyingblock.pcm.tags;

import java.util.Arrays;
import java.util.Calendar;
import net.md_5.bungee.api.ProxyServer;

/**
 *  
 * @author Nick_Pro
 */
public class CurrentTag extends Tag
{
    public static String world = "world";
    
    public static final String HOURS = "%hour%";
    public static final String HOURS_24 = "%hour24%";
    public static final String MINUTE = "%minute%";
    public static final String SECOND = "%second%";
    public static final String MILLISECOND = "%millis%";
    public static final String AM_PM = "%ampm%";
    
    
    public CurrentTag()
    {
        super(Arrays.asList(HOURS, HOURS_24, MINUTE, SECOND, MILLISECOND, AM_PM));
    }
    
    @Override
    public String applyTo(String str)
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int hour12 = hour % 12;
        String ampm = "am";
        if(hour >= 12)
            ampm = "pm";
        if(hour == 0)
            hour = 12;
        if(hour12 == 0)
            hour12 = 12;
        
        while(this.containsTarget(str))
        {
            if(str.contains(HOURS))
                str = str.replaceFirst(HOURS, Integer.toString(hour12));
            else if(str.contains(HOURS_24))
                str = str.replaceFirst(HOURS_24, Integer.toString(hour));
            else if(str.contains(MINUTE))
                str = str.replaceFirst(MINUTE, Integer.toString(c.get(Calendar.MINUTE)));
            else if(str.contains(SECOND))
                str = str.replaceFirst(SECOND, Integer.toString(c.get(Calendar.SECOND)));
            else if(str.contains(MILLISECOND))
                str = str.replaceFirst(MILLISECOND, Integer.toString(c.get(Calendar.MILLISECOND)));
            else if(str.contains(AM_PM))
                str = str.replaceFirst(AM_PM, ampm);
        }
        return str;
    }
    
    @Override
    public boolean removeLine(String str)
    {
        return false;
    }
    
    @Override
    public boolean reset()
    {
        return false;
    }
    
}
