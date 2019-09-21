package util;

/**
 * Cette classe regroupe les calculs physiques necessaires au mouvement des objets des divers objets dans la scene. 
 * 
 * @author Johnatan Gao
 * @author Corentin Gouanvic
 * @author Melie Leclerc
 * @author Ivana Bera 
 *
 */
public class MoteurPhysique implements Constantes {//debut classe
	
	/**
	 * 
	 * @param deltaT L'ecoulement du temps
	 * @param position La position
	 * @param vitesse La vitesse
	 * @param accel l'acceleration
	 */
	//Corentin
	
	public static void step(double deltaT, Vecteur position, Vecteur vitesse, Vecteur accel) {//debut methode		
		
		Vecteur deltaVitesse = Vecteur.multiplie(accel, deltaT);
		Vecteur resultV = vitesse.additionne( deltaVitesse ); 
		vitesse.setX(resultV.getX());	//on change le vecteur vitesse
		vitesse.setY(resultV.getY());
		
		Vecteur deltaPosition = Vecteur.multiplie(vitesse, deltaT);
		Vecteur resultP = position.additionne(deltaPosition); //on calcule la position en considerant la nouvelle vitesse
		position.setX(resultP.getX());  //on change le vecteur position
		position.setY(resultP.getY());

	}//fin methode
	
	/**
	 * Calculer la consommation de carburant selon le temps
	 * 
	 * @param pousseeFusee  La poussee de la fusee
	 * @param impulsionSpec L'impulsion specifique d'un moteur
	 * @return dmdt         La variation de la masse en fonction du temps
	 */
	//Johnatan Gao
	public static double calculerdMdt(double pousseeFusee, double impulsionSpec) {//debut methode		

		double dmdt = pousseeFusee/(impulsionSpec * SMath.G0);
		
		if(SMath.nearlyEquals(pousseeFusee, 0) || SMath.nearlyEquals(impulsionSpec, 0)) {
			return 0;
		}
		
		return dmdt; //retourne le dmdt en kg/s
		
	}//fin methode
	
	/**
	 * Calculer l'acceleration gravitationnelle selon l'altitude
	 * @param r0 Le rayon de la Terre
	 * @param h  La hauteur de la fusee par rapport au sol
	 * @return SMath.G0 * Math.pow(r0/(r0+h), 2) L'acceleration gravitationnelle selon l'altitude
	 */
	//Johnatan Gao
	public static double calculerdGdh(double r0, double h) {//debut methode
		return SMath.G0 * Math.pow(r0/(r0+h), 2);
	}//fin methode
	
	/**
	 * Calculer l'angle teta selon le temps
	 * @param v    La vitesse de la fusee
	 * @param beta L'angle beta
	 * @param r0   Le rayon initial
	 * @param h    La hauteur de la fusee par rapport au sol
	 * @return v*Math.sin(beta)/(r0+h) L'angle teta selon le temps
	 */
	
	public static double calculerdTetadt(double v, double beta, double r0, double h) {//debut methode
		
		return v*Math.sin(beta)/(r0+h);
		
	}//fin methode
	
	/**
	 * Calculer l'angle beta selon le temps
	 * @param r0   Le rayon initial
	 * @param h    La hauteur de la fusee par rapport au sol
	 * @param beta L'angle beta
	 * @param v    La vitesse de la fusee
	 * @return g * Math.sin(beta)/v - calculerdTetadt(v, beta, r0, h) L'angle beta selon le temps
	 */
	//Johnatan Gao
	public static double calculerdBetadt(double r0, double h, double beta, double v) {//debut methode
		
		double g = calculerdGdh(r0, h);
		return g * Math.sin(beta)/v - calculerdTetadt(v, beta, r0, h);
	}//fin methode
	
	/**
	 * Calculer la hauteur selon le temps
	 * @param v                   La vitesse de la fusee
	 * @param beta                L'angle beta
	 * @return v * Math.cos(beta) La hauteur de la fusee par rapport au sol selon le temps
	 */
	//Johnatan G
	
	public static double calculerdhdt(double v, double beta) {//debut methode
		
		return v * Math.cos(beta);
		
	}//fin methode
	
	/**
	 * Calculer la pression selon l'altitude en kPa 
	 * @param altitude L'altitude de la fusee
	 * @return PRESSION_NIVEAU_MER * Math.exp(C_P * altitude)/1000 La pression selon l'altitude en kPa 
	 */
	//Melie L
	
	public static double calculerdPdh(double altitude) {//debut methode
		
		return PRESSION_NIVEAU_MER * Math.exp(C_P * altitude)/1000;
		
	}//fin methode
	
