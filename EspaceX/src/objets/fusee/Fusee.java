package objets.fusee;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;

import objets.Objet;
import objets.fusee.composants.Booster;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.Reservoir;
import objets.fusee.composants.enums.Combustible;
import objets.fusee.composants.enums.EnumBooster;
import objets.fusee.composants.enums.SystemeDePropulsion;
import util.Constantes;
import util.Dessinable;
import util.MoteurPhysique;
import util.SMath;
import util.Vecteur;

/**
 * (https://www.sciencelearn.org.nz/resources/436-investigating-rockets-key-terms) 
 * 
 * Classe dessinable qui va creer et un dessiner la fusee
 * @author Johnatan Gao
 * @author Melie Leclerc
 *
 */

public class Fusee extends Objet implements Dessinable, Constantes, Serializable{//debut classe

	private static final long serialVersionUID = 1L;
	//Variables qui gerent les listes
	private ArrayList <Moteur> listMoteur;
	private ArrayList <Booster> listBooster;
	private ArrayList <Reservoir> listReservoir;

	//Variables pour le booster
	private int nbBooster = 0;
	private double impulsionSpecifiqueBooster;
	private double masseCombustibleBooster;
	private Vecteur pousseeTotaleBooster;

	//Variables pour les dimensions de l'application
	private double largeurDuMonde, hauteurDuMonde;

	//Variables du moteur
	private double diametreTotalMoteur = 0;
	private double hauteurMaxMoteur = 0;
	private double hauteurReservoir = 0;
	private double hauteurBooster = 0;
	private double impulsionSpecifiqueMoteur;
	private Vecteur pousseeTotaleMoteur;

	//Variables pour le reservoir
	private double rayonReservoir = 0;
	private double masseCombustibleReservoir;

	//Variables qui permettent de realiser les operations de base sur un vecteur Euclidien en deux dimensions (x,y), ou x et y sont les composantes du vecteur
	private Vecteur poids;
	private Vecteur vitesse;
	private Vecteur acceleration;
	private Vecteur deltaPosition;
	private Vecteur resistanceAir;
	private Vecteur sommeDesForces;

	//Variable qui determine si les boosters sont vides
	private boolean estVide = true;
	private double altitude;

	//Variable qui gere le temps ecoule depuis le deollage de la fusee
	private double cptTempsEcoule = 0;

	//Variable qui gere l'angle de deviation de la fusee
	private double angleDeviation = 0;

	private double hauteurDeviation = 0;

	private double boosterThrustScale = 1;
	private double moteurThrustScale = 1;
	private double g;

	private Vecteur forcesDansEspace = new Vecteur();

	private Ellipse2D.Double fuseeDansEspace = new Ellipse2D.Double();
	private Vecteur centreFusee;
	private double aBeta;

	/**
	 * Constructeur de la classe Fusee qui va creer la fusee
	 */
	//Johnatan G

	public Fusee() {//debut constructeur
		super(0);
		listMoteur = new ArrayList<Moteur>();
		listBooster = new ArrayList<Booster>();
		listReservoir = new ArrayList<Reservoir>();

		this.pousseeTotaleBooster = new Vecteur(0, SMath.EPSILON, 0);
		this.pousseeTotaleMoteur = new Vecteur(0, SMath.EPSILON, 0);
		this.sommeDesForces = new Vecteur();
		this.resistanceAir = new Vecteur();

		vitesse = new Vecteur(0, SMath.EPSILON, 0);
		this.position = new Vecteur();
		acceleration = new Vecteur(0, SMath.EPSILON, 0);
		this.poids = new Vecteur(0, -SMath.EPSILON, 0);
		this.deltaPosition = new Vecteur();

	}//fin constructeur

	/**
	 * Constructeur de la classe Fusee qui va creer la fusee
	 * @param f La fusee
	 */
	//Johnatan G

	public Fusee(Fusee f) {//debut constructeur
		super(f.getMasse());
		this.listBooster = new ArrayList<Booster>(f.getListBooster());
		this.listReservoir = new ArrayList <Reservoir>(f.getListReservoir());
		this.listMoteur = new ArrayList <Moteur> (f.getListMoteur());

		this.nbBooster = f.getNbBooster();
		this.impulsionSpecifiqueBooster = f.getImpulsionSpecifiqueBooster();
		this.impulsionSpecifiqueMoteur = f.getImpulsionSpecifiqueMoteur();
		this.pousseeTotaleMoteur = new Vecteur(f.getPousseeTotaleMoteur());
		this.rayonReservoir = f.getRayonReservoir();
		this.rayon = f.getRayon();
		this.masseCombustibleReservoir = f.getMasseCombustibleReservoir();
		this.masseCombustibleBooster = f.getMasseCombustibleBooster();
		this.pousseeTotaleBooster = new Vecteur(f.getPousseeTotaleBooster());
		this.pousseeTotaleMoteur = new Vecteur(f.getPousseeTotaleMoteur());
		this.largeurDuMonde = f.getLargeurDuMonde();
		this.hauteurDuMonde = f.getHauteurDuMonde();
		this.hauteurMaxMoteur = f.getHauteurMaxMoteur();
		this.angleDeviation = f.getAngleDeviation();
		this.sommeDesForces = new Vecteur(f.getSommeDesForces());
		this.poids = new Vecteur(f.getPoids());
		this.position = new Vecteur(f.getPosition());
		this.altitude = f.getAltitude();
		this.acceleration = new Vecteur(f.getAcceleration());
		this.resistanceAir = new Vecteur(f.getResistanceAir());

		this.deltaPosition = new Vecteur(f.getDeltaPosition());

		this.vitesse = new Vecteur(f.getVitesse());

		this.diametreTotalMoteur = f.getDiametreTotalMoteur();

		this.nom = f.getNom();

	}//fin constructeur



