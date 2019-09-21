package aapplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modele.ModeleDeDonnees;
import objets.Objet;
import util.Vecteur;

/**
 * Panneau qui donne a l'utilisateur la chance de modifier un objet celeste dans
 * le systeme solaire
 * 
 * @author Corentin
 */
public class ModificationObjet extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private ModeleDeDonnees md;
	private JTextField nomField;
	private JSpinner spnX;
	private JSpinner spnY;
	private JSpinner spnZ;
	private JButton sauvegarderBtn;
	private JButton annulerBtn;
	private JTextField masseField;
	private JTextField rayonField;
	private DecimalFormat df = new DecimalFormat("##.####");

	/**
	 * Constructeur de la fenetre pour changer les parametres des objets du systeme solaire
	 */
	public ModificationObjet() {
		setTitle("Modification d'objet");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNom = new JLabel("Nom ");
		lblNom.setBounds(10, 88, 47, 14);
		contentPane.add(lblNom);

		JLabel lblParamtresCourrant = new JLabel("Param\u00E8tres courrant");
		lblParamtresCourrant.setBounds(98, 13, 173, 37);
		lblParamtresCourrant.setHorizontalAlignment(SwingConstants.CENTER);
		lblParamtresCourrant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblParamtresCourrant);

		JLabel lblMasse = new JLabel("Masse");
		lblMasse.setBounds(10, 113, 73, 20);
		contentPane.add(lblMasse);

		JLabel lblRayon = new JLabel("Rayon");
		lblRayon.setBounds(10, 144, 73, 14);
		contentPane.add(lblRayon);

		JLabel lblVitVect = new JLabel("Vitesse vectorielle");
		lblVitVect.setBounds(217, 61, 154, 14);
		contentPane.add(lblVitVect);

		nomField = new JTextField();
		nomField.setBounds(54, 85, 103, 20);
		contentPane.add(nomField);
		nomField.setColumns(10);

		JLabel lblX = new JLabel("X :");
		lblX.setBounds(217, 88, 46, 14);
		contentPane.add(lblX);

		JLabel lblY = new JLabel("Y :");
		lblY.setBounds(217, 116, 46, 14);
		contentPane.add(lblY);

		JLabel lblZ = new JLabel("Z :");
		lblZ.setBounds(217, 145, 46, 14);
		contentPane.add(lblZ);

		sauvegarderBtn = new JButton("Sauvegarder");
		sauvegarderBtn.setBounds(227, 171, 110, 23);
		sauvegarderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificationVitesse();
				dispose();
			}
		});
		contentPane.add(sauvegarderBtn);

		annulerBtn = new JButton("Annuler");
		annulerBtn.setBounds(228, 223, 109, 23);
		annulerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(annulerBtn);

		spnX = new JSpinner();
		spnX.setBounds(240, 85, 81, 22);
		spnX.setModel(new SpinnerNumberModel(30000.0, 12500.0, 50000.0, 2500.0));
		contentPane.add(spnX);

		spnY = new JSpinner();
		spnY.setBounds(240, 112, 81, 22);
		spnY.setModel(new SpinnerNumberModel(30000.0, 12500.0, 50000.0, 2500.0));
		contentPane.add(spnY);

		spnZ = new JSpinner();
		spnZ.setBounds(240, 141, 81, 22);
		spnZ.setModel(new SpinnerNumberModel(30000.0, 12500.0, 50000.0, 2500.0));
		contentPane.add(spnZ);

		JLabel lblVitX = new JLabel("km/s");
		lblVitX.setBounds(326, 87, 44, 16);
		contentPane.add(lblVitX);

		JLabel lblVitY = new JLabel("km/s");
		lblVitY.setBounds(326, 115, 44, 16);
		contentPane.add(lblVitY);

		JLabel lblVitZ = new JLabel("km/s");
		lblVitZ.setBounds(326, 144, 44, 16);
		contentPane.add(lblVitZ);
		
		JLabel label = new JLabel("x 10^25 kg");
		label.setBounds(131, 115, 86, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("km");
		label_1.setBounds(130, 147, 56, 16);
		contentPane.add(label_1);
		
		masseField = new JTextField();
		masseField.setBounds(54, 113, 73, 20);
		contentPane.add(masseField);
		masseField.setColumns(10);
		
		rayonField = new JTextField();
		rayonField.setBounds(55, 144, 73, 20);
		contentPane.add(rayonField);
		rayonField.setColumns(10);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(203, 56, 157, 156);
		horizontalBox.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		contentPane.add(horizontalBox);
		
		JLabel lblParametres = new JLabel("Param\u00E8tres");
		lblParametres.setBounds(10, 61, 103, 14);
		contentPane.add(lblParametres);
		
		JButton button = new JButton("Sauvegarder");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificationParametre();
				dispose();
			}
		});
		button.setBounds(20, 171, 110, 23);
		contentPane.add(button);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		horizontalBox_1.setBounds(4, 56, 190, 156);
		contentPane.add(horizontalBox_1);

	}

	/**
	 * Met a jour les valeur afficher de l'objet en cours de modification
	 */
	public void miseAJoursAffichage() {
		Objet objetSelec = md.getObjetSelectionner();
		nomField.setText(objetSelec.getNom());
		masseField.setText(df.format(objetSelec.getMasse()/1e25 )+ "");
		rayonField.setText(df.format(objetSelec.getRayon()/1000) + "");
		
		spnX.setValue(objetSelec.getVitesse().getX()/1000);
		spnY.setValue(objetSelec.getVitesse().getY()/1000);
		spnZ.setValue(objetSelec.getVitesse().getZ()/1000);
	}
	
	/**
	 * Methode qui change seulement la vitesse de l'objet selon les valeurs mise dans les spinners
	 */
	private void modificationVitesse() {
		Objet objetSelec = md.getObjetSelectionner();
		
		objetSelec.setVitesse(new Vecteur((double)spnX.getValue(),(double)spnY.getValue(),(double)spnZ.getValue()));
	}
	
	/**
	 * Methode qui change le nom, la masse et le rayon de la planete selon les valeurs mise dans les places respectives (JTextField)
	 */
	private void modificationParametre() {
		Objet objetSelec = md.getObjetSelectionner();
		
		objetSelec.setNom(nomField.getText());
		objetSelec.setMasse(Double.parseDouble(masseField.getText()) * 1e25);
		objetSelec.setRayon(Double.parseDouble(rayonField.getText()) * 1000);
	}
	
	
	/**
	 * Permet de changer les modele de donnees auxquelles les modifications seront apportees
	 * @param md Le modele de donnees
	 */
	public void setModeleDeDonnees(ModeleDeDonnees md) {
		this.md = md;
	}
}