	/**
	 * Calculer la temperature en Kelvin selon l'altitude 
	 * @param altitude L'altitude de la fusee
	 * @return C1*Math.pow(altitude, 5)+
				C2*Math.pow(altitude, 4)+
				C3*Math.pow(altitude, 3)+
				C4* Math.pow(altitude, 2)+
				C5 * altitude + C6 + CONSTANTE_CELSIUS_A_KELVIN La temperature en Kelvin
	 */
	//Melie L
	
	public static double calculerdTdh(double altitude) {//debut methode

		return (C1*Math.pow(altitude, 5)+
				C2*Math.pow(altitude, 4)+
				C3*Math.pow(altitude, 3)+
				C4* Math.pow(altitude, 2)+
				C5 * altitude + C6 + CONSTANTE_CELSIUS_A_KELVIN); //pour convertir en kelvin

	}//fin methode

	/**
	 * Calcul du rho en fonction de la hauteur
	 * @param altitude L'altitude de a fusee
	 * @return MASSE_MOLAIRE_AIR*pression/(CONSTANTE_U_GAZ_PARFAIT* temperatureEnKelvin) Le rho en fonction de la hauteur
	 */
	//Melie L
	
	public static double calculedRhodh(double altitude) {//debut methode
		
		double pression = calculerdPdh(altitude);
		double temperatureEnKelvin = calculerdTdh(altitude);
		
		return MASSE_MOLAIRE_AIR*pression/(CONSTANTE_U_GAZ_PARFAIT* temperatureEnKelvin);	
	}//fin methode
	
	/**
	 * Calcul la resistance de l'air 
	 * @param altitude L'altitude de la fusee
	 * @param aire     L'aire
	 * @param vitesse  La vitesse la fusee
	 * @return new Vecteur(0.5 * DRAG_COEF_MOYEN * rho * Math.pow(vitesse.getX(), 2), 0.5 * DRAG_COEF_MOYEN * rho * Math.pow(vitesse.getY(), 2), 0) La resistance de l'air
	 */
	//Melie L
	
	public static Vecteur calculerResistanceAir(double altitude, double aire, Vecteur vitesse) {//debut methode

		double rho = calculedRhodh(altitude);
		return new Vecteur(0.5 * DRAG_COEF_MOYEN * rho * Math.pow(vitesse.getX(), 2), 0.5 * DRAG_COEF_MOYEN * rho * Math.pow(vitesse.getY(), 2), 0); //retourne la ressitance de l'air en Newton

	}//fin methode
	
	/**
	 * Calcule l'acceleration en fonction de la masse et des forces appliquees
	 * @param sommeDesForces La somme des forces appliquees
	 * @param masse          La masse de la fusee
	 * @param accel          En sortie, l'acceleraion calculee, avec a=m/F
	 */
	//Johnatan G

	public static void misAJourAcceleration(Vecteur sommeDesForces, double masse, Vecteur accel) {//debut methode
		
		accel.setX(sommeDesForces.getX()/masse);
		accel.setY(sommeDesForces.getY()/masse);
		
	}//fin methode
	
	/**
	 * Calcule une iteration de l'algorithme d'integration numerique d'Euler semi-implicite. 
	 * Les vecteurs position et vitesse seront modifies a la sortie!
	 * 
	 * @param deltaT   Incrementation du temps
	 * @param position Vecteur de position (sera modifie)
	 * @param vitesse  Vecteur de vitesse (sera modifie)
	 * @param accel    Vecteur d'acceleration
	 */
	//Johnatan G
	public static void unPasEuler(double deltaT, Vecteur position, Vecteur vitesse, Vecteur accel) {//debut methode

		Vecteur deltaVitesse = Vecteur.multiplie(accel, deltaT);
		Vecteur resultV = vitesse.additionne( deltaVitesse ); 
		vitesse.setX(resultV.getX());	//on change le vecteur vitesse
		vitesse.setY(resultV.getY());
	
		Vecteur deltaPosition = Vecteur.multiplie(vitesse, deltaT);
		Vecteur resultP = position.additionne(deltaPosition); //on calcule la position en considerant la nouvelle vitesse
		position.setX(resultP.getX());  //on change le vecteur position
		position.setY(resultP.getY());

	}//fin methode
	
	/**
	 * Calcul de la loi gravitationnelleUniverselle
	 * @param posCible La position de la cible
	 * @param posSource La position de la source
	 * @param masse1 La masse du premier objet
	 * @param masse2 La masse du deuxieme objet
	 * @return Loi gravitationnelle  Universelle
	 */
	//Johnatan G
	public static Vecteur loiGravitationnelleUniverselle(Vecteur posCible, Vecteur posSource, double masse1, double masse2) {//debut methode
			
		Vecteur orientation = new Vecteur((posCible.soustrait(posSource)).multiplie(1/(posCible.soustrait(posSource).module())));
		
		return orientation.multiplie(-CONSTANTE_G_U * masse1 * masse2 / Math.pow((posCible.soustrait(posSource).module()),2));
		
		
	}//fin methode
	

}//fin classe
