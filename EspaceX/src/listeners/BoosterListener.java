package listeners;

import java.awt.geom.AffineTransform;

import objets.fusee.composants.Booster;
import util.Vecteur;

/**
 * L'interface BoosterListener permet de definir les nouveaux evenements sous la forme d’une classe interface
 * 
 * @author Melie Leclerc
 */

public interface BoosterListener {//debut

	/**
	 * Methode qui notifie quelle booster est selectionne
	 * @param b              Le booster selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeurMonde   La largeur du monde
	 * @param matBooster     La matrice La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L
	
	public void setBoosterSelectionne(Booster b, Vecteur positionSouris, double largeurMonde, AffineTransform matBooster);
	
	/**
	 * Methode qui notifie que le booster est deselectionne
	 */
	//Melie L
	
	public void deselectionnerBooster();
	
	/**
	 * Methode qui notifie que le booster est en mouvement
	 * @param positionSouris La position du curseur de la souris
	 * @param largeurMonde   La largeur du monde
	 * @param matBooster     La matrice La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L
	
	public void dragBoosterSelectionne(Vecteur positionSouris, double largeurMonde, AffineTransform matBooster);
		
}//fin
