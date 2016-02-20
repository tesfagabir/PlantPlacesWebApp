package com.plantplaces.imageprocessor;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImagePostProcessor implements Runnable{

	public static void main(String[] args) {
		//Create an instance of the object and run it in a new thread
		ImagePostProcessor imageProcessor = new ImagePostProcessor();
		Thread imageThread = new Thread(imageProcessor);
		imageThread.start();

	}

	@Override
	public void run() {
		Connection connection = null;
		try {
			ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = jmsConnectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue photoQueue = session.createQueue("photos");
			MessageConsumer consumer = session.createConsumer(photoQueue);
			
			//infinite loop that listens for messages
			while(true){
				TextMessage message = (TextMessage)consumer.receive();
				String payload = message.getText();
				
				//DO PHOTO PROCESSING HERE...
				//Create a file that represents the image that was uploaded
				File file = new File(payload);
				//The directory where the watermark exists
				String path = "/home/qiime/Downloads/JavaFullStackWeb/MyJavaFullStackEnterpriseWeb";
				path += "/PlantPlaces/WebContent/resources/images";
				File directory = new File(path);
				//Get the watermark
				BufferedImage watermark = ImageIO.read(new File(directory, "watermark.png"));
		 		//Use Thumbnailator to add the watermark to the image.
		 		Thumbnails.of(file).scale(1).watermark(Positions.BOTTOM_RIGHT, watermark, 0.9f).toFile(file);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("I'm here");
		}
		
		if(connection!=null){
			try {
				connection.stop();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
