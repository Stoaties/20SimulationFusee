package util;

/**
 * Classe ou on fait les calculs mathematiques utilises dans l'application
 * 
 * @author Ivana Bera
 *
 */

public class SMath {
	public static final double G = 6.67408e-11;
	public static final double G0 = 9.80665;

	public static final double EPSILON = 10E-8;
	
	public static final double MASSE_SOLEIL = 1.9891E30;

	private double accelerationCentripete;
	private double forceGravitationelle;
	private double vitesse;

	/**
	 * Methode qui calcule l'acceleration centripete d'une planete qui tourne autour
	 * d'une autre planete
	 * 
	 * @param forceGravi   Force gravitationelle entre deux masses
	 * @param massePlanete Masse de la planete choisi
	 * @return L'acceleration centripete de la planete
	 */
	public double calculerAccCentripete(double forceGravi, double massePlanete) {
		accelerationCentripete = forceGravitationelle / massePlanete;

		return accelerationCentripete;
	}

	/**
	 * Methode qui calcule la force gravitationelle entre deux planetes
	 * 
	 * @param masse1   Masse de la premiere planete
	 * @param masse2   Masse de la deuxieme planete
	 * @param distance Distance entre les deux planetes
	 * @return La force gravitationelle entre deux planete
	 */

	public double calculerForceGravDeuxObjets(double masse1, double masse2, double distance) {
		forceGravitationelle = (G * masse1 * masse2) / Math.pow(distance, 2);

		return forceGravitationelle;
	}

	/**
	 * Méthode qui calcule la force gravitaionelle pour un seul objet
	 * 
	 * @param masse Masse de l'objet
	 * @param rayon Rayon de l'objet
	 * @return La force gravitationelle de l'objet
	 */
	public double calculerForceGrav(double masse, double rayon) {
		forceGravitationelle = (G * masse) / (Math.pow(rayon, 2));
		return forceGravitationelle;
	}

	/**
	 * Methode qui calcule la vitesse d'une planete
	 * 
	 * @param accCentri             L'acceleration centripete de la planete
	 * @param distanceEntrePlanetes la distance entre les deux planetes
	 * @return la vitesse de la planete
	 */

	public double calculerVitesse(double accCentri, double distanceEntrePlanetes) {
		vitesse = Math.sqrt(accCentri * distanceEntrePlanetes);

		return vitesse;

	}

	/**
	 * Methode qui donne acces a l'acceleration centripete
	 * 
	 * @return l'acceleration centripete
	 */
	public double getAccelerationCentripete() {
		return accelerationCentripete;
	}

	/**
	 * Methode qui modifie l'acceleration centripete
	 * 
	 * @param accelerationCentripete L'acceleration centripete
	 */
	public void setAccelerationCentripete(double accelerationCentripete) {
		this.accelerationCentripete = accelerationCentripete;
	}

	/**
	 * Methode qui donne acces a la force gravitationelle entre deux planetes
	 * 
	 * @return la force gravitationelle
	 */
	public double getForceGravitationelle() {
		return forceGravitationelle;
	}

	/**
	 * Methode qui modifie la force gravitationelle
	 * 
	 * @param forceGravitationelle La force gravitationelle
	 */
	public void setForceGravitationelle(double forceGravitationelle) {
		this.forceGravitationelle = forceGravitationelle;
	}

	/**
	 * Methode qui donne acces a la vitesse d'une planete
	 * 
	 * @return la vitesse
	 */
	public double getVitesse() {
		return vitesse;
	}

	/**
	 * Methode qui modifie la vitesse d'une planete
	 * 
	 * @param vitesse La vitese
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

/**
 * Methode qui compare deux double
 * @param a le premier double
 * @param b le deuxieme double
 * @return vrai s'ils sont egaux
 */
	public static boolean nearlyEquals(double a, double b) {

		double epsilon = 1E-06;

		if (Math.abs(a - b) <= epsilon) {
			return true;
		} else {
			return false;
		}

	}

}
