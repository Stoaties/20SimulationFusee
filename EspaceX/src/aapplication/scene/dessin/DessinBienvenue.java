package aapplication.scene.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import listeners.BienvenueListener;
import modele.ModeleAffichage;
import objets.fusee.Fusee;
import objets.fusee.composants.Booster;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.Reservoir;
import util.Vecteur;
import javax.swing.JList;

/**
 * Classe qui va dessiner la page de bienvenue
 * @author Johnatan Gao
 * @author Melie Leclerc
 *
 */
public class DessinBienvenue extends JPanel {//debut classe

	private static final long serialVersionUID = 1L;

	//Variables de la largeur et de la hauteur du panel dessinBooster
	private double largeurMonde, hauteurMonde;

	//Matrices qui permettent la transition de pixel en metre
	private AffineTransform mat;

	//Variables qui gere les fusees pre-construites
	private Fusee fuseeNasa, fuseeRusse, fuseeSpaceX, fuseeSelectionne;

	//Variables qui gerent la position des fusees pre-construites
	private Vecteur positionInitialeNasa, positionInitialeRusse, positionInitialeX;

	//Variable qui gere la liste des fusees pre-construites
	private ArrayList <Fusee> listFusee = new ArrayList <Fusee>();

	//Variable qui gere la liste d'ecouteurs
	private ArrayList <BienvenueListener> listeEcouteurs = new ArrayList <BienvenueListener> ();

	//Variable qui gere si la fusee est selectionne
	private boolean isFuseeSelectionne = false;

	//Variable qui gere quelle fusee pre-construite est cree
	private int indexFusee;

	//Variables qui gerent les images des drapeaux
	private Image img1 = null;
	private Image img2 = null;
	private Image img3 = null;

	//Variable qui gere les infos des fusees
	private JTextArea txtInfoFusee;
	private Vecteur positionSouris;
	
	private JPanel pnlLoadFusee;

	/**
	 * Constructeur qui permet le dessinage de la page de bienvenue
	 * @param mdBienvenue La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Johnatan G

	public DessinBienvenue(ModeleAffichage mdBienvenue) {//debut constructeur
		ajouterGestionSouris();

		lireImageNasa("nasa.png");
		lireImageRussie("russie.png");
		lireImageSpaceX("Logo-SpaceX.png");

		setBackground(Color.white);
		this.largeurMonde = mdBienvenue.getLargUnitesReelles();
		this.hauteurMonde = mdBienvenue.getHautUnitesReelles();

		this.mat = new AffineTransform(mdBienvenue.getMatMC());
		mat.translate(largeurMonde/2, hauteurMonde/1.4);
		mat.scale(1, -1);

		this.positionInitialeNasa = new Vecteur(-largeurMonde/8, 0, 0);
		this.positionInitialeRusse = new Vecteur(0,0,0);
		this.positionInitialeX = new Vecteur(largeurMonde/8, 0, 0);

		this.fuseeNasa = new Fusee(Fusee.creerFuseeNasa());
		this.fuseeNasa.deplacementFusee(positionInitialeNasa);


		this.fuseeRusse = new Fusee(Fusee.creerFuseeRusse());
		this.fuseeRusse.deplacementFusee(positionInitialeRusse);

		this.fuseeSpaceX = new Fusee(Fusee.creerFuseeSpaceX());
		this.fuseeSpaceX.deplacementFusee(positionInitialeX);

		setLayout(null);

		int x = (int) (this.largeurMonde/200 * mat.getScaleX());
		int y = (int) (this.hauteurMonde*-1/40 * mat.getScaleY());
		int w = (int) (this.largeurMonde/3.4 * mat.getScaleX());
		int h = (int) (this.hauteurMonde*-1*0.5 * mat.getScaleY());

		txtInfoFusee = new JTextArea();
		txtInfoFusee.setText("Nom: "
				+ "\nMasse: "
				+ "\nPrix:"
				+ "\nErgols: " 
				+ "\nImpulsion specifique (vide): "
				+ "\nImpulsion specifique (terrestre):"
				+ "\nVitesse d'ejection (terrestre) : " 
				+ "\nVitesse d'ejection (vide): "
				+ "\nPoussee (terrestre): " 
				+ "\nPoussee (vide): " 
				+ "\nHauteur: " 
				+ "\nDiametre: " 
				+ "\nDuree de fonctionnement: ");
		txtInfoFusee.setVisible(false);
		txtInfoFusee.setBounds(x, y, w, h);
		txtInfoFusee.setEditable(false);
		add(txtInfoFusee);
		txtInfoFusee.setColumns(10);

		listFusee.add(fuseeNasa);
		listFusee.add(fuseeRusse);
		listFusee.add(fuseeSpaceX);
		
		this.pnlLoadFusee = new JPanel();
		pnlLoadFusee.setBounds((int) (largeurMonde/2 * mat.getScaleX()), (int) ( hauteurMonde/2 * mat.getScaleY()), (int) ( largeurMonde/2 ), (int) ( hauteurMonde/2));
		pnlLoadFusee.setLayout(null);
		pnlLoadFusee.setBackground(Color.BLACK);
		add(pnlLoadFusee);
		
	}//fin constructeur


	/**
	 * Methode qui va dessiner la fusee construite
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Melie L

	public void paintComponent(Graphics g) {//debut methode


		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		for(int indexFusee = 0; indexFusee < listFusee.size(); indexFusee++) {
			listFusee.get(indexFusee).draw(g2d, mat);
		}

		g2d.drawImage(img1, (int)((largeurMonde/3 + largeurMonde/50)* mat.getScaleX()), (int) (-hauteurMonde/1.30 * mat.getScaleY()), (int)(largeurMonde/25 * mat.getScaleX()), (int)(largeurMonde/40 * mat.getScaleX()), null);
		g2d.drawImage(img2,  (int)((largeurMonde/2.18 + largeurMonde/50)* mat.getScaleX()), (int) (-hauteurMonde/1.32 * mat.getScaleY()), (int)(largeurMonde/25 * mat.getScaleX()), (int)(largeurMonde/30 * mat.getScaleX()), null);
		g2d.drawImage(img3,  (int)((largeurMonde/1.7 + largeurMonde/50)* mat.getScaleX()), (int) (-hauteurMonde/1.32 * mat.getScaleY()), (int)(largeurMonde/25 * mat.getScaleX()), (int)(largeurMonde/30 * mat.getScaleX()), null);


	}//fin methode

	/**
	 * Methode qui contient toutes les gestions de souris
	 * 
	 */
	//Johnatan G

