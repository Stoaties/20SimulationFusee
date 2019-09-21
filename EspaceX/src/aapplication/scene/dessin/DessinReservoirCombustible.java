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
import listeners.ReservoirListener;
import modele.ModeleAffichage;
import objets.fusee.composants.Reservoir;
import objets.fusee.composants.enums.Combustible;
import util.Constantes;
import util.Vecteur;

/**
 * Classe qui va dessiner le reservoir et le combustible
 * 
 * @author Melie Leclerc
 *
 */

public class DessinReservoirCombustible extends JPanel implements Constantes {//debut classe

	private static final long serialVersionUID = 1L;//debut classe

	//Variables de la largeur et de la hauteur du panel dessinReservoirCombustible
	private double largeurMonde, hauteurMonde;

	//Matrices qui permettent la transition de pixel en metre
	private AffineTransform mat;

	//Variable qui gere si le reservoir est selectionne
	private boolean isReservoirSelectionne = false;

	//Variables qui contiennent le composant qui dessine le reservoir
	private Reservoir reservoir;
	private Reservoir reservoirReel;

	//Variable qui gere la position initiale dans le panel dessinReservoirCombustible et celui de dessinFusee
	private Vecteur positionInitiale;

	//Variables qui permettent le deplacement de reservoir
	private double xPrecedent=0, yPrecedent=0;
	private double translateX = 0;
	private double translateY = 0;

	//Variable de la largeur et de la hauteur du reservoir
	private double largeurReservoir, hauteurReservoir;

	//Variable qui contiennent les composants qui dessinent les combustibles
	private Combustible lOX_LH2 = Combustible.LOX_LH2;
	private Combustible kerosene = Combustible.KEROSENE_RP1;

	//Variable qui gere la liste d'ecouteurs
	private ArrayList <ReservoirListener> listeEcouteurs = new ArrayList <ReservoirListener>();

	//Variable qui contient les informations du reservoir et du combustible	
	private JTextArea txtReservoirCombustible;

	/**
	 * Constructeur de la classe DessinReservoirCombustible qui va creer un Objet Dessinable de type DessinReservoirCombustible
	 * @param ma La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Melie L

	public DessinReservoirCombustible(ModeleAffichage ma) {//debut constructeur

		ajouterGestionSouris();
		this.mat = ma.getMatMC();
		setBackground(Color.white);

		this.largeurMonde = ma.getLargUnitesReelles();
		this.hauteurMonde = ma.getHautUnitesReelles();

		mat.translate(largeurMonde/2, hauteurMonde/2);
		mat.scale(1, -1);

		this.reservoir = new Reservoir();

		int x = (int) (this.largeurMonde/1.6 * mat.getScaleX());
		int y = (int) (this.hauteurMonde/40*-1 * mat.getScaleY());
		int w = (int) (this.largeurMonde/3 * mat.getScaleX());
		int h = (int) (this.hauteurMonde*0.43*-1 * mat.getScaleY());

		txtReservoirCombustible = new JTextArea();
		txtReservoirCombustible.setBackground(null);
		txtReservoirCombustible.setBounds(x, y, w, h);
		txtReservoirCombustible.setEditable(false);
		txtReservoirCombustible.setText("Nom: " 
				+"\nMasse : " +reservoir
				+ "\nPrix par kg de reservoir : " 
				+ "\nMasse du reservoir vide : " 
				+ "\nMasse du combustible :"
				+ "\nNombre de kg de reservoir par kg de combustible : "+ reservoir.getKgReservoirParKgCombustible()+ " kg"
				+ "\nHauteur : "
				+ "\nHauteur par kg de reservoir vide : "
				+ "\nDiametre : " 
				+ "\nDiametre par kg de reservoir : ");

		txtReservoirCombustible.setVisible(false);
		add(txtReservoirCombustible);
		setLayout(null);

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

				try {

					if(reservoir.contains(positionSouris)) {

						isReservoirSelectionne = true;

						reservoirReel = new Reservoir(reservoir);
						reservoirReel.setHauteur(hauteurReservoir);
						reservoirReel.setDiametre(largeurReservoir);

						xPrecedent = positionSouris.getX();
						yPrecedent = positionSouris.getY();

						setReservoirEvenement(reservoirReel, positionSouris, largeurMonde, mat);

					}
				}catch(Exception reservoirNull) {
					//	reservoirNull.printStackTrace();
				}

				txtReservoirCombustible.setText("Nom: " +reservoir.getNom()
				+"\nMasse : " +reservoir.getMasse()+" kg"
				+ "\nPrix par kg de reservoir : " +reservoir.getPRIX_PAR_KG_DE_RESERVOIR()+ " M$"
				+ "\nMasse du reservoir vide : " +reservoir.getMasseReservoirVide()+ " kg"
				+ "\nMasse du combustible :"+reservoir.getMasseCombustible()+ " kg"
				+ "\nNombre de kg de reservoir par kg de combustible : "+ reservoir.getKgReservoirParKgCombustible()+ " kg"
				+ "\nHauteur : " +reservoir.getHauteur()+ " m"
				+ "\nHauteur par kg de reservoir vide : "+ reservoir.getHAUTEUR_PAR_KG_RESERVOIR()+ " m/kg"
				+ "\nDiametre : " +reservoir.getDiametre()+ " m"
				+ "\nDiametre par kg de reservoir : " +reservoir.getDIAMETRE_PAR_KG_RESERVOIR()+" m/kg");

				if(isReservoirSelectionne) {
					txtReservoirCombustible.setVisible(true);
				}else {
					txtReservoirCombustible.setVisible(false);
				}

			}//fin pressed

			public void mouseReleased(MouseEvent e) {//debut released

				deselectionneReservoirEvenement();
				reservoir.setPosition(positionInitiale);
				isReservoirSelectionne = false;

				reservoirReel = null;

				repaint();

			}//fin released
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {//debut dragged

				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);
				txtReservoirCombustible.setVisible(false);

				if(isReservoirSelectionne) {

					translateX = positionSouris.getX() - xPrecedent;
					translateY = positionSouris.getY() - yPrecedent;

					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();

					reservoir.setPosition(new Vecteur(reservoir.getPosition().getX()+translateX, reservoir.getPosition().getY()+translateY, 0));

					dragReservoirEvenement(positionSouris, largeurMonde, mat);

					repaint();
				}

			}//fin dragged
		});

	}//fin methode

	/**
	 * Methode qui permet de dessiner une scene qui inclut le reservoir et le combustible
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Melie L

	public void paintComponent(Graphics g) {//debut methode
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		try {

			reservoir.draw(g2d, mat);		

		}catch(Exception e) {
			System.out.println("La masse du reservoir est null"+ e);
		}

	}//fin methode

	/**
	 * Methode qui modifie la masse du LOX
	 * @param masseEnKg La masse du LOX en kg
	 */
	//Melie L

