package objets.celeste;

import java.awt.Color;
import java.io.Serializable;

import objets.Objet;

/**
 * Classe qui instancie un objet celeste
 * @author Corentin
 */
public class ObjetCeleste extends Objet implements Serializable{
	protected double rayonOrbite, rotationInitiale;
	protected String nom;
	protected Color couleurObjet = Color.orange;

	/**
	 * Constructeur d'un objet de type ObjetCeleste
	 * @param rayon Rayon de l'objet (m)
	 * @param masse Masse de l'objet (kg)
	 * @param rayonOrbite Rayon d'orbitation de l'objet (m)
	 * @param rotationInitiale Rotation initiale du cycle orbitale (rad)
	 */
	public ObjetCeleste(double rayon, double masse, double rayonOrbite, double rotationInitiale) {
		super(masse);
		this.rayon = rayon;
		this.rayonOrbite = rayonOrbite;
		this.rotationInitiale = rotationInitiale;
		
	}

	/**
	 * Constructeur d'un objet de type ObjetCeleste
	 * @param rayon Rayon de l'objet (m)
	 * @param masse Masse de l'objet (kg)
	 */
	public ObjetCeleste(double rayon, double masse) {
		super(masse);
		this.rayon = rayon;
	}
	/**
	 * Methode qui modifie la couleur de l'objet celeste
	 * @param couleurObjet la couleur de l'objet
	 */
	public void setCouleurObjet(Color couleurObjet) {
		this.couleurObjet = couleurObjet;
	}
}
