package aapplication;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aapplication.information.APropos;
import aapplication.information.InfoScientifiques;
import aapplication.information.Instructions;
import aapplication.scene.Scene;
import aapplication.scene.dessin.DessinBienvenue;
import aapplication.scene.dessin.DessinBooster;
import aapplication.scene.dessin.DessinCentreControle;
import aapplication.scene.dessin.DessinFusee;
import aapplication.scene.dessin.DessinMoteur;
import aapplication.scene.dessin.DessinReservoirCombustible;
import graphique.DessinGraphiqueSortie;
import listeners.BienvenueListener;
import listeners.BoosterListener;
import listeners.CentreControleListener;
import listeners.FuseeListener;
import listeners.MoteurListener;
import listeners.ReservoirListener;
import modele.ModeleAffichage;
import modele.ModeleDeDonnees;
import objets.Objet;
import objets.fusee.Fusee;
import objets.fusee.composants.Booster;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.Reservoir;
import objets.fusee.composants.enums.Combustible;
import util.Constantes;
import util.MoteurPhysique;
import util.SMath;
import util.Save;
import util.Vecteur;

/**
 * Classe qui va demarrer l'application et montrer le resultat des animations a
 * l'aide d'ecouteurs personalises
 * 
 * @author Corentin Gouanvic
 * @author Ivana Bera
 * @author Johnatan Gao
 * @author Melie Leclerc
 */

public class App20SimulationFusee extends JFrame {// debut classe

	private static final long serialVersionUID = 1L;

	//////////////////////////////////////////////////////////// COMPOSANTS QUI
	//////////////////////////////////////////////////////////// ORGANISENT LES
	//////////////////////////////////////////////////////////// AUTRES COMPOSANTS

	private JPanel contentPane;
	private JPanel pnlobjetsFusee;
	private JPanel pnlFusee;

	//////////////////////////////////////////////////////////// COMPOSANT PERSONNEL
	//////////////////////////////////////////////////////////// SUR LEQUEL AFFICHE
	//////////////////////////////////////////////////////////// L'ANIMATION +
	//////////////////////////////////////////////////////////// CALCULS DES FORCES

	private Scene sceneAnimee;
	private final double SCALE_SCENE = 0.30;

	//////////////////////////////////////////////////////////// COMPOSANTS
	//////////////////////////////////////////////////////////// D'ACTIVATION

	private JButton btnQuitter;

	//////////////////////////////////////////////////////////// COMPOSANTS DES
	//////////////////////////////////////////////////////////// ONGLETS

	private JTabbedPane tabbedPaneFusee;
	private JLayeredPane layeredPaneControl;
	private JLayeredPane layeredPaneMoteur;
	private JLayeredPane layeredPaneCombustible;
	private JLayeredPane layeredPaneBooster;

	//////////////////////////////////////////////////////////// Modeles d'affichage

	private ModeleDeDonnees md = new ModeleDeDonnees();
	private ModeleAffichage modeleAffMoteur;
	private ModeleAffichage modeleAffReservoir;
	private ModeleAffichage modeleAffFusee;
	private ModeleAffichage modeleAffBooster;
	private ModeleAffichage modeleAffGraphique;
	private ModeleAffichage modeleAffBienvenue;
	private ModeleAffichage modeleAffEntrees;

	//////////////////////////////////////////////////////////// Variables de dessin

	private DessinFusee dessinFusee;
	private DessinMoteur dessinMoteur;
	private DessinBooster dessinBooster;
	private DessinReservoirCombustible dessinReservoirCombustible;
	private JMenu mnEnregistrer;
	private JMenuItem mntmEnregistrerSystSolaire;
	private JMenu mnApropos;

	//////////////////////////////////////////////////////////// Ajout de planetes

	private AjoutFenetre ajout = new AjoutFenetre();

	//////////////////////////////////////////////////////////// Modifications
	//////////////////////////////////////////////////////////// d'objets

	private ModificationObjet modificationObjet = new ModificationObjet();

	//////////////////////////////////////////////////////////// Spinner masse
	//////////////////////////////////////////////////////////// Kerosene et LOX

	private JSpinner spnMasseKerosene;
	private JSpinner spnMasseLOX;

	//////////////////////////////////////////////////////////// COMPOSANTS DU MENU

	private JMenuBar menuBar;
	private JMenu mnCharger;
	private JMenu mnAide;
	private JMenu mnEditeur;
	private JMenuItem mntmChargerSystSolaire;
	private JMenuItem mntmChargerFusee;
	private JMenuItem mntmInstructions;
	private JMenuItem mntmDonneesScientifiques;
	private JMenuItem mntmAjouter;
	private JMenuItem mntmSupprimer;
	private JMenuItem mntmEnregistrerFusee;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmEditerUnObjet;
	private ModeleDeDonnees mdSauvegarder;
	private String nomSauvegardeSolaire = null;

	/////////////////////////////////////////////////////////// Fenetres
	/////////////////////////////////////////////////////////// instructions et
	/////////////////////////////////////////////////////////// scientifiques

	private Instructions fenInstructions;
	private InfoScientifiques fenScientifique;
	private APropos aPropos;

//////////////////////////////////////////////////////////Composants de toutes les sorties
	private JLayeredPane layeredPaneGraphiques;

	////////////////////////////////////////////////////////// Composants de toutes
	////////////////////////////////////////////////////////// les sorties

	private DessinGraphiqueSortie graphiqueSortie1;
	private DessinGraphiqueSortie graphiqueSortie2;
	private DessinGraphiqueSortie graphiqueSortie3;
	private DessinGraphiqueSortie graphiqueSortie4;
	private JComboBox cboxChoixGraphique1;
	private JComboBox cboxChoixGraphique2;
	private JComboBox cboxChoixGraphique3;
	private JComboBox cboxChoixGraphique4;
	private DessinCentreControle dessinCentreControle;

	//////////////////////////////////////////////////////////// Composants de la
	//////////////////////////////////////////////////////////// page de Bienvenue

	private JLayeredPane layeredPaneDebut;
	private DessinBienvenue dessinBienvenue;
	
	//Variable de la dimension de l'ecran
	private Dimension screen;
	
	//Variable qui gère si l'objet est dans l'espace
	private Boolean isInSpace = false;
	
	//Variable qui gere la fusee dans l'Espace
	private Fusee fEspace = new Fusee();
	private Fusee fuseeInitiale;
	private Fusee fuseeSauvegarder = new Fusee();
	private JComboBox cmbBoxLoadFusee;
	
	////////////////////////////////////////////
	
	private ArrayList <Objet> listeObjetSauvegarde = new ArrayList <Objet> ();
	private ArrayList <String> listNom = new ArrayList <String>();
	
	///////////////////////////////////////////
	