	/**
	 * Methode qui va permettre le dessinage de la fusee dans la scene d'animation
	 * 
	 * @param g2d composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform atr = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		if(this.position.getY() < IN_SPACE) {
			for(int a = 0; a < listMoteur.size(); a++) {
				listMoteur.get(a).draw(g2d, atr);
			}

			for(int b = 0; b < listBooster.size(); b++) {
				listBooster.get(b).draw(g2d, atr);
			}		

			for(int c = 0; c < listReservoir.size(); c++) {
				listReservoir.get(c).draw(g2d, atr);
			}
		}else {


			fuseeDansEspace = new Ellipse2D.Double(this.position.getX(), this.position.getY(), 1E9,1E9);
			g2d.setColor(Color.red);
			g2d.fill(atr.createTransformedShape(fuseeDansEspace));	

		}


		g2d.setTransform(tInit);
		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui dessine le booster lors de l'animation
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan Gindex

	public void drawEnAnimation(Graphics2D g2d, AffineTransform aff) {//debut methode

		AffineTransform atr = new AffineTransform(aff);
		Color cInit = g2d.getColor();
		AffineTransform tInit = g2d.getTransform();

		if(this.position.getY() < IN_SPACE) {
			for(int a = 0; a < listMoteur.size(); a++) {
				listMoteur.get(a).drawEnAnimation(g2d, atr);
			}

			for(int b = 0; b < listBooster.size(); b++) {
				listBooster.get(b).drawEnAnimation(g2d, atr);
			}		

			for(int c = 0; c < listReservoir.size(); c++) {
				listReservoir.get(c).draw(g2d, atr);
			}
		}else {


			fuseeDansEspace = new Ellipse2D.Double(this.position.getX(), this.position.getY(), 1E20,1E20);
			g2d.setColor(Color.red);
			g2d.fill(atr.createTransformedShape(fuseeDansEspace));



		}


		g2d.setTransform(tInit);
		g2d.setColor(cInit);

	}//fin methode


	/**
	 * Methode qui va permettre d'effectuer une mise a jour a la position, la vitesse et l'acceletation en effectuant un pas de delta T
	 * @param deltaT Le lapse de temps sur laquelle on evalue la vitesse, la position et l'acceleration
	 */
	//Johnatan G

	public void unPasEuler(double deltaT) {//debut methode

		cptTempsEcoule += 1;

		Vecteur positionAvant = new Vecteur(this.position);

		if(position.getY() <= IN_SPACE) {
			altitude = position.getY();
		}

		g = MoteurPhysique.calculerdGdh( RAYON_TERRE, altitude); //r0 = rayon de la Terre

		this.poids = new Vecteur(0, -this.masse * g, 0);

		Vecteur moduleResistanceAir = MoteurPhysique.calculerResistanceAir(altitude, Math.PI* Math.pow(listReservoir.get(0).getRayon(), 2), vitesse);
		resistanceAir = new Vecteur(moduleResistanceAir.getX() * -vitesse.getX(), moduleResistanceAir.getY() * -vitesse.getY(), 0);

		if(this.masseCombustibleReservoir <= 0) {
			this.pousseeTotaleMoteur = new Vecteur();
		}

		if(this.masseCombustibleBooster <= 0) {
			this.pousseeTotaleBooster = new Vecteur();

			if(estVide) {
				this.masse -= (EnumBooster.SRB.getMasseVide() * listBooster.size()); //Une fois que les boosters n'ont plus de combustible, ils sont ejectes du systeme
				estVide = false; //Pour que la masse soit soustrait seulement une fois
			}
		}

		Vecteur pousseeTotalParMoteur = this.pousseeTotaleMoteur.multiplie(listMoteur.size()).multiplie(this.moteurThrustScale);
		Vecteur pousseeTotalParBooster = this.pousseeTotaleBooster.multiplie(listBooster.size()).multiplie(this.boosterThrustScale);
		
		
		if(position.getY() >= IN_SPACE) {

			
			sommeDesForces = 
					pousseeTotalParMoteur
					.additionne(pousseeTotalParBooster)
					.additionne(forcesDansEspace).additionne(resistanceAir);

		}else {

			sommeDesForces = 
					pousseeTotalParMoteur
					.additionne(pousseeTotalParBooster)
					.additionne(resistanceAir).additionne(poids);
		}
		
		if(position.getY() >= this.hauteurDeviation) {

			centreFusee = new Vecteur(this.position.getX() - this.getRayon(), (this.position.getY() + hauteurMaxMoteur + Math.max(hauteurBooster, hauteurReservoir))/2, 0);
			aBeta = MoteurPhysique.calculerdBetadt(RAYON_TERRE, position.getY(), angleDeviation, vitesse.module());


			sommeDesForces.setXYZ( sommeDesForces.signeVecteurX() * sommeDesForces.module() * Math.cos(Math.toRadians(90)- aBeta)  , sommeDesForces.signeVecteurY() * sommeDesForces.module() * Math.sin(Math.toRadians(90) - aBeta), 0);



			for(Moteur m: listMoteur) {
				m.setAngleDeviation(aBeta);
				m.setCentreFusee(centreFusee);
			}

			for(Reservoir r: listReservoir) {
				r.setAngleDeviation(aBeta);
				r.setCentreFusee(centreFusee);
			}

			for(Booster b: listBooster) {
				b.setAngleDeviation(aBeta);
				b.setCentreFusee(centreFusee);
			}
		}
		
		

		MoteurPhysique.misAJourAcceleration(sommeDesForces, this.masse, acceleration);
		MoteurPhysique.unPasEuler(deltaT, position, vitesse, acceleration);

		double dmReservoir = MoteurPhysique.calculerdMdt(this.pousseeTotaleMoteur.module()  , this.impulsionSpecifiqueMoteur) * listMoteur.size() * this.moteurThrustScale * deltaT;
		double dmBooster = (EnumBooster.SRB.getMasseRempli() - EnumBooster.SRB.getMasseVide())/EnumBooster.SRB.getDureeFonctionnement() * listBooster.size() * this.boosterThrustScale * deltaT;

		if(dmReservoir != 0 && this.masseCombustibleReservoir >= 0) {
			this.masseCombustibleReservoir -= dmReservoir;
			this.masse -= dmReservoir;
		}

		if(dmBooster != 0 && this.masseCombustibleBooster >= 0) {
			this.masseCombustibleBooster -= dmBooster;
			this.masse -= dmBooster;
		}	

		deltaPosition = new Vecteur(this.position.soustrait(positionAvant));	

		deplacementFusee(deltaPosition);

		/*
		System.out.println("-----------------------------------------------------------");

		System.out.println("Somme des force: "+ sommeDesForces);
		System.out.println("pousseeTotale moteur: "+this.pousseeTotaleMoteur);
		System.out.println("pousseTotale booster: "+ this.pousseeTotaleBooster);
		System.out.println("Vitesse:"+ this.vitesse);
		System.out.println("Resistance air "+ resistanceAir.toString());
		System.out.println("deltaPosition "+ deltaPosition.toString());
		System.out.println("acceleration: "+ this.acceleration);
		System.out.println("dmReservoir: "+ dmReservoir);
		System.out.println("dmBooster: "+ dmBooster);
		System.out.println("poids "+ poids.toString());
		System.out.println("altitude "+altitude);
		System.out.println("masseCombustibleBooster " + this.masseCombustibleBooster);
		System.out.println("masseCombustibleReservoir " + this.masseCombustibleReservoir);
		System.out.println("masse "+ this.masse);
		 */

	}//fin methode

