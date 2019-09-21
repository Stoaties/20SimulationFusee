package objets.celeste;

import java.io.Serializable;

/**
 * Classe qui instancie un Satellite
 * @author Corentin Gouanvic
 *
 */

public class Satellite extends ObjetCeleste implements Serializable{

	/**
	 * Constructeur d'un objet de type Satellite
	 * @param rayon Rayon du satellite (m)
	 * @param masse Masse du satellite (kg)
	 * @param rayonOrbit Rayon d'orbite du satellite (m)
	 * @param rotationInitiale Rotation initiale du cycle orbitale (rad)
	 */
	public Satellite(double rayon, double masse, double rayonOrbit, double rotationInitiale) {
		super(rayon, masse, rayonOrbit, rotationInitiale);
	}

}
