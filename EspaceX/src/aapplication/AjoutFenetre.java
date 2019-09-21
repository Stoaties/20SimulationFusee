package aapplication;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aapplication.scene.LecteurImage;
import modele.ModeleDeDonnees;
import objets.celeste.Etoile;
import objets.celeste.Planete;
import objets.celeste.Satellite;
import util.SMath;
import util.Vecteur;

/**
 * Classe qui ajoute la fenetre d'ajout d'objet
 * @author Corentin Gouanvic
 *
 */
public class AjoutFenetre extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSpinner spnMasse;
	private JSpinner spnRayon;
	private JSpinner spnRayonOrb;
	private JRadioButton radEtoile;
	private JRadioButton radPlanete;
	private JRadioButton radSatellite;
	private JTextField txtFieldNomObjet;
	private ModeleDeDonnees md;
	private LecteurImage lecteur = new LecteurImage();
	private JTextField txtFieldX;
	private JTextField txtFieldY;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton rdbtnEnOrbite;
	private JRadioButton rdbtnPosition;
	private JButton btnAjouter;

	/**
	 * Constructeur de la fenetre d'ajout
	 */
	public AjoutFenetre() {
		setTitle("Ajouter un objet c\u00E9leste");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomObjet = new JLabel("Nom de votre objet c\u00E9leste");
		lblNomObjet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomObjet.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomObjet.setBounds(136, 1, 169, 32);
		contentPane.add(lblNomObjet);

		radEtoile = new JRadioButton("\u00C9toile");
		buttonGroup.add(radEtoile);
		radEtoile.setBounds(181, 67, 79, 23);
		contentPane.add(radEtoile);

		radPlanete = new JRadioButton("Plan\u00E8te");
		radPlanete.setSelected(true);
		buttonGroup.add(radPlanete);
		radPlanete.setBounds(51, 67, 79, 23);
		contentPane.add(radPlanete);

		radSatellite = new JRadioButton("Satellite");
		buttonGroup.add(radSatellite);
		radSatellite.setBounds(311, 67, 79, 23);
		contentPane.add(radSatellite);

		JLabel lblMasse = new JLabel("Masse:");
		lblMasse.setBounds(49, 195, 56, 14);
		contentPane.add(lblMasse);

		JLabel lblRayon = new JLabel("Rayon:");
		lblRayon.setBounds(49, 225, 56, 14);
		contentPane.add(lblRayon);

		JLabel lblRayonOrb = new JLabel("Rayon d'orbite:");
		lblRayonOrb.setBounds(10, 250, 94, 14);
		contentPane.add(lblRayonOrb);

		btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnEnOrbite.isSelected()) {
					creeObjetOrbite();
				} else {
					creeObjetPosition();
				}
				dispose();
			}
		});
		btnAjouter.setBounds(158, 279, 116, 23);
		contentPane.add(btnAjouter);

		JLabel lblParametres = new JLabel("Param\u00E8tres");
		lblParametres.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblParametres.setHorizontalAlignment(SwingConstants.CENTER);
		lblParametres.setBounds(165, 94, 109, 42);
		contentPane.add(lblParametres);

		spnMasse = new JSpinner();
		spnMasse.setModel(new SpinnerNumberModel(45.0, 1.0, 100.0, 5.0));
		spnMasse.setBounds(91, 194, 118, 22);
		contentPane.add(spnMasse);

		spnRayon = new JSpinner();
		spnRayon.setModel(new SpinnerNumberModel(new Double(6500), new Double(2500), null, new Double(2500)));
		spnRayon.setBounds(91, 220, 118, 22);
		contentPane.add(spnRayon);

		spnRayonOrb = new JSpinner();
		spnRayonOrb.setModel(new SpinnerNumberModel(1.5E8, null, 8.0E8, 2.5E7));
		spnRayonOrb.setBounds(91, 246, 118, 22);
		contentPane.add(spnRayonOrb);

		JLabel lblKg = new JLabel("x 10^25 kg");
		lblKg.setBounds(219, 197, 86, 16);
		contentPane.add(lblKg);

		JLabel lblKmRayon = new JLabel("km");
		lblKmRayon.setBounds(218, 224, 56, 16);
		contentPane.add(lblKmRayon);

		JLabel lblKmRayonOrb = new JLabel("km");
		lblKmRayonOrb.setBounds(218, 249, 56, 16);
		contentPane.add(lblKmRayonOrb);

		txtFieldNomObjet = new JTextField();
		txtFieldNomObjet.setBounds(163, 31, 116, 22);
		contentPane.add(txtFieldNomObjet);
		txtFieldNomObjet.setColumns(10);

		JLabel lblPosition = new JLabel("Position");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPosition.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosition.setBounds(320, 165, 70, 14);
		contentPane.add(lblPosition);

		txtFieldX = new JTextField();
		txtFieldX.setBounds(331, 222, 59, 20);
		contentPane.add(txtFieldX);
		txtFieldX.setColumns(10);

		JLabel lblX = new JLabel("X:");
		lblX.setBounds(311, 225, 22, 14);
		contentPane.add(lblX);

		txtFieldY = new JTextField();
		txtFieldY.setBounds(331, 247, 59, 20);
		contentPane.add(txtFieldY);
		txtFieldY.setColumns(10);

		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(311, 251, 22, 13);
		contentPane.add(lblY);
		
		JLabel lblXKm = new JLabel("x 10^6 km");
		lblXKm.setBounds(396, 225, 70, 14);
		contentPane.add(lblXKm);
		
		JLabel label = new JLabel("x 10^6 km");
		label.setBounds(396, 250, 70, 14);
		contentPane.add(label);
		
		rdbtnPosition = new JRadioButton("Position");
		buttonGroup_1.add(rdbtnPosition);
		rdbtnPosition.setBounds(280, 135, 109, 23);
		contentPane.add(rdbtnPosition);
		
		rdbtnEnOrbite = new JRadioButton("En orbite");
		rdbtnEnOrbite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnEnOrbite.isSelected()) {
					spnRayonOrb.setEnabled(true);
					txtFieldX.setEnabled(false);
					txtFieldY.setEnabled(false);
				} else {
					spnRayonOrb.setEnabled(false);
					txtFieldX.setEnabled(true);
					txtFieldY.setEnabled(true);
				}
			}
		});
		rdbtnEnOrbite.setSelected(true);
		buttonGroup_1.add(rdbtnEnOrbite);
		rdbtnEnOrbite.setBounds(111, 135, 109, 23);
		contentPane.add(rdbtnEnOrbite);
	}

	/**
	 * Methode qui cree un objet a une position exacte
	 */
	private void creeObjetPosition() {
		if (radEtoile.isSelected()) {
			Etoile etoileTemp = new Etoile((int) spnRayon.getValue()* 1000, (int) spnMasse.getValue());
			etoileTemp.setPosition(new Vecteur(Double.parseDouble(txtFieldX.getText())*1e9,Double.parseDouble(txtFieldY.getText())*1e9,0));
			etoileTemp.setModeleDeDonnees(md);
			md.addEtoile(etoileTemp);
		}

		if (radPlanete.isSelected()) {
			Planete planeteTemp = new Planete( (double) spnRayon.getValue() * 1000,
					(double) spnMasse.getValue(),  (double)spnRayonOrb.getValue()* 1000, 0,lecteur.getImgTerre());
			planeteTemp.setPosition(new Vecteur(Double.parseDouble(txtFieldX.getText())*1e9,Double.parseDouble(txtFieldY.getText())*1e9,0));
			planeteTemp.setModeleDeDonnees(md);
			planeteTemp.setVitesse(new Vecteur(1,1,0));
			md.addPlanete(planeteTemp);
		}

		if (radSatellite.isSelected()) {
			Satellite satelliteTemp = new Satellite((int) spnRayon.getValue()* 1000,(int) spnMasse.getValue(),(int) spnRayonOrb.getValue()* 1000,0);
			satelliteTemp.setPosition(new Vecteur(Double.parseDouble(txtFieldX.getText())*1e9,Double.parseDouble(txtFieldY.getText())*1e9,0));
			satelliteTemp.setModeleDeDonnees(md);
			md.addSatellite(satelliteTemp);
		}
		
	}

	/**
	 * Methode qui cree un objet a une en orbite d'un autre objet
	 */
	private void creeObjetOrbite() {
		
		double masseCentre = md.getObjetSelectionner().getMasse();//spnMasse.getValue();
		double distance = (double)spnRayonOrb.getValue()*1000;
		double vitesseModule = Math.sqrt((SMath.G * masseCentre )/ (distance));
		
		Vecteur vitesse = new Vecteur(0,vitesseModule,0);
		vitesse = vitesse.additionne(md.getObjetSelectionner().getVitesse());
		Vecteur position = md.getObjetSelectionner().getPosition().additionne(new Vecteur(distance,0,0));
		
		if (radEtoile.isSelected()) {
			Etoile etoileTemp = new Etoile((int) spnRayon.getValue()* 1000, (int) spnMasse.getValue());
			etoileTemp.setPosition(position);
			etoileTemp.setVitesse(vitesse);
			etoileTemp.setModeleDeDonnees(md);
			md.addEtoile(etoileTemp);
		}

		if (radPlanete.isSelected()) {
			Planete planeteTemp = new Planete( (double) spnRayon.getValue() * 1000,
					(double) spnMasse.getValue(),  (double)spnRayonOrb.getValue()* 1000, 0,lecteur.getImgTerre());
			planeteTemp.setPosition(position);
			planeteTemp.setVitesse(vitesse);
			planeteTemp.setModeleDeDonnees(md);
			md.addPlanete(planeteTemp);
		}

		if (radSatellite.isSelected()) {
			Satellite satelliteTemp = new Satellite((double) spnRayon.getValue()* 1000,(double) spnMasse.getValue(),(double) spnRayonOrb.getValue()* 1000,0);
			satelliteTemp.setPosition(position);
			satelliteTemp.setVitesse(vitesse);
			satelliteTemp.setModeleDeDonnees(md);
			md.addSatellite(satelliteTemp);
		}
		
	}
	
	/**
	 * Methode pour mettre un modele de donnees pour y ajouter les objets.
	 * @param md Modele de donnees ou ajouter les objets.
	 */
	public void setModeleDeDonnees(ModeleDeDonnees md) {
		this.md = md;
	}
}