	/**
	 * Methode qui ajoute un moteur dans la liste de moteurs
	 * @param m Le moteur choisi
	 */
	//Johnatan G

	private void addListMoteur(Moteur m) {//debut methode

		listMoteur.add(new Moteur(m));

		this.diametreTotalMoteur += m.getRayon()*2;
		this.hauteurMaxMoteur = listMoteur.get(0).getHauteur();

		for(int n = 0; n < listMoteur.size(); n++) {

			hauteurMaxMoteur = Math.max(hauteurMaxMoteur, listMoteur.get(n).getHauteur());

		}

		resetPositionMoteur();

		this.masse += m.getMasse(); //ajouter la masse du moteur a la masse totale
		this.impulsionSpecifiqueMoteur = m.getImpulsionSpecifiqueTerrestre();
		this.pousseeTotaleMoteur = new Vecteur(0, m.getPousseeTerrestre(), 0);


	}//fin methode

	/**
	 * Methode qui enleve un moteur de la liste de moteurs
	 * @param m Le moteur a enlever
	 */
	//Johnatan G

	private void removeListMoteur(Moteur m) {//debut methode

		listMoteur.remove(m);


		this.diametreTotalMoteur -= m.getRayon()*2;

		if(listMoteur.size() == 0) {

			this.diametreTotalMoteur = 0;
			this.hauteurMaxMoteur = 0;
			this.impulsionSpecifiqueMoteur = 0;
			this.pousseeTotaleMoteur = new Vecteur();

		}else{

			this.hauteurMaxMoteur = listMoteur.get(0).getHauteur();

			for(int n = 0; n < listMoteur.size(); n++) {
				hauteurMaxMoteur = Math.max(hauteurMaxMoteur, listMoteur.get(n).getHauteur());
			}
		}

		this.masse -= m.getMasse();

		resetPositionMoteur();

	}//fin methode

	/**
	 * Methode qui repositionne le moteur
	 * 
	 */
	//Johnatan G

	private void resetPositionMoteur() {//debut methode

		for(int n = 0; n < this.listMoteur.size(); n++) {

			if(n == 0) {
				listMoteur.get(n).setPosition(new Vecteur(this.largeurDuMonde/2-diametreTotalMoteur/2, 0, 0));
			}else {
				listMoteur.get(n).setPosition(new Vecteur(listMoteur.get(n-1).getPosition().getX() + listMoteur.get(n-1).getDiametre(), 0, 0));
			}


		}	

		if(listReservoir.size() != 0) {	
			listReservoir.get(0).setPosition(new Vecteur(-listReservoir.get(0).getRayon() ,  this.hauteurMaxMoteur , 0));	

		}


	}//fin methode

	/**
	 * Methode qui repositionne les composants de la fusee
	 * 
	 */
	//Johnatan G

	public void resetPositionComposant() {//debut methode

		resetPositionMoteur();
		resetPositionBooster();

	}//fin methode

	/**
	 * Methode qui ajoute un booster dans la liste de boosters
	 * @param b Le booster choisi
	 */
	//Johnatan G

	private void addListBooster(Booster b) {//debut methode

		this.listBooster.add(b);

		this.nbBooster += 1;

		this.masse += b.getMasse(); //ajoute la  masse du moteur
		this.masseCombustibleBooster += (b.getMasseRempli()-b.getMasseVide()); //ajoute la masse de combustible du booster

		this.hauteurBooster = b.getHauteur();

		this.pousseeTotaleBooster = new Vecteur(0, b.getPoussee(), 0);
		this.setImpulsionSpecifiqueBooster(b.getImpulsionSpecifiqueTerrestre());

		resetPositionComposant();

	}//fin methode

	/**
	 * Methode qui enleve un booser de la liste de boosters
	 * @param b Le booster a enlever
	 */
	//Johnatan G

	private void removeListBooster(Booster b) {//debut methode

		listBooster.remove(b);

		this.nbBooster -= 1;
		resetPositionBooster();

		if(nbBooster == 0) {	
			this.setImpulsionSpecifiqueBooster(0);
			this.pousseeTotaleBooster = new Vecteur();
			this.hauteurBooster = 0;
		}

		this.masse -= b.getMasse(); //enleve la masse du moteur
		this.masseCombustibleBooster -= (b.getMasseRempli()-b.getMasseVide()); //ajoute la masse du combustible du booster

	}//fin methode

	/**
	 * Methode qui ajoute un reservoir dans la liste de reservoirs
	 * @param r Le reservoir choisi
	 */
	//Johnatan G

	private void addListReservoir(Reservoir r) {//debut methode	

		if(listReservoir.size() >= 1) {
			System.out.println("On ne peut pas avoir plus que un reservoir");
		}else {
			this.listReservoir.add(r);		
			this.rayonReservoir = r.getRayon();
			this.hauteurReservoir = r.getHauteur();

		}

		resetPositionBooster();

		this.masse += r.getMasse();
		this.masseCombustibleReservoir += r.getMasseCombustible();

	}//fin methode

	/**
	 * Methode qui enleve un reservoir de la liste de reservoirs
	 * @param r Le reservoir a enlever
	 */
	//Johnatan G

	private void removeListReservoir(Reservoir r) {//debut methode

		listReservoir.remove(r);

		this.rayonReservoir = 0;
		this.hauteurReservoir = 0;
		resetPositionBooster();

		this.masse -= r.getMasse();
		this.masseCombustibleReservoir -= r.getMasseCombustible();

	}//fin methode

