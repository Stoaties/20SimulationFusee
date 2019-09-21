package listeners;

import java.util.EventListener;

import objets.fusee.Fusee;
import objets.fusee.composants.Moteur;
import util.Vecteur;

/**
 * L'interface FuseeListener permet de definir les nouveaux evenements sous la forme d�une classe interface
 * 
 * @author Johnatan Gao
 */

public interface FuseeListener extends EventListener {//debut

	/**
	 * Methode qui notifie quel combustible doit etre utilise
	 * @param m Le moteur utilise
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	//Johnatan G
	
	public void setCombustibleUtilise(Moteur m, int listMoteurSize);
	
	/**
	 * Methode qui notifie quel objet de la fusee doit etre enleve
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	//Johnatan G
	
	public void enleverObjetUtilise(int listMoteurSize);
	
	/**
	 * Methode qui met a jour les valeurs du graphique
	 * @param tempsEcoule        Le temps ecoule
	 * @param sommesDesForcesX   La somme des forces en X
	 * @param sommesDesForcesY   La somme des forces en Y
	 * @param vitesseX           La vitesse de la fusee en X
	 * @param vitesseY           La vitesse de la fusee en Y
	 * @param accelerationX      L'acceleration de la fusee en X
	 * @param accelerationY      L'acceleration de la fusee en Y
	 * @param masseFusee         La masse de la fusee
	 * @param masseReservoir     La masse du reservoir de la fusee
	 * @param masseBooster       La masse du booster de la fusee
	 * @param resistanceAir      La resistance de l'air de la fusee
	 * @param distanceFuseeTerre La distance entre le fusee et la Terre
	 * @param angleFusee         L'angle de la fusee
	 */
	//Johnatan G
	
	public void updateValeurs(double tempsEcoule, double sommesDesForcesX, double sommesDesForcesY, 
			double vitesseX, double vitesseY, double accelerationX, double accelerationY, 
			double masseFusee, double masseReservoir, 
			double masseBooster, double resistanceAir, double distanceFuseeTerre, double angleFusee);
	
	
	/**
	 * Methode qui permet le deplacement de la fusee dans l'Espace
	 * @param positionFusee Le deplacement de la fusee
	 * @param masseFusee La masse de la fusee
	 */
	//Johnatan G
	
	public void dansEspace(Vecteur positionFusee, double masseFusee);
	
	
	
}//fin
