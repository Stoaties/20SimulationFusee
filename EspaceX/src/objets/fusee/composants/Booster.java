package objets.fusee.composants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import objets.Objet;
import objets.fusee.composants.enums.EnumBooster;
import util.Dessinable;
import util.Vecteur;

/**
 * Classe dessinable qui va creer et un dessiner le booster
 * @author Johnatan Gao
 */

public class Booster extends Objet  implements Dessinable{//debut classe

	private static final long serialVersionUID = 1L;

	//Variables pour creer le booster
	private double poussee, dureeFonctionnement, diametre, masseVide, masseRempli, prix, impulsionSpecifiqueTerrestre, impulsionSpecifiqueVide;

	//Variable qui gere la forme du booster
	private RoundRectangle2D.Double boosterShape;

	//Variable qui gere l'image du booster
	private transient Image img = null;

	private double scaleBoosterThrust = 1;
	
	private Vecteur centreFusee;
	private double angleDeviation = 0;
	
	/**
	 * Constructeur de la classe Booster qui va creer le booster
	 * @param booster Le booster
	 * @param position La position du booster
	 */
	//Johnatan G

	public Booster(EnumBooster booster, Vecteur position) {//debut constructeur

		super(booster.getMasseRempli());
		lireImageURL();
		this.poussee = booster.getPoussee();
		this.dureeFonctionnement = booster.getDureeFonctionnement();
		this.hauteur = booster.getHauteur();
		this.diametre = booster.getDiametre();

		this.rayon = diametre/2;

		this.masseVide = booster.getMasseVide();
		this.masseRempli = booster.getMasseRempli();
		this.prix = booster.getPrix();

		this.position = position;

		this.couleur = new Color(1f,0f,0f, 0.4f);

		this.nom = booster.getNom();
		this.impulsionSpecifiqueTerrestre = booster.getImpulsionSpecifiqueTerrestre();
		this.impulsionSpecifiqueVide = booster.getImpulsionSpecifiqueVide();

		this.shapeObjet = boosterShape;

	}//fin constructeur

	/**
	 * Constructeur de la classe Booster qui va creer le booster
	 * @param b Le booster
	 */
	//Johnatan G

	public Booster(Booster b) {//debut constructeur

		super(b.getMasse());
		lireImageURL();
		this.poussee = b.getPoussee();
		this.dureeFonctionnement = b.getDureeFonctionnement();
		this.hauteur = b.getHauteur();

		this.diametre = b.getDiametre();
		this.rayon = b.getRayon();
		this.position = b.getPosition();

		this.masseVide = b.getMasseVide();
		this.masseRempli = b.getMasseRempli();
		this.prix = b.getPrix();
		this.couleur = b.getCouleur();

		this.nom = b.getNom();

		this.shapeObjet = b.getShape();
		this.boosterShape = b.getBoosterShape();

		this.impulsionSpecifiqueTerrestre = b.getImpulsionSpecifiqueTerrestre();
		this.impulsionSpecifiqueVide = b.getImpulsionSpecifiqueVide();

	}//fin constructeur

	/**
	 * Constructeur de la classe Booster qui va creer le booster
	 * 
	 */
	//Johnatan G

	public Booster() {//debut constructeur
		super(0);
		lireImageURL();
	}//fin constructeur

