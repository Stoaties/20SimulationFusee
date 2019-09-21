package objets.fusee.composants.enums;

/**
 * Enum qui va definir un booster qui s'appelle le "Solid Rocket Booster"
 * 
 * Le prix du booster venant de: https://space.stackexchange.com/questions/13986/why-were-solid-rockets-chosen-for-the-sls
 * 
 * SRBs (Solid Rocket Booster): https://en.wikipedia.org/wiki/Space_Shuttle_Solid_Rocket_Booster
 * - Poussee = 12 000 kN
 * - duree de Fonctionnement = 127s
 * - hauteur = 45.46m
 * - diametre = 3.71
 * - masse vide = 90 000 kg
 * - masse remplie = 590 000 kg
 * - prix = 23 M
 * - Impulsion Specifique (terrestre) = 242 s
 * - Impulsion Specifique (vide) = 268 s
 * 
 * 
 * @author Johnatan Gao
 *
 */

public enum EnumBooster {//debut classe
	
	//Variables qui creent le booster
	SRB("SRB",12000000.0, 127.0, 45.46, 3.71, 90000, 590000, 23 , 242, 268);
	
	//caracteristique du booster
	private double poussee, dureeFonctionnement, hauteur, diametre, masseVide, masseRempli, prix, impulsionSpecifiqueVide, impulsionSpecifiqueTerrestre;
	private String nom;
	
	/**
	 * Constructeur qui permet la construction du booster
	 * @param nom                    Le nom du booster
	 * @param poussee                La poussee du booster
	 * @param dureeFonctionnement    La duree de fonctionnement du booster
	 * @param hauteur                La hauteur du booster
	 * @param diametre               Le diametre du booster
	 * @param masseVide              La masse vide du booster
	 * @param masseRempli            La masse du booster rempli
	 * @param prix                   Le prix du booster
	 * @param impSpecifiqueTerrestre L'impulsion specifique terrestre du booster
	 * @param impSpecifiqueVide      L'impulsion specifique vide du booster
	 */
	//Johnatan G
	
	EnumBooster(String nom, double poussee, double dureeFonctionnement, double hauteur, double diametre, double masseVide, double masseRempli, double prix, double impSpecifiqueTerrestre, double impSpecifiqueVide){//debut constructeur
		
		this.poussee = poussee;
		this.dureeFonctionnement = dureeFonctionnement;
		this.hauteur = hauteur;
		this.masseVide = masseVide;
		this.masseRempli = masseRempli;
		this.prix = prix;
		this.setNom(nom);
		this.diametre = diametre;
		
		this.impulsionSpecifiqueTerrestre = impSpecifiqueTerrestre;
		this.impulsionSpecifiqueVide = impSpecifiqueVide;
		
	}//fin constructeur

	/**
	 * Methode qui retourne la poussee du booster
	 * @return poussee La poussee du booster
	 */
	//Johnatan G
	
	public double getPoussee() {//debut methode
		return poussee;
	}//fin methode

	/**
	 * Methode qui modifie la poussee du booster
	 * @param poussee La poussee le poussee du booster
	 */
	//Johnatan G
	
	public void setPoussee(double poussee) {//debut methode
		this.poussee = poussee;
	}//fin methode

	/**
	 * Methode qui retourne la duree de fonctionnement du booster
	 * @return dureeFonctionnement La duree de fonctionnement du booster
	 */
	//Johnatan G
	
