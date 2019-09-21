package objets.celeste;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import util.Dessinable;

/**
 * Classe de planete derivant de la classe ObjetCeleste.
 * @author Corentin Gouanvic
 * @author Ivana Bera
 *
 */
public class Planete extends ObjetCeleste implements Dessinable,Serializable{
	
	private TexturePaint texturePlanete;
	
	/**
	 * Constructeur de planete.
	 * @param rayon			   Rayon de la planete.
	 * @param masse			   Masse de la planete.
	 * @param rayonOrbite	   Rayon de l'orbite.
	 * @param rotationInitiale La rotation initiale
	 */
	//Corentin
	public Planete(double rayon, double masse, double rayonOrbite, double rotationInitiale) {
		super(rayon, masse, rayonOrbite, rotationInitiale);
		shapeObjet = new Ellipse2D.Double(position.getX(), position.getY(), rayon*2, rayon*2);
	}
	
	
	/**
	 * Constructeur de planete.
	 * @param nom Le nom
	 * @param rayon			Rayon de la planete.
	 * @param masse			Masse de la planete.
	 * @param rayonOrbite	Rayon de l'orbite.
	 * @param rotationInitiale La rotation initiale
	 */
	//Corentin
	public Planete(String nom, double rayon, double masse, double rayonOrbite, double rotationInitiale) {
		super(rayon, masse, rayonOrbite, rotationInitiale);
		shapeObjet = new Ellipse2D.Double(position.getX(), position.getY(), rayon*2, rayon*2);
		this.nom = nom;
	}
	
	/**
	 * Constructeur de planete avec texture
	 * @param rayon Rayon de la planete
	 * @param masse Masse de la planete
	 * @param rayonOrbite Rayon de l'orbite
	 * @param rotationInitiale Rotation initiale
	 * @param imgTexture Texture de la planete
	 */
	//Corentin
	public Planete(double rayon, double masse, double rayonOrbite, double rotationInitiale, BufferedImage imgTexture) {
		super(rayon, masse, rayonOrbite, rotationInitiale);
		shapeObjet = new Ellipse2D.Double(position.getX(), position.getY(), rayon*2, rayon*2);
		setTexture(imgTexture);
	}

	
	/**
	 * Methode de dessin pour la planete.
	 */
	//Corentin
	public void draw(Graphics2D g2d, AffineTransform at) {
		AffineTransform atTemp = new AffineTransform(at);
		TexturePaint textureTemp = texturePlanete;
		g2d.setPaint(textureTemp);
		shapeObjet = new Ellipse2D.Double(position.getX()-rayon, position.getY()-rayon, rayon*2, rayon*2);
		g2d.fill(at.createTransformedShape(shapeObjet));
		at = atTemp;
	}
	
	/**
	 * Methode qui transforme une image en texture
	 * @param imageTexture image de la texture de la planete
	 */
	//Ivana
	public void setTexture(BufferedImage imageTexture) {
		Rectangle2D rectangleTexture = new Rectangle2D.Double(0, 0, imageTexture.getWidth(),	imageTexture.getHeight());
		texturePlanete = new TexturePaint(imageTexture, rectangleTexture);
	}
	
}
