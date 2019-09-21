package objets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;

import modele.ModeleDeDonnees;
import util.MoteurPhysique;
import util.SMath;
import util.Vecteur;

/**
 * Classe de base dont tous les objets derivent pour generaliser certaine methodes et parametres
 * @author Corentin
 * @author Ivana Bera
 * @author Jonathan Gao
 */
public class Objet implements Serializable{//debut classe
	protected double rayon, masse, hauteur;
	protected Vecteur vitesse, acceleration, position;
	protected Shape shapeObjet;
	protected String nom;
	protected Color couleur;
	protected ModeleDeDonnees md;
	
	private transient Image imgObjet;
	
	private double angleDeviation = 0;

	/**
	 * Constructeur de la classe objet
	 * @param masse La masse
	 */
	//Corentin
	public Objet(double masse) {
		position = new Vecteur();
		position.setXYZ(SMath.EPSILON, SMath.EPSILON, SMath.EPSILON);
		vitesse = new Vecteur();
		acceleration = new Vecteur();
		this.masse = masse;
	}
	
	/**
	* Constructeur de la classe objet
	 * @param o L'objet
	 */
	//Jonathan G
	public Objet(Objet o) {
		this.masse = o.getMasse();
		position = o.getPosition();
		vitesse = o.getVitesse();
		acceleration = o.getAcceleration();
		this.shapeObjet = o.getShape();
	}

	/**
	 * Methode de dessin de l'objet
	 * 
	 * @param g2d Contexte graphique
	 * @param af  Matrice de transformation pour le dessin
	 */
	//Corentin
	public void draw(Graphics2D g2d, AffineTransform af) {
		AffineTransform afTemp = new AffineTransform(af);
		Color colorTemp = g2d.getColor();
		g2d.drawImage(
				imgObjet ,
				(int) (afTemp.createTransformedShape(shapeObjet).getBounds2D().getX() ) ,
				(int) afTemp.createTransformedShape(shapeObjet).getBounds2D().getY(), 
				(int)(afTemp.createTransformedShape(shapeObjet).getBounds2D().getWidth()),
				(int) (afTemp.createTransformedShape(shapeObjet).getBounds2D().getHeight()),
				null);
		g2d.setColor(colorTemp);
	}
	
	/**
	 * Methode qui dessine la forme d'un objet
	 * @param g2d Context graphique
	 * @param af  Matrice de transformation pour le dessin
	 */
	//Johnatan G
	
	public void drawForme(Graphics2D g2d, AffineTransform af) {//debut methode
		AffineTransform afTemp = new AffineTransform(af);
		Color colorTemp = g2d.getColor();
		g2d.setColor(couleur);
		g2d.draw(afTemp.createTransformedShape(shapeObjet));
		g2d.setColor(colorTemp);
	}//fin methode

	/**
	 * Update les position de l'objet selon tous les objets environnent.
	 */
	//Corentin
	public void update() {

		if(md != null) {
			ArrayList<Objet> objets = md.getAllObjets();

			Vecteur sommeDesForces = new Vecteur(0, 0, 0);
			for (Objet objet : objets) {
				if (objet != null && !(objet == this)) {
					sommeDesForces = sommeDesForces.additionne(objet.forceGravitation(this));
				}
			}
			Vecteur accel = sommeDesForces.multiplie(1 / this.masse);
			MoteurPhysique.step(md.getDeltaT(), position, vitesse, accel);

		}

		
		
	}

