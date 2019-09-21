package listeners;

import java.awt.geom.AffineTransform;
import java.util.EventListener;

import objets.fusee.composants.Reservoir;
import util.Vecteur;

/**
 * L'interface ReservoirListener permet de definir les nouveaux evenements sous la forme d’une classe interface
 * 
 * @author Melie Leclerc
 */

public interface ReservoirListener extends EventListener {//debut

	/**
	 * Methode qui notifie quel reservoir est selectionne
	 * @param r              Le reservoir selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matReservoir   La  matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L

	
	public void setReservoirSelectionne(Reservoir r, Vecteur positionSouris, double largeur, AffineTransform matReservoir);
	
	/**
	 * Methode qui notifie que le reservoir est deselectionne
	 * 
	 */
	//Melie L
	
	public void deselectionneReservoir();
	
	/**
	 * Methode qui notifie que le reservoir est en mouvement
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matReservoir   La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L
	
	public void dragReservoirSelectionne(Vecteur positionSouris, double largeur, AffineTransform matReservoir);
	
}//fin
