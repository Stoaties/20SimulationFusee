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
import objets.fusee.composants.enums.Combustible;
import util.Dessinable;
import util.Vecteur;

/**
 * Classe dessinable qui va creer et un dessiner le reservoir
 * 
 */

/**
 * Enum qui va definir la masse du reservoir inspire du Space Shuttle External Tank selon le volume en se basant sur un modele de la NASA (Space Shuttle External Tank)
 * 
 * **METHODE**
 * Etablir une relation entre la masse du reservoir ainsi que sa capacite
 * 
 * 
 * Space Shuttle External Tank:
 * - Masse (vide): 35426 kg
 * - Masse (remplie): 756446 kg
 * - Masse de liquide maximale: 719116 kg
 * - Prix: 75M (https://www.quora.com/What-was-the-cost-of-a-Space-Shuttle-fuel-tank)
 * 
 *---------------------------------------------------------------------------------------------------------
 *
 * 
 * Fonction lineaire qui determine la quantite de combustible (en kg) / kg de reservoir
 * 
 * y1 = 35426 kg
 * y2 = 756446 kg
 * x1 = 0 kg
 * x2 = 719116 kg
 * 
 * a = (y2-y1)/(x2-x1) = 1.052 kg de "reservoir" / 1kg de liquide
 * 
 * 
 * f(x) = 1.052x
 * 
 * ou 
 * 
 * x = la masse de combustible (en kg)
 * y = la masse totale du reservoir (incluant la masse du combustible)
 * 
 * 
 * ----------------------------------------------------------------------------------------------------------
 * 
 * 
 * Fonction lineaire qui va determiner le prix/kg pour le reservoir
 * 
 * 
 * y = Prix du reservoir
 * x = Masse du reservoir
 * 
 * f(x) = 75M/35427 = 2117$/kg de reservoir
 * 
 * 
 * ----------------------------------------------------------------------------------------------------------
 * 
 * 
 * Fonction lineaire qui va determiner la hauteur et le diametre du reservoir
 * 
 * 
 * y1 = hauteur du reservoir
 * x1 = masse du reservoir
 * 
 * y1 = 47/26535*x1 = 0.0017712455*x1
 * 
 * 
 * y2 = diametre du reservoir
 * x2 = masse du reservoir
 * 
 * y2 = 8.5/26535* x2 = 0.000320331637* x2

 * 
 * ----------------------------------------------------------------------------------------------------------
 * 
 * 
 * 
 * @author Johnatan Gao
 *
 */


public class Reservoir extends Objet implements Dessinable {//debut classe

	private static final long serialVersionUID = 1L;
	//Variables pour creer le reservoir
	private final static double KG_RESERVOIR_PAR_KG_COMBUSTIBLE = 1.052;
	private final double PRIX_PAR_KG_DE_RESERVOIR = 2117;
	private double prixDuReservoir;
	private double masseCombustible;
	private double masseReservoirVide;
	private double diametre;
	
	//Variables qui gerent les dimensions du reservoir
	private final double HAUTEUR_PAR_KG_RESERVOIR = 0.0017712455; //en m
	private final double DIAMETRE_PAR_KG_RESERVOIR = 0.000320331637; // en m

	//Variables qui gerent l'image du reservoir
	private transient Image img = null;

	//Variable qui gere la forme du reservoir
	private RoundRectangle2D.Double formeReservoir;
	
	private double angleDeviation = 0;
	private Vecteur centreFusee;

	/**
	 * Constructeur de la classe Reservoir qui va creer le reservoir
	 * @param combustible Le combustible
	 * @param masseCombustible La masse du combustible
	 * @param position La position du reservoir
	 */
	//Johnatan G

