package objets.fusee.composants;

import java.awt.Color;  
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import util.Dessinable;
import util.Vecteur;

/**
 * Classe qui permet de dessiner les flammes
 * @author Melie Leclerc
 *
 */

public class Flammes implements Dessinable {//debut classe
	
	//Variables pour creer les flammes
	private final int NB_IMAGES = 30;
	private transient static Image images[] = null;
	private URL url[] = null;
	private int quelleImage=0;
	
	//Variables de la position et des dimensions des flammes
	private double x, y, width, height;

	private double angleDeviation;
	private Vecteur centreFusee;

	/**
	 * Constructeur qui dessine les flammes
	 * @param x La position en X      
	 * @param y La position en Y
	 * @param width La largeur des flammes
	 * @param height La hauteur des flammes
	 * @param angleDeviation L'angle de deviation des flammes
	 * @param centreFusee Le centre de la fusee
	 */
	//Melie L

	public Flammes(double x, double y, double width, double height, double angleDeviation, Vecteur centreFusee) {//debut constructeur
		
		if (images == null) {
			lireLesImages();
		}
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angleDeviation = angleDeviation;
		
		if(centreFusee != null) {
			this.centreFusee = new Vecteur(centreFusee);
		}
		
		
	}//fin constructeur

	/**
	 * Methode qui va permettre le dessinage des flammes 
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie Leclerc

	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		matLocale.scale(1, -1);
		Color cInit = g2d.getColor();

		AffineTransform transfoAvant = g2d.getTransform();
		g2d.setTransform(matLocale);
		g2d.scale(1, 1);

		if(centreFusee != null) {
			g2d.rotate(angleDeviation, centreFusee.getX(), centreFusee.getY());
		}


		g2d.drawImage(images[quelleImage], (int) (x), (int) (y), (int) (width), (int) (height), null);
		quelleImage=(quelleImage+1)%NB_IMAGES;	

		g2d.setColor(cInit);		
		g2d.setTransform(transfoAvant);

	}//fin methode


	/**
	 * Methode qui permet de lire les images dans le tableau "image"
	 */
	//Melie L
	
	private void lireLesImages() {//debut methode
		images = new Image[NB_IMAGES];
		url = new URL[NB_IMAGES];
		for (int k = 0; k < NB_IMAGES; k++) {
			String nomFichier = "frame-" + (k+1) + ".gif";
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
	
}//fin classe
