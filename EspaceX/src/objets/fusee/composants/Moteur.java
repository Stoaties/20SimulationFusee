package objets.fusee.composants;

import java.awt.Color;  
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import objets.Objet;
import objets.fusee.composants.enums.SystemeDePropulsion;
import util.Dessinable;
import util.Vecteur;

/**
 * Classe dessinable qui va creer et un dessiner le moteur
 * @author Johnatan Gao
 */

public class Moteur extends Objet implements Dessinable {
	private static final long serialVersionUID = 1L;
//debut classe

	//Variables pour creer le moteur
	private double prix, impulsionSpecifiqueTerrestre, impulsionSpecifiqueVide, 
	vitesseEjectionTerrestre, vitesseEjectionVide, pousseeTerrestre, 
	pousseeVide, diametre, dureeFonctionnement;
	private String Ergols, paysOrigine;

	//Variable qui gere la forme du moteur
	private Rectangle2D.Double shapeMoteur;
	private double scaleMoteurThrust = 1;
	
	private Vecteur centreFusee;
	
	//private boolean dimension=true;

	/**
	 * Constructeur de la classe Moteur qui va creer le moteur
	 */
	//Johnatan G

	public Moteur() {//debut constructeur
		super(0);
	}//fin constructeur

	/**
	 * Constructeur de la classe Moteur qui va creer le moteur
	 * @param propSysteme Le systeme de propulsion 
	 * @param position La position du moteur
	 */
	//Johnatan G

	public Moteur(SystemeDePropulsion propSysteme, Vecteur position) {//debut constructeur
		super(propSysteme.getMasse());

		this.prix = propSysteme.getPrix();
		this.impulsionSpecifiqueTerrestre = propSysteme.getImpulsionSpecifiqueTerrestre();
		this.impulsionSpecifiqueVide = propSysteme.getImpulsionSpecifiqueVide();
		this.vitesseEjectionTerrestre = propSysteme.getVitesseEjectionTerrestre();
		this.vitesseEjectionVide = propSysteme.getVitesseEjectionVide();
		this.pousseeTerrestre = propSysteme.getPousseeTerrestre();
		this.pousseeVide = propSysteme.getPousseeVide();

		this.hauteur = propSysteme.getHauteur();
		this.diametre = propSysteme.getDiametre();

		this.rayon = diametre/2;

		this.dureeFonctionnement = propSysteme.getDureeFonctionnement();

		this.position = position;

		this.nom = propSysteme.getNom();
		this.Ergols = propSysteme.getErgols();
		this.paysOrigine = propSysteme.getPaysOrigine();

		this.setImgObjet(propSysteme.getImgMoteur());

		this.couleur = new Color(1f,0f,0f, 0.4f);

	}//fin constructeur

	/**
	 * Constructeur de la classe Moteur qui va creer le moteur
	 * @param moteurSelectionne Le moteur selecitonne
	 */
	//Johnatan G

	public Moteur(Moteur moteurSelectionne) {//debut constructeur

		super(moteurSelectionne.getMasse());
		this.prix = moteurSelectionne.getPrix();
		this.impulsionSpecifiqueTerrestre = moteurSelectionne.getImpulsionSpecifiqueTerrestre();
		this.impulsionSpecifiqueVide = moteurSelectionne.getImpulsionSpecifiqueVide();
		this.vitesseEjectionTerrestre = moteurSelectionne.getVitesseEjectionTerrestre();
		this.vitesseEjectionVide = moteurSelectionne.getVitesseEjectionVide();
		this.pousseeTerrestre = moteurSelectionne.getPousseeTerrestre();
		this.pousseeVide = moteurSelectionne.getPousseeVide();

		this.hauteur = moteurSelectionne.getHauteur();
		this.diametre = moteurSelectionne.getDiametre();

		this.rayon = diametre/2;

		this.dureeFonctionnement = moteurSelectionne.getDureeFonctionnement();

		this.position = moteurSelectionne.getPosition();

		this.nom = moteurSelectionne.getNom();
		this.Ergols = moteurSelectionne.getErgols();
		this.paysOrigine = moteurSelectionne.getPaysOrigine();
		this.setImgObjet(moteurSelectionne.getImgObjet());
		this.couleur = moteurSelectionne.couleur;

		this.shapeObjet = moteurSelectionne.getShape();

	}//fin constructeur

