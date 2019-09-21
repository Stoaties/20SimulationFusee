package aapplication.scene.dessin.environnement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import objets.Objet;
import util.Dessinable;

/**
 * Classe qui va dessiner le sol
 * 
 * @author Johnatan Gao
 * 
 */

public class Sol extends Objet implements Dessinable {//debut classe

	//Variables pour dessiner le sol
	private Rectangle2D.Double formeSol;
	private double positionX, positionY;
	private double hauteur, largeur;
	private Image img = null;

	/**
	 * Constructeur de la classe Sol qui va creer un Objet Dessinable de type Sol
	 * @param x La position du sol en x
	 * @param y La position du sol en y
	 * @param w La largeur du sol
	 * @param h La hauteur du sol
	 */
	//Johnatan G

	public Sol(double x, double y, double w, double h) {//debut constructeur
		
		super(5.972E24);
		formeSol = new Rectangle2D.Double(x,y,w,h);
		this.hauteur = h;
		this.largeur = w;
		this.setPositionX(x);
		this.setPositionY(y);

		lireImageURL();

	}//fin constructeur


	/**
	 * Methode qui dessine le plancher
	 * @param g2d Contexte graphique qui va permettre de dessiner les elements
	 * @param aff La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Johnatan G

	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		Color couleurInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(aff);

		this.shapeObjet = matLocale.createTransformedShape(formeSol);	

		g2d.drawImage(img,(int) (shapeObjet.getBounds2D().getX() ) ,(int) shapeObjet.getBounds2D().getY(), (int)(shapeObjet.getBounds2D().getWidth()), (int) (shapeObjet.getBounds2D().getHeight()), null);

		g2d.setColor(couleurInit);

	}//fin methode

	/**
	 * Retourne la position en Y du sol
	 * @return positionY La position en Y du sol
	 */
	//Johnatan G

	public double getPositionY() {//debut methode
		return positionY;
	}//fin methode

	/**
	 * Methode qui modifie la position en Y du sol
	 * @param positionY La position en Y du sol
	 */
	//Johnatan G

	public void setPositionY(double positionY) {//debut methode
		this.positionY = positionY;
	}//fin methode

	/**
	 * Methode qui retourne la position en X du sol
	 * @return positionX La position en X du sol
	 */
	//Johnatan G

	public double getPositionX() {//debut methode
		return positionX;
	}//fin methode

	/**
	 * Methode qui modifie la position en X du sol
	 * @param positionX La position en X du sol
	 */
	//Johnatan G

	public void setPositionX(double positionX) {//debut methode
		this.positionX = positionX;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur du sol
	 * @return hauteur La hauteur du sol
	 */
	//Johnatan G

	public double getHauteur() {//debut methode
		return hauteur;
	}//fin methode

	/**
	 * Methode qui modifie la hauteur du sol
	 * @param hauteur La hauteur du sol
	 */
	//Johnatan G

	public void setHauteur(double hauteur) {//debut methode
		this.hauteur = hauteur;
	}//fin methode

	/**
	 * Retourne la largeur du sol
	 * @return largeur  La largeur du sol
	 */
	//Johnatan G

	public double getLargeur() {//debut methode
		return largeur;
	}//fin methode

	/**
	 * Methode qui modifie la largeur du sol
	 * @param largeur La largeur du sol
	 */
	//Johnatan G

	public void setLargeur(double largeur) {//debut methode
		this.largeur = largeur;
	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 */
	//Johnatan G

	private void lireImageURL() {//debut methode
		URL urlImg = getClass().getClassLoader().getResource("sol.jpg");

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

}//fin classe
