package aapplication.scene;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modele.ModeleDeDonnees;
import objets.Objet;
import util.SMath;

import util.Vecteur;


/**
 * Classe qui cree le panel ou seront contenu les informations sur la planete *
 * selectionner
 * 
 * @author Ivana Bera
 *
 */

public class PanneauInfoPlanete extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblNomPlanete;
	private JLabel lblVitOrbitale;
	private JLabel lblRayon;
	private JLabel lblMasse;
	private JLabel lblGravite;
	private JLabel lblVitLiberation;

	private ArrayList<ArrayList> lesListes = new ArrayList<ArrayList>();

	private ArrayList<Objet> listePlanete = new ArrayList<Objet>();
	
	private DecimalFormat df = new DecimalFormat("##");

	/**
	 * Le constructeur du panneau d'information qui va creer tous les composants sur
	 * le JFrame
	 */
	public PanneauInfoPlanete() {
		setLayout(null);

		lblNomPlanete = new JLabel("Nom de la plan\u00E8te");
		lblNomPlanete.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomPlanete.setForeground(Color.BLACK);
		lblNomPlanete.setBounds(10, 10, 258, 14);
		add(lblNomPlanete);

		lblVitOrbitale = new JLabel("");
		lblVitOrbitale.setBounds(10, 34, 258, 14);
		add(lblVitOrbitale);

		lblRayon = new JLabel("Rayon: ");
		lblRayon.setBounds(10, 58, 258, 14);

		add(lblRayon);

		lblMasse = new JLabel("Masse: ");
		lblMasse.setBounds(10, 82, 258, 14);
		add(lblMasse);

		lblGravite = new JLabel("Gravit\u00E9 \u00E0 la surface: ");
		lblGravite.setBounds(10, 130, 258, 14);
		add(lblGravite);
		lblVitLiberation = new JLabel("Vitesse de lib\u00E9ration: ");
		lblVitLiberation.setBounds(10, 154, 258, 14);
		add(lblVitLiberation);
	}

	/**
	 * Insere les informations de la planete dans le panneau
	 * 
	 * @param listeInfos liste qui contient les informations de la planete
	 * @param planeteSelectionne La planete selectionnee
	 */

	public void setPanneauPlanete(ArrayList<String> listeInfos) {
		lblNomPlanete.setText(listeInfos.get(0));
		lblVitOrbitale.setText("Vitesse orbitale moyenne: " + listeInfos.get(1) + " km/s");
		lblRayon.setText("Rayon: " + listeInfos.get(2) + " km");
		lblMasse.setText("Masse: " + listeInfos.get(3) + " kg");
		lblGravite.setText("Gravite: " + listeInfos.get(4) + " m/s^2");
		lblVitLiberation.setText("Vitesse de liberation: " + listeInfos.get(5) + " km/s");
	}

	/**
	 * * Insere les informations de la planete selectionner dans le panneau
	 * d'information
	 * 
	 * @param planeteSelectionee la planete selectionee
	 * @param mdCourant Le modele de donnees courant
	 */
	public void setInformations(Objet planeteSelectionee, ModeleDeDonnees mdCourant) {
		ArrayList <String> infoPlanete = new ArrayList<String>();
		infoPlanete.add(planeteSelectionee.getNom());
		infoPlanete.add(df.format(planeteSelectionee.getVitesse().module()) + "");
		infoPlanete.add(df.format(planeteSelectionee.getRayon()) + "");
		infoPlanete.add(planeteSelectionee.getMasse() + "");
		Objet o = new Objet(planeteSelectionee);
		o.setPosition(o.getPosition().additionne(new Vecteur(o.getRayon(), 0, 0)));
		infoPlanete.add(df.format(planeteSelectionee.forceGravitation(o).module()) + "");
		infoPlanete.add(df.format(Math.sqrt(2 * SMath.G * planeteSelectionee.getMasse() / planeteSelectionee.getRayon())) + "");
		setPanneauPlanete(infoPlanete);
	}
	
}