	public void setMasseLOX(double masseEnKg) {//debut methode

		this.lOX_LH2.setMasse(masseEnKg);

		reservoir = new Reservoir(lOX_LH2, lOX_LH2.getMasse(), new Vecteur());

		this.hauteurReservoir = reservoir.getHauteur();
		this.largeurReservoir = reservoir.getDiametre();

		if(reservoir.getMasse() >= MASSE_LIMITE_RESERVOIR_TEMPORAIRE) {

			double masseRes = MASSE_LIMITE_RESERVOIR_TEMPORAIRE * reservoir.getKgReservoirParKgCombustible();
			double masseResVide = masseRes-MASSE_LIMITE_RESERVOIR_TEMPORAIRE;

			double hauteurReservoirTmp = reservoir.getHAUTEUR_PAR_KG_RESERVOIR()*masseResVide;
			double diametreReservoirTmp = reservoir.getDIAMETRE_PAR_KG_RESERVOIR()*masseResVide;

			reservoir.setDiametre(diametreReservoirTmp);
			reservoir.setHauteur(hauteurReservoirTmp);

		}

		positionInitiale = new Vecteur(-reservoir.getDiametre()/2, -reservoir.getHauteur()/2, 0);
		Vecteur position = new Vecteur(positionInitiale);

		reservoir.setPosition(position);

		repaint();

	}//fin methode

	/**
	 * Methode qui modifie la masse du kerosene
	 * @param masseEnKg La masse du kerosene en kg
	 */
	//Melie L

	public void setMasseKerosene(double masseEnKg) {//debut methode

		this.kerosene.setMasse(masseEnKg);

		reservoir = new Reservoir(kerosene, kerosene.getMasse(), new Vecteur());

		this.hauteurReservoir = reservoir.getHauteur();
		this.largeurReservoir = reservoir.getDiametre();


		if(reservoir.getMasse() >= MASSE_LIMITE_RESERVOIR_TEMPORAIRE) {

			double masseRes = MASSE_LIMITE_RESERVOIR_TEMPORAIRE * reservoir.getKgReservoirParKgCombustible();
			double masseResVide = masseRes-MASSE_LIMITE_RESERVOIR_TEMPORAIRE;

			double hauteurReservoirTmp = reservoir.getHAUTEUR_PAR_KG_RESERVOIR()*masseResVide;
			double diametreReservoirTmp = reservoir.getDIAMETRE_PAR_KG_RESERVOIR()*masseResVide;

			reservoir.setDiametre(diametreReservoirTmp);
			reservoir.setHauteur(hauteurReservoirTmp);

		}

		positionInitiale = new Vecteur(-reservoir.getDiametre()/2, -reservoir.getHauteur()/2, 0);
		Vecteur position = new Vecteur(positionInitiale);
		reservoir.setPosition(position);

		repaint();

	}//fin methode

	/**
	 * Methode qui va permettre d'ajouter les ecouteurs personalises au ArrayList
	 * @param objEcout Ecouteur personalise
	 */
	//Melie L

	public void addReservoirListener(ReservoirListener objEcout) {//debut methode
		this.listeEcouteurs.add(objEcout);
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie que le reservoir est selectionne 
	 * @param r              Le reservoir
	 * @param positionSouris La position du curseur
	 * @param largeur        La largeur du monde reel
	 * @param matReservoir   La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 
	 */
	//Melie L

	private void setReservoirEvenement(Reservoir r, Vecteur positionSouris, double largeur, AffineTransform matReservoir) {//debut methode
		for(ReservoirListener objEcout: listeEcouteurs) {
			objEcout.setReservoirSelectionne(r, positionSouris, largeur, matReservoir);
		}
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand le reservoir est deselectionne
	 * 
	 */
	//Melie L

	private void deselectionneReservoirEvenement() {//debut methode		
		for(ReservoirListener objEcout: listeEcouteurs) {
			objEcout.deselectionneReservoir();
		}
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie quand le reservoir est en mouvement
	 * @param positionSouris La position du curseur
	 * @param largeurMonde   La largeur du monde reel
	 * @param matReservoir   La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations   
	 */
	//Melie L

	private void dragReservoirEvenement(Vecteur positionSouris, double largeurMonde, AffineTransform matReservoir) {//debut methode
		for(ReservoirListener objEcout: listeEcouteurs) {
			objEcout.dragReservoirSelectionne(positionSouris, largeurMonde, matReservoir);
		}

	}//fin methode

}//fin classe
