package listeners;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.EventListener;

/**
 * L'interface BoosterListener permet de definir les nouveaux evenements sous la forme d’une classe interface
 * 
 * @author Johnatan Gao
 * @author Melie Leclerc
 */

public interface CentreControleListener extends EventListener {//debut

	/**
	 * Methode qui permet de lancement de la fusee
	 */
	// Johnatan G
	
	public void lancerFusee();
	
	/**
	 * Methode qui fait recommencer le lancement de la fusee
	 */
	// Johnatan G
	
	public void reinitFusee();
	
	/**
	 * Methode qui anime la fusee globalement
	 */
	//Johnatan G
	
	public void animerGlobal();
	
	/**
	 * Methode qui arrete la fusee globalement
	 */
	//Johnatan G
	
	public void arreterGlobal();
	
	/**
	 * Methode qui va faire animer le pas a pas
	 */
	// Johnatan G
	
	public void prochainGlobal();
	
	/**
	 * Methode qui reinitialise les composants de la page centre de controle
	 */
	// Johnatan G
	public void reinitGlobal();
	
	/**
	 * Methode qui arrete le son de la fusee
	 */
	//Melie L
	
	public void arreterSon();
	/**
	 * Methode qui allume le son de la fusee
	 */
	//Melie L
	
	public void allumerSon();
	
	/**
	 * Methode qui modifie le pourcentage de la pousse du moteur
	 * @param pourcentagePousseeMoteurs Le pourcentage de la pousse du moteur
	 */
	// Johnatan G
	
	public void changerPousseeMoteurs(double pourcentagePousseeMoteurs);
	
	/**
	 * Methode qui modifie le pourcentage de la pousse du booster
	 * @param pourcentagePousseeBoosters Le pourcentage de la pousse du booster
	 */
	// Johnatan G
	
	public void changerPousseeBoosters(double pourcentagePousseeBoosters);
	/**
	 * Methode qui change l'angle de deviation de la fusee
	 * @param angle L'angle de deviation de la fusee
	 */
	//Johnatan G
	
	public void changerAngleDeviation(double angle);
	
	/**
	 * Methode qui modifie la hauteur de la fusee a laquelle elle va devier
	 * @param hauteurDeviation La hauteur de la fusee a laquelle elle va devier
	 */
	// Johnatan G
	
	public void changerHauteurDeviation(double hauteurDeviation);
	
	/**
	 * Methode qui change l'ecoulement du temps
	 * @param nDeltaT L'ecoulement du temps
	 */
	//Johnatan G
	
	public void changerEcoulementTemps(double nDeltaT);
	
}//fin 