	private void ajouterGestionSouris() {//debut methode

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {//debut pressed

				positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);

				isFuseeSelectionne = false;

				for(int indexFusee = 0; indexFusee < listFusee.size(); indexFusee++) {

					if(listFusee.get(indexFusee).contains(positionSouris)) {

						isFuseeSelectionne = true;
						DessinBienvenue.this.indexFusee = indexFusee;

						fuseeSelectionne = new Fusee(listFusee.get(DessinBienvenue.this.indexFusee));

						setFuseeSelectionneEvenement(new ArrayList<Moteur>(fuseeSelectionne.getListMoteur()), new ArrayList<Booster>(fuseeSelectionne.getListBooster()), new ArrayList<Reservoir>(fuseeSelectionne.getListReservoir()));

					}
				}

				montrerInformations();

			}//fin pressed
		});

	}//fin methode

	/**
	 * Methode qui montre les informations des fusees pre-construites
	 */
	//Melie L

	private void montrerInformations(){//debut methode

		if(isFuseeSelectionne) {
			txtInfoFusee.setVisible(true);
			txtInfoFusee.setText("Nom: "+fuseeSelectionne.getNom()
			+"\nDiametre total du moteur: "+(String.format("%.2f", fuseeSelectionne.getDiametreTotalMoteur())+" m"
					+"\nImpulsion specifique du booster: "+fuseeSelectionne.getImpulsionSpecifiqueBooster()+" secondes"
						+"\nImpulsion specifique du moteur: "+fuseeSelectionne.getImpulsionSpecifiqueMoteur()
							+"\nMasse: "+(String.format("%.2f",fuseeSelectionne.getMasse())+" kg"
								+"\nNombre de boosters: "+fuseeSelectionne.getNbBooster()+" booster(s)"
									+"\nMasse des boosters: "+fuseeSelectionne.getMasseCombustibleBooster()+ " kg"
										+"\nMasse du reservoir: "+fuseeSelectionne.getMasseCombustibleReservoir()+ " kg"
											+"\nRayon du reservoir: "+fuseeSelectionne.getRayonReservoir()+ " m"
				

							)
					)
					);
		}else {			
			txtInfoFusee.setVisible(false);
		}

	}//fin methode


	/**
	 * Methode qui va permettre d'ajouter les ecouteurs personalises au ArrayList
	 * @param objEcout Ecouteur personalise
	 */
	//Johnatan G

	public void addBienvenueListener(BienvenueListener objEcout) {//debut methode
		this.listeEcouteurs.add(objEcout);
	}//fin methode

	/**
	 * Methode qui lance un evenement qui notifie que la fusee est selectionne 
	 * @param listMoteur    La liste moteur
	 * @param listBooster   La liste de moteur
	 * @param listReservoir La liste de reservoir
	 */ 
	//Johnatan G
	
	private void setFuseeSelectionneEvenement(ArrayList <Moteur> listMoteur, ArrayList <Booster> listBooster, ArrayList <Reservoir> listReservoir) {//debut methode

		for(BienvenueListener objEcout: listeEcouteurs) {
			objEcout.setFuseeSelectionne(listMoteur, listReservoir, listBooster);
		}

	}//fin methode



	/**
	 * Methode qui va permettre la lecture d'un fichier du drapeau de la Nasa contenu dans le projet
	 * @param URL L'image du drapeau de la Nasa
	 */
	//Melie L

	private void lireImageNasa(String URL) {//debut methode
		URL urlImg = getClass().getClassLoader().getResource(URL);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				img1= ImageIO.read(urlImg);
			}

			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier du drapeau de la Russie contenu dans le projet
	 *  @param URL L'image du drapeau de la Russie
	 */
	//Melie L

	private void lireImageRussie(String URL) {//debut methode
		URL urlImg = getClass().getClassLoader().getResource(URL);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				img2= ImageIO.read(urlImg);
			}

			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

	}//fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier du drapeau de SpaceX contenu dans le projet
	 *  @param URL L'image du drapeau de SpaceX
	 */
	//Melie L

	private void lireImageSpaceX(String URL) {//debut methode
		URL urlImg = getClass().getClassLoader().getResource(URL);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}else {
			try {
				img3= ImageIO.read(urlImg);
			}

			catch (IOException e) {
				System.out.println("erreur de lecteur du fichier d'image");
			}
		}

	}//fin methode
}//fin classe
