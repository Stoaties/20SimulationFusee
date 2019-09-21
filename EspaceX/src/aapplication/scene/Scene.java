package aapplication.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.ModeleAffichage;
import modele.ModeleDeDonnees;
import objets.Objet;
import objets.celeste.Etoile;
import objets.celeste.Planete;
import util.Constantes;
import util.Vecteur;

/**
 * Classe qui va realiser l'animation ainsi que la creation d'ecouteurs
 * personalises permettant de mettre les valeurs a jour en temsp reels
 * 
 * @author Corentin Gouanvic
 * @author Ivana Bera
 */
public class Scene extends JPanel implements Runnable, Constantes {

	private static final long serialVersionUID = 1L;
	private Thread th;
	private ModeleAffichage ma;
	private ModeleDeDonnees md;
	private AffineTransform at;
	private Point2D.Double centre = new Point2D.Double();
	private boolean premiereFois = true;
	private boolean enCoursDAnimation = false;
	private boolean initMonde = false;
	private boolean mouseClicked = false;
	private double deltaT = 1500;
	private JLabel tempsSimLbl;
	private JLabel echelleLbl;
	private double largeurMonde = 10e9 * 1000;
	private double hauteurMonde;
	private double[] axeX = new double[2];
	private double[] axeY = new double[2];
	DecimalFormat df = new DecimalFormat("###");
	private PanneauInfoPlanete panelInfoPlanete;
	private Objet planeteSelectionner;
	private int NOMBRE_ETOILE_BACKGROUND = 400;
	private ArrayList<Ellipse2D.Double> backgroudStars = new ArrayList<Ellipse2D.Double>();
	private LecteurImage lecteurImg = new LecteurImage();
	private ArrayList<Objet> liste = new ArrayList<Objet>();
	private int compteur;
	boolean voirPanneauInfo = true;
	private double xPrecedent, yPrecedent, translateX, translateY;

