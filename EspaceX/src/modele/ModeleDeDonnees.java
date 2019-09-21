package modele;

import java.io.Serializable;
import java.util.ArrayList;

import objets.Objet;
import objets.celeste.Etoile;
import objets.celeste.Planete;
import objets.celeste.Satellite;
import objets.fusee.Fusee;

/**
 * Un objet ModeleAffichage permet de memoriser un ensemble de valeurs pour passer du monde reel vers un composant de dessin dont les 
 * coordonnees sont en pixels. On peut interroger l'objet pour connaitre la matrice de transformation (qui contient un scale), le nombre de pixels par unite, 
 * les dimensions dans le monde reel, etc.
 * 
 * @author Corentin Gouanvic
 *
 */

public class ModeleDeDonnees implements Serializable{//debut classe
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Objet> planetes = new ArrayList<Objet>();
	private ArrayList<Objet> satellites = new ArrayList<Objet>();
	private ArrayList<Objet> etoiles = new ArrayList<Objet>();
	private Fusee fuseeConstruite;
	
	private double tempsSimuler = 0;
	private double deltaT = 1;
	private final double DELTA_T = 500;
	
	private Objet objetSelectionner;

	/**
	 * Constructeur du modele de donnees.
	 */
	public ModeleDeDonnees() {
		
	}
	
	/**
	 * Ajoute du temps au temps simuler.
	 */
	public void ajoutPas() {
		tempsSimuler += deltaT;
		
		
	}
	
	/**
	 * Retourne le temps simuler depuis le depart de l'animation.
	 * @return Retourne le temps simuler.
	 */
	public double getTempsSimuler() {
		return tempsSimuler;
	}
	
	/**
	 * Retourne la liste de planetes.
	 * @return Retourne la liste de planetes.
	 */
	public ArrayList<Objet> getPlanetes() {
		return planetes;
	}
	
	/**
	 * Permet de changer la liste de planetes.
	 * @param planetes Liste de plantes.
	 */
	public void setPlanetes(ArrayList<Objet> planetes) {
		this.planetes = planetes;
	}
	
	/**
	 * Ajoute une planete a la fin de la liste.
	 * @param planete Planete a ajouter.
	 */
	public void addPlanete(Planete planete) {
		planetes.add(planete);
	}

	/**
	 * Retourn tous les objets de type planete, satellite et etoile
	 * @return ArrayList des objet de type planete, satellite et etoile
	 */
	public ArrayList<Objet> getAllObjets() {
		ArrayList <Objet> temp = new ArrayList<Objet>();
		temp.addAll(etoiles);
		temp.addAll(planetes);
		temp.addAll(satellites);

		try {
			if(fuseeConstruite != null) {
				temp.add(fuseeConstruite);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}


		
		
		return temp;
	}

	/**
	 * Changer le pas de temps a pour les prochaines iterations
	 * @param deltaT Le pas de temps a pour les prochaines iterations
	 */
	//Corentin
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}
	
	/**
	 * Retourne le pas de temps courrament utiliser
	 * @return Variable de type long qui quantifie le pas de temps a chaque iteration
	 */
	public double getDeltaT() {
		return deltaT;
	}

	/**
	 * Ajouter une etoile a la liste des objets de type Etoile
	 * @param etoile Etoile a ajouter au modele de donnees
	 */
	public void addEtoile(Etoile etoile) {
		etoiles.add(etoile);
		
	}

	/**
	 * Methode qui modifie l'objet selectionne
	 * @param objetSelectionner L'objet selectionne
	 */
	public void setObjetSelectionner(Objet objetSelectionner) {
		this.objetSelectionner = objetSelectionner;
	}
	
	/**
	 * Retourne le dernier objet selectionner par l'utilisateur
	 * @return Objet selectionner par l'utilisateur
	 */
	public Objet getObjetSelectionner() {
		return objetSelectionner;
	}
	
	/**
	 * Ajouter un satellite a la liste des objets de type Satellite
	 * @param satellite Le satellite
	 */
	public void addSatellite(Satellite satellite) {
		satellites.add(satellite);
	}
	
	
	public Planete getPlaneteTerre() {
		
		for(Objet p: this.planetes) {
			
			if(p.getNom().equalsIgnoreCase("Terre")) {
				return (Planete) p;
			}
			
		}	
	return null;
	}
	
	/**
	 * Permet de changer la fusee enregistree dans le modele de donnees
	 * @param fuseeConstruite Nouvelle fusee
	 */
	public void setFusee(Fusee fuseeConstruite) {
		this.fuseeConstruite = new Fusee(fuseeConstruite);
	}
	
	/**
	 * Supprime l'objet selectionner des arraylist respective
	 */
	public void supprimerObjetSelectionner() {
		for(int i = 0; i < planetes.size();i++) {
			if(planetes.get(i) == objetSelectionner) {
				planetes.remove(i);
			}
		}
		
		for(int i = 0; i < etoiles.size();i++) {
			if(etoiles.get(i) == objetSelectionner) {
				etoiles.remove(i);
			}
		}
		
		for(int i = 0; i < satellites.size();i++) {
			if(satellites.get(i) == objetSelectionner) {
				satellites.remove(i);
			}
		}
	}

	/**
	 * Methode qui donne acces a la liste d'etoiles
	 * @return liste d'etoiles
	 */
	public ArrayList<Objet> getEtoiles() {
		return etoiles;
	}

	/**
	 * Remet le deltaT a la valeur initiale au lancement du programme
	 */
	public void setDeltaTInitiale() {
		deltaT = DELTA_T;
	}
}


