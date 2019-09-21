package listeners;

import java.util.ArrayList;
import java.util.EventListener;

import objets.fusee.composants.Booster;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.Reservoir;

/**
 * L'interface BienvenueListener permet de definir les nouveaux evenements sous la forme d’une classe interface
 * 
 * @author Johnatan Gao
 */

public interface BienvenueListener extends EventListener{//debut 

	/**
	 * Methode qui notifie quelle fusee est selectionnee
	 * @param listMoteur    La liste de moteur
	 * @param listReservoir La liste de reservoir
	 * @param listBooster   La liste de booster
	 */
	//Johnatan G
	
	public void setFuseeSelectionne(ArrayList <Moteur> listMoteur, ArrayList <Reservoir> listReservoir, ArrayList <Booster> listBooster);
	
}//fin
