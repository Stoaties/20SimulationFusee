package util;

/**
 * Classe qui gere toutes les constantes finales
 * @author Johnatan Gao
 *
 */
public interface Constantes {//debut classe

	//toutes les constantes finales
	final double PRESSION_NIVEAU_MER = 104606.685710345; //en Pa
	final double MASSE_MOLAIRE_AIR = 28.976; //en g/mol
	final double CONSTANTE_U_GAZ_PARFAIT = 8.3144621; 
	
	final double CONSTANTE_G_U = 6.674E-11;
	
	final double C1 = 6.7759529965018E-22, //Des constantes qui font partie d'une fonction de degree 5 qui estime la temperature en fonction de la l'altitude
			C2 = -9.86883265430398E-17, 
			C3 = 1.53822343418819E-12, 
			C4 = 2.57822935850161E-07, 
			C5 = -0.008975791007056,
			C6 = 14.3321149382422;
	
	final double C_P = -0.000145915503334; //Une constante quelconque qui permet de determiner la pression en fonction de l'altitude
	
	final double CONSTANTE_CELSIUS_A_KELVIN = 273.15; // en degre celsius
	
	final double DRAG_COEF_MOYEN = 0.75;

	final double MASSE_LIMITE_RESERVOIR_TEMPORAIRE = 500000; //en kg
	
	final double DELTA_T_FUSEE = 0.0025; //en s
	
	final double SIZE_POINT = 0.25; //La grosseur des points dans les graphiques
	
	final int NB_LIGNE_GRAPHIQUE = 16;
	final int NB_COLONNE_GRAPHIQUE = 12;
	
	final double IN_SPACE = 100000;
	
	final int SLEEP = 5;
	
	final double RAYON_TERRE = 637100; //en m
	
}//fin classe
