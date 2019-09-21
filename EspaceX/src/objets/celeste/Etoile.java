package objets.celeste;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import util.Dessinable;

/**
 * Classe qui instancie une etoile
 * @author Corentin
 */

public class Etoile extends ObjetCeleste implements Dessinable, Serializable{

	/**
	 * Constructeur de base de l'etoile.
	 * @param rayon Rayon de l'etoile (m)
	 * @param masse Masse de l'etoile (kg)
	 */
	public Etoile(double rayon, double masse) {
		super(rayon, masse);
		shapeObjet = new Ellipse2D.Double(position.getX(), position.getY(), rayon*2, rayon*2);
	
	}
	
	/**
	 * Methode de dessin pour les objet de type etoile
	 * @param g2d Contexte graphique utilise lors du dessin
	 * @param at Matrice de transformation utilisee lors du dessin
	 */
	public void draw(Graphics2D g2d, AffineTransform at) {
		AffineTransform atTemp = new AffineTransform(at);
		Color colorTemp = g2d.getColor();
	
		shapeObjet = new Ellipse2D.Double(position.getX()-rayon, position.getY()-rayon, rayon*2, rayon*2);
		
		g2d.setColor(couleurObjet);
		g2d.fill(at.createTransformedShape(shapeObjet));
		
		at = atTemp;
		g2d.setColor(colorTemp);
	}
}