	/**
	 * Retourne le vecteur de force gravitationelle d'une de deux objet.
	 * 
	 * @param objetA Objet d'influence gravitationelle.
	 * @return Vecteur de la force gravitationelle.
	 */
	//Corentin
	public Vecteur forceGravitation(Objet objetA) {
		Vecteur r12 = objetA.getPosition().soustrait(this.position);
		Vecteur force = new Vecteur();
		try {
			
			if(SMath.nearlyEquals(r12.module(), 0)) {
				force = new Vecteur();
			}else {
				force = r12.normalise().multiplie((-SMath.G * this.masse * objetA.masse) / (Math.pow(r12.module(), 2)));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return force;
	}

	/**
	 * Change le nom de l'objet pour un string en parametre
	 * @param nom Nom de l'objet
	 */
	//Corentin
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le nom de l'objet
	 * @return Nom de l'objet
	 */
	//Corentin
	public String getNom() {
		return nom;
	}

	/**
	 * Retourne le shape de la forme
	 * @return Shape de l'objet
	 */
	//Corentin
	public Shape getShape() {
		return shapeObjet;
	}

	/**
	 * Retourne la masse de l'objet
	 * @return Masse de l'objet
	 */
	//Corentin
	public double getMasse() {
		return masse;
	}

	/**
	 * Permet de changer la masse de l'objet
	 * @param masse Masse de l'objet
	 */
	//Corentin
	public void setMasse(double masse) {
		this.masse = masse;
	}

	/**
	 * Retourne la position de l'objet
	 * @return Position de l'objet
	 */
	//Corentin
	public Vecteur getPosition() {
		return position;
	}

	/**
	 * Permet de changer la position d'un objet
	 * @param positionIn Position de l'objet
	 */
	//Corentin
	public void setPosition(Vecteur positionIn) {
		position = positionIn;
	}

	/**
	 * Permet de changer le ModeleDeDonnees utiliser
	 * @param md Modele de donnees que l'objet utilisera pour faire les mise a jours
	 */
	//Corentin
	public void setModeleDeDonnees(ModeleDeDonnees md) {
		this.md = md;
	}

	/**
	 * Retourne le rayon de l'objet
	 * @return Rayon de l'objet
	 */
	//Corentin
	public double getRayon() {
		return rayon;
	}

	/**
	 * Permet de changer le ranyon de l'objet
	 * @param rayon Rayon de l'objet
	 */
	//Corentin
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

	/**
	 * Methode qui donne acces a la hauteur de l'objet
	 * @return Hauteur de l'objet
	 */
	//Ivana
	public double getHauteur() {
		return hauteur;
	}

	/**
	 * Methode qui defini l'hauteur de l'objet
	 * @param hauteur Hauteur de l'objet
	 */
	//Ivana
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * Retourne la couleur utiliser pour le dessin de l'objet
	 * @return Couleur de l'objet lors du dessin
	 */
	//Corentin
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Permet de changer la couleur de dessin de l'objet
	 * @param couleur Couleur utiliser pour le dessin de l'objet
	 */
	//Corentin
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Permet de changer la vitesse de l'objet
	 * @param vitesse Vecteur vitesse de l'objet
	 */
	//Corentin
	public void setVitesse(Vecteur vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * Retourne l'angle de deviation de la fusee
	 * @return Angle de derivation
	 */
	//Jonathan
	public double getAngleDeviation() {
		return angleDeviation;
	}

	/**
	 * Permet de changer l'angle de deviation de la fusee
	 * @param angleDeviation Nouvel angle de derivation
	 */
	//Jonathan
	public void setAngleDeviation(double angleDeviation) {
		this.angleDeviation = angleDeviation;
	}

	
	/**
	 * Retourne la vitesse de l'objet
	 * @return Vitesse de l'objet
	 */
	//Corentin
	public Vecteur getVitesse() {
		return vitesse;
	}

	/**
	 * Retourne l'acceleration de l'objet
	 * @return Acceleration de l'objet
	 */
	//Corentin
	public Vecteur getAcceleration() {
		return acceleration;
	}

	/**
	 * Permet de changer l'acceleration de l'objet
	 * @param acceleration Vecteur acceleration de l'objet
	 */
	//Corentin
	public void setAcceleration(Vecteur acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the imgObjet
	 */
	//Ivana
	public Image getImgObjet() {
		return imgObjet;
	}

	/**
	 * @param imgObjet the imgObjet to set
	 */
	//Ivana
	public void setImgObjet(Image imgObjet) {
		this.imgObjet = imgObjet;
	}

	/**
	 * Methode qui verifie si un point se situe dans la forme de l'objet
	 * @param point Point a verifier
	 * @return Vrai si il est a l'interieur
	 */
	//Johnatan G
	public boolean contains(Vecteur point) {//debut methode
		if (point.getX() >= this.position.getX() && point.getX() <= this.position.getX() + this.rayon * 2) {
			if (point.getY() >= this.position.getY() && point.getY() <= this.position.getY() + this.hauteur) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
	
	/**
	 * Methode qui verifie si une forme se situe dans la forme de l'objet
	 * @param s La forme a verifier
	 * @return Vrai si il est a l'interieur
	 */
	//Johnatan G
	public boolean contains(Shape s) {//debut methode
		Area a1 = new Area(this.shapeObjet);
		Area a2 = new Area(s);
		a1.intersect(a2);
		if(a1.isEmpty()) return false;
		else return true;		
	}
	
	/**
	 * Methode qui verifie si un objet se situe dans la forme de l'objet
	 * @param s La forme a verifier
	 * @return Vrai si il est a l'interieur
	 */
	//Johnatan G
	public boolean contains(Objet o) {//debut methode
		if(contains(o.getShape())) return true;
		else return false;
	}//fin methode
	
	/**
	 * Methode qui fait rotationner les composants de la fusee
	 * @param g2d Contexte graphique
	 * @param angleDeviationEnRadian L'angle de deviation en radian
	 * @param centreFusee Le centre de la fusee
	 */
	//Johnatan G
	
	public void rotate(Graphics2D g2d, double angleDeviationEnRadian, Vecteur centreFusee) {//debut methode
		
		g2d.rotate(angleDeviationEnRadian, centreFusee.getX(), centreFusee.getY());
		
	}//fin methode
	
	
}//fin classe