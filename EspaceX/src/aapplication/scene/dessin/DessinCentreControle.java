package aapplication.scene.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import listeners.CentreControleListener;
import modele.ModeleAffichage;

/**
 * Classe qui va dessiner la page d'entrees
 * 
 * @author Melie Leclerc
 * @author Johnatan Gao
 *
 */

public class DessinCentreControle extends JPanel {// debut classe

	private static final long serialVersionUID = -5891969023662241835L;

	// Matrice qui permet la transition de pixel en metre
	private double largeurMonde, hauteurMonde;
	private AffineTransform mat;
	private JLabel lblPourcentagePousseeMoteurs;
	private JSlider sldPourcentagePousseMoteur;
	private final String DEGREE = "\u00b0";
	private JPanel pnlPourcentagePousseeMoteurs;
	private JPanel pnlPourcentagePousseBoosters;
	private JLabel lblPourcentagePousseeBoosters;
	private JSlider sldPourcentagePousseBoosters;
	private JPanel pnlAjustementAngle;
	private JLabel lblAngle;
	private JSlider sldAjustementDegree;
	private JPanel pnlAjusterDeltaT;
	private JLabel lblDeltaT;
	private JSlider sldDeltaT;
	private JSpinner spnPourcentagePousseeMoteurs;
	private JSpinner spnPourcentagePousseBoosters;
	private JSpinner spnAngle;
	private JSpinner spnDeltaT;
	private JLabel lblUniteMesure;
	private JPanel pnlHauteur;
	private JLabel lblHauteur;
	private JSpinner spnHauteur;

	private JPanel pnlControlFusee;
	private JLabel lblControlFusee;

	private JPanel pnlControlGlobal;
	private JLabel lblControlGlobal;

	private JButton btnlancerFusee, btnReinitFusee;
	private JButton btnAnimer, btnProchain, btnReinit, btnSon;

	private boolean isAnimer = false;
	private boolean isPlaying = false;

	private DessinFusee fuseeSon;

	private ArrayList<CentreControleListener> listeEcouteurs = new ArrayList<CentreControleListener>();

	/**
	 * Constructeur qui va permettre de dessiner le centre de controle
	 * 
	 * @param ma La matrice de transformation en unite reelle qui va permettre
	 *           d'appliquer les transformations lors du dessinage des elements
	 */
	// Melie L
	public DessinCentreControle(ModeleAffichage ma) {// debut constructeur
		setLayout(null);
		this.largeurMonde = ma.getLargUnitesReelles();
		this.hauteurMonde = ma.getHautUnitesReelles();
		this.mat = ma.getMatMC();

		setBackground(Color.white);
		//////////////////////

		ajouterComposants();

		////////////////////

	}// fin constructeur

	/**
	 * Methode qui va dessiner le fond d'ecran
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	// Melie L

	public void paintComponent(Graphics g) {// debut methode

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

	}// fin methode

	/**
	 * Methode qui ajoute les fenetres de controle de la fusee
	 */
	// Melie L

