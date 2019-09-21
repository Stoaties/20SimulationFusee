package aapplication.information;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe qui va afficher les informations scientifiques
 * 
 * @author Ivana Bera
 *
 */

public class InfoScientifiques extends JFrame {//debut classe

	////////////////////////////////////////////////////////////COMPOSANTS QUI ORGANISENT LES AUTRES COMPOSANTS

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnQuitter;
	private ImageAvecDefilement panConcepts;
	private JButton btnQuitterLapplication;
	private JButton btnPrecedent;
	private JButton btnSuivant;
	private String fichiersGuideScientifique[]={"guidescience-1.jpg", "guidescience-2.jpg","guidescience-3.jpg","guidescience-4.jpg","guidescience-5.jpg","guidescience-6.jpg"};
	private int k=0;

	/**
	 /**
	 * La methode main de l'application qui va creer la fenetre JFrame sur lequel
	 * les autres composants se baseront
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoScientifiques frame = new InfoScientifiques();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Le constructeur de l'application qui va creer tous les composants sur le JFrame
	 */

	public InfoScientifiques() {
		setTitle("Concepts scientifiques");//Debut constructeur

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0,  (int)(screen.getWidth()/1.5), (int)(screen.getHeight()));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//debut
				dispose();
			}//fin
		});
		btnQuitter.setBounds((int)(screen.getWidth()/20+(screen.getWidth()/2)), (int)(screen.getHeight()/20+(screen.getHeight()/1.20)), (int)(screen.getWidth()/20), (int)(screen.getHeight()/40));
		contentPane.add(btnQuitter);

		panConcepts = new ImageAvecDefilement();


		panConcepts.setBounds((int)(screen.getWidth()/20), (int)(screen.getHeight()/20), (int)(screen.getWidth()/1.75), (int)(screen.getHeight()/1.25));
		contentPane.add(panConcepts);

		//Pour fixer couleur du cadre
		panConcepts.setBackground(Color.white);
		//Pour modifier la largeur du cadre 
		panConcepts.setLargeurCadre(10);
		//Pour charger l'image pre-fabriquee
		panConcepts.setFichierImage(fichiersGuideScientifique[0]);


		btnQuitterLapplication = new JButton("Quitter l'application");
		btnQuitterLapplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//debut
				System.exit(0);
			}//fin
		});
		btnQuitterLapplication.setBounds((int)(screen.getWidth()/20+(screen.getWidth()/2)), (int)(screen.getHeight()/20+(screen.getHeight()/1.15)), (int)(screen.getWidth()/10), (int)(screen.getHeight()/40));
		contentPane.add(btnQuitterLapplication);

		btnPrecedent = new JButton("Precedent");
		btnPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panConcepts.setFichierImage(fichiersGuideScientifique[k-1]);
				k=k-1;

				if(k==0) {
					btnPrecedent.setEnabled(false);
				}
				
				btnSuivant.setEnabled(true);
			}
		});

		if(k==0) {
			btnPrecedent.setEnabled(false);
		}
		
		btnPrecedent.setBounds(15, 938, 115, 29);
		contentPane.add(btnPrecedent);

		btnSuivant = new JButton("Suivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panConcepts.setFichierImage(fichiersGuideScientifique[k+1]);
				k=k+1;
				btnPrecedent.setEnabled(true);

				if(k==fichiersGuideScientifique.length-1) {
					btnSuivant.setEnabled(false);
				}
			}
		});


		btnSuivant.setBounds(180, 938, 115, 29);
		contentPane.add(btnSuivant);
	}//fin constructeur
}//fin classe