	/**
	 * La methode main de l'application qui va creer la fenetre JFrame sur lequel
	 * les autres composants se baseront
	 * @param args
	 */

	public static void main(String[] args) {//debut
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App20SimulationFusee fenetrePrincipale = new App20SimulationFusee();

					fenetrePrincipale.setExtendedState(JFrame.MAXIMIZED_BOTH);
					fenetrePrincipale.setUndecorated(true);
					fenetrePrincipale.setVisible(true);
					
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (Exception e) {
					    // If Nimbus is not available, you can set the GUI to another look and feel.
					}
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//fin

	/**
	 * Le constructeur de l'application qui va creer tous les composants sur le
	 * JFrame
	 */

	public App20SimulationFusee() {// debut constructeur
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lireSauvegarde();
//////////////////////////////////////////////////////////////////COMPOSANTS DU PANNEAU DE SORTIES///////////////////////////////////////

		initialiserMenuBar();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, (int) screen.getWidth(), (int) (screen.getHeight()));

////////////////////////////////////////////////SCENE ANIME DE L'ESPACE/////////////////////////////////////////////////////////////////////

		sceneAnimee = new Scene(md);
		sceneAnimee.setBounds(0, 0, (int) screen.getWidth(), (int) (screen.getWidth() * SCALE_SCENE));
		contentPane.add(sceneAnimee);
		sceneAnimee.setLayout(null);

////////////////////////////////////////////////COMPOSANT DE L'ANIMATION DE LA FUSEE/////////////////////////////////////////////////////////////////////

		// Johnatan G
		pnlobjetsFusee = new JPanel();
		pnlobjetsFusee.setBackground(new Color(0, 0, 0, 1f));
		pnlobjetsFusee.setBounds(0, sceneAnimee.getHeight(), (int) (screen.getWidth()),
				(int) (((screen.getHeight() - sceneAnimee.getHeight()) * 0.96)));
		contentPane.add(pnlobjetsFusee);

		// Johnatan G
		tabbedPaneFusee = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneFusee.setBounds(0, 0, (int) (screen.getWidth() * 0.8), pnlobjetsFusee.getHeight());
		pnlobjetsFusee.setLayout(null);

		// Johnatan G
		pnlFusee = new JPanel();
		pnlFusee.setBounds((int) tabbedPaneFusee.getWidth(), (int) Scene.HEIGHT,
				(int) (screen.getWidth() - tabbedPaneFusee.getWidth()), tabbedPaneFusee.getHeight()); 
		pnlobjetsFusee.add(pnlFusee);
		pnlFusee.setLayout(null);

		// Johnatan G
		modeleAffFusee = new ModeleAffichage(pnlFusee.getWidth(), pnlFusee.getHeight(), 80);
		dessinFusee = new DessinFusee(modeleAffFusee);

		dessinFusee.addFuseeListener(new FuseeListener() {//debut ecouteur

			public void setCombustibleUtilise(Moteur m, int listMoteurSize) {// debut methode

				setCombustible(m, listMoteurSize);

			}// fin methode

			public void enleverObjetUtilise(int listMoteurSize) {// debut methode

				enleverObjet(listMoteurSize);

			}// fin methode

			public void updateValeurs(double tempsEcoule, double sommeDesForcesX, double sommeDesForcesY,
					double vitesseX, double vitesseY, double accelerationX, double accelerationY, double masseFusee,
					double masseReservoir, double masseBooster, double resistanceAir, double distanceFuseeTerre,
					double angleFusee) {// debut methode

				updateGraphique(graphiqueSortie1, graphiqueSortie2, graphiqueSortie3, graphiqueSortie4, tempsEcoule,
						sommeDesForcesX, sommeDesForcesY, vitesseX, vitesseY, accelerationX, accelerationY,
						masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);

			}// fin methode

			public void dansEspace(Vecteur positionFusee, double masseFusee) {//debut methode
				
				calculerFuseeDansEspace(positionFusee, masseFusee);
				
			}//fin methode

		}//fin ecouteurs
		);
		dessinFusee.setBounds(0, 0, pnlFusee.getWidth(), (int) (pnlFusee.getHeight()));
		pnlFusee.add(dessinFusee);

		//////////////////////////////////////////////// COMPOSANT QUI INCLUT LA
		//////////////////////////////////////////////// CONSTRUCTION DE LA
		//////////////////////////////////////////////// FUSEE/////////////////////////////////////////////////////////////////////

		// Johnatan G
		pnlobjetsFusee.add(tabbedPaneFusee);

		// Johnatan G
		layeredPaneDebut = new JLayeredPane();
		layeredPaneDebut.setOpaque(true);
		layeredPaneDebut.setBackground(Color.white);
		tabbedPaneFusee.addTab("Bienvenue!", null, layeredPaneDebut, null);

		// Johnatan G
		this.modeleAffBienvenue = new ModeleAffichage(tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight(), 400);
		dessinBienvenue = new DessinBienvenue(this.modeleAffBienvenue);
		dessinBienvenue.addBienvenueListener(new BienvenueListener() {//debut ecouteur

			public void setFuseeSelectionne(ArrayList<Moteur> listMoteur, ArrayList<Reservoir> listReservoir,
					ArrayList<Booster> listBooster) {// debut methode

				setFusee(listMoteur, listReservoir, listBooster);

			}// fin methode
		}//fin ecouteur
		);
		
		cmbBoxLoadFusee = new JComboBox<Objet>();
		cmbBoxLoadFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		cmbBoxLoadFusee.setModel(new DefaultComboBoxModel(listNom.toArray()));
		cmbBoxLoadFusee.setBounds((int) (pnlobjetsFusee.getWidth()/2 + pnlobjetsFusee.getWidth()/10), (int) (pnlobjetsFusee.getHeight()/2 + pnlobjetsFusee.getHeight()/3.5), (int) (pnlobjetsFusee.getWidth()/8),  (int) (pnlobjetsFusee.getHeight()/20));
		cmbBoxLoadFusee.setVisible(true);
		layeredPaneDebut.add(cmbBoxLoadFusee);
		
		dessinBienvenue.setBounds(0, 0, (int) modeleAffBienvenue.getLargPixels(),
				(int) modeleAffBienvenue.getHautPixels());
		layeredPaneDebut.add(dessinBienvenue);
		
		
		// Johnatan G
		layeredPaneMoteur = new JLayeredPane();
		tabbedPaneFusee.addTab("Moteur", null, layeredPaneMoteur, null);

		// Johnatan G
		this.modeleAffMoteur = new ModeleAffichage(tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight(), 30);
		dessinMoteur = new DessinMoteur(modeleAffMoteur);
		dessinMoteur.addMoteurListener(new MoteurListener() {//debut ecouteur

			public void deselectionnerMoteur() {// debut methode

				deslectionnerM();

			}// fin methode

			public void dragMoteurSelectionne(Vecteur positionSouris, double largeurMonde, AffineTransform matMoteur) {// debut
																														// methode

				dragMoteur(positionSouris, largeurMonde, matMoteur);

			}// fin methode

			public void setMoteurSelectionne(Moteur m, Vecteur positionSouris, double largeur,
					AffineTransform matMoteur) {// debut methode

				setMoteur(m, positionSouris, largeur, matMoteur);

			}// fin methode
		}//fin ecouteur
		);

		// Johnatan G
		dessinMoteur.setBounds(0, 0, tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight());
		layeredPaneMoteur.add(dessinMoteur);

		// Melie L
		layeredPaneCombustible = new JLayeredPane();

		tabbedPaneFusee.addTab("Combustible et Reservoir", null, layeredPaneCombustible, null);


		// Melie L
		modeleAffReservoir = new ModeleAffichage(tabbedPaneFusee.getWidth(), pnlobjetsFusee.getHeight(), 200);
		dessinReservoirCombustible = new DessinReservoirCombustible(modeleAffReservoir);
		dessinReservoirCombustible.addReservoirListener(new ReservoirListener() {//debut ecouteur

			public void deselectionneReservoir() {// debut methode

				deselectionneR();

			}// fin methode

			public void dragReservoirSelectionne(Vecteur positionSouris, double largeur, AffineTransform matReservoir) {// debut
																														// methode

				dragReservoir(positionSouris, largeur, matReservoir);

			}// fin methode

			public void setReservoirSelectionne(Reservoir r, Vecteur positionSouris, double largeur,
					AffineTransform matReservoir) {// debut methode

				setReservoir(r, positionSouris, largeur, matReservoir);

			}// fin methode
		}//fin ecouteur
		);
		dessinReservoirCombustible.setBounds(0, 0, tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight());
		layeredPaneCombustible.add(dessinReservoirCombustible);
		dessinReservoirCombustible.setLayout(null);

		// Melie L
		JLabel lblLOX = new JLabel("Masse d'oxygene liquide et d'hydrogene liquide (en kg)");
		lblLOX.setBounds(10, 40, 782, 25);
		dessinReservoirCombustible.add(lblLOX);

		// Melie L
		JLabel lblMasseDeKerosene = new JLabel("Masse de kerosene liquide (en kg)");
		lblMasseDeKerosene.setBounds(10, 149, 680, 25);
		dessinReservoirCombustible.add(lblMasseDeKerosene);

		// Johnatan G
		spnMasseLOX = new JSpinner();
		spnMasseLOX.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(100000)));
		spnMasseLOX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {// debut

				modifierSpnLOX();

			}// fin
		});
		spnMasseLOX.setBounds(10, 91, 144, 20);
		dessinReservoirCombustible.add(spnMasseLOX);

		// Johnatan G
		spnMasseKerosene = new JSpinner();
		spnMasseKerosene.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(100000)));
		spnMasseKerosene.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {// debut
				modifierSpnKerosene();
			
			}// fin
		});

		spnMasseKerosene.setBounds(10, 207, 144, 20);
		dessinReservoirCombustible.add(spnMasseKerosene);

		// Melie L
		layeredPaneBooster = new JLayeredPane();
		tabbedPaneFusee.addTab("Booster", null, layeredPaneBooster, null);

		// Melie L
		modeleAffBooster = new ModeleAffichage(tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight(), 200);
		dessinBooster = new DessinBooster(modeleAffBooster);
		dessinBooster.addBoosterListener(new BoosterListener() {

			public void deselectionnerBooster() {// debut methode

				deselectionnerB();

			}// fin methode

			public void dragBoosterSelectionne(Vecteur positionSouris, double largeurMonde,
					AffineTransform matBooster) {// debut methode

				dragB(positionSouris, largeurMonde, matBooster);

			}// fin methode

			public void setBoosterSelectionne(Booster b, Vecteur positionSouris, double largeurMonde,
					AffineTransform matBooster) {// debut methode

				setBooster(b, positionSouris, largeurMonde, matBooster);

			}// fin methode
		});
		dessinBooster.setBounds(0, 0, tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight());
		layeredPaneBooster.add(dessinBooster);

		// Johnatan G
		layeredPaneControl = new JLayeredPane();

		tabbedPaneFusee.addTab("Centre de controle", null, layeredPaneControl, null);

		// Johnatan G
		this.modeleAffEntrees = new ModeleAffichage(tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight(), 30);
		dessinCentreControle = new DessinCentreControle(modeleAffEntrees);
		dessinCentreControle.addCentreControleListener(new CentreControleListener() {
			public void animerGlobal() {//debut methode

				animerGlobalement();

			}//fin methode
			public void arreterGlobal() {//debut methode

				arreterGlobalement();

			}//fin methode
			public void changerAngleDeviation(double angle) {//debut methode

				changerDeviationAngle( angle);

			}//fin methode

			public void changerEcoulementTemps(double nDeltaT) {//debut methode

				changerEcoulementT(nDeltaT);
				
				if(isInSpace) {
					
					md.setDeltaT(dessinCentreControle.getSldDeltaT().getValue() * 150);
					
				}
				
				
			}//fin methode
			public void changerHauteurDeviation(double hauteurDeviation) {//debut methode
				
				changerDeviationHauteur(hauteurDeviation);
				
			}//fin methode
			public void changerPousseeBoosters(double pourcentagePousseeBoosters) {//debut methode
				
				changerPousseeB(pourcentagePousseeBoosters);
				
			}//fin methode
			
			public void changerPousseeMoteurs(double pourcentagePousseeMoteurs) {//debut methode
				
				changerPousseeM(pourcentagePousseeMoteurs);
				
			}//fin methode
			public void lancerFusee() {//debut methode
				
				lancementFusee();
				
			}//fin methode
			
			public void prochainGlobal() {//debut methode
				
				prochainGlobalement();
				
			}//fin methode
			public void reinitFusee() {//debut methode
				
				reinitialiserFusee();
				
			}//fin methode
			public void reinitGlobal() {//debut methode
				
				reinitGlobalement();
				
			}//fin methode
			
			public void arreterSon() {//debut methode
				arretSon();
			}//fin methode
			
			public void allumerSon() {//debut methode
				allumeSon();
			}//fin methode
		});
		dessinCentreControle.setBounds(0, 0, tabbedPaneFusee.getWidth(), tabbedPaneFusee.getHeight());
		layeredPaneControl.add(dessinCentreControle);
		layeredPaneGraphiques = new JLayeredPane();
		layeredPaneGraphiques.setOpaque(true);
		layeredPaneGraphiques.setBackground(Color.white);
		tabbedPaneFusee.addTab("Graphiques", null, layeredPaneGraphiques, null);
		// Graphiques faits par Johnatan G

		// Johnatan G
		modeleAffGraphique = new ModeleAffichage(tabbedPaneFusee.getWidth() / 4.22, tabbedPaneFusee.getHeight() * 0.75,
				20);

		// Johnatan G
		graphiqueSortie1 = new DessinGraphiqueSortie(modeleAffGraphique, Color.blue);
		graphiqueSortie1.setBounds(tabbedPaneFusee.getWidth() / 100, tabbedPaneFusee.getHeight() / 50,
				(int) (tabbedPaneFusee.getWidth() / 4.22), (int) (tabbedPaneFusee.getHeight() * 0.75));

		layeredPaneGraphiques.add(graphiqueSortie1);

		// Johnatan G
		graphiqueSortie2 = new DessinGraphiqueSortie(modeleAffGraphique, Color.black);
		graphiqueSortie2.setBounds(graphiqueSortie1.getX() * 2 + graphiqueSortie1.getWidth(),
				tabbedPaneFusee.getHeight() / 50, graphiqueSortie1.getWidth(), graphiqueSortie1.getHeight());

		layeredPaneGraphiques.add(graphiqueSortie2);

		// Johnatan G
		graphiqueSortie3 = new DessinGraphiqueSortie(modeleAffGraphique, Color.RED);
		graphiqueSortie3.setBounds(
				graphiqueSortie2.getX() + graphiqueSortie2.getWidth() + tabbedPaneFusee.getWidth() / 100,
				tabbedPaneFusee.getHeight() / 50, graphiqueSortie1.getWidth(), graphiqueSortie1.getHeight());

		layeredPaneGraphiques.add(graphiqueSortie3);

		// Johnatan G
		graphiqueSortie4 = new DessinGraphiqueSortie(modeleAffGraphique, Color.magenta);
		graphiqueSortie4.setBounds(
				graphiqueSortie3.getX() + graphiqueSortie3.getWidth() + tabbedPaneFusee.getWidth() / 100,
				tabbedPaneFusee.getHeight() / 50, graphiqueSortie1.getWidth(), graphiqueSortie1.getHeight());

		layeredPaneGraphiques.add(graphiqueSortie4);

		// Johnatan G
		int widthCBox = (int) (graphiqueSortie1.getWidth() * 0.75);
		int heightCBox = graphiqueSortie1.getHeight() / 15;

		// Johnatan G
		cboxChoixGraphique1 = new JComboBox();
		cboxChoixGraphique1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// debut

				selectionnerComboBox1();

			}// fin
		});
		setComboBoxModel(cboxChoixGraphique1);

		// Johnatan G
		cboxChoixGraphique1.setSelectedIndex(1);
		cboxChoixGraphique1.setBounds((graphiqueSortie1.getX() + graphiqueSortie1.getWidth()) / 2 - widthCBox / 2,
				graphiqueSortie1.getY() + graphiqueSortie1.getHeight(), widthCBox, heightCBox);
		layeredPaneGraphiques.add(cboxChoixGraphique1);

		// Johnatan G
		cboxChoixGraphique2 = new JComboBox();
		cboxChoixGraphique2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// debut
				selectionnerComboBox2();
			}// fin
		});
		setComboBoxModel(cboxChoixGraphique2);
		cboxChoixGraphique2.setSelectedIndex(3);

		// Johnatan G
		cboxChoixGraphique2.setBounds((graphiqueSortie2.getX() + graphiqueSortie1.getWidth() / 2 - widthCBox / 2),
				graphiqueSortie1.getY() + graphiqueSortie1.getHeight(), widthCBox, heightCBox);
		layeredPaneGraphiques.add(cboxChoixGraphique2);

		// Johnatan G
		cboxChoixGraphique3 = new JComboBox();
		cboxChoixGraphique3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// debut

				selectionnerComboBox3();

			}// fin
		});

		setComboBoxModel(cboxChoixGraphique3);
		cboxChoixGraphique3.setSelectedIndex(5);
		cboxChoixGraphique3.setBounds((graphiqueSortie3.getX() + graphiqueSortie1.getWidth() / 2 - widthCBox / 2),
				graphiqueSortie1.getY() + graphiqueSortie1.getHeight(), widthCBox, heightCBox);

		// Johnatan G
		layeredPaneGraphiques.add(cboxChoixGraphique3);

		// Johnatan G
		cboxChoixGraphique4 = new JComboBox();
		cboxChoixGraphique4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {// debut

				selectionnerComboBox4();

			}// fin
		});
		setComboBoxModel(cboxChoixGraphique4);
		cboxChoixGraphique4.setSelectedIndex(6);
		cboxChoixGraphique4.setBounds((graphiqueSortie4.getX() + graphiqueSortie1.getWidth() / 2 - widthCBox / 2),
				graphiqueSortie1.getY() + graphiqueSortie1.getHeight(), widthCBox, heightCBox);
		layeredPaneGraphiques.add(cboxChoixGraphique4);

		//////////////////////////////////////////////// COMPOSANTS DES
		//////////////////////////////////////////////// BOUTONS/////////////////////////////////////////////////////////////////////

		sceneAnimee.setFocusable(true);
		sceneAnimee.requestFocus();
		sceneAnimee.grabFocus();

		ajout.setModeleDeDonnees(md);
		modificationObjet.setModeleDeDonnees(md);
		
	}// fin constructeur

	/**
	 * Methode qui permet la lecture de sauvegarde
	 */
	//Corentin G
	private void lireSauvegarde() {
		Save loader = new Save();
		fuseeSauvegarder = new Fusee();
		try {
			fuseeSauvegarder = loader.loadFusee("save");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode qui notifie quel combustible doit etre utilise
	 * 
	 * @param m              Le moteur utilise
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	// Johnatan G

	private void setCombustible(Moteur m, int listMoteurSize) {// debut methode
		dessinMoteur.setNomMoteurSelectionne(m.getNom());

		if (m.getErgols().equalsIgnoreCase("lox_lh2")) {
			choisirLOX();
		} else if (m.getErgols().equalsIgnoreCase("kerosene_rp1")) {
			choisirKerosene();
		}

		dessinMoteur.setListMoteurSize(listMoteurSize);

		spnMasseKerosene.setEnabled(dessinMoteur.isKerosene());
		spnMasseLOX.setEnabled(dessinMoteur.isLOX());

	}// fin methode

	/**
	 * Methode qui notifie quel combustible doit etre enleve
	 * 
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	// Johnatan G

	private void enleverObjet(int listMoteurSize) {// debut methode
		dessinMoteur.setListMoteurSize(listMoteurSize);

		if (listMoteurSize == 0) {

			dessinMoteur.setNomMoteurSelectionne(""); // Pour dire qu'il n'y a pas de moteur selectionne
			
			spnMasseLOX.setValue((double) 0);
			spnMasseKerosene.setValue((double) 0);

			if ((double) spnMasseLOX.getValue() != 0) {
				choisirLOX();
			} else if ((double) spnMasseKerosene.getValue() != 0) {
				choisirKerosene();
			} else {

				if (dessinFusee.getFuseeConstruite().getListReservoir().size() == 0) {
					
					choisirLesDeux();

				}

			}
		}

		spnMasseKerosene.setEnabled(dessinMoteur.isKerosene());
		spnMasseLOX.setEnabled(dessinMoteur.isLOX());

		dessinMoteur.repaint();

	}// fin methode

	/**
	 * Methode qui met a jour les valeurs du graphique
	 * 
	 * @param dG1                Le dessin du premier graphique
	 * @param dG2                Le dessin du deuxieme graphique
	 * @param dG3                Le dessin du troisieme graphique
	 * @param dG4                Le dessin du quatrieme graphique
	 * @param tempsEcoule        Le temps ecoule
	 * @param sommeDesForcesX    La somme des forces en X
	 * @param sommeDesForcesY    La somme des forces en Y
	 * @param vitesseX           La vitesse de la fusee en X
	 * @param vitesseY           La vitesse de la fusee en Y
	 * @param accelerationX      L'acceleration de la fusee en X
	 * @param accelerationY      L'acceleration de la fusee en Y
	 * @param masseFusee         La masse de la fusee
	 * @param masseReservoir     La masse du reservoir de la fusee
	 * @param masseBooster       La masse du booster de la fusee
	 * @param resistanceAir      La resistance de l'air de la fusee
	 * @param distanceFuseeTerre La distance entre le fusee et la Terre
	 * @param angleFusee         L'angle de la fusee
	 */
	// Johnatan G

	private void updateGraphique(DessinGraphiqueSortie dG1, DessinGraphiqueSortie dG2, DessinGraphiqueSortie dG3,
			DessinGraphiqueSortie dG4, double tempsEcoule, double sommeDesForcesX, double sommeDesForcesY,
			double vitesseX, double vitesseY, double accelerationX, double accelerationY, double masseFusee,
			double masseReservoir, double masseBooster, double resistanceAir, double distanceFuseeTerre,
			double angleFusee) {// debut methode

		dG1.updateValeurs(tempsEcoule, sommeDesForcesX, sommeDesForcesY, vitesseX, vitesseY, accelerationX,
				accelerationY, masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);

		dG2.updateValeurs(tempsEcoule, sommeDesForcesX, sommeDesForcesY, vitesseX, vitesseY, accelerationX,
				accelerationY, masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);

		dG3.updateValeurs(tempsEcoule, sommeDesForcesX, sommeDesForcesY, vitesseX, vitesseY, accelerationX,
				accelerationY, masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);

		dG4.updateValeurs(tempsEcoule, sommeDesForcesX, sommeDesForcesY, vitesseX, vitesseY, accelerationX,
				accelerationY, masseFusee, masseReservoir, masseBooster, resistanceAir, distanceFuseeTerre, angleFusee);

	}// fin methode

	/**
	 * Methode qui modifie quelle fusee pre-construite est selectionnee
	 * 
	 * @param listMoteur    La liste de moteur
	 * @param listReservoir La liste de reservoir
	 * @param listBooster   La liste de booster
	 */
	// Johnatan G

	private void setFusee(ArrayList<Moteur> listMoteur, ArrayList<Reservoir> listReservoir,
			ArrayList<Booster> listBooster) {// debut methode

		if (SMath.nearlyEquals(dessinFusee.getFuseeConstruite().getPosition().getY(), 0)) {
			dessinFusee.clearAll();

			for (int indexMoteur = 0; indexMoteur < listMoteur.size(); indexMoteur++) {

				dessinFusee.setObjetSelectionne(listMoteur.get(indexMoteur), new Vecteur(), 0, new AffineTransform());
				dessinFusee.deselectionneObjet();

			}

			for (int indexReservoir = 0; indexReservoir < listReservoir.size(); indexReservoir++) {

				if (listReservoir.get(indexReservoir).getNom().equalsIgnoreCase(Combustible.LOX_LH2.getNom())) {
					App20SimulationFusee.this.spnMasseLOX
							.setValue(listReservoir.get(indexReservoir).getMasseCombustible());
					App20SimulationFusee.this.spnMasseKerosene.setValue(0.0);

					dessinReservoirCombustible.setMasseLOX((double) spnMasseLOX.getValue());

				} else if (listReservoir.get(indexReservoir).getNom()
						.equalsIgnoreCase(Combustible.KEROSENE_RP1.getNom())) {
					App20SimulationFusee.this.spnMasseKerosene
							.setValue(listReservoir.get(indexReservoir).getMasseCombustible());
					App20SimulationFusee.this.spnMasseLOX.setValue(0.0);

					dessinReservoirCombustible.setMasseKerosene((double) spnMasseKerosene.getValue());
				}

				dessinFusee.setObjetSelectionne(listReservoir.get(indexReservoir), new Vecteur(), 0,
						new AffineTransform());
				dessinFusee.deselectionneObjet();

			}

			for (int indexBooster = 0; indexBooster < listBooster.size(); indexBooster++) {

				dessinFusee.setObjetSelectionne(listBooster.get(indexBooster), new Vecteur(), 0, new AffineTransform());
				dessinFusee.deselectionneObjet();

			}
		}

	}// fin methode

	/**
	 * Methode qui notifie que le moteur est deselectionne
	 */
	// Johnatan G

	private void deslectionnerM() {// debut methode

		dessinFusee.deselectionneObjet();

	}// fin methode

	/**
	 * Methode qui notifie que le moteur est en mouvement
	 * 
	 * @param positionSouris La position du curseur de la souris
	 * @param largeurMonde   La largeur du monde reel
	 * @param matMoteur      La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 */
	// Johnatan G

	private void dragMoteur(Vecteur positionSouris, double largeurMonde, AffineTransform matMoteur) {// debut methode
		dessinFusee.dragObjetSelectionne(positionSouris, largeurMonde, matMoteur);
	}// fin methode

	/**
	 * Methode qui notifie quel moteur est selectionne
	 * 
	 * @param m              Le moteur selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matMoteur      La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 */
	// Johnatan G

	private void setMoteur(Moteur m, Vecteur positionSouris, double largeur, AffineTransform matMoteur) {// debut
																											// methode
		dessinFusee.setObjetSelectionne(m, positionSouris, largeur, matMoteur);
	}// fin methode

	/**
	 * Methode qui notifie que le reservoir est deselectionne
	 * 
	 */
	// Melie L

	private void deselectionneR() {// debut methode

		dessinFusee.deselectionneObjet();

	}// fin methode

	/**
	 * Methode qui notifie que le reservoir est en mouvement
	 * 
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matReservoir   La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 */
	// Melie L

	private void dragReservoir(Vecteur positionSouris, double largeur, AffineTransform matReservoir) {// debut methode

		dessinFusee.dragObjetSelectionne(positionSouris, largeur, matReservoir);

	}// fin methode

	/**
	 * Methode qui notifie quel reservoir est selectionne
	 * 
	 * @param r              Le reservoir selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeur        La largeur du monde reel
	 * @param matReservoir   La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 */
	// Melie L

	private void setReservoir(Reservoir r, Vecteur positionSouris, double largeur, AffineTransform matReservoir) {// debut
																													// methode

		dessinFusee.setObjetSelectionne(r, positionSouris, largeur, matReservoir);

	}// fin methode

	/**
	 * Methode qui notifie que le booster est deselectionne
	 */
	// Melie L

	private void deselectionnerB() {// debut methode

		dessinFusee.deselectionneObjet();

	}// fin methode

	/**
	 * Methode qui notifie que le booster est en mouvement
	 * 
	 * @param positionSouris La position du curseur de la souris
	 * @param largeurMonde   La largeur du monde reel
	 * @param matBooster     La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 * 
	 */
	// Melie L

	private void dragB(Vecteur positionSouris, double largeurMonde, AffineTransform matBooster) {// debut methode
		dessinFusee.dragObjetSelectionne(positionSouris, largeurMonde, matBooster);
	}// fin methode

	/**
	 * Methode qui notifie quel le booster est selectionne
	 * 
	 * @param b              Le booster selectionne
	 * @param positionSouris La position du curseur de la souris
	 * @param largeurMonde   La largeur du monde reel
	 * @param matBooster     La matrice de transformation qui va permettre le
	 *                       passage des unites pixels en unites reelles
	 * 
	 */
	// Melie L

	private void setBooster(Booster b, Vecteur positionSouris, double largeurMonde, AffineTransform matBooster) {// debut
																													// methode

		dessinFusee.setObjetSelectionne(b, positionSouris, largeurMonde, matBooster);

	}// fin methode

	/**
	 * Methode qui selectionne le premier graphique
	 */
	// Johnatan G

	private void selectionnerComboBox1() {// debut methode
		selectionnerComboBox(cboxChoixGraphique1, graphiqueSortie1);
	}// fin methode

	/**
	 * Methode qui selectionne le deuxieme graphique
	 */
	// Johnatan G

	private void selectionnerComboBox2() {// debut methode
		selectionnerComboBox(cboxChoixGraphique2, graphiqueSortie2);
	}// fin methode

	/**
	 * Methode qui selectionne le troisieme graphique
	 */
	// Johnatan G

	private void selectionnerComboBox3() {// debut methode
		selectionnerComboBox(cboxChoixGraphique3, graphiqueSortie3);
	}// fin methode

	/**
	 * Methode qui selectionne le quatrieme graphique
	 */
	// Johnatan G

	private void selectionnerComboBox4() {// debut methode
		selectionnerComboBox(cboxChoixGraphique4, graphiqueSortie4);
	}// fin methode

	/**
	 * Methode qui rend accessible LOX
	 */
	// Johnatan G

	private void choisirLOX() {// debut methode

		dessinMoteur.setKerosene(false);
		dessinMoteur.setLOX(true);

	}// fin methode

	/**
	 * Methode qui rend accessible Kerosene
	 */
	// Johnatan G

	private void choisirKerosene() {// debut methode

		dessinMoteur.setKerosene(true);
		dessinMoteur.setLOX(false);

	}// fin methode
	
	/**
	 * Methode qui permet de rendre accessible LOX et Kerosene
	 */
	//Johnatan G
	
	private void choisirLesDeux() {//debut methode
		
		dessinMoteur.setKerosene(true);
		dessinMoteur.setLOX(true);
		
	}//fin methode	

	/**
	 * Methode qui anime la fusee globalement
	 */
	//Johnatan G
	
	private void animerGlobalement() {//debut methode
		
		sceneAnimee.demarrer();
		
		if(!dessinCentreControle.getBtnlancerFusee().isEnabled()) {
			dessinFusee.demarrer();
		}
		
	}//fin methode
	
	/**
	 * Methode qui arrete la fusee globalement
	 */
	//Johnatan G
	
	private void arreterGlobalement() {//debut methode
		
		// debut
		sceneAnimee.arreter();
		dessinFusee.arreter();
		// fin 
		
	}//fin methode
	
	/**
	 * Methode qui change l'angle de deviation de la fusee
	 * @param angle L'angle de deviation de la fusee
	 */
	//Johnatan G
	
	private void changerDeviationAngle(double angle) {//debut methode
		
		dessinFusee.getFuseeConstruite().setAngleDeviation(angle);
		dessinFusee.repaint();
		
	}//fin methode
	
	/**
	 * Methode qui change l'ecoulement du temps
	 * @param nDeltaT L'ecoulement du temps
	 */
	//Johnatan G
	
	private void changerEcoulementT(double nDeltaT) {//debut methode
		
		dessinFusee.setNDeltaT(nDeltaT);
		md.setDeltaT(dessinFusee.getDeltaT());
		repaint();
		
	}//fin methode
	
	/**
	 * Methode qui modifie la hauteur de la fusee a laquelle elle va devier
	 * @param hauteurDeviation La hauteur de la fusee a laquelle elle va devier
	 */
	// Johnatan G
	
	private void changerDeviationHauteur(double hauteurDeviation) {//debut methode
		
		dessinFusee.getFuseeConstruite().setHauteurDeviation(hauteurDeviation);
		repaint();
		
	}//fin methode
	
	/**
	 * Methode qui modifie le pourcentage de la pousse du booster
	 * @param pourcentagePousseeBoosters Le pourcentage de la pousse du booster
	 */
	// Johnatan G
	
	private void changerPousseeB(double pourcentagePousseeBoosters) {//debut methode
		
		dessinFusee.getFuseeConstruite().setBoosterThrustScale(pourcentagePousseeBoosters);
		dessinFusee.repaint();
		
	}//fin methode
	
	/**
	 * Methode qui modifie le pourcentage de la pousse du moteur
	 * @param pourcentagePousseeMoteurs Le pourcentage de la pousse du moteur
	 */
	// Johnatan G
	
	private void changerPousseeM(double pourcentagePousseeMoteurs) {//debut methode
		
		dessinFusee.getFuseeConstruite().setMoteurThrustScale(pourcentagePousseeMoteurs);
		dessinFusee.repaint();
		
	}//fin methode
	
	/**
	 * Methode qui permet de lancement de la fusee
	 */
	// Johnatan G
	private void lancementFusee() {//debut methode
		fuseeInitiale = new Fusee(dessinFusee.getFuseeConstruite());
		
		System.out.println(dessinFusee.getDeltaT());
		
		md.setDeltaT(dessinFusee.getDeltaT());
		dessinFusee.demarrer();
		
		if(dessinFusee.getFuseeConstruite().getListMoteur().size() == 0 || dessinFusee.getFuseeConstruite().getListReservoir().size() == 0) {
			dessinCentreControle.getBtnlancerFusee().setEnabled(true);
		}else {
			dessinCentreControle.getBtnlancerFusee().setEnabled(false);
		}
		
	}//fin methode
	
	/**
	 * Methode qui va faire animer le pas a pas
	 */
	// Johnatan G
	
	private void prochainGlobalement() {//debut methode
		
		// debut
		sceneAnimee.arreter();
		sceneAnimee.miseAjour();
		dessinFusee.uneIterationPhysique();
		repaint();
		//
		
	}//fin methode
	
	/**
	 * Methode qui fait recommencer le lancement de la fusee
	 */
	// Johnatan G
	
	private void reinitialiserFusee() {//debut methode

		AffineTransform tempAt = dessinFusee.getMat();
		tempAt.translate(dessinFusee.getFuseeConstruite().getPosition().getX(), dessinFusee.getFuseeConstruite().getPosition().getY());
		dessinFusee.setMat(tempAt);
		dessinFusee.arreter();
		dessinFusee.setFuseeConstruite(fuseeInitiale);
		dessinFusee.getFuseeConstruite().resetPositionComposant();
		
		repaint();
		
	}//fin methode
	
	/**
	 * Methode qui reinitialise les composants de la page centre de controle
	 */
	// Johnatan G
	
	private void reinitGlobalement() {//debut methode
		
		// debut
		sceneAnimee.reinitialiser();
		reinitialiserFusee();
		dessinCentreControle.reinitCentre();
		repaint();
		// fin
		
	}//fin methode
	
	/**
	 * Methode qui arrete le son de la fusee
	 */
	//Melie L
	
	private void arretSon() {//debut methode
		dessinFusee.arreterSon();
	}//fin methode
	
	/**
	 * Methode qui allume le son de la fusee
	 */
	//Melie L
	
	private void allumeSon() {//debut methode
		dessinFusee.allumerSon();
	}//fin methode
	
	/**
	 * Methode qui modifie la masse de LOX
	 */
	//Johnatan G
	
	private void modifierSpnLOX() {//debut methode
		
		if ((double) spnMasseLOX.getValue() != 0
				|| dessinFusee.getFuseeConstruite().getListReservoir().size() != 0 && dessinFusee
						.getFuseeConstruite().getListReservoir().get(0).getNom().equalsIgnoreCase("lox_lh2")) {

			choisirLOX();

		} else {

			if (dessinMoteur.getListMoteurSize() == 0) {

				choisirLesDeux();

			}

		}

		spnMasseLOX.setEnabled(dessinMoteur.isLOX());
		spnMasseKerosene.setEnabled(dessinMoteur.isKerosene());

		dessinReservoirCombustible.setMasseLOX((double) spnMasseLOX.getValue());
	}//fin methode
	
	/**
	 * Methode qui modifie la masse de Kerosene
	 */
	//Johnatan G
	
	private void modifierSpnKerosene() {//debut methode
		
		if ((double) spnMasseKerosene.getValue() != 0
				|| dessinFusee.getFuseeConstruite().getListReservoir().size() != 0
						&& dessinFusee.getFuseeConstruite().getListReservoir().get(0).getNom()
								.equalsIgnoreCase("kerosene_rp1")) {

			choisirKerosene();

		} else {

			if (dessinMoteur.getListMoteurSize() == 0) {
				choisirLesDeux();
			}
		}

		spnMasseLOX.setEnabled(dessinMoteur.isLOX());
		spnMasseKerosene.setEnabled(dessinMoteur.isKerosene());

		dessinReservoirCombustible.setMasseKerosene((double) spnMasseKerosene.getValue());
		
	}//fin methode

	/**
	 * Methode qui selectionne le comboBox
	 * 
	 * @param cmbBox Le comboBox
	 * @param dG     Le dessin graphique
	 */
	// Johnatan G

	private void selectionnerComboBox(JComboBox cmbBox, DessinGraphiqueSortie dG) {// debut methode

		if (cmbBox.getSelectedItem().equals("Somme des forces en X (dF/dT)")) {
			dG.setTypeGraphique(1);
		} else if (cmbBox.getSelectedItem().equals("Vitesse en X (dV/dT)")) {
			dG.setTypeGraphique(2);
		} else if (cmbBox.getSelectedItem().equals("Acceleration en X (dA/dT)")) {
			dG.setTypeGraphique(3);
		} else if (cmbBox.getSelectedItem().equals("Masse de la fusee (dM/dT)")) {
			dG.setTypeGraphique(4);
		} else if (cmbBox.getSelectedItem().equals("Masse du reservoir (dM/dT)")) {
			dG.setTypeGraphique(5);
		} else if (cmbBox.getSelectedItem().equals("Masse des boosters (dM/dT)")) {
			dG.setTypeGraphique(6);
		} else if (cmbBox.getSelectedItem().equals("Resistance de l'air (dF/dT)")) {
			dG.setTypeGraphique(7);
		} else if (cmbBox.getSelectedItem().equals("Somme des forces en Y (dF/dT)")) {
			dG.setTypeGraphique(8);
		} else if (cmbBox.getSelectedItem().equals("Vitesse en Y (dV/dT)")) {
			dG.setTypeGraphique(9);
		} else if (cmbBox.getSelectedItem().equals("Acceleration en Y (dA/dT)")) {
			dG.setTypeGraphique(10);
		} else if (cmbBox.getSelectedItem().equals("Angle de deviation (dTeta/dT)")) {
			dG.setTypeGraphique(11);
		} else if (cmbBox.getSelectedItem().equals("Distance fusee-Terre (dH/dT)")) {
			dG.setTypeGraphique(12);
		}
		dG.repaint();

	}// fin methode

	/**
	 * Methode qui modifie la liste deroulante de graphiques
	 * 
	 * @param cmbBox La liste deroulante de graphiques
	 */
	// Johnatan G

	private void setComboBoxModel(JComboBox cmbBox) {// debut methode

		cmbBox.setModel(new DefaultComboBoxModel(new String[] { "Somme des forces en X (dF/dT)",
				"Somme des forces en Y (dF/dT)", "Vitesse en X (dV/dT)", "Vitesse en Y (dV/dT)",
				"Acceleration en X (dA/dT)", "Acceleration en Y (dA/dT)", "Masse de la fusee (dM/dT)",
				"Masse du reservoir (dM/dT)", "Masse des boosters (dM/dT)", "Resistance de l'air (dF/dT)",
				"Angle de deviation (dTeta/dT)", "Distance fusee-Terre (dH/dT)" }));

	}// fin methode

	/**
	 * Methode qui initialiser la barre de menu, ses menuItems et le bouton quitter
	 */
	// Ivana
	public void initialiserMenuBar() {

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnAide = new JMenu("Aide");
		menuBar.add(mnAide);

		mntmInstructions = new JMenuItem("Guide d'utilisation...");
		mntmInstructions.addActionListener(new ActionListener() {

			// Ivana
			public void actionPerformed(ActionEvent arg0) {
				fenInstructions = new Instructions();
				fenInstructions.setVisible(true);
			}
		});
		mnAide.add(mntmInstructions);

		mntmDonneesScientifiques = new JMenuItem("Concepts Scientifiques...");
		mntmDonneesScientifiques.addActionListener(new ActionListener() {
			// Ivana
			public void actionPerformed(ActionEvent e) {
				fenScientifique = new InfoScientifiques();
				fenScientifique.setVisible(true);
			}
		});
		mnAide.add(mntmDonneesScientifiques);

		mnEditeur = new JMenu("\u00C9diteur");
		menuBar.add(mnEditeur);

		mntmAjouter = new JMenuItem("Ajouter un objet celeste...");
		mntmAjouter.addActionListener(new ActionListener() {
			// Corentin
			public void actionPerformed(ActionEvent e) {
				ajout.setVisible(true);
			}
		});
		mnEditeur.add(mntmAjouter);

		mntmSupprimer = new JMenuItem("Supprimer l'objet selectionn\u00E9e...");

		mntmSupprimer.addActionListener(new ActionListener() {
			// Ivana
			public void actionPerformed(ActionEvent e) {
				md.supprimerObjetSelectionner();
			}

		});
		mnEditeur.add(mntmSupprimer);

		mntmEditerUnObjet = new JMenuItem("\u00C9diter un objet c\u00E9leste...");
		mntmEditerUnObjet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				modificationObjet.setVisible(true);
				modificationObjet.miseAJoursAffichage();
			}
		});
		mnEditeur.add(mntmEditerUnObjet);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			// Ivana
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mnEnregistrer = new JMenu("Enregistrer");
		menuBar.add(mnEnregistrer);

		mntmEnregistrerSystSolaire = new JMenuItem("Enregistrer le syst\u00E8me solaire...");
		mntmEnregistrerSystSolaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnEnregistrer.add(mntmEnregistrerSystSolaire);

		mntmEnregistrerFusee = new JMenuItem("Enregistrer la fus\u00E9e...");
		mntmEnregistrerFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomFichier = JOptionPane.showInputDialog(null, "Quelle est le nom de votre sauvegarde?", "Sauvegarde", JOptionPane.QUESTION_MESSAGE);
				Save saver = new Save();
				try {
					saver.saveFusee("save", dessinFusee.getFuseeConstruite());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				lireSauvegarde();
				cmbBoxLoadFusee.setModel(new DefaultComboBoxModel(listNom.toArray()));
				
				
				

			}
		});
		mnEnregistrer.add(mntmEnregistrerFusee);

		mnCharger = new JMenu("Charger");
		menuBar.add(mnCharger);

		mntmChargerSystSolaire = new JMenuItem("Charger le syst\u00E8me solaire...");
		mntmChargerSystSolaire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sceneAnimee.setMd(mdSauvegarder);
				repaint();
				JOptionPane.showMessageDialog(null, "Le systï¿½me solaire " + nomSauvegardeSolaire + " a ï¿½tï¿½ chargï¿½ !");
			}
		});
		mnCharger.add(mntmChargerSystSolaire);
		mntmChargerSystSolaire.setEnabled(false);

		mntmChargerFusee = new JMenuItem("Charger la fus\u00E9e...");
		mntmChargerFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				dessinFusee.setFuseeConstruite(fuseeSauvegarder);
				dessinFusee.repaint();
			}
		});
		mnCharger.add(mntmChargerFusee);
		
		mnApropos = new JMenu("A propos");
		menuBar.add(mnApropos);
		
		mntmNewMenuItem = new JMenuItem("A propos");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aPropos = new APropos();
				aPropos.setVisible(true);
			}
		});
		mnApropos.add(mntmNewMenuItem);
		menuBar.add(btnQuitter);
	}

	/**
	 * Methode qui permet le deplacement de la fusee dans l'Espace
	 * @param deltaPositionFusee Le deplacement de la fusee
	 * @param masseFusee La masse de la fusee
	 */
	//Johnatan G
	
	private void calculerFuseeDansEspace(Vecteur deltaPositionFusee, double masseFusee) {//debut methode

		

		if(!isInSpace) {
			Vecteur positionFE = new Vecteur();
			positionFE.setXYZ(
					md.getPlaneteTerre().getPosition().additionne(new Vecteur(0, md.getPlaneteTerre().getRayon(), 0)).additionne(deltaPositionFusee).getX(),
					md.getPlaneteTerre().getPosition().additionne(new Vecteur(0, md.getPlaneteTerre().getRayon(), 0)).additionne(deltaPositionFusee).getY(), 
					0);
			fEspace.setPosition(positionFE);
			dessinCentreControle.getSldDeltaT().setValue(1);
			isInSpace = !isInSpace;
		}

		Vecteur forceEspace = new Vecteur();

		for(Objet o: md.getAllObjets()) {
			if(!(o instanceof Fusee)) {
				forceEspace = forceEspace.additionne(MoteurPhysique.loiGravitationnelleUniverselle(fEspace.getPosition(), o.getPosition(), dessinFusee.getFuseeConstruite().getMasse(), o.getMasse()));
			}
		}

		try {
			
			dessinFusee.getFuseeConstruite().setAltitude((fEspace.getPosition().soustrait(md.getPlaneteTerre().getPosition())).module());

		}catch(Exception e) {

			e.printStackTrace();
		}

		
	//	System.out.println(dessinFusee.getFuseeConstruite().getSommeDesForces());
		System.out.println(forceEspace);
		
		md.setDeltaT(dessinCentreControle.getSldDeltaT().getValue() * 150);

		dessinFusee.setNDeltaT(md.getDeltaT()/Constantes.DELTA_T_FUSEE);
		dessinFusee.getFuseeConstruite().setForcesDansEspace(forceEspace);
		fEspace.setPosition(fEspace.getPosition().additionne(deltaPositionFusee));
		md.setFusee(fEspace);
		
		forceEspace = new Vecteur();
		
	}//fin methode
}// fin classe