	private void ajouterPnlControleFusee() {// debut methode

		this.pnlControlFusee = new JPanel();
		this.lblControlFusee = new JLabel("Lancement de la fusee");
		this.pnlControlFusee.setLayout(null);

		this.btnlancerFusee = new JButton();

		btnlancerFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!isAnimer) {
					lancerFuseeListener();
					btnSon.setIcon(new ImageIcon(new ImageIcon(lireImageURL("son.png")).getImage().getScaledInstance(
							btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));
					btnReinitFusee.setEnabled(true);
					btnlancerFusee.setEnabled(false);
				}
			}
		});

		this.btnReinitFusee = new JButton();
		btnReinitFusee.setEnabled(false);
		btnReinitFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reinitFuseeListener();
				btnlancerFusee.setEnabled(true);
				btnReinitFusee.setEnabled(true);
			
			}
		});

		pnlControlFusee.setBounds(
				pnlPourcentagePousseeMoteurs.getX() + pnlPourcentagePousseeMoteurs.getWidth()
						+ pnlPourcentagePousseeMoteurs.getWidth() / 10,
				(int) (pnlAjusterDeltaT.getY() + pnlAjusterDeltaT.getHeight() + pnlAjusterDeltaT.getHeight() / 5),
				(int) (pnlAjusterDeltaT.getWidth()), (int) (hauteurMonde / 4 * mat.getScaleY()));

		btnlancerFusee.setBounds(pnlControlFusee.getWidth() / 2 - pnlControlFusee.getWidth() / 6,
				pnlControlFusee.getHeight() / 3 - pnlControlFusee.getHeight() / 16, pnlControlFusee.getWidth() / 6,
				pnlControlFusee.getWidth() / 6);
		btnlancerFusee.setBackground(null);
		btnlancerFusee.setBorder(null);
		btnlancerFusee.setIcon(new ImageIcon(new ImageIcon(lireImageURL("launch.png")).getImage()
				.getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getWidth(), Image.SCALE_DEFAULT)));

		btnReinitFusee.setBounds(btnlancerFusee.getX() + btnlancerFusee.getWidth() + pnlControlFusee.getWidth() / 32,
				btnlancerFusee.getY(), btnlancerFusee.getWidth(), btnlancerFusee.getHeight());
		;
		btnReinitFusee.setBackground(null);
		btnReinitFusee.setBorder(null);
		btnReinitFusee.setIcon(new ImageIcon(new ImageIcon(lireImageURL("restart.png")).getImage()
				.getScaledInstance(btnReinitFusee.getWidth(), btnReinitFusee.getWidth(), Image.SCALE_DEFAULT)));

		pnlControlFusee.setBorder(BorderFactory.createLineBorder(Color.black));
		add(pnlControlFusee);

		lblControlFusee.setBounds(pnlControlFusee.getWidth() / 2 - pnlControlFusee.getWidth() / 7,
				pnlControlFusee.getHeight() / 16, pnlControlFusee.getWidth() / 2, pnlControlFusee.getHeight() / 4);
		pnlControlFusee.add(lblControlFusee);
		pnlControlFusee.add(btnlancerFusee);
		pnlControlFusee.add(btnReinitFusee);

	}// fin methode

	/**
	 * Methode qui ajoute la fenetre de controle globale
	 */
	// Melie L

	private void ajouterPnlControlGlobal() {// debut methode

		this.pnlControlGlobal = new JPanel();
		this.pnlControlGlobal.setLayout(null);
		this.lblControlGlobal = new JLabel("Controle de l'animation globale");

		this.btnAnimer = new JButton();
		btnAnimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!isAnimer) {
					btnAnimer.setIcon(
							new ImageIcon(new ImageIcon(lireImageURL("1Animer.png")).getImage().getScaledInstance(
									btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

					isAnimer = !isAnimer;
					btnProchain.setEnabled(true);
					arreterGlobale();

					btnSon.setIcon(
							new ImageIcon(new ImageIcon(lireImageURL("sansSon.png")).getImage().getScaledInstance(
									btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

				} else {

					animerGlobale();
					btnAnimer.setIcon(
							new ImageIcon(new ImageIcon(lireImageURL("2PauseProchaineImage_attention FORMAT JPG.jpg"))
									.getImage().getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getHeight(),
											Image.SCALE_DEFAULT)));

					isAnimer = !isAnimer;
					btnProchain.setEnabled(false);

					btnSon.setIcon(new ImageIcon(new ImageIcon(lireImageURL("son.png")).getImage().getScaledInstance(
							btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

				}
			}
		});
		this.btnProchain = new JButton();
		btnProchain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prochainGlobale();
			}
		});
		this.btnSon = new JButton();

		btnSon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!isPlaying) {
					btnSon.setName("sansSon");
					btnSon.setIcon(
							new ImageIcon(new ImageIcon(lireImageURL("sansSon.png")).getImage().getScaledInstance(
									btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

					isPlaying = !isPlaying;
					arreterSon();

				} else {
					btnSon.setName("avecSon");
					btnSon.setIcon(new ImageIcon(new ImageIcon(lireImageURL("son.png")).getImage().getScaledInstance(
							btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

					isPlaying = !isPlaying;

					allumerSon();

				}

			}
		});
		this.btnReinit = new JButton();
		btnReinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reinitGlobale();

			isAnimer = false;

			reinitCentre();
				
			}
		});
		btnAnimer.setBorder(null);
		btnReinit.setBorder(null);
		btnProchain.setBorder(null);
		btnSon.setBorder(null);

		btnAnimer.setIcon(new ImageIcon(new ImageIcon(lireImageURL("2PauseProchaineImage_attention FORMAT JPG.jpg"))
				.getImage()
				.getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

		btnReinit.setIcon(new ImageIcon(new ImageIcon(lireImageURL("3Recommencer.png")).getImage()
				.getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

		btnProchain.setIcon(new ImageIcon(new ImageIcon(lireImageURL("5ProchaineImage.png")).getImage()
				.getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

		btnProchain.setEnabled(false);

		btnSon.setIcon(new ImageIcon(new ImageIcon(lireImageURL("sansSon.png")).getImage()
				.getScaledInstance(btnlancerFusee.getWidth(), btnlancerFusee.getHeight(), Image.SCALE_DEFAULT)));

		pnlControlGlobal.setBounds(pnlControlFusee.getX() + pnlControlFusee.getWidth() + pnlControlFusee.getWidth() / 5,
				(int) (hauteurMonde / 2 * mat.getScaleY() - pnlControlFusee.getHeight() / 2),
				(int) (pnlControlFusee.getWidth()), (int) (pnlControlFusee.getHeight()));

		btnAnimer.setBounds(pnlControlGlobal.getWidth() / 5, pnlControlGlobal.getHeight() / 3,
				btnlancerFusee.getWidth(), btnlancerFusee.getHeight());
		btnProchain.setBounds(btnAnimer.getX() + btnAnimer.getWidth() + btnAnimer.getWidth() / 10, btnAnimer.getY(),
				btnAnimer.getWidth(), btnAnimer.getHeight());
		btnReinit.setBounds(btnProchain.getX() + btnProchain.getWidth() + btnProchain.getWidth() / 10,
				btnProchain.getY(), btnProchain.getWidth(), btnProchain.getHeight());
		btnSon.setBounds(btnReinit.getX() + btnReinit.getWidth() + btnReinit.getWidth() / 10, btnReinit.getY(),
				btnReinit.getWidth(), btnReinit.getHeight());

		pnlControlGlobal.setBorder(BorderFactory.createLineBorder(Color.black));
		add(pnlControlGlobal);

		pnlControlGlobal.add(btnAnimer);
		pnlControlGlobal.add(btnProchain);
		pnlControlGlobal.add(btnReinit);
		pnlControlGlobal.add(btnSon);

		lblControlGlobal.setBounds(pnlControlGlobal.getWidth() / 2 - pnlControlGlobal.getWidth() / 5,
				pnlControlGlobal.getHeight() / 16, (int) (pnlControlGlobal.getWidth() / 1.5),
				pnlControlGlobal.getHeight() / 4);
		pnlControlGlobal.add(lblControlGlobal);

	}// fin methode

	/**
	 * Methode qui configure les composants de la fenetre
	 * 
	 * @param spn                Le tourniquet de la fenetre
	 * @param sld                Le curseur glissiere de la fenetre
	 * @param lbl                L'etiquette de la fenetre
	 * @param lblU               L'etiquette d'unit� de mesure de la fenetre
	 * @param pnl                La fenetre
	 * @param xPanel             La position en X de la fenetre
	 * @param yPanel             La position en Y de la fenetre
	 * @param wPanel             La largeur de la fenetre
	 * @param hPanel             La hauteur de la fenetre
	 * @param valeurDepartSlider La valeur de depart du slider de la fenetre
	 * @param maximumSlider      La valeur maximale du curseur glissiere de la
	 *                           fenetre
	 * @param minimumSlider      La valeur mminimale du curseur glissiere de la
	 *                           fenetre
	 */
	// Melie L

	private void configuerComposants(JSpinner spn, JSlider sld, JLabel lbl, JLabel lblU, JPanel pnl, int xPanel,
			int yPanel, int wPanel, int hPanel, int valeurDepartSlider, int maximumSlider, int minimumSlider) {// debut
																												// methode

		pnl.setBounds(xPanel, yPanel, wPanel, hPanel);
		pnl.setBorder(BorderFactory.createLineBorder(Color.black));
		pnl.setLayout(null);

		sld.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spn.setValue(sld.getValue());
			}
		});

		spn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				sld.setValue((int) spn.getValue());
				ajouterEcouteurs((int) spn.getValue(), spn);
			}
		});

		lbl.setBounds(wPanel / 2 - wPanel / 3, hPanel / 20, wPanel / 2, hPanel / 2);
		spn.setBounds((int) (lbl.getX() + lbl.getWidth()), (int) (lbl.getY() + lbl.getHeight() / 4),
				(int) (lbl.getWidth() / 3.5), lbl.getHeight() / 2);
		lblU.setBounds(spn.getX() + spn.getWidth(), spn.getY() - spn.getHeight() / 2, wPanel / 10, hPanel / 2);

		spn.setModel(new SpinnerNumberModel(new Integer(valeurDepartSlider), new Integer(minimumSlider),
				new Integer(maximumSlider), new Integer(1)));

		sld.setValue(valeurDepartSlider);

		sld.setBounds((int) (wPanel / 10), (int) (lbl.getY() + lbl.getHeight() * 0.9), (int) (wPanel * 0.8),
				(int) (hPanel / 2));

		sld.setPaintLabels(true);
		sld.setPaintTicks(true);
		sld.setMinorTickSpacing(maximumSlider / 20);
		sld.setMajorTickSpacing(maximumSlider / 4);
		sld.setMaximum(maximumSlider);
		sld.setMinimum(minimumSlider);

		pnl.add(lblU);
		pnl.add(spn);
		pnl.add(sld);
		pnl.add(lbl);
		add(pnl);

	}// fin methode

	/**
	 * Methode qui ajoute la fenetre de la poussee de la fusee
	 */
	// Melie L

	private void ajouterPnlPousseeFusee() {// debut methode

		pnlPourcentagePousseeMoteurs = new JPanel();
		lblPourcentagePousseeMoteurs = new JLabel("Poussee des moteurs : ");
		sldPourcentagePousseMoteur = new JSlider();
		spnPourcentagePousseeMoteurs = new JSpinner();
		spnPourcentagePousseeMoteurs.setName("pousseeMoteurs");
		lblUniteMesure = new JLabel(" %");

		configuerComposants(spnPourcentagePousseeMoteurs, sldPourcentagePousseMoteur, lblPourcentagePousseeMoteurs,
				lblUniteMesure, pnlPourcentagePousseeMoteurs, (int) (largeurMonde / 40 * mat.getScaleX()),
				(int) (hauteurMonde / 30 * mat.getScaleY()), (int) (largeurMonde / 4 * mat.getScaleX()),
				(int) (hauteurMonde / (3.9) * mat.getScaleY()), 100, 100, 0);

	}// fin methode

	/**
	 * Methode qui ajoute la fenetre de la poussee du booster
	 */
	// Melie L

	private void ajouterPnlPousseeBooster() {// debut methode

		pnlPourcentagePousseBoosters = new JPanel();
		lblPourcentagePousseeBoosters = new JLabel("Poussee des boosters : ");
		sldPourcentagePousseBoosters = new JSlider();
		spnPourcentagePousseBoosters = new JSpinner();
		spnPourcentagePousseBoosters.setName("pousseeBoosters");
		lblUniteMesure = new JLabel(" %");

		configuerComposants(spnPourcentagePousseBoosters, sldPourcentagePousseBoosters, lblPourcentagePousseeBoosters,
				lblUniteMesure, pnlPourcentagePousseBoosters, pnlPourcentagePousseeMoteurs.getX(),
				pnlPourcentagePousseeMoteurs.getY() + pnlPourcentagePousseeMoteurs.getHeight()
						+ pnlPourcentagePousseeMoteurs.getHeight() / 5,
				pnlPourcentagePousseeMoteurs.getWidth(), pnlPourcentagePousseeMoteurs.getHeight(), 100, 100, 0);
	}// fin methode

	/**
	 * Methode qui ajoute la panel de l'angle de deviation de la fusee
	 */
	// Melie L

	private void ajouterPnlAngle() {// debut methode

		pnlAjustementAngle = new JPanel();
		lblAngle = new JLabel("Angle de deviation : ");
		sldAjustementDegree = new JSlider();
		spnAngle = new JSpinner();
		spnAngle.setName("angleDeviation");
		lblUniteMesure = new JLabel(" " + this.DEGREE);

		configuerComposants(spnAngle, sldAjustementDegree, lblAngle, lblUniteMesure, pnlAjustementAngle,
				pnlPourcentagePousseBoosters.getX(),
				pnlPourcentagePousseBoosters.getY() + pnlPourcentagePousseBoosters.getHeight()
						+ pnlPourcentagePousseBoosters.getHeight() / 5,
				pnlPourcentagePousseBoosters.getWidth(), pnlPourcentagePousseBoosters.getHeight(), 0, 180, -180);

	}// fin methode

	/**
	 * Methode qui ajoute la fenetre de la hauteur de deviation de la fusee
	 */
	// Melie L

	private void ajouterPnlHauteurDeviation() {// debut methode

		pnlHauteur = new JPanel();
		lblHauteur = new JLabel("Hauteur de deviation: ");
		spnHauteur = new JSpinner();
		spnHauteur.setName("spnHauteur");
		lblUniteMesure = new JLabel(" m");

		pnlHauteur.setBounds(
				pnlPourcentagePousseeMoteurs.getX() + pnlPourcentagePousseeMoteurs.getWidth()
						+ pnlPourcentagePousseeMoteurs.getWidth() / 10,
				pnlPourcentagePousseeMoteurs.getY(), pnlAjustementAngle.getWidth(), pnlAjustementAngle.getHeight() / 2);
		pnlHauteur.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlHauteur.setLayout(null);

		lblHauteur.setBounds(pnlHauteur.getWidth() / 2 - pnlHauteur.getWidth() / 3, pnlHauteur.getHeight() / 4,
				(int) (pnlHauteur.getWidth() / (2.3)), pnlHauteur.getHeight() / 2);
		spnHauteur.setBounds((int) (lblHauteur.getX() + lblHauteur.getWidth()),
				(int) (lblHauteur.getY() + lblHauteur.getHeight() / 5), (int) (lblHauteur.getWidth() / 2),
				(int) (lblHauteur.getHeight() / (1.3)));
		lblUniteMesure.setBounds(spnHauteur.getX() + spnHauteur.getWidth(),
				(int) (spnHauteur.getY() - spnHauteur.getHeight() / 3), pnlHauteur.getWidth() / 10,
				pnlHauteur.getHeight() / 2);

		spnHauteur.setModel(
				new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(100000), new Integer(100)));

		spnHauteur.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				ajouterEcouteurs((int) spnHauteur.getValue(), spnHauteur);
			}
		});

		pnlHauteur.add(lblUniteMesure);
		pnlHauteur.add(spnHauteur);
		pnlHauteur.add(lblHauteur);
		add(pnlHauteur);

	}// fin methode

	/**
	 * Methode qui ajoute le panel du delta temps
	 */
	// Melie L

	private void ajouterPnlDeltaT() {// debut methode

		pnlAjusterDeltaT = new JPanel();
		lblDeltaT = new JLabel("Ecoulement du temps: ");
		sldDeltaT = new JSlider();
		spnDeltaT = new JSpinner();
		spnDeltaT.setName("deltaT");
		lblUniteMesure = new JLabel(" X");

		configuerComposants(spnDeltaT, sldDeltaT, lblDeltaT, lblUniteMesure, pnlAjusterDeltaT, pnlHauteur.getX(),
				pnlHauteur.getY() + pnlHauteur.getHeight() + pnlAjustementAngle.getHeight() / 5, pnlHauteur.getWidth(),
				pnlAjustementAngle.getHeight(), 1, 100, 1);
	}// fin methode

	/**
	 * Methode qui ajoute tous les composants des fenetres
	 */
	// Melie L

	private void ajouterComposants() {// debut methode

		ajouterPnlPousseeFusee();
		ajouterPnlPousseeBooster();
		ajouterPnlAngle();
		ajouterPnlHauteurDeviation();
		ajouterPnlDeltaT();

		ajouterPnlControleFusee();
		ajouterPnlControlGlobal();

	}// fin methode

	/**
	 * Methode qui va permettre la lecture d'un fichier contenu dans le projet
	 * 
	 * @param nomFichier L'image
	 * @return urlImg L'image URL
	 */
	// Melie L

	private URL lireImageURL(String nomFichier) {// debut methode

		URL urlImg = getClass().getClassLoader().getResource(nomFichier);

		if (urlImg == null) {
			System.out.println("Fichier introuvable");
		}

		return urlImg;
	}// fin methode

	/**
	 * Methode qui va permettre d'ajouter les ecouteur personalise au ArrayList
	 * 
	 * @param objEcout Ecouteur personalise
	 */
	// Johnatan G

	public void addCentreControleListener(CentreControleListener objEcout) {// debut methode
		this.listeEcouteurs.add(objEcout);
	}// fin methode

	/**
	 * Methode qui ajoute les ecouteurs
	 * 
	 * @param valeur La valeur des ecouteurs
	 * @param spn    Le spinner
	 */
	// Johnatan G

	private void ajouterEcouteurs(double valeur, JSpinner spn) {// debut methode

		String spnName = spn.getName();

		if (spnName.equalsIgnoreCase(this.spnAngle.getName())) {
			ajusterAngleDeviation(valeur);
		} else if (spnName.equalsIgnoreCase(this.spnDeltaT.getName())) {
			ajusterDeltaT(valeur);
		} else if (spnName.equalsIgnoreCase(this.spnHauteur.getName())) {
			ajusterHauteurDeviation(valeur);
		} else if (spnName.equalsIgnoreCase(this.spnPourcentagePousseBoosters.getName())) {
			pousseeBoostersListener(valeur);
		} else if (spnName.equalsIgnoreCase(this.spnPourcentagePousseeMoteurs.getName())) {
			pousseeMoteurListener(valeur);
		}
	}// fin methode

	/**
	 * Methode qui lance un evenement que la pousse du moteur est modifiee
	 * 
	 * @param pourcMoteurs Le pourcentage de la pousse du moteur
	 */
	// Johnatan G

	private void pousseeMoteurListener(double pourcMoteurs) {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.changerPousseeMoteurs(pourcMoteurs);
		}
	}// fin methode

	/**
	 * Methode qui lance un evenement que la pousse du booster est modifie
	 * 
	 * @param pourcBoosters Le pourcentage de la pousse du booster
	 */
	// Johnatan G

	private void pousseeBoostersListener(double pourcBoosters) {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.changerPousseeBoosters(pourcBoosters);
		}
	}// fin methode

	/**
	 * Methode qui lance un evenement que la hauteur de deviation est ajustee
	 * 
	 * @param hauteurDeviation La hauteur de deviation est ajustee
	 */
	// Johnatan G

	private void ajusterHauteurDeviation(double hauteurDeviation) {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.changerHauteurDeviation(hauteurDeviation);
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'ecoulement du temps est ajuste
	 * 
	 * @param ecoulementTemps L'ecoulement du temps est ajuste
	 */
	// Johnatan G

	private void ajusterDeltaT(double ecoulementTemps) {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.changerEcoulementTemps(ecoulementTemps);
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'angle de deviation est ajuste
	 * 
	 * @param angleDeviation L'angle de deviation
	 */
	// Johnatan G

	private void ajusterAngleDeviation(Double angleDeviation) {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.changerAngleDeviation(angleDeviation);
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que la fusee est lancee
	 */
	// Johnatan G

	private void lancerFuseeListener() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.lancerFusee();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que le lancement de la fusee est reinitialisee
	 */
	// Johnatan G

	private void reinitFuseeListener() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.reinitFusee();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'animation globale est en cours
	 */
	// Johnatan G

	private void animerGlobale() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.animerGlobal();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'animation globale est arretee
	 */
	// Johnatan G

	private void arreterGlobale() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.arreterGlobal();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'animation globale est en cours pas a pas
	 */
	// Johnatan G

	private void prochainGlobale() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.prochainGlobal();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que l'animation globale est reinitialisee
	 */
	// Johnatan G

	private void reinitGlobale() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.reinitGlobal();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que le son est arrete
	 */
	// Melie L

	private void arreterSon() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.arreterSon();
		}
	}// fin methode

	/**
	 * Methode qui lance un evement que le son est allume
	 */
	// Melie L

	private void allumerSon() {// debut methode
		for (CentreControleListener objEcout : listeEcouteurs) {
			objEcout.allumerSon();
		}
	}// fin methode

	/**
	 * Methode qui retourne le bouton lancer fusee
	 * 
	 * @return Le bouton lancer fusee
	 */
	// Melie L

	public JButton getBtnlancerFusee() {// debut methode
		return btnlancerFusee;
	}// fin methode

	/**
	 * Methode qui modifie le bouton lancer fusee
	 * 
	 * @param btnlancerFusee Le bouton lancer fusee
	 */
	// Melie L

	public void setBtnlancerFusee(JButton btnlancerFusee) {// debut methode
		this.btnlancerFusee = btnlancerFusee;
	}// fin methode

	/**
	 * Methode qui retourne le bouton son
	 * 
	 * @return btnSon Le bouton son
	 */
	// Melie L

	public JButton getBtnSon() {// debut methode
		return btnSon;
	}// fin methode

	/**
	 * Methode qui modifie le bouton son
	 * 
	 * @param btnSon Le bouton son
	 */
	// Melie L

	public void setBtnSon(JButton btnSon) {// debut methode
		this.btnSon = btnSon;
	}// fin methode

	
	/**
	 * Reinitialise les valeurs dans les composantes
	 */
	//Melie L
	
	public void reinitCentre() {//debut methode
		sldPourcentagePousseMoteur.setValue(100);
		sldPourcentagePousseBoosters.setValue(100);
		sldAjustementDegree.setValue(0);
		sldDeltaT.setValue(1);
		spnPourcentagePousseeMoteurs.setValue(100);
		spnPourcentagePousseBoosters.setValue(100);
		spnAngle.setValue(0);
		spnDeltaT.setValue(1);
		btnlancerFusee.setEnabled(true);
		isAnimer = false;
		repaint();
	}//fin methode
	
	
/**
 * Methode qui retourne le curseur d'ecoulement du temps
 * @return Le curseur d'ecoulement du temps
 */
	//Melie L
	public JSlider getSldDeltaT() {
		return sldDeltaT;
	}

	/**
	 * Methode qui modifie le curseur d'ecoulement du temps
	 * @param sldDeltaT Le curseur d'ecoulement du temps
	 */
		//Melie L
	public void setSldDeltaT(JSlider sldDeltaT) {
		this.sldDeltaT = sldDeltaT;
	}

	/**
	 * Methode qui retourne le tourniquet d'ecoulement du temps
	 * @return Le tourniquet d'ecoulement du temps
	 */
		//Melie L
	public JSpinner getSpnDeltaT() {
		return spnDeltaT;
	}

	/**
	 * Methode qui modifie le tourniquet d'ecoulement du temps
	 * @param spnDeltaT Le tourniquet d'ecoulement du temps
	 */
		//Melie L
	public void setSpnDeltaT(JSpinner spnDeltaT) {
		this.spnDeltaT = spnDeltaT;
	}

}// fin classe