	/**
	 * Methode qui va permettre le dessinage du moteur dans la scene d'animation
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		shapeMoteur = new Rectangle2D.Double(position.getX(), position.getY(), diametre, hauteur);

		this.shapeObjet = matLocale.createTransformedShape(shapeMoteur);
		
		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		
		g2d.drawImage(this.getImgObjet() ,(int) (this.shapeObjet.getBounds2D().getX() ) ,(int) this.shapeObjet.getBounds2D().getY(), (int)(this.shapeObjet.getBounds2D().getWidth() * this.scaleMoteurThrust), (int) (this.shapeObjet.getBounds2D().getHeight() * this.scaleMoteurThrust), null);
		g2d.setColor(this.couleur);

		g2d.setColor(cInit);
		g2d.setTransform(tInit);

	}//fin methode

	/**
	 * Methode qui permet de dessiner la zone de depot du moteur
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void drawForme(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		shapeMoteur = new Rectangle2D.Double(position.getX(), position.getY(), diametre, hauteur);
		this.shapeObjet = matLocale.createTransformedShape(shapeMoteur);

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.setColor(this.couleur);
		g2d.fill(shapeObjet);	

		g2d.setColor(cInit);
		g2d.setTransform(tInit);

	}//fin methode

	/**
	 * Methode qui dessine les moteurs lors de l'animation
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	public void drawEnAnimation(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		shapeMoteur = new Rectangle2D.Double(position.getX(), position.getY(), diametre, hauteur);

		this.shapeObjet = matLocale.createTransformedShape(shapeMoteur);

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.drawImage(this.getImgObjet() ,(int) (this.shapeObjet.getBounds2D().getX() ) ,(int) this.shapeObjet.getBounds2D().getY(), (int)(this.shapeObjet.getBounds2D().getWidth()), (int) (this.shapeObjet.getBounds2D().getHeight()), null);
		
		
		
		Flammes flammes = new Flammes(position.getX(), -position.getY(), this.rayon , this.hauteur * this.scaleMoteurThrust, getAngleDeviation(), centreFusee);
		flammes.draw(g2d, matLocale);	
		g2d.setColor(cInit);
		g2d.setTransform(tInit);

	}//fin methode

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
	 * Methode qui retourne l'impulsion specifique du moteur vide
	 * @return impulsionSpecifiqueVide L'impulsion specifique du moteur vide
	 */
	//Johnatan G

	public double getImpulsionSpecifiqueVide() {//debut methode
		return impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique du moteur vide
	 * @param impulsionSpecifiqueVide L'impulsion specifique du moteur vide
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
	 * Methode qui retourne la poussee terrestre du moteur
	 * @return pousseeTerrestre La poussee terrestre du moteur
	 */
	//Johnatan G

	public double getPousseeTerrestre() {//debut methode
		return pousseeTerrestre;
	}//fin methode

	/**
	 * Methode qui modifie la poussee terrestre du moteur
	 * @param pousseeTerrestre La poussee terrestre du moteur
	 */
	//Johnatan G

	public void setPousseeTerrestre(double pousseeTerrestre) {//debut methode
		this.pousseeTerrestre = pousseeTerrestre;
	}//fin methode

	/**
	 * Methode qui retourne la poussee du moteur vide
	 * @return pousseeVide La poussee du moteur vide
	 */
	//Johnatan G

	public double getPousseeVide() {//debut methode
		return pousseeVide;
	}//fin methode

	/**
	 * Methode qui modifie la poussee du moteur vide
	 * @param pousseeVide La poussee du moteur vide
	 */
	//Johnatan G

	public void setPousseeVide(double pousseeVide) {//debut methode
		this.pousseeVide = pousseeVide;
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
	 * Methode qui retourne la diametre du moteur
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
	 * @return dureeFonctionnement La duree de fonctionnement  du moteur
	 */
	//Johnatan G

	public double getDureeFonctionnement() {//debut methode
		return dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui modifie la duree de fonctionnement du moteur
	 * @param dureeFonctionnement La duree de fonctionnement  du moteur
	 */
	//Johnatan G

	public void setDureeFonctionnement(double dureeFonctionnement) {//debut methode
		this.dureeFonctionnement = dureeFonctionnement;
	}//fin methode

	/**
	 * Methode qui retourne les ergols du moteur
	 * @return Ergols Les ergols du moteur
	 */
	//Johnatan G

	public String getErgols() {//debut methode
		return Ergols;
	}//fin methode

	/**
	 * Methode qui modifie les ergols du moteur
	 * @param ergols Les ergols du moteur
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
	 * Methode qui retourne le pourcentage de la pousse du moteur
	 * @return Le pourcentage de la pousse du moteur
	 */
	//Johnatan G
	
	public double getScaleMoteurThrust() {//debut methode
		return scaleMoteurThrust;
	}//fin methode

	/**
	 * Methode qui modifie le pourcentage de la pousse du moteur
	 * @param scaleMoteurThrust Le pourcentage de la pousse du moteur
	 */
	//Johnatan G
	
	public void setScaleMoteurThrust(double scaleMoteurThrust) {//debut methode
		this.scaleMoteurThrust = scaleMoteurThrust;
	}//fin methode

	/**
	 * Methode qui retourne le centre de la fusee
	 * @return Le centre de la fusee
	 */
	//Johnatan G
	
	public Vecteur getCentreFusee() {//debut methode
		return centreFusee;
	}//fin methode

	/**
	 * Methode qui modifie le centre de la fusee
	 * @param centreFusee Le centre de la fusee
	 */
	//Johnatan G
	
	public void setCentreFusee(Vecteur centreFusee) {//debut methode
		this.centreFusee = new Vecteur(centreFusee);
	}//fin methode


}//fin classe