	/**
	 * Methode qui va permettre le dessinage du booster dans la scene d'animation
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform atr = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		this.boosterShape = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 35/atr.getScaleX(), 35/atr.getScaleY());
		this.shapeObjet = atr.createTransformedShape(boosterShape);

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.drawImage(img,(int) (shapeObjet.getBounds2D().getX() ) ,(int) shapeObjet.getBounds2D().getY(), (int)(shapeObjet.getBounds2D().getWidth()), (int) (shapeObjet.getBounds2D().getHeight()), null);
		g2d.setColor(couleur);
		//g2d.fill(shapeObjet);	
		g2d.setColor(cInit);	
		g2d.setTransform(tInit);

	}//fin methode

	/**
	 * Methode qui permet de dessiner la zone de depot du booster
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void drawForme(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform atr = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		this.boosterShape = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 35/atr.getScaleX(), 35/atr.getScaleY());
		this.shapeObjet = atr.createTransformedShape(boosterShape);

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.setColor(couleur);
		g2d.fill(shapeObjet);	
		g2d.setColor(cInit);		
		g2d.setTransform(tInit);

	}//fin methode

	/**
	 * Methode qui dessine le booster lors de l'animation
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void drawEnAnimation(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		this.boosterShape = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 35/matLocale.getScaleX(), 35/matLocale.getScaleY());

		this.shapeObjet = matLocale.createTransformedShape(boosterShape);
		
		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.drawImage(img,(int) (shapeObjet.getBounds2D().getX() ) ,(int) shapeObjet.getBounds2D().getY(), (int)(shapeObjet.getBounds2D().getWidth()), (int) (shapeObjet.getBounds2D().getHeight()), null);

		Flammes flammes = new Flammes(position.getX(), -position.getY(), this.rayon *2, this.hauteur * this.scaleBoosterThrust, getAngleDeviation(), centreFusee);
		flammes.draw(g2d, matLocale);	
		g2d.setColor(cInit);
		g2d.setTransform(tInit);

		//		Fumee fumee = new Fumee(position.getX(), -position.getY(), this.rayon *2, this.hauteur); 
		//		fumee.draw(g2d, matLocale);	
		//		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui retourne la poussee du booster
	 * @return poussee La poussee  du booster
	 */
	//Johnatan G

	public double getPoussee() {//debut methode
		return poussee;
	}//fin methode

	/**
	 * Methode qui modifie a poussee du booster
	 * @param poussee La pousse e du booster
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
		this.rayon = this.diametre/2;
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
	 * Methode qui retourne la forme du booster
	 * @return boosterShape La forme du booster
	 */
	//Johnatan G

	public RoundRectangle2D.Double getBoosterShape() {//debut methode
		return boosterShape;
	}//fin methode

	/**
	 * Methode qui modifie la forme du booster
	 * @param boosterShape La forme du booster
	 */
	//Johnatan G

	public void setBoosterShape(RoundRectangle2D.Double boosterShape) {//debut methode
		this.boosterShape = boosterShape;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique terrestre
	 * @return impulsionSpecifiqueTerrestre L'impulsion specifique terrestre
	 */
	//Johnatan G

	public double getImpulsionSpecifiqueTerrestre() {//debut methode

		return this.impulsionSpecifiqueTerrestre;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique terrestre
	 * @param impulsionSpecifiqueTerrestre L'impulsion specifique terrestre
	 */
	//Johnatan G

	public void setImpulsionSpecifiqueTerrestre(double impulsionSpecifiqueTerrestre) {//debut methode
		this.impulsionSpecifiqueTerrestre = impulsionSpecifiqueTerrestre;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique vide
	 * @return impulsionSpecifiqueVide L'impulsion specifique vide
	 */
	//Johnatan G

	public double getImpulsionSpecifiqueVide() {//debut methode
		return impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique vide
	 * @param impulsionSpecifiqueVide L'impulsion specifique vide
	 */
	//Johnatan G

	public void setImpulsionSpecifiqueVide(double impulsionSpecifiqueVide) {//debut methode
		this.impulsionSpecifiqueVide = impulsionSpecifiqueVide;
	}//fin methode

	/**
	 * Methode qui retourne le pourcentage de la pousse du booster
	 * @return Le pourcentage de la pousse du booster
	 */
	//Johnatan G
	
	public double getScaleBoosterThrust() {//debut methode
		return scaleBoosterThrust;
	}//fin methode

	/**
	 * Methode qui modifie le pourcentage de la pousse du booster
	 * @param scaleBoosterThrust Le pourcentage de la pousse du booster
	 */
	//Johnatan G
	
	public void setScaleBoosterThrust(double scaleBoosterThrust) {//debut methode
		this.scaleBoosterThrust = scaleBoosterThrust;
	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * 
	 */
	//Johnatan G

	private void lireImageURL() {//debut methode
		URL urlImg = getClass().getClassLoader().getResource("booster.png");

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				img= ImageIO.read(urlImg);
			}

			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

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

	/**
	 * Methode qui retourne l'angle de deviation de la fusee
	 * @return angleDeviation l'angle de deviation
	 */
	//Johnatan G
	
	public double getAngleDeviation() {//debut methode
		return angleDeviation;
	}//fin methode

	/**
	 * Methode qui modifie l'angle de deviation de la fusee
	 */
	//Johnatan G
	
	public void setAngleDeviation(double angleDeviation) {//debut methode
		this.angleDeviation = angleDeviation;
	}//fin methode
	
}//fin classe