	/**
	 * Methode qui repositionne le booster de la fusee
	 * 
	 */
	//Johnatan G

	private void resetPositionBooster() {//debut methode

		if(listBooster.size() != 0) {
			for(int n = 0; n < this.nbBooster ; n++) {
				if(n % 2 == 0) {
					listBooster.get(n).setPosition(new Vecteur(-2*listBooster.get(n).getRayon()-this.rayonReservoir-n*this.listBooster.get(n).getRayon(),  hauteurMaxMoteur/2, 0));
				}else {
					listBooster.get(n).setPosition(new Vecteur(-listBooster.get(n).getRayon()+this.rayonReservoir+ n* listBooster.get(n).getRayon(),  hauteurMaxMoteur/2, 0));
				}
			}
		}

	}//fin methode

	/**
	 *Methode qui retourne le diametre total du moteur de la fusee
	 * @return diametreTotalMoteur Le diametre total du moteur de la fusee
	 */
	//Johnatan G

	public double getDiametreTotalMoteur() {//debut methode
		return diametreTotalMoteur;
	}//fin methode

	/**
	 * Methode qui modifie le diametre totale du moteur de la fusee
	 * @param diametreTotalMoteur Le diametre totale du moteur de la fusee 
	 */
	//Johnatan G

	public void setDiametreTotalMoteur(double diametreTotalMoteur) {//debut methode
		this.diametreTotalMoteur = diametreTotalMoteur;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur maximale du moteur de la fusee
	 * @return hauteurMaxMoteur La hauteur maximale du moteur de la fusee
	 */
	//Johnatan G

	public double getHauteurMaxMoteur() {//debut methode
		return hauteurMaxMoteur;
	}//fin methode

	/**
	 * Methode qui modifie la hauteur maximale du moteur de la fusee
	 * @param hauteurMaxMoteur La hauteur maximale du moteur de la fusee 
	 */
	//Johnatan G

	public void setHauteurMaxMoteur(double hauteurMaxMoteur) {//debut methode
		this.hauteurMaxMoteur = hauteurMaxMoteur;
	}//fin methode

	/**
	 * Methode qui retourne le nombre de boosters dans la fusee
	 * @return nbBooster Le nombre de boosters dans la fusee
	 */
	//Johnatan G

	public int getNbBooster() {//debut methode
		return nbBooster;
	}//fin methode

	/**
	 * Methode qui mofifie le nombre de booster dans la fusee
	 * @param nbBooster Le nombre de booster dans la fusee
	 */
	//Johnatan G

	public void setNbBooster(int nbBooster) {//debut methode
		this.nbBooster = nbBooster;
	}//fin methode

	/**
	 * Methode qui retourne la liste de de moteurs
	 * @return listMoteur La liste de de moteurs
	 */
	//Johnatan G

	public ArrayList<Moteur> getListMoteur() {//debut methode
		return listMoteur;
	}//fin methode

	/**
	 * Methode qui modifie la liste de moteurs
	 * @param listMoteur La liste de moteur 
	 */
	//Johnatan G

	public void setListMoteur(ArrayList<Moteur> listMoteur) {//debut methode
		this.listMoteur = listMoteur;
	}//fin methode

	/**
	 * Methode qui retourne la liste de boosters
	 * @return listBooster La liste de boosters
	 */
	//Johnatan G

	public ArrayList<Booster> getListBooster() {//debut methode
		return listBooster;
	}//fin methode

	/**
	 * Methode qui modifie la liste de boosters
	 * @param listBooster La liste de boosters
	 */
	//Johnatan G

	public void setListBooster(ArrayList<Booster> listBooster) {//debut methode
		this.listBooster = listBooster;
	}//fin methode

	/**
	 * Methode qui retourne la liste de reservoirs
	 * @return listReservoir La liste de reservoirs
	 */
	//Johnatan G

	public ArrayList<Reservoir> getListReservoir() {//debut methode
		return listReservoir;
	}//fin methode

	/**
	 * Methode qui modifie la liste de reservoirs
	 * @param listReservoir La liste de reservoirs
	 */
	//Johnatan G

	public void setListReservoir(ArrayList<Reservoir> listReservoir) {//debut methode
		this.listReservoir = listReservoir;
	}//fin methode

	/**
	 * Methode qui retourne le rayon du reservoir
	 * @return rayonReservoir Le rayon du reservoir
	 */
	//Johnatan G

	public double getRayonReservoir() {//debut methode
		return rayonReservoir;
	}//fin methode

	/**
	 * Methode qui modifie le rayon du reservoir
	 * @param rayonReservoir Le rayon du reservoir
	 */
	//Johnatan G

	public void setRayonReservoir(double rayonReservoir) {//debut methode
		this.rayonReservoir = rayonReservoir;
	}//fin methode

	/**
	 * Methode qui retourne la masse du combustible reservoir
	 * @return masseCombustibleReservoir La masse du combustible reservoir
	 */
	//Johnatan G

	public double getMasseCombustibleReservoir() {//debut methode
		return masseCombustibleReservoir;
	}//fin methode

	/**
	 * Methode qui modifie la masse du combustible reservoir
	 * @param masseCombustibleReservoir La masse du combustible reservoir
	 */
	//Johnatan G

	public void setMasseCombustibleReservoir(double masseCombustibleReservoir) {//debut methode
		this.masseCombustibleReservoir = masseCombustibleReservoir;
	}//fin methode

	/**
	 * Methode qui retourne la masse du combustible booster
	 * @return masseCombustibleBooster La masse du combustible booster
	 */
	//Johnatan G

	public double getMasseCombustibleBooster() {//debut methode
		return masseCombustibleBooster;
	}//fin methode

	/**
	 * Methode qui modifie la masse du combustible booster 
	 * @param masseCombustibleBooster La masse du combustible booster 
	 */
	//Johntan G

	public void setMasseCombustibleBooster(double masseCombustibleBooster) {//debut methode
		this.masseCombustibleBooster = masseCombustibleBooster;
	}//fin methode


	/**
	 * Methode qui retourne la vitesse de la fusee en x
	 * @return vitesse La vitesse de la fusee en y
	 */
	//Johnatan G

	public double getVitesseX() {//debut methode
		return vitesse.getX();
	}//fin methode

	/**
	 * Methode qui retourne la vitesse de la fusee en y
	 * @return vitesse La vitesse de la fusee en y
	 */
	//Johnatan G
	
	public double getVitesseY() {
		return vitesse.getY();
	}

	/**
	 * Methode qui modifie la vitesse de la fusee
	 * @param vitesse La vitesse de la fusee
	 */
	//Johnatan G

	public void setVitesse(Vecteur vitesse) {//debut methode
		this.vitesse = vitesse;
	}//fin methode

	/**
	 * Methode qui retourne la position de la fusee
	 * @return position La position de la fusee
	 */
	//Johnatan G

	public Vecteur getPosition() {//debut methode
		return position;
	}//fin methode

	/**
	 * Methode qui modifie la position de la fusee
	 * @param position La position de la fusee
	 */
	//Johnatan G

	public void setPosition(Vecteur position) {//debut methode
		this.position = position;
	}//fin methode

	/**
	 * Methode qui retourne l'acceleration en y de la fusee
	 * @return acceleration.getY() L'acceleration en y de la fusee
	 */
	//Johnatan G

	public double getAccelerationY() {//debut methode
		return acceleration.getY();	
	}//fin methode

	/**
	 * Methode qui retourne l'acceleration en y de la fusee
	 * @return acceleration.getX() L'acceleration en x de la fusee
	 */
	//Johnatan G

	public double getAccelerationX() {//debut methode
		return acceleration.getX();
	}//fin methode

	/**
	 * Methode qui modifie l'acceleration de la fusee
	 * @param acceleration L'acceleration de la fusee
	 */
	//Johnatan G

	public void setAcceleration(Vecteur acceleration) {//debut methode
		this.acceleration = acceleration;
	}//fin methode

	/**
	 * Methode qui retourne le deplacement de la fusee
	 * @return deltaPosition Le deplacement de la fusee
	 */
	//Johnatan G

	public Vecteur getDeltaPosition() {//debut methode
		return deltaPosition;
	}//fin methode

	/**
	 * Methode qui modifie le deplacement de la fusee
	 * @param deltaPosition Le deplacement de la fusee
	 */
	//Johnatan G

	public void setDeltaPosition(Vecteur deltaPosition) {//debut methode
		this.deltaPosition = deltaPosition;
	}//fin methode

	/**
	 * Methode qui retourne la resistance de l'air
	 * @return resistanceAir La resistance de l'air
	 */
	//Johnatan G

	public Vecteur getResistanceAir() {//debut methode
		return resistanceAir;
	}//fin methode

	/**
	 * Methode qui modifie la resistance de l'air
	 * @param resistanceAir La resistance de l'air
	 */
	//Johnatan G

	public void setResistanceAir(Vecteur resistanceAir) {//debut methode
		this.resistanceAir = resistanceAir;
	}//fin methode

	/**
	 * Methode qui retourne la somme des forces en Y
	 * @return sommeDesForces.getY() La somme des forces en Y
	 */
	//Johnatan G

	public double getSommeDesForcesY() {//debut methode

		return sommeDesForces.getY();

	}//fin methode

	/**
	 * Methode qui retourne la somme des forces en X
	 * @return sommeDesForces.getX() La somme des forces en X
	 */

	public double getSommeDesForcesX() {//debut methode
		return sommeDesForces.getX();
	}//fin methode

	/**
	 * Methode qui modifie la somme des forces
	 * @param sommeDesForces La somme des forces
	 */
	//Johnatan G
	public void setSommeDesForces(Vecteur sommeDesForces) {//debut methode
		this.sommeDesForces = sommeDesForces;
	}//fin methode

	/**
	 * Methode qui retourne l'altitude
	 * @return altitude L'altitude 
	 */
	//Johnatan G
	public double getAltitude() {//debut methode
		return altitude;
	}//fin methode

	/**
	 * Methode qui modifie l'altitude
	 * @param altitude L'altitude de la fusee
	 */
	//Johnatan G
	public void setAltitude(double altitude) {//debut methode
		this.altitude = altitude;
	}//fin methode

	/**
	 * Methode qui retourne l'impulsion specifique du booster
	 * @return impulsionSpecifiqueBooster L'impulsion specifique du booster
	 */
	//Johnatan G
	public double getImpulsionSpecifiqueBooster() {//debut methode
		return impulsionSpecifiqueBooster;
	}//fin methode

	/**
	 * Methode qui modifie l'impulsion specifique du booster
	 * @param impulsionSpecifiqueBooster L'impulsion specifique du booster
	 */
	//Johnatan G
	public void setImpulsionSpecifiqueBooster(double impulsionSpecifiqueBooster) {//debut methode
		this.impulsionSpecifiqueBooster = impulsionSpecifiqueBooster;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur du monde
	 * @return hauteurDuMonde La hauteur du monde
	 */
	//Johnatan G
	public double getHauteurDuMonde() {//debut methode
		return hauteurDuMonde;
	}//fin methode

	/**
	 * Methode qui modifie la hauteur du monde
	 * @param hauteurDuMonde a hauteur du monde
	 */
	//Johnatan G
	public void setHauteurDuMonde(double hauteurDuMonde) {//debut methode
		this.hauteurDuMonde = hauteurDuMonde;
	}//fin methode

	/**
	 * Methode qui retourne l'angle de deviation de la fusee
	 * @return angleDeviation L'angle de deviation de la fusee
	 *
	 */
	//Johnatan G
	public double getAngleDeviation() {//debut methode
		return angleDeviation;
	}//fin methode

	/**
	 * Methode qui modifie l'angle de deviation de la fusee
	 * @param angleDeviation L'angle de deviation de la fusee
	 */
	//Johnatan G
	public void setAngleDeviation(double angleDeviation) {//debut methode
		this.angleDeviation = Math.toRadians(angleDeviation);

	}//fin methode

	/**
	 * Methode qui retourne la poussee totale du booster
	 * @return pousseeTotaleBooster La poussee totale du booster
	 */
	//Johnatan G

	public Vecteur getPousseeTotaleBooster() {//debut methode
		return pousseeTotaleBooster;
	}//fin methode

	/**
	 * Methode qui modifie la poussee totale du booster
	 * @param pousseeTotaleBooster La poussee totale du booster
	 */
	//Johnatan G

	public void setPousseeTotaleBooster(Vecteur pousseeTotaleBooster) {//debut methode
		this.pousseeTotaleBooster = pousseeTotaleBooster;
	}//fin methode

	/**
	 * Methode qui retourne la largeur du monde
	 * @return largeurDuMonde La largeur du monde
	 */
	//Johnatan G

	public double getLargeurDuMonde() {//debut methode
		return largeurDuMonde;
	}//fin methode

	/**
	 * Methode qui modifie la largeur du monde
	 * @param largeurDuMonde La largeur du monde
	 */
	//Johnatan G

	public void setLargeurDuMonde(double largeurDuMonde) {//debut methode
		this.largeurDuMonde = largeurDuMonde;
	}//fin methode

	/**
	 * Methode qui retourne l'impulstion specifique du moteur
	 * @return impulsionSpecifiqueMoteur L'impulstion specifique du moteur
	 */	
	//Johnatan G

	public double getImpulsionSpecifiqueMoteur() {//debut methode
		return impulsionSpecifiqueMoteur;
	}//fin methode

	/**
	 * Methode qui modifie l'impulstion specifique du moteur
	 * @param impulsionSpecifiqueMoteur L'impulstion specifique du moteur
	 */
	//Johnatan G

	public void setImpulsionSpecifiqueMoteur(double impulsionSpecifiqueMoteur) {//debut methode
		this.impulsionSpecifiqueMoteur = impulsionSpecifiqueMoteur;
	}//fin methode

	/**
	 * Methode qui retourne la poussee totale du moteur
	 * @return pousseeTotaleMoteur La poussee totale du moteur
	 */
	//Johnatan G

	public Vecteur getPousseeTotaleMoteur() {//debut methode
		return pousseeTotaleMoteur;
	}//fin methode

	/**
	 * Methode qui modifie la poussee totale du moteur
	 * @param pousseeTotaleMoteur La poussee totale du moteur
	 */
	//Johnatan G

	public void setPousseeTotaleMoteur(Vecteur pousseeTotaleMoteur) {//debut methode
		this.pousseeTotaleMoteur = pousseeTotaleMoteur;
	}//fin methode

	/**
	 * Methode qui retourne le poids de la fusee
	 * @return poids Le poids de la fusee
	 */
	//Johnatan G

	public Vecteur getPoids() {//debut methode
		return poids;
	}//fin methode

	/**
	 * Methode qui modifie le poids de la fusee
	 * @param poids Le poids de la fusee
	 */
	//Johnatan G

	public void setPoids(Vecteur poids) {//debut methode
		this.poids = poids;
	}//fin methode

	/**
	 * Methode qui retourne la somme des forces dans l'Espace
	 * @return La somme des forces dans l'Espace
	 */
	//Johnatan G

	public Vecteur getForcesDansEspace() {//debut methode
		return forcesDansEspace;
	}//fin methode

	/**
	 * Methode qui modifie la somme des forces dans l'Espace
	 * @param forcesDansEspace La somme des forces dans l'Espace
	 */
	//Johnatan G

	public void setForcesDansEspace(Vecteur forcesDansEspace) {//debut methode
		this.forcesDansEspace = forcesDansEspace;
	}//fin methode

	/**
	 * Methode qui retourne la hauteur de la fusee a laquelle elle devie
	 * @return La hauteur de la fusee a laquelle elle devie
	 */
	//Johnatan G

	public double getHauteurDeviation() {//debut methode
		return hauteurDeviation;
	}//fin methode
	/**
	 * Methode qui modifie la hauteur de la fusee a laquelle elle devie
	 * @param hauteurDeviation La hauteur de la fusee a laquelle elle devie
	 */
	//Johnatan G

	public void setHauteurDeviation(double hauteurDeviation) {//debut methode
		this.hauteurDeviation = hauteurDeviation;
	}//fin methode
	/**
	 * Methode qui retourne le pourcentage de la pousse du booster
	 * @return  Le pourcentage de la pousse du booster
	 */
	//Johnatan G

	public double getBoosterThrustScale() {//debut methode
		return boosterThrustScale;
	}//fin methode
	/**
	 * Methode qui modifie le pourcentage de la pousse du booster
	 * @param boosterThrustScale Le pourcentage de la pousse du booster
	 */
	//Johnatan G

	public void setBoosterThrustScale(double boosterThrustScale) {//debut methode
		this.boosterThrustScale = boosterThrustScale/100;

		for(int indexBooster = 0; indexBooster < this.listBooster.size(); indexBooster++) {		
			listBooster.get(indexBooster).setScaleBoosterThrust(this.boosterThrustScale);
		}


	}//fin methode
	/**
	 * Methode qui retourne le pourcentage de la pousse du moteur
	 * @return  Le pourcentage de la pousse du moteur
	 */
	//Johnatan G

	public double getMoteurThrustScale() {//debut methode
		return moteurThrustScale;
	}
	/**
	 * Methode qui modifie le pourcentage de la pousse du moteur
	 * @param moteurThrustScale Le pourcentage de la pousse du moteur
	 */
	//Johnatan G

	public void setMoteurThrustScale(double moteurThrustScale) {//debut methode
		this.moteurThrustScale = moteurThrustScale/100;

		for(int indexMoteur = 0; indexMoteur < this.listMoteur.size(); indexMoteur++) {
			listMoteur.get(indexMoteur).setScaleMoteurThrust(this.moteurThrustScale);
		}

	}//fin methode

	/**
	 * Methode qui retourne si un composant de la fusee est vide
	 * @return estVide Vrai si c'est vide
	 */
	//Johnatan G

	public boolean isEstVide() {//debut methode
		return estVide;
	}//fin methode

	/**
	 * Methode qui modifie si un composant de la fusee est vide
	 * @param estVide Vrai si c'est vide 
	 */
	//Johnatan G

	public void setEstVide(boolean estVide) {//debut methode
		this.estVide = estVide;
	}//fin methode

	/**
	 * Methode qui retourne le temps ecoule depuis le decollage de la fusee
	 * @return cptTempsEcoule Le temps ecoule depuis le decollage de la fusee
	 */
	//Johnatan G

	public double getCptTempsEcoule() {//debut methode
		return cptTempsEcoule;
	}//fin methode

	/**
	 * Methode qui modifie le temps ecoule depuis le decollage de la fusee 
	 * @param cptTempsEcoule Le temps ecoule depuis le decollage de la fusee
	 */
	//Johnatan G

	public void setCptTempsEcoule(double cptTempsEcoule) {//debut methode
		this.cptTempsEcoule = cptTempsEcoule;
	}//fin methode

	/**
	 * Methode qui retourne la vitesse de la fusee
	 * @return vitesse La vitesse de la fusee
	 */
	//Johnatan G

	public Vecteur getVitesse() {//debut methode
		return vitesse;
	}//fin methode

	/**
	 * Methode qui retourne l'acceleration de la fusee
	 * @return acceleration L'acceleration de la fusee 
	 */
	//Johnatan G

	public Vecteur getAcceleration() {//debut methode
		return acceleration;
	}//fin methode

	/**
	 * Methode qui retourne la somme des forces
	 * @return sommeDesForces La somme des forces
	 */
	//Johnatan G

	public Vecteur getSommeDesForces() {//debut methode
		return sommeDesForces;
	}//fin methode

	/**
	 * Methode qui gere si les composants font partie du point a verifier
	 * @param point Le point a verifier
	 * @return vrai si le point est contenu sinon faux
	 */
	//Johnatan G

	public boolean contains(Vecteur point) {//debut methode

		for(int n = 0; n < listMoteur.size(); n++) {			
			if(listMoteur.get(n).contains(point)) {
				return true;
			}	
		}

		for(int n = 0; n < listBooster.size(); n++) {
			if(listBooster.get(n).contains(point)) {
				return true;
			}
		}

		for(int n = 0; n < listReservoir.size(); n++) {
			if(listReservoir.get(n).contains(point)) {
				return true;
			}
		}

		return false;

	}//fin methode

	/**
	 * Methode qui verifie si l'objet est contenu dans la liste
	 * @param o L'objet
	 * @return Vrai s'il est contenu dans la liste
	 */
	//Johnatan G

	public boolean contains(Objet o) {//debut methode

		for(int n = 0; n < listMoteur.size(); n++) {			
			if(listMoteur.get(n).contains(o)) {
				return true;
			}	
		}

		for(int n = 0; n < listBooster.size(); n++) {
			if(listBooster.get(n).contains(o)) {
				return true;
			}
		}

		for(int n = 0; n < listReservoir.size(); n++) {
			if(listReservoir.get(n).contains(o)) {
				return true;
			}
		}

		return false;

	}//fin methode



	/**
	 * Methode qui retourne le moteur de la fusee
	 * @param point Le point a verifier
	 * @return listMoteur.get(n) Le moteur a retourner
	 * @return listBooster.get(n) Le booster a retourner
	 * @return listReservoir.get(n) Le reservoir a retourner
	 */
	//Johnatan G

	public Objet returnObjet(Vecteur point) {//debut methode

		for(int n = 0; n < listMoteur.size(); n++) {			
			if(listMoteur.get(n).contains(point)) {
				return listMoteur.get(n);
			}	
		}

		for(int n = 0; n < listBooster.size(); n++) {
			if(listBooster.get(n).contains(point)) {
				return listBooster.get(n);
			}
		}

		for(int n = 0; n < listReservoir.size(); n++) {
			if(listReservoir.get(n).contains(point)) {
				return listReservoir.get(n);
			}
		}

		return null;

	}//fin methode

	/**
	 * Methode qui modifie l'objet d'un composant de la fusee
	 * @param o L'objet d'un composant de la fusee
	 */
	//Johnatan G

	public void setObjet(Objet o) {//debut methode

		if(o instanceof Moteur) {
			for(int n = 0; n < listMoteur.size(); n++) {			
				if(listMoteur.get(n).equals((Moteur) o)) {
					listMoteur.set(n, (Moteur)o);
				}	
			}
		}else 
			if(o instanceof Booster) {
				for(int n = 0; n < listBooster.size(); n++) {
					if(listBooster.get(n).equals((Booster) o)) {
						listBooster.set(n, (Booster) o);
					}
				}
			}else
				if(o instanceof Reservoir) {
					for(int n = 0; n < listReservoir.size(); n++) {
						if(listReservoir.get(n).equals((Reservoir) o)) {
							listReservoir.set(n, (Reservoir) o);
						}
					}
				}

	}//fin methode

	/**
	 * Methode qui ajoute l'objet d'un composant de la fusee
	 * @param o L'objet d'un composant de la fusee
	 */
	//Johnatan G

	public void addObjet(Objet o) {//debut methode

		if(o instanceof Moteur) {
			addListMoteur((Moteur)o);
		}else
			if(o instanceof Reservoir) {
				addListReservoir((Reservoir) o);
			}else
				if(o instanceof Booster) {
					addListBooster((Booster) o);
				}

	}//fin methode

	/**
	 * Methode qui enleve l'objet d'un composant de la fusee
	 * @param o L'objet d'un composant de la fusee
	 */
	//Johnatan G 

	public void removeObjet(Objet o) {//debut methode

		if(o instanceof Moteur) {
			for(int n = 0; n < listMoteur.size(); n++) {			
				if(listMoteur.get(n).equals((Moteur) o)) {
					removeListMoteur((Moteur) o);
				}	
			}
		}else 
			if(o instanceof Booster) {
				for(int n = 0; n < listBooster.size(); n++) {
					if(listBooster.get(n).equals((Booster) o)) {
						removeListBooster((Booster)o);
					}
				}
			}else 
				if(o instanceof Reservoir) {
					for(int n = 0; n < listReservoir.size(); n++) {
						if(listReservoir.get(n).equals((Reservoir) o)) {
							removeListReservoir((Reservoir) o);
						}
					}
				}

	}//fin methode

	/**
	 * Methode qui retourne la hauteur du reservoir
	 * @return hauteurReservoir La hauteur du reservoir
	 */
	//Johnatan G

	public double getHauteurReservoir() {
		return hauteurReservoir;
	}

	/**
	 * Methode qui modifie la hauteur du reservoir
	 * @param hauteurReservoir La hauteur du reservoir
	 */
	//Johnatan G

	public void setHauteurReservoir(double hauteurReservoir) {//debut methode
		this.hauteurReservoir = hauteurReservoir;
	}//fin methode

	/**
	 * Methode qui efface tous les compostants de la fusee
	 */
	//Melie L

	public void clearAll() {//debut methode

		this.diametreTotalMoteur = 0;
		this.rayonReservoir = 0;
		this.nbBooster = 0;
		this.hauteurMaxMoteur = 0;
		this.hauteurBooster = 0;
		this.hauteurReservoir = 0;

		this.deltaPosition = new Vecteur();
		this.position = new Vecteur();
		this.vitesse = new Vecteur(0, SMath.EPSILON, 0);
		this.acceleration = new Vecteur(0, SMath.EPSILON, 0);
		this.poids = new Vecteur(0, -SMath.EPSILON, 0);
		this.angleDeviation = 0;

		this.impulsionSpecifiqueBooster = 0;
		this.impulsionSpecifiqueMoteur = 0;

		this.pousseeTotaleMoteur = new Vecteur();
		this.pousseeTotaleBooster = new Vecteur();

		this.masse = 0;
		this.masseCombustibleBooster = 0;
		this.masseCombustibleReservoir = 0;

		this.listMoteur.clear();
		this.listBooster.clear();
		this.listReservoir.clear();

		this.listBooster.trimToSize();
		this.listMoteur.trimToSize();
		this.listReservoir.trimToSize();

		resetPositionComposant();

	}//fin methode

	/**
	 * Methode qui permet le deplacement de la fusee
	 * @param deltaPosition Le deplacement de la fusee
	 */
	//Johnatan G

	public void deplacementFusee(Vecteur deltaPosition) {//debut methode		

		for(int n = 0; n < listMoteur.size(); n++) {		
			listMoteur.get(n).setPosition(new Vecteur(listMoteur.get(n).getPosition().getX() + deltaPosition.getX(), listMoteur.get(n).getPosition().getY() + deltaPosition.getY(), 0));
		}
		for(int n = 0; n < listReservoir.size(); n++) {
			listReservoir.get(n).setPosition(new Vecteur(listReservoir.get(n).getPosition().getX() + deltaPosition.getX(), listReservoir.get(n).getPosition().getY() + deltaPosition.getY(), 0));
		}
		if(this.masseCombustibleBooster >= 0) {
			for(int n = 0; n < listBooster.size(); n++) {
				listBooster.get(n).setPosition(new Vecteur(listBooster.get(n).getPosition().getX() + deltaPosition.getX(), listBooster.get(n).getPosition().getY() + deltaPosition.getY(), 0));
			}
		}else {		
			listBooster.clear();
		}
	}//fin methode

	/**
	 * Methode qui modifie la position de la fusee
	 * @param position La position de la fusee
	 */
	//Johnatan G

	public void setPositionFusee(Vecteur position) {//debut methode

		this.deltaPosition = position.soustrait(this.position);

		deplacementFusee(deltaPosition);

	}//fin methode


	/**
	 * Methode qui retourne la fusee Apollo
	 * @return fuseeNasa La fusee Apollo
	 */
	//Johnatan G

	public static Fusee creerFuseeNasa() { //Debut de la methode qui permet de creer des fusee prefaites

		Fusee fuseeNasa = new Fusee();

		fuseeNasa.setNom("Apollo");

		for(int nbMoteur = 0; nbMoteur < 3; nbMoteur++) {
			fuseeNasa.addObjet(new Moteur(SystemeDePropulsion.SSME_RS25, new Vecteur(0, 0 , 0)));
		}

		fuseeNasa.addObjet(new Reservoir(Combustible.LOX_LH2, 750000, new Vecteur(0, 0 , 0)));

		for(int nbBooster = 0; nbBooster < 2 ; nbBooster++) {
			fuseeNasa.addObjet(new Booster(EnumBooster.SRB, new Vecteur(0, 0 , 0)));
		}

		return fuseeNasa;

	}//fin methode

	/**
	 * Methode qui retourne la fusee Cosmos
	 * @return fuseeRusse La fusee Cosmos
	 */
	//Johnatan G

	public static Fusee creerFuseeRusse() { //Debut de la methode qui permet de creer des fusee prefaites

		Fusee fuseeRusse = new Fusee();

		fuseeRusse.setNom("Cosmos");

		for(int nbMoteur = 0; nbMoteur < 3; nbMoteur++) {
			fuseeRusse.addObjet(new Moteur(SystemeDePropulsion.RD_180, new Vecteur(0, 0 , 0)));
		}

		fuseeRusse.addObjet(new Reservoir(Combustible.KEROSENE_RP1, 750000, new Vecteur(0, 0 , 0)));

		for(int nbBooster = 0; nbBooster < 2 ; nbBooster++) {
			fuseeRusse.addObjet(new Booster(EnumBooster.SRB, new Vecteur(0, 0 , 0)));
		}

		return fuseeRusse;

	}//fin methode

	/**
	 * Methode qui retourne la fusee Falkon
	 * @return fuseeRusse La fusee Falkon
	 */
	//Johnatan G

	public static Fusee creerFuseeSpaceX() { //Methode qui permet de creer des fusee prefaites

		Fusee fuseeSpaceX = new Fusee();

		fuseeSpaceX.setNom("Falkon");

		for(int nbMoteur = 0; nbMoteur < 3; nbMoteur++) {
			fuseeSpaceX.addObjet(new Moteur(SystemeDePropulsion.MERLIN, new Vecteur(0, 0 , 0)));
		}

		fuseeSpaceX.addObjet(new Reservoir(Combustible.KEROSENE_RP1, 750000, new Vecteur(0, 0 , 0)));

		for(int nbBooster = 0; nbBooster < 2 ; nbBooster++) {
			fuseeSpaceX.addObjet(new Booster(EnumBooster.SRB, new Vecteur(0, 0 , 0)));
		}

		return fuseeSpaceX;

	}//fin methode

	/**
	 * Methode qui retourne l'angle beta
	 * @return L'angle beta
	 */
	//Johnatan G

	public double getaBeta() {//debut methode
		return aBeta;
	}//fin methode

	/**
	 * Methode qui modifie l'angle beta
	 * @param aBeta L'angle beta
	 */
	//Johnatan G

	public void setaBeta(double aBeta) {//debut methode
		this.aBeta = aBeta;
	}//fin methode

}//fin classe
