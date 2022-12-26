package com.guru.commands.admin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.utils.ImageUtils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;

@CommandMeta(category = Category.ADMIN, description = "a developer command used to test certain things", name = {"test", "t"}, usage = {"test"}, permission = {"ADMINISTRATOR"})
public class Test extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		int width = 1920;
      	int height = 1080;
 
        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 
        // Create a graphics which can be used to draw into the buffered image
        Graphics2D g = bufferedImage.createGraphics();
 
        // fill all the image with white
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
  
        // Disposes of this graphics context and releases any system resources that it is using. 
        g.dispose();

		event.getChannel().sendFiles(FileUpload.fromData(ImageUtils.toByteArray(bufferedImage, "png"), "a.png")).queue();
		
	}


	
}
