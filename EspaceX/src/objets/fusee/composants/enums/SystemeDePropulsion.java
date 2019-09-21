 package objets.fusee.composants.enums;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * Enum qui va creer les differents types de moteurs et definir leur puissance, leur consommation de combustible ainsi que de leur prix
 * (https://fr.wikipedia.org/wiki/Moteur-fus%C3%A9e) 
 * 
 * ROCKET PROPULSION: http://www.braeunig.us/space/propuls.htm 
 * 
 * 
 * SSME: (https://fr.wikipedia.org/wiki/Space_Shuttle_Main_Engine)
 * - Prix: 40M
 * - Ergols: LOX_LH2
 * - Impulsion specifique (vide): 453s (4.436 km/s)
 * - Impulsion specifique (terrestre): 363s (3.59 km/s)
 * - Vitesse d'ejection (terrestre) : 3560 m/s
 * - Vitesse d'ejection (vide): 4440 m/s
 * - Poussee (terrestre): 1860 kN
 * - Poussee (vide): 2279kN
 * - Consommation de carburant: 3917 litres/s --> 4465.38 kg/s
 * - Masse: 3200kg
 * - Hauteur: 4.3m
 * - Diametre: 2.4m
 * - Thrust-Specific fuel consumption: 225 g/kN*s
 * - Duree de fonctionnement: 520s
 * 
 * MERLIN: (https://fr.wikipedia.org/wiki/Merlin_(moteur-fus%C3%A9e)#Merlin_1D)
 * - Prix: 2.17M
 * - Ergols: Kerosene_RP1
 * - Impulsion specifique (vide): 380s
 * - Impulsion specifique (terrestre): 347.9s
 * - Vitesse d'ejection (terrestre): 2766 m/s
 * - Vitesse d'ejection (vide) : 3040 m/s
 * - Poussee (terrestre): 870 kN
 * - Poussee (vide): 934 kN
 * - Consommation de carburant: 273,7 kg/s
 * - Thrust-Specific fuel consumption: 270 g/ kN*s
 * - Masse: 490kg
 * - Hauteur: 2.7m
 * - Diametre: 2.5m
 * - Duree de fonctionnement: 397s
 * 
 * RD-180: (https://en.wikipedia.org/wiki/RD-180) 	
 * - Prix: 25M
 * - Ergols: Kerosene_RP1
 * - Impulsion specifique (vide): 338s (3.31 km/s)
 * - Impulsion specifique (terrestre): 311s (3.05 km/s)
 * - Vitesse d'ejection (terrestre):
 * - Vitesse d'ejection (vide):
 * - Poussee (terrestre): 3830 kN
 * - Poussee (vide): 4150 kN
 * - Thrust-specific fuel consumption: 308 g/KN*s
 * - Masse: 5480 kg
 * - Hauteur: 3.56m
 * - Diametre: 3.15m
 * - Duree de fonctionnement: 325s
 * 
 * 
 * @author Johnatan Gao
 *
 */

public enum SystemeDePropulsion {//debut classe
	
	//Variables qui creent les moteurs
	SSME_RS25("SSME_RS25",40.0, 363.0, 453.0, 3560.0, 4440000.0, 1860000.0, 2279.0, 3200.0, 4.3, 2.4, 520, "LOX_LH2", "USA", "ssme.png"), 
	MERLIN("MERLIN",2.17, 347.9, 380.0, 2766.0, 3040.0, 870000.0, 934000.0, 490.0, 2.7, 2.5, 520, "Kerosene_RP1", "USA", "merlin.png"), 
	RD_180("RD180",25.0, 311.0, 338.0, 3620.0, 4462.0, 3830000.0, 4150000.0, 5480.0, 3.56, 3.15, 325, "Kerosene_RP1", "Union Sovietique", "rd180.png"); 
	
	//caracteristique des moteurs en double
	private double prix, impulsionSpecifiqueTerrestre, impulsionSpecifiqueVide, 
	vitesseEjectionTerrestre, vitesseEjectionVide, pousseeTerrestre, 
	pousseeVide, masse, hauteur, diametre, dureeFonctionnement;
	
	//images des moteurs
	private transient Image imgMoteur;
	
	//caracteristique des moteurs en double en String
	private String Ergols, paysOrigine, nom;
	
	/**
	 * Constructeur qui permet la creation des moteurs
	 * @param nom                          Le nom du moteur
	 * @param prix                         Le prix du moteur
	 * @param impulsionSpecifiqueTerrestre L'impulsion specifique terrestre du moteur
	 * @param impulsionSpecifiqueVide      L'impulsion specifique vide du moteur
	 * @param vitesseEjectionTerrestre     La vitesse d'ejection terrestre du moteur
	 * @param vitesseEjectionVide          La vitesse d'ejection vide du moteur
	 * @param pousseeTerrestre             La pousse terrestre du moteur
	 * @param pousseeVide                  La pousse vide du moteur
	 * @param masse                        La masse du moteur
	 * @param hauteur                      La hauteur du moteur
	 * @param diametre                     Le diametre du moteur
	 * @param dureeFonctionnement          La duree de fonctionnement du moteur
	 * @param Ergols                       Le nom de l'ergol
	 * @param paysOrigine                  Le pays d'origine du moteur
	 * @param imgPath                      L'image du moteur
	 */
	//Johnatan G
	
	SystemeDePropulsion(String nom, double prix, double impulsionSpecifiqueTerrestre, double impulsionSpecifiqueVide, 
			double vitesseEjectionTerrestre, double vitesseEjectionVide, double pousseeTerrestre, 
			double pousseeVide, double masse, double hauteur, double diametre, double dureeFonctionnement, String Ergols, String paysOrigine, String imgPath) {//debut constructeur
		
		this.prix = prix;
		this.impulsionSpecifiqueTerrestre = impulsionSpecifiqueTerrestre;
		this.impulsionSpecifiqueVide = impulsionSpecifiqueVide;
		this.vitesseEjectionTerrestre = vitesseEjectionTerrestre;
		this.vitesseEjectionVide = vitesseEjectionVide;
		this.pousseeTerrestre = pousseeTerrestre;
		this.pousseeVide = pousseeVide;
		this.masse = masse;
		this.hauteur = hauteur;
		this.diametre = diametre;
		this.dureeFonctionnement = dureeFonctionnement;
		this.Ergols = Ergols;
		this.paysOrigine = paysOrigine;
		
		this.setNom(nom);
		
		lireImageURL(imgPath);
		
	}//fin constructeur

	/**
	 * Methode qui retourne le prix du moteur
	 * @return prix Le prix du moteur
	 */
	//Johnatan G
	
	public double getPrix() {//debut methode
		return prix;
	}//fin methode

	/**
	 * Methode qui modifie le prix du moteur
	 * @param prix Le prix du moteur
	 */
	//Johnatan G
	
	public void setPrix(double prix) {//debut methode
		this.prix = prix;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique terrestre du moteur
	 * @return impulsionSpecifiqueTerrestre L'impulsion specifique terrestre du moteur
	 */
	//Johnatan G
	
	public double getImpulsionSpecifiqueTerrestre() {//debut methode
		return impulsionSpecifiqueTerrestre;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique terrestre du moteur
	 * @param impulsionSpecifiqueTerrestre L'impulsion specifique terrestre du moteur
	 */
	//Johnatan G
	
	public void setImpulsionSpecifiqueTerrestre(double impulsionSpecifiqueTerrestre) {//debut methode
		this.impulsionSpecifiqueTerrestre = impulsionSpecifiqueTerrestre;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique vide du moteur
	 * @return impulsionSpecifiqueVide L'impulsion specifique vide du moteur
	 */
	//Johnatan G
	
	public double getImpulsionSpecifiqueVide() {//debut methode
		return impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique vide du moteur
	 * @param impulsionSpecifiqueVide L'impulsion specifique vide du moteur
	 */
	//Johnatan G
	
	public void setImpulsionSpecifiqueVide(double impulsionSpecifiqueVide) {//debut methode
		this.impulsionSpecifiqueVide = impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui retourne la vitesse d'ejection terrestre du moteur 
	 * @return vitesseEjectionTerrestre La vitesse d'ejection terrestre du moteur 
	 */
	//Johnatan G
	
	public double getVitesseEjectionTerrestre() {//debut methode
		return vitesseEjectionTerrestre;
	}//fin methode

	/**
	 * Methode qui modifie la vitesse d'ejection terrestre du moteur
	 * @param vitesseEjectionTerrestre La vitesse d'ejection terrestre du moteur
	 */
	//Johnatan G
	
	public void setVitesseEjectionTerrestre(double vitesseEjectionTerrestre) {//debut methode
		this.vitesseEjectionTerrestre = vitesseEjectionTerrestre;
	}//fin methode

	/**
	 * Methode qui retourne la vitesse d'ejection du moteur vide 
	 * @return vitesseEjectionVide La vitesse d'ejection du moteur vide 
	 */
	//Johnatan G
	
	public double getVitesseEjectionVide() {//debut methode
		return vitesseEjectionVide;
	}//fin methode

	/**
	 * Methode qui modifie la vitesse d'ejection du moteur vide 
	 * @param vitesseEjectionVide La vitesse d'ejection du moteur vide 
	 */
	//Johnatan G
	
	public void setVitesseEjectionVide(double vitesseEjectionVide) {//debut methode
		this.vitesseEjectionVide = vitesseEjectionVide;
	}//fin methode

	/**
	 * Methode qui modifie la pousse terrestre du moteur
	 * @return pousseeTerrestre La pousse terrestre du moteur
	 */
	//Johnatan G
	
	public double getPousseeTerrestre() {//debut methode
		return pousseeTerrestre;
	}//fin methode

	/**
	 * Methode qui modifie la pousse terrestre du moteur
	 * @param pousseeTerrestre La pousse terrestre du moteur
	 */
	//Johnatan G
	
	public void setPousseeTerrestre(double pousseeTerrestre) {//debut methode
		this.pousseeTerrestre = pousseeTerrestre;
	}//fin methode

	/**
	 * Methode qui retourne la pousse du moteur vide
	 * @return pousseeVide La pousse du moteur vide
	 */
	//Johnatan G
	
	public double getPousseeVide() {//debut methode
		return pousseeVide;
	}//fin methode

	/**
	 * Methode qui modifie la pousse du moteur vide
	 * @param pousseeVide La pousse du moteur vide
	 */
	//Johnatan G
	
	public void setPousseeVide(double pousseeVide) {//debut methode
		this.pousseeVide = pousseeVide;
	}//fin methode

	/**
	 * Methode qui retourne la masse du moteur
	 * @return masse La masse du moteur
	 */
	//Johnatan G
	
	public double getMasse() {//debut methode
		return masse;
	}//fin methode

	/**
	 * Methode qui modifie la masse du moteur
	 * @param masse La masse du moteur
	 */
	//Johnatan G
	
	public void setMasse(double masse) {//debut methode
		this.masse = masse;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur du moteur
	 * @return hauteur La hauteur du moteur
	 */
	//Johnatan G
	
	public double getHauteur() {//debut methode
		return hauteur;
	}//fin methode

	/**
	 * Methode qui modifie la hauteur du moteur
	 * @param hauteur La hauteur du moteur
	 */
	//Johnatan G
	
	public void setHauteur(double hauteur) {//debut methode
		this.hauteur = hauteur;
	}//fin methode

	/**
	 * Methode qui retourne le diametre du moteur 
	 * @return diametre Le diametre du moteur
	 */
	//Johnatan G
	
	public double getDiametre() {//debut methode
		return diametre;
	}//fin methode

	/**
	 * Methode qui modifie le diametre du moteur
	 * @param diametre Le diametre du moteur
	 */
	//Johnatan G
	
	public void setDiametre(double diametre) {//debut methode
		this.diametre = diametre;
	}//fin methode

	/**
	 * Methode qui retourne la duree de fonctionnement du moteur
	 * @return dureeFonctionnement La duree de fonctionnement du moteur
	 */
	//Johnatan G
	
	public double getDureeFonctionnement() {//debut methode
		return dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui modifie la duree de fonctionnement du moteur
	 * @param dureeFonctionnement La duree de fonctionnement du moteur
	 */
	//Johnatan G
	
	public void setDureeFonctionnement(double dureeFonctionnement) {//debut methode
		this.dureeFonctionnement = dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui retourne l'ergol du moteur
	 * @return Ergols L'ergol du moteur
	 */
	//Johnatan G
	
	public String getErgols() {//debut methode
		return Ergols;
	}//fin methode

	/**
	 * Methode qui modifie l'ergol du moteur
	 * @param ergols L'ergol du moteur
	 */
	//Johnatan G
	
	public void setErgols(String ergols) {//debut methode
		Ergols = ergols;
	}//fin methode

	/**
	 * Methode qui retourne le pays d'origine du moteur
	 * @return paysOrigine Le pays d'origine du moteur
	 */
	//Johnatan G
	
	public String getPaysOrigine() {//debut methode
		return paysOrigine;
	}//fin methode

	/**
	 * Methode qui modifie le pays d'origine du moteur
	 * @param paysOrigine Le pays d'origine du moteur
	 */
	//Johnatan G
	
	public void setPaysOrigine(String paysOrigine) {//debut methode
		this.paysOrigine = paysOrigine;
	}//fin methode

	/**
	 * Methode qui retourne le nom du moteur
	 * @return nom Le nom du moteur
	 */
	//Johnatan G
	
	public String getNom() {//debut methode
		return nom;
	}//fin methode

	/**
	 * Methode qui modifie le nom du moteur
	 * @param nom Le nom du moteur
	 */
	//Johnatan G
	
	public void setNom(String nom) {//debut methode
		this.nom = nom;
	}//fin methode
	
	/**
	 * Methode qui retourne l'image du moteur
	 * @return imgMoteur L'image du moteur
	 */
	//Johnatan G
	
	public Image getImgMoteur() {//debut methode
		return imgMoteur;
	}//fin methode

	/**
	 * Methode qui modifie l'image du moteur
	 * @param imgMoteur L'image du moteur
	 */
	//Johnatan G
	
	public void setImgMoteur(Image imgMoteur) {//debut methode
		this.imgMoteur = imgMoteur;
	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * @param imgPath L'image du moteur
	 */
	//Johnatan Gao
	
	private void lireImageURL(String imgPath) {//debut methode
		URL urlImg = getClass().getClassLoader().getResource(imgPath);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				setImgMoteur(ImageIO.read(urlImg));
			}
			
			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

	}//fin methode
	
}//fin classe
