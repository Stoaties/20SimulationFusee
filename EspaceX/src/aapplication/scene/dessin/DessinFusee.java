package aapplication.scene.dessin;
import java.applet.Applet; 
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import aapplication.scene.dessin.environnement.Ciel;
import aapplication.scene.dessin.environnement.Nuage;
import aapplication.scene.dessin.environnement.Sol;
import listeners.FuseeListener;
import modele.ModeleAffichage;
import objets.Objet;
import objets.fusee.Fusee;
import objets.fusee.composants.Booster;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.Reservoir;
import util.Constantes;
import util.SMath;
import util.Vecteur;

/**
 * Classe qui va dessiner la fusee
 * 
 * @author Johnatan Gao
 * @author Melie Leclerc
 */

public class DessinFusee extends JPanel implements Runnable, Constantes{//debut classe

	private static final long serialVersionUID = 1L;

	//Matrice qui permet la transition de pixel en metre
	private double largeurMonde, hauteurMonde;
	private AffineTransform mat;
	private AffineTransform matEchelle;
	
	//Variable de la fusee construite
	private Fusee fuseeConstruite;

	//Variables qui contiennent les composants de la fusee
	private Objet objetSelectionne;
	private Objet objAire;

	//Variable du sol
	private Sol solTerre;

	//Variable du ciel
	private Ciel cielTerre;

	//Variable qui gere si le l'objet est selectionne
	private boolean isObjetSelectionne = false;;

	//Variables qui permettent le deplacement des objets de la fusee
	private double xPrecedent=0, yPrecedent=0, xTranslate=0, yTranslate=0;

	//Variables qui gerent si le moteur, le reservoir et le booster doivent etre dessines ou non
	private boolean dessinerMoteur = false, dessinerReservoir = false, dessinerBooster = false;

	//Variables pour l'animation
	private boolean enAnimation = false;
	private Thread processus;
	private boolean premiereFois = true;
	private double tempsEcoule = 0;
	private int cptTempsEcoule = 0;

	//Variable qui gere le bouton de la poubelle
	private JButton btnPoubelle;

	//Variable qui gere la liste d'ecouteurs
	private ArrayList <FuseeListener> listeEcouteurs = new ArrayList<FuseeListener>();

	//Variables qui gerent les sons
	private String fichiersDeSon[]={"sonFusee.wav"};
	private ArrayList<AudioClip> lesClips;

	//Variable qui dessine l'echelle de graduation 
	private Line2D.Double ligneEchelle;

	//Variables qui dessinent les nuages
	private Nuage nuage1, nuage2, nuage3, nuage4, nuage5, nuage6, nuage7;
	
	private double nDeltaT = 1;
	private double deltaT;

	/**
	 * Constructeur de la classe DessinFusee qui va creer un Objet Dessinable de type Fusee
	 * @param mdFusee La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Johnatan G

	public DessinFusee(ModeleAffichage mdFusee) {//debut constructeur

		lireLesSons() ;

		ajouterGestionSouris();
		setBackground(Color.black);

		this.largeurMonde = mdFusee.getLargUnitesReelles();
		this.hauteurMonde = mdFusee.getHautUnitesReelles();

		this.mat = mdFusee.getMatMC();
		this.mat.translate( largeurMonde/2, hauteurMonde*0.8);
		this.mat.scale(1, -1);

		
		this.matEchelle = new AffineTransform(mat);

		this.cielTerre = new Ciel(largeurMonde, hauteurMonde);
		this.solTerre = new Sol(-largeurMonde, -1.5*hauteurMonde, 4* largeurMonde,  1.5*hauteurMonde );
		this.fuseeConstruite = new Fusee();
		setLayout(null);

		ajouterPoubelle();

		ligneEchelle = new Line2D.Double(largeurMonde/7, -hauteurMonde/8, largeurMonde/7 + largeurMonde/3, -hauteurMonde/8);		

		nuage1 = new Nuage(10,100);
		nuage2 = new Nuage(-10, 120);
		nuage3 = new Nuage(20, 140);
		nuage4 = new Nuage(30, 130);
		nuage5 = new Nuage(5, 160);
		nuage6 = new Nuage(-15, 180);
		nuage7 = new Nuage(25, 200);	
		
		setNDeltaT(1);

	}//fin constructeur

	/**
	 * Methode qui contient toutes les gestions de souris
	 * 
	 */
	//Johnatan G

