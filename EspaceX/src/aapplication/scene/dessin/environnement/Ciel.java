package aapplication.scene.dessin.environnement;

import java.awt.Color; 
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import util.Dessinable;

/**
 * Classe qui dessine le ciel
 * @author Johnatan Gao
 *
 */

public class Ciel implements Dessinable {//debut classe

	//Variables qui dessinent le ciel
	private Rectangle2D.Double rectCiel;
	
	private final double POSITIONX, POSITIONY, LARGEUR_MONDE;
	
	/**
	 * Constructeur qui permet le dessinage du ciel
	 * @param largeurMonde La largeur du monde
	 * @param hauteurMonde La hauteur du monde
	 */
	//Johnatan G
	
	public Ciel(double largeurMonde, double hauteurMonde) {//debut constructeur
		
		this.LARGEUR_MONDE = largeurMonde;
		
		this.POSITIONX = -this.LARGEUR_MONDE * 50;
		this.POSITIONY = 0;
		
		rectCiel = new Rectangle2D.Double(this.POSITIONX, POSITIONY , LARGEUR_MONDE * 100, 100000);
		
	}//fin constructeur

	/**
	 * Methode qui dessine le ciel
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode
		
		Color cInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(aff);
		
		AffineTransform transfoAvant = g2d.getTransform();	
		
		Color couleurDepart = new Color(102,178,255); //Bleu pale
		Color couleurFin = new Color(0, 0, 25); //Bleu foncee (noir)

		float y1 = (float) rectCiel.y;
		float x2 = (float) rectCiel.width;
		float y2 = (float) rectCiel.height;
		
		GradientPaint gp = new GradientPaint((float) ((x2/2) * matLocale.getScaleX()), (float) (y1 * matLocale.getScaleY() + matLocale.getTranslateY()), couleurDepart, (float) ((x2/2) * matLocale.getScaleX()), (float) (y2 * matLocale.getScaleY() + matLocale.getTranslateY()), couleurFin);
		g2d.setPaint(gp);
		g2d.setTransform(transfoAvant);
		
		
		g2d.fill(matLocale.createTransformedShape(rectCiel));
		
		g2d.setColor(cInit);
		g2d.setTransform(transfoAvant);
		
	}//fin methode
	
}//fin classe