	public double getDureeFonctionnement() {//debut methode
		return dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui modifie la duree de fonctionnement du booster
	 * @param dureeFonctionnement La duree de fonctionnement du booster
	 */
	//Johnatan G
	
	public void setDureeFonctionnement(double dureeFonctionnement) {//debut methode
		this.dureeFonctionnement = dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur du booster
	 * @return hauteur La hauteur du booster
	 */
	//Johnatan G
	
	public double getHauteur() {//debut methode
		return hauteur;
	}//fin methode

	/**
	 * Methode qui modifie la hauteur du booster
	 * @param hauteur La hauteur du booster
	 */
	//Johnatan G
	
	public void setHauteur(double hauteur) {//debut methode
		this.hauteur = hauteur;
	}//fin methode

	/**
	 * Methode qui retourne le diametre du booster
	 * @return diametre Le diametre du booster
	 */
	//Johnatan G
	
	public double getDiametre() {//debut methode
		return diametre;
	}//fin methode

	/**
	 * Methode qui modifie le diametre du booster
	 * @param diametre Le diametre du booster
	 */
	//Johnatan G
	
	public void setDiametre(double diametre) {//debut methode
		this.diametre = diametre;
	}//fin methode

	/**
	 * Methode qui retourne la masse du booster vide 
	 * @return masseVide La masse du booster vide
	 */
	//Johnatan G
	
	public double getMasseVide() {//debut methode
		return masseVide;
	}//fin methode

	/**
	 * Methode qui modifie la masse du booster vide 
	 * @param masseVide La masse du booster vide
	 */
	//Johnatan G
	
	public void setMasseVide(double masseVide) {//debut methode
		this.masseVide = masseVide;
	}//fin methode

	/**
	 * Methode qui retourne la masse du booster rempli
	 * @return masseRempli La masse du booster rempli
	 */
	//Johnatan G
	
	public double getMasseRempli() {//debut methode
		return masseRempli;
	}//fin methode

	/**
	 * Methode qui modifie la masse du booster rempli
	 * @param masseRempli La masse du booster rempli
	 */
	//Johnatan G
	
	public void setMasseRempli(double masseRempli) {//debut methode
		this.masseRempli = masseRempli;
	}//fin methode

	/**
	 * Methode qui retourne le prix du booster
	 * @return prix Le prix du booster
	 */
	//Johnatan G
	
	public double getPrix() {//debut methode
		return prix;
	}//fin methode

	/**
	 * Methode qui modifie le prix du booster
	 * @param prix Le prix du booster
	 */
	//Johnatan G
	
	public void setPrix(double prix) {//debut methode
		this.prix = prix;
	}//fin methode

	/**
	 * Methode qui retourne le nom du booster
	 * @return nom Le nom du booster
	 */
	//Johnatan G
	
	public String getNom() {//debut methode
		return nom;
	}//fin methode

	/**
	 * Methode qui modifie le nom du booster
	 * @param nom Le nom du booster
	 */
	//Johnatan G
	
	public void setNom(String nom) {//debut methode
		this.nom = nom;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique vide du booster
	 * @return impulsionSpecifiqueVide L'impulsion specifique vide du booster
	 */
	//Johnatan G
	
	public double getImpulsionSpecifiqueVide() {//debut methode
		return impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique vide du booster
	 * @param impulsionSpecifiqueVide L'impulsion specifique vide du booster
	 */
	//Johnatan G
	
	public void setImpulsionSpecifiqueVide(double impulsionSpecifiqueVide) {//debut methode
		this.impulsionSpecifiqueVide = impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique terrestre du booster
	 * @return impulsionSpecifiqueTerrestre  L'impulsion specifique terrestre du booster
	 */
	//Johnatan G
	
	public double getImpulsionSpecifiqueTerrestre() {//debut methode
		
		return impulsionSpecifiqueTerrestre;
	}//fin methode

	/**
	 * Methode qui modfifie l'impulsion specifique terrestre du booster
	 * @param impulsionSpecifiqueTerrestre L'impulsion specifique terrestre du booster
	 */
	//Johnatan G
	
	public void setImpulsionSpecifiqueTerrestre(double impulsionSpecifiqueTerrestre) {//debut methode
		this.impulsionSpecifiqueTerrestre = impulsionSpecifiqueTerrestre;
	}//fin methode
	
}//fin classe
