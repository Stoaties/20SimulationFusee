package util;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Interface qui va permettre la creation de classe dessinable
 * @author Melie Leclerc
 *
 */

public interface Dessinable {//debut
	
	/**
	 * Methode qui va dessiner les composantes de la fusee
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	public void draw( Graphics2D g2d, AffineTransform aff);
	
}//fin 
