package aapplication.scene.dessin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import aapplication.App20SimulationFusee;

/**
 * Classe qui va dessiner la page d'Accueil
 * @author Melie Leclerc
 *
 */

public class DessinAccueil extends JPanel implements Runnable {//debut classe

	private static final long serialVersionUID = 1L;

	////////////////////////////////////////////////////////////COMPOSANTS QUI INDIQUENT A L'UTILISATEUR LA NATURE DES CHANGEMENTS

	private JLabel lblSimulation;

	////////////////////////////////////////////////////////////COMPOSANT PERSONNEL SUR LEQUEL AFFICHE l'APPLICATION PRINCIPALE

	private App20SimulationFusee fenApplication;

	////////////////////////////////////////////////////////////COMPOSANTS D'ACTIVATION

	private JButton btnCommencer;
	private JButton btnQuitter;

	//Variables qui gerent les images gif
	private final int NB_IMAGES = 9;
	private Image images[] = null;
	private URL url[] = null;
	private int quelleImage=0;

	//Variable qui gere si la page d'accueil est ouverte
	private boolean enAnimation = false;

	/**
	 * Constructeur qui permet de dessiner la page d'Accueil
	 */
	//Melie L

	public DessinAccueil() {//debut constructeur

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		if (images == null) {
			lireLesImages();
		}
		setLayout(null);
		
		lblSimulation = new JLabel("Simulation d'une fusee");
		lblSimulation.setBounds((int)(screen.getWidth()/15), (int)(screen.getHeight()/14), (int)(screen.getWidth()/2), (int)(screen.getHeight()/15));
		lblSimulation.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimulation.setFont(new Font("Courier", Font.PLAIN, 66));
		add(lblSimulation);

		btnCommencer = new JButton("C'est parti !");
		btnCommencer.setBounds((int)(screen.getWidth()/2), (int)(screen.getHeight()/5), (int)(screen.getWidth()/10), (int)(screen.getHeight()/25));
		btnCommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//debut 
				fenApplication = new App20SimulationFusee();
				fenApplication.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				fenApplication.setUndecorated(true);
				fenApplication.setVisible(true);
				//fin
			}

		});
		btnCommencer.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(btnCommencer);

		btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds((int)(screen.getWidth()/2), (int)(screen.getHeight()/3), (int)(screen.getWidth()/10), (int)(screen.getHeight()/25));
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//debut 
				System.exit(0);
				//fin
			}

		});
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(btnQuitter);

	}//fin constructeur

	/**
	 * Methode qui va dessiner la fusee construite
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Melie L

	public void paintComponent(Graphics g) {//debut methode

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(images[quelleImage], 0, 0, getWidth(), getHeight(), null);


	}//fin methode

	/**
	 * Methode qui change l'image gif
	 */
	//Melie L

	private void changerImage() {//debut methode

		quelleImage=(quelleImage+1)%NB_IMAGES;
		repaint();

	}//fin methode

	/**
	 * Methode qui permet de lire les images dans le tableau "image"
	 */
	//Melie L

	private void lireLesImages() {//debut methode


		images = new Image[NB_IMAGES];
		url = new URL[NB_IMAGES];
		for (int k = 0; k < NB_IMAGES; k++) {
			String nomFichier = "fusee-" + (k+1) + ".gif";
			url[k] = getClass().getClassLoader().getResource(nomFichier);
			if (url[k] == null) {
				System.out.println("lireLesImages: incapable de lire le fichier d'image " + nomFichier);
				return; 
			}
		}
		for (int k = 0; k < NB_IMAGES; k++) {
			try {
				images[k] = ImageIO.read(url[k]);
			} 
			catch (IOException e) {
				System.out.println("IOException lors de la lecture avec ImageIO");
			}	
		}
	}//fin methode

	/**
	 * Methode qui va effectuer l'animation en utilisant un thread tout en mettant les forces en jeu a jour
	 */
	//Melie L

	public void run() {//debut methode

		while(enAnimation) {

			changerImage();

			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}//fin methode

	/**
	 * Methode qui permet de demarrer le thread si celui-ci n'est pas encore demarre
	 * 
	 */
	//Melie L
	
	public void demarrer() {//debut methode

		Thread t = new Thread(this);
		t.start();

		this.enAnimation = true;

	}//fin methode

}//fin classe
