package aapplication.scene.dessin;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import listeners.BoosterListener;
import modele.ModeleAffichage;
import objets.fusee.composants.Booster;
import objets.fusee.composants.enums.EnumBooster;
import util.Vecteur; 

/**
 * Classe qui va dessiner le booster
 * 
 * @author Melie Leclerc
 *
 */

public class DessinBooster extends JPanel {//debut classe

	private static final long serialVersionUID = 1L;

	//Variables de la largeur et de la hauteur du panel dessinBooster
	private double largeurMonde, hauteurMonde;

	//Matrices qui permettent la transition de pixel en metre
	private AffineTransform mat;

	//Variable qui gere si le booster est selectionne
	private boolean isBoosterSelectionne = false;

	//Variable qui contient le composant qui dessine le booster srb 
	private Booster srb;

	//Variables qui gerent la position initiale dans le panel dessinBooster et celui de dessinFusee
	private Vecteur positionInitiale;

	//Variables qui permettent le deplacement des boosters
	private double xPrecedent=0, yPrecedent=0, translateX = 0, translateY = 0;

	//Variable qui contient les informations du booster	
	private JTextArea txtInfoBooster;

	//Variable qui gere la liste d'ecouteurs
	private ArrayList <BoosterListener> listeEcouteurs = new ArrayList <BoosterListener> ();

	/**
	 * Constructeur de la classe DessinBooster qui va creer un Objet Dessinable de type Booster
	 * @param ma La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Melie L

	public DessinBooster(ModeleAffichage ma) {//debut constructeur	

		ajouterGestionSouris();
		setBackground(Color.white);
		this.largeurMonde = ma.getLargUnitesReelles();
		this.hauteurMonde = ma.getHautUnitesReelles();

		this.mat = ma.getMatMC();
		setLayout(null);
		mat.translate(largeurMonde/2, hauteurMonde/2);
		mat.scale(1, -1);
		srb = new Booster(EnumBooster.SRB, new Vecteur(0,-EnumBooster.SRB.getHauteur()/2.5,0));
		positionInitiale = new Vecteur(srb.getPosition());

		int x = (int) (this.largeurMonde/40 * mat.getScaleX());
		int y = (int) (this.hauteurMonde/40*-1 * mat.getScaleY());
		int w = (int) (this.largeurMonde/7 * mat.getScaleX());
		int h = (int) (this.hauteurMonde*0.35*-1 * mat.getScaleY());

		txtInfoBooster = new JTextArea();
		txtInfoBooster.setEditable(false);
		txtInfoBooster.setBackground(null);
		txtInfoBooster.setText("Nom: \r\nPrix : \r\nMasse : \r\nMasse remplie : \r\nMasse vide : \r\nPoussee :\r\nRayon : \r\nHauteur : \r\nDiametre : \r\nDuree de fonctionnement : ");
		txtInfoBooster.setBounds(x, y, w, h);
		txtInfoBooster.setVisible(false);
		setLayout(null);
		add(txtInfoBooster);

	}//fin constructeur

	/**
	 * Methode qui contient toutes les gestions de souris
	 * 
	 */
	//Melie L

	private void ajouterGestionSouris() {//debut methode

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {//debut pressed

				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);

				if(srb.contains(positionSouris)) {

					isBoosterSelectionne = true;

					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();

					setBoosterSelectionneEvenement(srb, positionSouris, largeurMonde, mat);

				}

				txtInfoBooster.setText("Nom: " + srb.getNom()
				+ "\nPrix : " +srb.getPrix()+ " M$"
				+"\nMasse : " +srb.getMasse()+" kg"
				+ "\nMasse remplie : " +srb.getMasseRempli()+" kg"
				+ "\nMasse vide : "+ srb.getMasseVide()+" kg"
				+ "\nPoussee :"+srb.getPoussee()+ " N"
				+ "\nRayon : " +srb.getRayon()+ " m"
				+ "\nHauteur : " +srb.getHauteur()+ " m"
				+ "\nDiametre : " +srb.getDiametre()+ " m"
				+ "\nDuree de fonctionnement : "+srb.getDureeFonctionnement()+" s");

				if(isBoosterSelectionne) {

					txtInfoBooster.setVisible(true);

				}else {

					txtInfoBooster.setVisible(false);

				}

			}//fin pressed

			public void mouseReleased(MouseEvent e) {//debut released

				isBoosterSelectionne = false;
				srb.setPosition(positionInitiale);

				deselectionnerBoosterEvenement();
				repaint();
				
			}//fin released
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {//debut dragged

				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);
				txtInfoBooster.setVisible(false);

				if(isBoosterSelectionne) {

					translateX = positionSouris.getX() - xPrecedent;
					translateY = positionSouris.getY() - yPrecedent;

					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();

					srb.setPosition(new Vecteur(srb.getPosition().getX()+translateX, srb.getPosition().getY()+translateY, 0));
					dragBoosterSelectionneEvenement(positionSouris, largeurMonde, mat);
					repaint();

				}
			}//fin dragged
		});

	}

	/**
	 * Methode qui permet de dessiner une scene qui inclut le booster SRB
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Melie L

	public void paintComponent(Graphics g) {//debut methode
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		srb.draw(g2d, mat);

	}//fin methode

	/**
	 * Methode qui va permettre d'ajouter les ecouteurs personalises au ArrayList
	 * @param objEcout Ecouteur personalise
	 */
	//Melie L

	public void addBoosterListener(BoosterListener objEcout) {//debut methode	
		
		this.listeEcouteurs.add(objEcout);	
		
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie que le booster est selectionne 
	 * @param b              Le booster selectionne
	 * @param positionSouris La position du curseur
	 * @param largeurMonde   La largeur du monde reel
	 * @param matBooster     La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 
	 */
	//Melie L

	private void setBoosterSelectionneEvenement(Booster b, Vecteur positionSouris, double largeurMonde, AffineTransform matBooster) {//debut methode

		for(BoosterListener objEcout: listeEcouteurs) {		
			
			objEcout.setBoosterSelectionne(b, positionSouris, largeurMonde, matBooster);		
		
		}
		
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand un booster est deselectionne
	 * 
	 */
	//Melie L

	private void deselectionnerBoosterEvenement() {//debut methode

		for(BoosterListener objEcout: listeEcouteurs) {	
			
			objEcout.deselectionnerBooster();		
			
		}
		
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand un booster est en mouvement
	 * @param positionSouris La position du curseur
	 * @param largeurMonde   La largeur du monde reel
	 * @param matBooster     La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 
	 */
	//Melie L

	private void dragBoosterSelectionneEvenement(Vecteur positionSouris, double largeurMonde, AffineTransform matBooster) {//debut methode

		for(BoosterListener objEcout: listeEcouteurs) {
			
			objEcout.dragBoosterSelectionne(positionSouris, largeurMonde, matBooster);
		
		}

	}//fin methode

}//fin classe
