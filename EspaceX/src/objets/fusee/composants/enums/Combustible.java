package objets.fusee.composants.enums;

/**
 * Enum qui va creer les differents types de carburants et qui va definir la masse et son prix ( https://fr.wikipedia.org/wiki/Impulsion_sp%C3%A9cifique )
 * 
 * Le prix des carburants vont etre estime selon les prix courants des elements en date de 2019-05-15
 * 
 * Oxygene liquide = 0.16$/kg (https://www.quora.com/How-much-does-NASA-pay-per-kg-for-hydrogen-and-oxygen-in-rocket-fuel)
 * Hydrogene liquide = 2.20$/kg (https://en.wikipedia.org/wiki/Hydrogen_economy)
 * Azote liquide = 0.132$/kg (https://hypertextbook.com/facts/2007/KarenFan.shtml)
 * Methane liquide = 1.35$/kg (www.thespacereview.com/article/2893/1)
 * Lithium liquide = 3$/kg (https://fr.wikipedia.org/wiki/Lithium)
 * Fluor liquide = 6$/kg (http://www.astronautix.com/l/lf2.html)
 * Kerosene = 0.82$/kg (https://www.nyserda.ny.gov/Researchers-and-Policymakers/Energy-Prices/Kerosene/Average-Kerosene-Prices)
 * 
 * 
 * @author Johnatan Gao
 *
 */

public enum Combustible {//debut classe
	
	LOX_LH2("LOX_LH2", 0.16+2.20), //Liquid oxygen - liquid hydrogen, le melange le plus performant en propulsion chimique. Propulseurs couteux a developper, stockage complexe et volume important occupe par l'hydrogene liquide, optimal pour les etages superieurs de lanceurs ; les fortes poussees sont difficiles a obtenir.
	
	KEROSENE_RP1("KEROSENE_RP1", 0.82),//Melange relativement performant permettant d'obtenir des poussees importantes avec des propulseurs moins complexes que le melange LOX-LH. Frequemment utilise sur les lanceurs russes
	
	TOTALE("TOTALE", 0);
	
	private double masse = 0; //La quantite de carburant utilise (en kg)
	private String nom;
	
	/**
	 * Constructeur des carburants 
	 * @param nom  Le nom du carburant
	 * @param prix Le prix du carburant
	 */
	//Johnatan G
	
	Combustible(String nom, double prix) {//debut constructeur
		
		this.setNom(nom);
		this.prix = prix;
		
	}//fin constructeur
	
	/**
	 * Methode qui retourne le prix du carburant
	 * @return prix Le prix du carburant
	 */
	//Johnatan G
	
	public double getPrix() {//debut methode
		return prix;
	}//fin methode

	/**
	 * Methode qui modifie le prix du carburant
	 * @param prix Le prix du carburant
	 */
	//Johnatan G
	
	public void setPrix(double prix) {//debut methode
		this.prix = prix;
	}//fin methode

	/**
	 * Methode qui retourne la masse du carburant
	 * @return masse La masse du carburant
	 */
	//Johnatan G
	
	public double getMasse() {//debut methode
		return masse;
	}//fin methode

	/**
	 * Methode qui modifie le cout total du carburant
	 * @param coutTotal Le cout total du carburant
	 */
	//Johnatan G
	
	public void setCoutTotal(double coutTotal) {//debut methode
		this.coutTotal = coutTotal;
	}//fin methode

	//Variables qui consernent le carburant
	private double prix; //Le prix du carburant par kg de carburant
	private double coutTotal; //Le cout total pour le carburant
	
	/**
	 * Methode qui va pouvoir determiner la masse du carburant utilise et automatiquement determniner le prix
	 * @param masse La masse du carburant
	 */
	//Johnatan G
	
	public void setMasse(double masse) {//debut methode
		
		this.masse = masse;
		this.coutTotal = this.masse * this.prix;
		
	}//fin methode
	
	/**
	 * Methode qui retourne le prix par kg du combustible
	 * @return prix Le prix par kg du combustible
	 */
	//Johnatan G
	
	public double getPrixParKg() {//debut methode
		return this.prix;
	}//fin methode
	
	/**
	 * Methode qui retourne le cout total du combustible
	 * @return coutTotal Le prix du combustible en dollar
	 */
	//Johnatan G
	
	public double getCoutTotal() {//debut methode
		return this.coutTotal;
	}//fin methode

	/**
	 * Methode qui retourne le nom du carburant
	 * @return nom Le nom du carburant
	 */
	//Johnatan G
	
	public String getNom() {//debut methode
		return nom;
	}//fin methode

	/**
	 * Methode qui modifie le nom du carburant
	 * @param nom Le nom du carburant
	 */
	//Johnatan G
	
	public void setNom(String nom) {//debut methode
		this.nom = nom;
	}//fin methode

}//fin classe