	public Reservoir(Combustible combustible, double masseCombustible, Vecteur position) {//debut constructeur

		super(masseCombustible* KG_RESERVOIR_PAR_KG_COMBUSTIBLE);
		lireImageURL();
		this.setMasseCombustible(masseCombustible);
		combustible.setMasse(masseCombustible);
		setPrixDuReservoir(calculPrixReservoir(masse));

		this.nom = combustible.getNom();

		this.masseReservoirVide = masse-masseCombustible;

		this.hauteur = this.HAUTEUR_PAR_KG_RESERVOIR*masseReservoirVide;
		this.diametre = this.DIAMETRE_PAR_KG_RESERVOIR*masseReservoirVide;

		this.rayon = diametre/2;

		this.couleur = new Color(1f,0f,0f, 0.4f);

		this.position = position;

		formeReservoir = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 25, 25);
		this.shapeObjet = formeReservoir;
		

	}//fin constructeur

	/**
	 * Constructeur de la classe Reservoir qui va creer le reservoir
	 * @param r Le reservoir
	 */
	//Johnatan G

	public Reservoir(Reservoir r) {//debut constructeur

		super(r.getMasse());
		lireImageURL();
		this.masseCombustible = r.getMasseCombustible();
		this.prixDuReservoir = r.getPrixDuReservoir();
		this.nom = r.getNom();

		this.masseReservoirVide = r.getMasseReservoirVide();
		this.hauteur = r.getHauteur();
		this.diametre = r.getDiametre();
		this.rayon = r.getRayon();

		this.couleur = r.getCouleur();
		this.position = r.getPosition();

		this.formeReservoir = r.getFormeReservoir();
		this.shapeObjet = r.getShape();

	}//fin constructeur

	/**
	 * Constructeur de la classe Reservoir qui va creer le reservoir
	 * 
	 */
	//Johnatan G

	public Reservoir() {//debut constructeur
		super(0);
		lireImageURL();
	}//fin constructeur

	/**
	 * Methode qui va permettre le dessinage du reservoir dans la scene d'animation
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();

		formeReservoir = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 32/matLocale.getScaleX(), 32/matLocale.getScaleY());		

		this.shapeObjet = matLocale.createTransformedShape(formeReservoir);

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		g2d.setColor(this.couleur);
		g2d.drawImage(img,(int) (this.shapeObjet.getBounds2D().getX() ) ,(int) this.shapeObjet.getBounds2D().getY(), (int)(this.shapeObjet.getBounds2D().getWidth()), (int) (this.shapeObjet.getBounds2D().getHeight()), null);

		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui permet de dessiner la zone de depot du reservoir
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void drawForme(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform matLocale = new AffineTransform(aff);
		Color cInit = g2d.getColor();

		formeReservoir = new RoundRectangle2D.Double(position.getX(), position.getY(), diametre, hauteur, 32/matLocale.getScaleX(), 32/matLocale.getScaleY());		

		if(centreFusee != null) {
			this.rotate(g2d, getAngleDeviation(), centreFusee);
		}
		
		this.shapeObjet = matLocale.createTransformedShape(formeReservoir);

		g2d.setColor(this.couleur);
		g2d.fill(shapeObjet);	

		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui calcule le prix du reservoir 
	 * @param masseDuReservoir La masse du reservoir
	 * @return PRIX_PAR_KG_DE_RESERVOIR*masseDuReservoir Le prix du reservoir calcule 
	 */
	//Johnatan G

	private double calculPrixReservoir(double masseDuReservoir){//debut methode

		return PRIX_PAR_KG_DE_RESERVOIR*masseDuReservoir;

	}//fin methode

	/**
	 * Methode qui retourne le prix du reservoir
	 * @return prixDuReservoir Le prix du reservoir
	 */
	//Johnatan G

	public double getPrixDuReservoir() {//debut methode
		return prixDuReservoir;
	}//fin methode

	/**
	 * Methode qui modifie le prix du reservoir
	 * @param prixDuReservoir Le prix du reservoir
	 */
	//Johnatan G

	public void setPrixDuReservoir(double prixDuReservoir) {//debut methode
		this.prixDuReservoir = prixDuReservoir;
	}//fin methode

	/**
	 * Methode qui retourne la masse du combustible
	 * @return masseCombustible La masse du combustible
	 */
	//Johnatan G

	public double getMasseCombustible() {//debut methode
		return masseCombustible;
	}//fin methode

	/**
	 * Methode qui modifie la masse du combustible 
	 * @param masseCombustible La masse du combustible 
	 */
	//Johnatan G

	public void setMasseCombustible(double masseCombustible) {//debut methode
		this.masseCombustible = masseCombustible;
	}//fin methode

	/**
	 * Methode qui retourne la masse du reservoir vide
	 * @return masseReservoirVide La masse du reservoir vide
	 */
	//Johnatan G

	public double getMasseReservoirVide() {//debut methode
		return masseReservoirVide;
	}//fin methode

	/**
	 *  Methode qui modifie la masse du reservoir vide
	 * @param masseReservoirVide La masse du reservoir vide
	 */
	//Johnatan G

	public void setMasseReservoirVide(double masseReservoirVide) {//debut methode
		this.masseReservoirVide = masseReservoirVide;
	}//fin methode

	/**
	 * Methode qui retourne la diametre du reservoir
	 * @return diametre La diametre du reservoir
	 */
	//Johnatan G

	public double getDiametre() {//debut methode
		return diametre;
	}//fin methode

	/**
	 * Methode qui modifie le diametre du reservoir
	 * @param diametre Le diametre du reservoir
	 */
	//Johnatan G

	public void setDiametre(double diametre) {//debut methode
		this.diametre = diametre;
		this.rayon = this.diametre/2;
	}//fin methode

	/**
	 * Methode qui retourne la forme du reservoir
	 * @return formeReservoir La forme du reservoir
	 */
	//Johnatan G

	public RoundRectangle2D.Double getFormeReservoir() {//debut methode
		return formeReservoir;
	}//fin methode

	/**
	 * Methode qui modifie la forme du reservoir
	 * @param formeReservoir La forme du reservoir
	 */
	//Johnatan G

	public void setFormeReservoir(RoundRectangle2D.Double formeReservoir) {//debut methode
		this.formeReservoir = formeReservoir;
	}//fin methode

	/**
	 * Methode qui retourne la masse du reservoir par kg de combustible
	 * @return KG_RESERVOIR_PAR_KG_COMBUSTIBLE La masse du reservoir par kg de combustible
	 */
	//Johnatan G

	public double getKgReservoirParKgCombustible() {//debut methode
		return KG_RESERVOIR_PAR_KG_COMBUSTIBLE;
	}//fin methode

	/**
	 * Methode qui retourne le prix par kg de reservoir
	 * @return PRIX_PAR_KG_DE_RESERVOIR Le prix par kg de reservoir
	 */
	//Johnatan G

	public double getPRIX_PAR_KG_DE_RESERVOIR() {//debut methode
		return PRIX_PAR_KG_DE_RESERVOIR;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur par kg de reservoir
	 * @return HAUTEUR_PAR_KG_RESERVOIR La hauteur par kg de reservoir
	 *///Johnatan G

	public double getHAUTEUR_PAR_KG_RESERVOIR() {//debut methode
		return HAUTEUR_PAR_KG_RESERVOIR;
	}//fin methode

	/**
	 * Methode qui retourne le diametre par kg de reservoir 
	 * @return DIAMETRE_PAR_KG_RESERVOIR Le diametre par kg de reservoir
	 */
	//Johnatan G

	public double getDIAMETRE_PAR_KG_RESERVOIR() {//debut methode
		return DIAMETRE_PAR_KG_RESERVOIR;
	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * 
	 */
	//Johnatan Gao

	private void lireImageURL() {//debut methode
		URL urlImg = getClass().getClassLoader().getResource("combustible.png");

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
