package com.flyingblock.pcm;

import com.flyingblock.pcm.tags.Tag;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class PingData extends ServerData
{
    public static int PING_INTERVAL = 100;
    private BufferedImage image;
    
    public PingData(String playerCount, String motd, File imagePath, List<String> players) throws IOException
    {
        super(motd.split("\n")[0], motd.split("\n")[1], ImageIO.read(imagePath), PING_INTERVAL, playerCount, players);
        image = ImageIO.read(imagePath);
    }
    
    public PingData(String playerCount, String motd, BufferedImage image, List<String> players) {
        super(motd.split("\n")[0], motd.split("\n")[1], image, PING_INTERVAL, playerCount, players);
    }
    
    public PingData getAppliedTo(Tag tag)
    {
        List<String> players = getPlayers();
        for(int i = 0; i < players.size(); i++)
        {
            players.set(i, tag.applyTo(players.get(i)));
        }
        String motd = tag.applyTo(this.getMotd());
        String version = tag.applyTo(this.getFormat());
        return new PingData(version, motd, image, players);
        //return new PingData(version, motd, this.getFavicon(), players);
    }

}
