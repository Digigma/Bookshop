package bookshop;

import java.awt.EventQueue;

public class LaunchBookshop {
	
	//main method to launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookshopMain frame = new BookshopMain();
					frame.setVisible(true);
				} catch (Exception e) {e.printStackTrace();}
			}
		});
	}
}