	/**
	 * Constructeur du panel du systeme solaire
	 * 
	 * @param md Modele de donnees a utiliser
	 */
	//Corentin
	public Scene(ModeleDeDonnees md) {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				panSouris(e);
				repaint();
			}

		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				pan(arg0.getKeyCode());
				repaint();
			}
		});

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				zoom(arg0.getWheelRotation());
				repaint();
			}
		});

		this.md = md;
		md.setDeltaT(deltaT);
		initialiser();
		addMouseListener(new MouseAdapter() {
			// Ivana
			public void mouseClicked(MouseEvent e) {
				// liste = md.getPlanetes();
				liste = md.getAllObjets();
				compteur = 0;
				boolean selectionner = false;
				voirPanneauInfo = isVoirPanneauInfo();
				while (compteur < liste.size() && !selectionner) {
					if (liste.get(compteur).getShape() != null && at.createTransformedShape(liste.get(compteur).getShape()).contains(e.getPoint())) {
						planeteSelectionner = liste.get(compteur);
						if (isVoirPanneauInfo()) {
							panelInfoPlanete.setInformations(planeteSelectionner, md);
							panelInfoPlanete.setVisible(true);
						}
						if (!isVoirPanneauInfo()) {
							supprimerUnePlanete(planeteSelectionner);
						}
						selectionner = true;
						md.setObjetSelectionner(planeteSelectionner);
					}
					compteur++;
				}
				if (!selectionner) {
					panelInfoPlanete.setVisible(false);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				mouseClicked = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Vecteur positionSouris = new Vecteur(e.getX() / at.getScaleX(), e.getY() / at.getScaleY(), 0);
				mouseClicked = true;
				xPrecedent = positionSouris.getX();
				yPrecedent = positionSouris.getY();
			}
		}); // fin de l'ecouteur
		setBackground(Color.black);
		setLayout(null);

		tempsSimLbl = new JLabel("");
		tempsSimLbl.setBounds(10, 11, 430, 14);
		add(tempsSimLbl);

		echelleLbl = new JLabel("");
		echelleLbl.setBounds(394, 275, 46, 14);
		add(echelleLbl);

		// Panel qui contient toute les informations sur les planetes

		panelInfoPlanete = new PanneauInfoPlanete();
		panelInfoPlanete.setVisible(false);
		panelInfoPlanete.setBounds(10, 36, (int) (tempsSimLbl.getWidth() / 1.5), 190);
		add(panelInfoPlanete);
		panelInfoPlanete.setLayout(null);

		demarrer();
		// setType(1);

	}

	/**
	 * Changer le modele de donnees de la scene.
	 * 
	 * @param md Modele de donnees.
	 */
	// Corentin
	public void setModeleDeDonnees(ModeleDeDonnees md) {
		this.md = md;
		md.setDeltaT(deltaT);
	}

	/**
	 * Methode qui va effectuer l'animation en utilisant un thread tout en mettant
	 * les valeurs de sortie a jour
	 */
	@Override
	// Corentin
	public void run() {
		while (enCoursDAnimation) {
			if (premiereFois) {
				premiereFois = false;
			}

			miseAjour();
			miseAjour();
			miseAjour();
			repaint();

			try {
				th.sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode qui permet de dessiner une scene q
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	// Corentin
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (!initMonde) {
			initMonde = !initMonde;
			ma = new ModeleAffichage(getWidth(), getHeight(), largeurMonde);
			at = ma.getMatMC();
			centre = new Point2D.Double(ma.getLargUnitesReelles() / 2, ma.getHautUnitesReelles() / 2);
			at.translate(centre.getX(), centre.getY());
		//	
			//at.scale(1, -1);
		//	
			createBackgroud();
			hauteurMonde = ma.getHautUnitesReelles();
			axeX[0] = -ma.getLargUnitesReelles() / 2;
			axeX[1] = ma.getLargUnitesReelles() / 2;
			axeY[0] = -ma.getHautUnitesReelles() / 2;
			axeY[1] = ma.getHautUnitesReelles() / 2;
		}

		dessinerBackground(g2d, at);
		drawEchelle(g2d, at);

		for (int i = 0; i < md.getAllObjets().size(); i++) {
			md.getAllObjets().get(i).draw(g2d, at);
			
			
		}
		tempsSimLbl.setText("<html><font color = 'white'>" + String.format("%.0f", md.getTempsSimuler() / 86400) + " jours simul\u00E9s ou "
				+ String.format("%.0f", md.getTempsSimuler()) + " seconde simul\u00E9es</font></html>");

		
	}

	/**
	 * Met a jour les composants de la scene: planetes, soleil, satelittes, etc.
	 * 
	 */
	// Corentin
	public void miseAjour() {
		
		md.ajoutPas();
		for (Objet objet : md.getAllObjets())
			objet.update();
		
	}

	/**
	 * Methode qu permet de demarrer le thread si celui-ci n'est pas encore demarrer
	 */
	// Corentin
	public void demarrer() {
		if (!enCoursDAnimation) {
			th = new Thread(this);
			enCoursDAnimation = true;
			th.start();
		}
	}

	/**
	 * Methode qui va demander l'arret du thread et de l'animation
	 */
	// Corentin
	public void arreter() {
		enCoursDAnimation = false;
	}

	/**
	 * Methode qui initialise les planetes dans le modele de donnees
	 */
	// Corentin
	public void initialiser() {

		Etoile etoile = new Etoile(1e7 * 1000, 1.989e30);
		etoile.setPosition(new Vecteur(0, 0, 0));
		etoile.setCouleurObjet(Color.yellow);
		etoile.setNom("Soleil");
		etoile.setModeleDeDonnees(md);
		md.addEtoile(etoile);

		//Planete mercure = new Planete(1.25e7 * 1000, 3.285e23, 1, 70, lecteurImg.getImgMercure());
		Planete mercure = new Planete(1.25e7 * 1000, 3.285e23, 1, 70, lecteurImg.getImgMercure());
		mercure.setPosition(new Vecteur(57.91e6 * 1000, 0, 0));
		mercure.setVitesse(new Vecteur(0, 4.74e4, 0));
		mercure.setNom("Mercure");
		mercure.setModeleDeDonnees(md);
		md.addPlanete(mercure);

		//Planete venus = new Planete(0.5e7 * 1000, 4.867e24, 1, 70, lecteurImg.getImgVenus());
		Planete venus = new Planete(0.5e7 * 1000, 4.867e24, 1, 70, lecteurImg.getImgVenus());
		venus.setPosition(new Vecteur(108.2e6 * 1000, 0, 0));
		venus.setVitesse(new Vecteur(0, 3.5e4, 0));
		venus.setNom("Venus");
		venus.setModeleDeDonnees(md);
		md.addPlanete(venus);

		//Planete terre = new Planete(1e7 * 1000, 5.972e24, 1, 70, lecteurImg.getImgTerre());
		Planete terre = new Planete(1e7 * 1000, 5.972e24, 1, 70, lecteurImg.getImgTerre());
		terre.setPosition(new Vecteur(149.6e6 * 1000, 0, 0));
		terre.setVitesse(new Vecteur(0, 3e4, 0));
		terre.setNom("Terre");
		terre.setModeleDeDonnees(md);
		md.addPlanete(terre);

		//Planete mars = new Planete(1e7 * 1000, 6.39e23, 1, 70, lecteurImg.getImgMars());
		Planete mars = new Planete(1e7 * 1000, 6.39e23, 1, 70, lecteurImg.getImgMars());
		mars.setPosition(new Vecteur(227.9e6 * 1000, 0, 0));
		mars.setVitesse(new Vecteur(0, 2.4e4, 0));
		mars.setNom("Mars");
		mars.setModeleDeDonnees(md);
		md.addPlanete(mars);

		//Planete jupiter = new Planete(3e7 * 1000, 1.898e27, 1, 70, lecteurImg.getImgJupiter());
		Planete jupiter = new Planete(3e7 * 1000, 1.898e27, 1, 70, lecteurImg.getImgJupiter());
		jupiter.setPosition(new Vecteur(778.5e6 * 1000, 0, 0));
		jupiter.setVitesse(new Vecteur(0, 1.307e4, 0));
		jupiter.setNom("Jupiter");
		jupiter.setModeleDeDonnees(md);
		md.addPlanete(jupiter);

		//Planete saturne = new Planete(2e7 * 1000, 5.683e26, 1, 70, lecteurImg.getImgSaturne());
		Planete saturne = new Planete(2e7 * 1000, 5.683e26, 1, 70, lecteurImg.getImgSaturne());
		saturne.setPosition(new Vecteur(1.434e9 * 1000, 0, 0));
		saturne.setVitesse(new Vecteur(0, 0.968e4, 0));
		saturne.setNom("Saturne");
		saturne.setModeleDeDonnees(md);
		md.addPlanete(saturne);

		//Planete uranus = new Planete(1.5e7 * 1000, 8.681e25, 1, 70, lecteurImg.getImgUranus());
		Planete uranus = new Planete(1.5e7 * 1000, 8.681e25, 1, 70, lecteurImg.getImgUranus());
		uranus.setPosition(new Vecteur(2.871e9 * 1000, 0, 0));
		uranus.setVitesse(new Vecteur(0, 0.68e4, 0));
		uranus.setNom("Uranus");
		uranus.setModeleDeDonnees(md);
		md.addPlanete(uranus);

		//Planete neptune = new Planete(1.5e7 * 1000, 1.024e26, 1, 70, lecteurImg.getImgNeptune());
		Planete neptune = new Planete(1.5e7 * 1000, 1.024e26, 1, 70, lecteurImg.getImgNeptune());
		neptune.setPosition(new Vecteur(4.495e9 * 1000, 0, 0));
		neptune.setVitesse(new Vecteur(0, 0.543e4, 0));
		neptune.setNom("Neptune");
		neptune.setModeleDeDonnees(md);
		md.addPlanete(neptune);
	}

	/**
	 * Methode qui va reinitialiser toutes les valeurs
	 */
	// Ivana
	public void reinitialiser() {
		md = new ModeleDeDonnees();
		initialiser();
		md.setDeltaTInitiale();
	}

	/**
	 * Methode qui cree un background d'etoiles aleatoires
	 */
	// Corentin
	public void createBackgroud() {
		for (int i = 0; i < NOMBRE_ETOILE_BACKGROUND; i++) {
			int x, y;
			x = (int) (Math.random() * getWidth() + 1);
			y = (int) (Math.random() * getHeight() + 1);
			backgroudStars.add(new Ellipse2D.Double(x, y, 0, 0));
		}
	}

	/**
	 * Methode qui dessine le background generer
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param af La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	// Corentin
	public void dessinerBackground(Graphics2D g2d, AffineTransform af) {
		Color colorTemp = g2d.getColor();
		g2d.setColor(Color.WHITE);
		for (Ellipse2D.Double star : backgroudStars) {
			g2d.draw(star);
		}
		g2d.setColor(colorTemp);
	}

	/**
	 * Methode de zoom sur la scene selon la direction de la molette de souris.
	 * 
	 * @param zoomAmount Direction de la molette de la souris.
	 */
	// Corentin
	private void zoom(int zoomAmount) {
		axeX[0] -= zoomAmount * largeurMonde * 0.05;
		axeX[1] += zoomAmount * largeurMonde * 0.05;
		largeurMonde = axeX[1] - axeX[0];
		ma = new ModeleAffichage(getWidth(), getHeight(), largeurMonde);
		hauteurMonde = ma.getHautUnitesReelles();
		at = ma.getMatMC();
		at.translate(largeurMonde / 2 + ((axeX[0] + axeX[1]) / 2) * -1,
				hauteurMonde / 2 + ((axeY[0] + axeY[1]) / 2) * -1);
	}

	/**
	 * Methode qui prend en parametre un keyCode de une des fleches du clavier pour
	 * apres ce deplacer dans cette direction.
	 * 
	 * @param keyCode KeyCode de la fleche du clavier.
	 */
	// Corentin
	private void pan(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			at.translate(0, hauteurMonde * 0.05);
			axeY[0] -= hauteurMonde * 0.05;
			axeY[1] -= hauteurMonde * 0.05;
			break;
		case KeyEvent.VK_DOWN:
			at.translate(0, -hauteurMonde * 0.05);
			axeY[0] += hauteurMonde * 0.05;
			axeY[1] += hauteurMonde * 0.05;
			break;
		case KeyEvent.VK_LEFT:
			at.translate(largeurMonde * 0.05, 0);
			axeX[0] -= largeurMonde * 0.05;
			axeX[1] -= largeurMonde * 0.05;
			break;
		case KeyEvent.VK_RIGHT:
			at.translate(-largeurMonde * 0.05, 0);
			axeX[0] += largeurMonde * 0.05;
			axeX[1] += largeurMonde * 0.05;
			break;
		default:

		}
	}

	/**
	 * Methode qui affiche une echelle avec un contexte graphique et une matrice de
	 * transformation.
	 * 
	 * @param g2d Contexte Graphice
	 * @param at  Matrice de transformation
	 */
	// Corentin
	private void drawEchelle(Graphics2D g2d, AffineTransform at) {
		final double RATIO_ECHELLE = 0.18;
		final double SPACING = 20; // Espace entre les bords en pixels
		boolean echelleInit = false;
		Color colorTemp = g2d.getColor();
		AffineTransform atTemp = new AffineTransform(at);
		double tailleEchelle = largeurMonde * RATIO_ECHELLE;
		g2d.setColor(Color.WHITE);
		Line2D.Double ligne = new Line2D.Double(getWidth() - getWidth() * RATIO_ECHELLE - SPACING,
				getHeight() - SPACING, getWidth() - SPACING, getHeight() - SPACING);
		g2d.draw(ligne);
		if (!echelleInit) {
			echelleLbl.setBounds((int) (getWidth() - getWidth() * RATIO_ECHELLE - SPACING),
					(int) (getHeight() - 2 * SPACING), 430, 14);
			echelleInit = true;
		}

		echelleLbl.setText("<html><font color = 'white'>" + df.format(tailleEchelle / 1000000000)
				+ " Millions de km </font></html>");
		g2d.setColor(colorTemp);
		//at = atTemp;
	}

	/**
	 * Methode qui supprime la planete selectionnee
	 * 
	 * @param planete la planete selectionnee
	 */
	// Ivana
	public void supprimerUnePlanete(Objet planete) {

		if (liste.get(compteur).equals(planete)) {
			liste.remove(planeteSelectionner);
			repaint();
		}

	}

	/**
	 * Methode qui modifie le booleen qui fait apparaitre ou disparaitre le panneau
	 * d'information
	 * 
	 * @param voirPanneauInfo booleen qui fait apparaitre ou disparaitre le panneau
	 *                        d'information
	 */
	//Ivana
	public void setVoirPanneauInfo(boolean voirPanneauInfo) {
		this.voirPanneauInfo = voirPanneauInfo;
	}

	/**
	 * Methode qui donne acces au booleen qui fait apparaitre ou disparaitre le
	 * panneau d'information
	 * 
	 * @return le booleen qui fait apparaitre ou disparaitre le panneau
	 *         d'information
	 */
	//Ivana
	public boolean isVoirPanneauInfo() {
		return voirPanneauInfo;
	}

	/**
	 * methode qui donne acces au modele de donnees courrant
	 * 
	 * @return md La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Ivana
	public ModeleDeDonnees getMd() {
		return md;
	}

	/**
	 * methode qui modifie le modele de donnees courrant
	 * 
	 * @param nouvMd La nouvelle matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Ivana
	public void setMd(ModeleDeDonnees nouvMd) {
		md = nouvMd;
	}

	/**
	 * Deplacement du systeme solaire avec la souris
	 * 
	 * @param e Evenement de la sourie
	 */
	//Corentin
	private void panSouris(MouseEvent e) {
		Vecteur positionSouris = new Vecteur(e.getX() / at.getScaleX(), e.getY() / at.getScaleY(), 0);
		if (mouseClicked) {
			translateX = positionSouris.getX() - xPrecedent;
			translateY = positionSouris.getY() - yPrecedent;

			xPrecedent = positionSouris.getX();
			yPrecedent = positionSouris.getY();
			at.translate(translateX, translateY);
			
			axeX[0] -= translateX;
			axeX[1] -= translateX;
			axeY[0] -= translateY;
			axeY[1] -= translateY;
		}

	}

}
