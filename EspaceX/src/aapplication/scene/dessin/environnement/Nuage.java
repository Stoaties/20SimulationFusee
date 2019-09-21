package aapplication.scene.dessin.environnement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import util.Dessinable;

/**
 * Classe qui dessine un nuage
 * @author Johnatan Gao
 *
 */

public class Nuage implements Dessinable {//debut classe
//Variables qui dessinent le nuage
	private Image img = null;
	
	//Variables de la position d'un nuage
	private double x,y;
	
	//Variables des dimmensions d'un nuage
	private final int WIDTH = 20, HEIGHT = 10;
	
	/**
	 * Constructeur qui permet le dessinage d'un nuage
	 * @param x La position en X d'un nuage
	 * @param y La position en Y d'un nuage
	 */
	//Johnatan G
	
	public Nuage(double x, double y) {//debut constructeur
		
		lireImageURL();
		this.x = x;
		this.y = y;
		
	}//fin constrcuteur
	
	
	/**
	 * Methode qui va permettre le dessinage du nuage
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode
		
		AffineTransform transfoInit = g2d.getTransform();
		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		matLocale.rotate(Math.toRadians(180));
		Composite old = g2d.getComposite();
		g2d.setTransform(matLocale);
		
		float opacity = 0.75f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
		g2d.drawImage(img, (int )-x, (int) -y, this.WIDTH, this.HEIGHT, null); //Je dessine avec -y puisque j'ai rotate le nuage de 180 degree
		
		g2d.setTransform(transfoInit);
		g2d.setColor(cInit);
		g2d.setComposite(old);
		
	}//fin methode
	
	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * 
	 */
	//Johnatan G
	
	private void lireImageURL() {//debut methode
		URL urlImg = getClass().getClassLoader().getResource("nuage.png");

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				img= ImageIO.read(urlImg);
			}
			
			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

	}//fin methode

	/**
	 * Methode qui retourne la position en x d'un nuage
	 * @return x La position en x d'un nuage
	 */
	//Johnatan G

	public double getX() {//debut methode
		return x;
	}//fin methode

	/**
	 * Methode qui modifie la position en x d'un nuage
	 * @param x La position en x d'un nuage
	 */
	//Johnatan G
	
	public void setX(int x) {//debut methode
		this.x = x;
	}//fin methode

	/**
	 * Methode qui retourne la position en y d'un nuage
	 * @return y La position en y d'un nuage
	 */
	//Johnatan G
	
	public double getY() {//debut methode
		return y; 
	}//fin methode

	/**
	 * Methode qui modifie la position en y d'un nuage
	 * @param y La position en y d'un nuage
	 */
	//Johnatan G
	
	public void setY(int y) {//debut methode
		this.y = y;
	}//fin methode

}//fin classe