	private void ajouterGestionSouris() {//debut methode	
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {//debut pressed

				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);

				if(fuseeConstruite.contains(positionSouris) && SMath.nearlyEquals(fuseeConstruite.getPosition().getY(), 0)) {

					isObjetSelectionne = true;
					objetSelectionne = fuseeConstruite.returnObjet(positionSouris);

					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();

				}else {

					isObjetSelectionne = false;
					objetSelectionne = null;
					
				}
			}//fin pressed

			public void mouseReleased(MouseEvent e) {//debut released

				if(isObjetSelectionne) {

					if(isOutofBound(objetSelectionne)) {
						fuseeConstruite.removeObjet(objetSelectionne);
						enleverObjetsUtilises(fuseeConstruite.getListMoteur().size());										
					}		

					fuseeConstruite.resetPositionComposant();
					objetSelectionne = null;
					xTranslate = 0;
					yTranslate = 0;
					xPrecedent = 0;
					yPrecedent = 0;
					isObjetSelectionne = false;
				}
				repaint();
			}//fin released
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {//debut dragged

				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);

				if(isObjetSelectionne) {

					xTranslate = positionSouris.getX() - xPrecedent;
					yTranslate = positionSouris.getY() - yPrecedent;

					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();

					objetSelectionne.setPosition(new Vecteur(objetSelectionne.getPosition().getX() + xTranslate, objetSelectionne.getPosition().getY() + yTranslate, 0));
					fuseeConstruite.setObjet(objetSelectionne);

					repaint();

				}			
			}
		}//fin dragged
				);	

	}//fin methode

	/**
	 * Methode qui permet de dessiner une scene qui inclut la fusee
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Johnatan G

	public void paintComponent(Graphics g) {//debut methode	

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		this.cielTerre.draw(g2d, mat);

		genererNuages(g2d,mat);
		
		if(fuseeConstruite.getPosition().getY() < 75) {
			solTerre.draw(g2d, mat);
		}

		if(dessinerMoteur || dessinerReservoir || dessinerBooster) {
			objetSelectionne.draw(g2d, mat);
		}

		if(!enAnimation) {
			fuseeConstruite.draw(g2d, mat);
		}else {
			fuseeConstruite.drawEnAnimation(g2d, mat);
		}
		
		dessinerAireDepos(g2d);	
		dessinerEchelle(g2d, matEchelle);
		dessinerAltitude(g2d,mat);

	}//fin methode

	/**
	 * Methode qui va effectuer l'animation en utilisant un thread tout en mettant les forces en jeu a jour
	 */
	//Johnatan G

	public void run() {//debut methode

			lesClips.get(0).loop();
		

		while(enAnimation) {

			tempsEcoule += calculerDeltaT(nDeltaT);
			this.cptTempsEcoule += 1;
			

			if(cptTempsEcoule % (120) == 0) {

				updateValeurs(tempsEcoule, 
						fuseeConstruite.getSommeDesForcesX(),
						fuseeConstruite.getSommeDesForcesY(),

						fuseeConstruite.getVitesseX(), 
						fuseeConstruite.getVitesseY(), 

						fuseeConstruite.getAccelerationX(), 
						fuseeConstruite.getAccelerationY(), 

						fuseeConstruite.getMasse(), 
						fuseeConstruite.getMasseCombustibleReservoir(), 
						fuseeConstruite.getMasseCombustibleBooster(), 
						fuseeConstruite.getResistanceAir().module(),
						fuseeConstruite.getPosition().getY(),
						fuseeConstruite.getaBeta());

			}

			if(fuseeConstruite.getPosition().getY() >= IN_SPACE) {
				isInSpace(fuseeConstruite.getDeltaPosition(), fuseeConstruite.getMasse());
			}
			
			calculerUneIterationPhysique();
			mat.translate(-fuseeConstruite.getDeltaPosition().getX(), -fuseeConstruite.getDeltaPosition().getY());

			if(fuseeConstruite.getDeltaPosition().getY() < 0) {

				if(fuseeConstruite.contains(solTerre)) {
					arreter();
				}

			}
			
			repaint();

			
			
			try {

					Thread.sleep(SLEEP);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}	

	}//fin methode

	/**
	 * Methode qui permet de demarrer le thread si celui-ci n'est pas encore demarre
	 * 
	 */
	//Johnatan G

	public void demarrer() {//debut methode

		if(!enAnimation) {

			processus  = new Thread(this);

			if(fuseeConstruite.getListMoteur().size() == 0 || fuseeConstruite.getListReservoir().size() == 0) {
				this.enAnimation = false;
			}else {

				if(premiereFois) {

					premiereFois = false;

				}

				this.enAnimation = true;
				processus.start();
			}	
		}

	}//fin methode


	/**
	 * Methode qui va demander l'arret du thread et de l'animation
	 * 
	 */
	//Johnatan G

	public void arreter() {//debut methode

		this.enAnimation = false;
		
		lesClips.get(0).stop();

	}//fin methode
	
	/**
	 * Methode qui arrete le son sans arreter l'animation
	 */
	//Melie L
	public void arreterSon() {//debut methode
		
		
		lesClips.get(0).stop();
	}//fin methode
	
	/**
	 * Methode qui allume le son
	 */
	//Melie L
	public void allumerSon() {//debut methode
		lesClips.get(0).loop();
	}//fin methode

	/** 
	 *Methode qui permet de mettre a jour les valeurs des forces, de la vitesse, de la position ainsi que l'acceleration
	 * 
	 */
	//Johnatan G

	private void calculerUneIterationPhysique() {//debut methode
		this.fuseeConstruite.unPasEuler(deltaT);
		this.tempsEcoule += calculerDeltaT(deltaT);

	}//fin methode

	/**
	 * Methode qui permet les iterations physiques
	 */
	//Johnatan G
	public void uneIterationPhysique() {//debut methode
		
		if(fuseeConstruite.getListMoteur().size() != 0 && fuseeConstruite.getListReservoir().size() != 0) {
			
			tempsEcoule += deltaT;
			this.cptTempsEcoule +=1 ;
			mat.translate(-fuseeConstruite.getDeltaPosition().getX(), -fuseeConstruite.getDeltaPosition().getY());

			if(cptTempsEcoule % (120) == 0) {

				updateValeurs(tempsEcoule, 
						fuseeConstruite.getSommeDesForcesX(),
						fuseeConstruite.getSommeDesForcesY(),

						fuseeConstruite.getVitesseX(), 
						fuseeConstruite.getVitesseY(), 

						fuseeConstruite.getAccelerationX(), 
						fuseeConstruite.getAccelerationY(), 

						fuseeConstruite.getMasse(), 
						fuseeConstruite.getMasseCombustibleReservoir(), 
						fuseeConstruite.getMasseCombustibleBooster(), 
						fuseeConstruite.getResistanceAir().module(),
						fuseeConstruite.getPosition().getY(),
						fuseeConstruite.getAngleDeviation());

			}

			calculerUneIterationPhysique();


			if(fuseeConstruite.getDeltaPosition().getY() < 0) {

				if(fuseeConstruite.contains(solTerre)) {
					arreter();
				}

			}


			repaint();
		}	
	}//fin methode
	
	
	
	/**
	 * Methode qui dessine l'echelle de graduation
	 * @param g2d Le contexte graphique qui va permettre de dessiner les elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L

	private void dessinerEchelle(Graphics2D g2d, AffineTransform mat) {//debut methode

		Color cInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(mat);

		g2d.setColor(Color.white);
		g2d.drawString(String.format("%.1f", largeurMonde/3) + "m", (int)((largeurMonde/2 + largeurMonde/7) * matLocale.getScaleX()), (int)((7.3*ligneEchelle.y1) * matLocale.getScaleY()));
		g2d.draw(mat.createTransformedShape(ligneEchelle));

		g2d.setColor(cInit);

	}//fin methode
	
	/**
	 * Methode qui dessine l'echelle de graduation
	 * @param g2d Le contexte graphique qui va permettre de dessiner les elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Melie L

	private void dessinerAltitude(Graphics2D g2d, AffineTransform mat) {//debut methode

		Color cInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(mat);

		g2d.setColor(Color.white);
		g2d.drawString("Distance fusee-Terre: "+String.format("%.1f", fuseeConstruite.getAltitude()) + "m", 0, (int)((ligneEchelle.y1 * 0.2) * matLocale.getScaleY()));

		g2d.setColor(cInit);

	}//fin methode
	/**
	 * Methode qui va creer et dessiner les zones de depots des objets de la fusee
	 * 
	 * @param g2d Le contexte graphique qui va permettre de dessiner les elements
	 */
	//Johnatan G

	private void dessinerAireDepos(Graphics2D g2d) {//debut methode

		Color colorInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(mat);

		if(objAire != null) {		
			if(!objAire.getCouleur().equals(new Color(0f,1f,0f,0.4f))) {
				objAire.setCouleur(new Color(1f,0f,0f, 0.4f));
			}
			objAire.drawForme(g2d, matLocale);
		}

		g2d.setColor(colorInit);

	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quel objet de la fusee est selectionne 
	 * @param o                 L'objet de la fusee selectionne
	 * @param positionSouris    La position du curseur
	 * @param largeurMondeObjet La largeur du monde reel
	 * @param matObjet          La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 
	 */
	//Johnatan G

	public void setObjetSelectionne(Objet o , Vecteur positionSouris, double largeurMondeObjet, AffineTransform matObjet) {//debut methode

		if(o instanceof Moteur) {
			this.objetSelectionne = new Moteur((Moteur)o);

			this.objAire = new Moteur((Moteur) o);
			objAire.setPosition(new Vecteur(-objAire.getRayon(), 0 ,0));

		}
		else
			if(o instanceof Reservoir) {

				this.objetSelectionne = new Reservoir((Reservoir) o);
				this.objAire = new Reservoir((Reservoir) o);
				objAire.setPosition(new Vecteur(-objAire.getRayon(), fuseeConstruite.getHauteurMaxMoteur() ,0));		
			}
			else
				if(o instanceof Booster){

					this.objetSelectionne = new Booster((Booster) o );
					this.objAire = new Booster((Booster) o );

					int nbBooster = fuseeConstruite.getNbBooster();
					double rayonReservoir = fuseeConstruite.getRayonReservoir();

					if(nbBooster % 2 == 0) {
						objAire.setPosition(new Vecteur(-2*objetSelectionne.getRayon()-rayonReservoir -nbBooster*this.objetSelectionne.getRayon(),  fuseeConstruite.getHauteurMaxMoteur()/2, 0));
					}else {
						objAire.setPosition(new Vecteur(-objetSelectionne.getRayon()+rayonReservoir + nbBooster* objetSelectionne.getRayon(),  fuseeConstruite.getHauteurMaxMoteur()/2, 0));
					}

				}

		Vecteur position = new Vecteur(((positionSouris.getX()-largeurMondeObjet/2)*matObjet.getScaleX()/(mat.getScaleX()*1.0) - mat.getTranslateX()),((positionSouris.getY())*matObjet.getScaleY()/mat.getScaleY()) + mat.getTranslateY(), 0);

		this.xPrecedent = position.getX();
		this.yPrecedent = position.getY();

		objetSelectionne.setPosition(position);

		repaint();

	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand un objet de la fusee est deselectionne
	 * 
	 */
	//Johnatan G

	public void deselectionneObjet() {//debut methode	

		try {

			if(objetSelectionne != null && objetSelectionne.contains(objAire.getShape())) {			

				ajouterObjetFusee();		
			}

		}catch(Exception e) {
			e.printStackTrace();
		}


		this.dessinerMoteur = false;
		this.dessinerReservoir = false;
		this.dessinerBooster = false;


		this.objetSelectionne = null;	
		this.objAire = null;

		repaint();

	}//fin methode

	/**
	 * Methode qui ajoute un objet pour construire la fusee
	 */
	//Johnatan G

	private void ajouterObjetFusee() {//debut methode
		
		fuseeConstruite.addObjet(objetSelectionne);
				
		objetSelectionne.setPosition(objAire.getPosition());	
		
		if(objetSelectionne instanceof Moteur) {
			setCombustibleUtiliseEvenement((Moteur) objetSelectionne, fuseeConstruite.getListMoteur().size());
		}			
	
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand un objet de la fusee est en mouvement
	 * @param positionSouris La position du curseur
	 * @param largeurObjet   La largeur de l'objet reeaffl
	 * @param matObjet       La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 	  
	 */
	//Johnatan G

	public void dragObjetSelectionne(Vecteur positionSouris, double largeurObjet, AffineTransform matObjet) {//debut methode

		if(objetSelectionne instanceof Moteur) {				
			this.dessinerMoteur = true;
		}
		else
			if(objetSelectionne instanceof Reservoir) {
				this.dessinerReservoir = true;
			}
			else
				if(objetSelectionne instanceof Booster) {
					this.dessinerBooster = true;
				}

		Vecteur position = new Vecteur(((positionSouris.getX()-largeurObjet/2)*matObjet.getScaleX()/(mat.getScaleX()*1.0) - largeurMonde/2),((positionSouris.getY())*matObjet.getScaleY()/mat.getScaleY()) + hauteurMonde *0.2, 0);

		this.xTranslate = position.getX() - xPrecedent;
		this.yTranslate = position.getY() - yPrecedent;

		xPrecedent = position.getX();
		yPrecedent = position.getY();

		objetSelectionne.setPosition(new Vecteur(objetSelectionne.getPosition().getX() + xTranslate, objetSelectionne.getPosition().getY() + yTranslate, 0));

		if(objetSelectionne.contains(
				objAire.getShape())) {
			objAire.setCouleur(new Color(0f, 1f, 0f, 0.4f));
		}else {
			objAire.setCouleur(new Color(1f,0f,0f, 0.4f));
		}
		repaint();

	}//fin methode
	
	/**
	 * Methode qui determine si l'objet selectionne est toujours dans le panel dessinFusee
	 * @param objetSelectionne L'objet de la fusee selectionne
	 * @return boolean         Vrai si l'objet est toujours dans le panel sinon faux
	 */
	//Johnatan G

	private boolean isOutofBound(Objet objetSelectionne) {//debut methode

		if(objetSelectionne.getPosition().getX() < -largeurMonde/2 || objetSelectionne.getPosition().getX() + objetSelectionne.getRayon() * 2 > largeurMonde/2) {
			return true;
		}

		if(objetSelectionne.getPosition().getY() < - hauteurMonde*0.2 || objetSelectionne.getPosition().getY() + objetSelectionne.getHauteur() > hauteurMonde* 0.8) {
			return true;
		}
		//	masseBooster
		return false;

	}//fin methode

	/**
	 * Methode qui va permettre d'ajouter les ecouteurs personalises au ArrayList
	 * @param objEcout Ecouteur personalise
	 */
	//Johnatan G

	public void addFuseeListener(FuseeListener objEcout) {//debut methode

		this.listeEcouteurs.add(objEcout);

	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quel combustible est selectionne 
	 * @param m              Le moteur selectionne 
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	//Johnatan G

	private void setCombustibleUtiliseEvenement(Moteur m, int listMoteurSize) {//debut methode

		for(FuseeListener objEcout: listeEcouteurs) {
			objEcout.setCombustibleUtilise(m, listMoteurSize);
		}

	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand un objet est enleve
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 * 
	 */
	//Johnatan G

	private void enleverObjetsUtilises(int listMoteurSize) {//debut methode

		for(FuseeListener objEcout: listeEcouteurs) {
			objEcout.enleverObjetUtilise(listMoteurSize);
		}

	}//fin methode

	/**
	 * Methode qui met a jour les sorties de l'application
	 * @param tempsEcoule        Le temps ecoule
	 * @param sommesDesForcesX   La somme des forces en X
	 * @param sommesDesForcesY   La somme des forces en Y
	 * @param vitesseX           La vitesse de la fusee en X
	 * @param vitesseY           La vitesse de la fusee en Y
	 * @param accelerationX      L'acceleration de la fusee en X
	 * @param accelerationY      L'acceleration de la fusee en Y
	 * @param masseFusee         La masse de la fusee
	 * @param masseReservoir     La masse du reservoir de la fusee
	 * @param masseBooster       La masse du booster de la fusee
	 * @param resistanceAir      La resistance de l'air de la fusee
	 * @param distanceFuseeTerre La distance de la fusee entre elle et la Terre
	 * @param angleFusee         L'angle de la fusee
	 */
	//Johnatan G
	
	private void updateValeurs(double tempsEcoule, 
			double sommesDesForcesX, double sommesDesForcesY,
			double vitesseX, double vitesseY,
			double accelerationX, double accelerationY,
			double masseFusee, double masseReservoir, 
			double masseBooster, double resistanceAir, double distanceFuseeTerre, double angleFusee) {//debut methode

		for(FuseeListener objEcout: listeEcouteurs) {
			objEcout.updateValeurs(tempsEcoule, 
					sommesDesForcesX, sommesDesForcesY,
					vitesseX, vitesseY, 
					accelerationX, accelerationY,
					masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);
		}

	}//fin methode
	
	/**
	 * Methode qui lance un evenement que la fusee est dans l'Espace
	 * @param positionFusee La position de la fusee
	 * @param masse La masse de la fusee
	 */
	//Johnatan G
	private void isInSpace(Vecteur positionFusee, double masse) {//debut methode
		
		for(FuseeListener objEcout: listeEcouteurs) {
			objEcout.dansEspace(positionFusee, masse);
		}
		
	}//fin methode
	
	
	
	/**
	 * Methode qui retourne la fusee construite
	 * @return fuseeConstruite La fusee construite
	 */
	//Johnatan G

	public Fusee getFuseeConstruite() {//debut methode
		return fuseeConstruite;
	}//fin methode

	/**
	 * Methode qui modifie la fusee construite
	 * @param fuseeConstruite La fusee construite
	 */
	//Johnatan G

	public void setFuseeConstruite(Fusee fuseeConstruite) {//debut methode
		this.fuseeConstruite = fuseeConstruite;
	}//fin methode

	/**
	 * Methode qui genere les nuages
	 * @param g2d Le contexte graphique qui va permettre de dessiner les elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	private void genererNuages(Graphics2D g2d, AffineTransform mat) {//debut methode

		AffineTransform matLocale = new AffineTransform(mat);
		Color CInit = g2d.getColor();

		if(fuseeConstruite.getPosition().getY() + hauteurMonde < 80000) {
			if(fuseeConstruite.getDeltaPosition().getY() > 0) {
				resetPositionNuageMontee();
			}else if(fuseeConstruite.getDeltaPosition().getY() < 0 &&  fuseeConstruite.getPosition().getY() - hauteurMonde > 100) {
				resetPositionNuageDescente();
			}

			dessinerNuages(g2d, matLocale);	
		}
		g2d.setColor(CInit);

	}//fin methode

	/**
	 * Methode qui repositionne les nuages qui montent
	 */
	//Johnatan G

	private void resetPositionNuageMontee() {//debut methode

		Random rand = new Random();

		double positionX = rand.nextInt((int) (largeurMonde)) - largeurMonde/2 + fuseeConstruite.getPosition().getX();

		if(nuage1.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage1 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage2.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage2 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage3.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage3 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage4.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage4 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage5.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage5 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage6.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage6 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

		if(nuage7.getY() < (fuseeConstruite.getAltitude() - this.hauteurMonde/6)) {		
			nuage7 = new Nuage(positionX, fuseeConstruite.getAltitude() + this.hauteurMonde);		
		}

	}//fin methode

	/**
	 * Methode qui repositionne les nuages qui descendent
	 */
	//Johnatan G

	private void resetPositionNuageDescente() {//debut methode

		Random rand = new Random();

		double positionX = rand.nextInt((int) (largeurMonde)) - largeurMonde/2 + fuseeConstruite.getPosition().getX();

		if(nuage1.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage1 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage2.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage2 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage3.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage3 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage4.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage4 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage5.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage5 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage6.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage6 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

		if(nuage7.getY() > fuseeConstruite.getAltitude() + this.hauteurMonde) {
			nuage7 = new Nuage(positionX, fuseeConstruite.getAltitude() - this.hauteurMonde/7);
		}

	}//fin methode

	/**
	 * Methode qui dessine les nuages
	 * @param g2d Le contexte graphique qui va permettre de dessiner les elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	private void dessinerNuages(Graphics2D g2d, AffineTransform mat) {//debut methode

		nuage1.draw(g2d, mat);
		nuage2.draw(g2d, mat);
		nuage3.draw(g2d, mat);
		nuage4.draw(g2d, mat);
		nuage5.draw(g2d, mat);
		nuage6.draw(g2d, mat);
		nuage7.draw(g2d, mat);

	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * @param nomFichier  Le nom de fichier qui represente le nom du fichier en question
	 * @return UrlImg     L'URL qui represente la location du fichier dans le projet
	 */
	//Melie L

	private URL lireImageURL(String nomFichier) {//debut methode
		URL urlImg = getClass().getClassLoader().getResource(nomFichier);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}

		return urlImg;
	}//fin methode

	/**
	 * Methode qui permet de lire les sons
	 */
	//Melie L

	private void lireLesSons() {//debut methode
		lesClips = new ArrayList<AudioClip>();
		int nbSons = fichiersDeSon.length;
		URL url;
		AudioClip clip;

		for (int k=0; k<nbSons; k++) {
			url =  getClass().getClassLoader().getResource( fichiersDeSon[k] );
			clip = Applet.newAudioClip(url);
			lesClips.add(clip);
		}
	}//fin methode

	/**
	 * Methode qui ajoute le bouton de la poubelle 
	 * Permet d'effacer la fusee lorsqu'on clique dessus
	 */
	//Melie L

	private void ajouterPoubelle() {//debut methode

		btnPoubelle = new JButton();
		btnPoubelle.setIcon(new ImageIcon(
				new ImageIcon(
						lireImageURL("poubelle.png"))
				.getImage()
				.getScaledInstance(
						(int)(largeurMonde/10 * mat.getScaleX()),
						(int) (largeurMonde/10 * mat.getScaleX()),
						Image.SCALE_DEFAULT
						)
				)
				);

		btnPoubelle.addActionListener(new ActionListener() {// debut ecouteur
			public void actionPerformed(ActionEvent e) {

				if(!enAnimation && SMath.nearlyEquals(fuseeConstruite.getAltitude(), 0) ) {
					fuseeConstruite.clearAll();		
					enleverObjetsUtilises(fuseeConstruite.getListMoteur().size());		
					clearAll();
					repaint();
				}
				

			}
		});// fin ecouteur
		btnPoubelle.setContentAreaFilled(false);
		btnPoubelle.setBorderPainted(false);
		btnPoubelle.setBounds( 0, (int) (-hauteurMonde/1.16 * mat.getScaleY()), (int)(largeurMonde/10 * mat.getScaleX()),(int) (largeurMonde/10 * mat.getScaleX()));

		add(btnPoubelle);

	}//fin methode
	
	/**
	 * Methode qui efface toute la fusee
	 */
	//Melie L
	
	public void clearAll() {//debut methode
		
		fuseeConstruite.clearAll();
		
	}//fin methode
	
	/**
	 * Methode qui modifie l'ecoulement du temps
	 * @param n L'ecoulement du temps
	 */
	//Johnatan G
	
	public void setNDeltaT(double n) {//debut methode
		this.nDeltaT = n;
		this.deltaT = calculerDeltaT(nDeltaT);
	}//fin methode
	
	/**
	 * Methode qui calcule l'ecoulement du temps
	 * @param nDeltaT Le temps
	 * @return L'ecoulement du temps
	 */
	//Johnatan G
	
	public double calculerDeltaT(double nDeltaT) {//debut methode
		return nDeltaT * DELTA_T_FUSEE;
	}//fin methode

	/**
	 * Methode qui retourne l'ecoulement du temps
	 * @return L'ecoulement du temps
	 */
	//Johnatan G
	
	public double getDeltaT() {//debut methode
		return deltaT;
	}//fin methode

	/**
	 * Methode qui modifie l'ecoulement du temps
	 * @param deltaT L'ecoulement du temps
	 */
	//Johnatan G
	public void setDeltaT(double deltaT) {//debut methode
		this.deltaT = deltaT;
	}//fin methode
	
	/**
	 * Methode qui retourne la matrice de transformation
	 * @return La matrice de transformation
	 */
	//Johnatan G
	public AffineTransform getMat() {//debut methode
		return mat;
	}//fin methode

	/**
	 * Methode qui modifie la matrice de transformation
	 * @param mat La matrice de transformation
	 */
	//Johnatan G
	public void setMat(AffineTransform mat) {//debut methode
		this.mat = mat;
	}//fin methode
	
}//fin classe
