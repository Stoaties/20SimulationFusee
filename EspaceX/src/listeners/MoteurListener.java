package listeners;

import java.awt.geom.AffineTransform;
import java.util.EventListener;

import objets.fusee.composants.Moteur;
import util.Vecteur;

/**
 * L'interface MoteurListener permet de definir les nouveaux evenements sous la forme d’une classe interface
 * 
 * @author Johnatan Gao
 */

public interface MoteurListener extends EventListener{//debut

	/**
	 * Methode qui notifie quel moteur est selectionne
	 * @param m              Le moteur selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matMoteur      La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	public void setMoteurSelectionne(Moteur m, Vecteur positionSouris, double largeur, AffineTransform matMoteur);
	
	/**
	 * Methode qui notifie que le moteur est deselectionne
	 */
	//Johnatan G
	
	public void deselectionnerMoteur();
	
	/**
	 * Methode qui notifie que le moteur est en mouvement
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matMoteur      La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	public void dragMoteurSelectionne(Vecteur positionSouris, double largeur, AffineTransform matMoteur);
	
}//fin
