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
 * Classe qui va afficher les auteurs du projet
 * 
 * @author Melie Leclerc
 *
 */
public class APropos extends JFrame {//debut classe

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	//Variable qui affiche la barrede defilement avec les instructions
	private ImageAvecDefilement panAPropos;
	
	//Variables qui gerent les boutons
	private JButton btnQuitter;
	private JButton btnQuitterLapplication;
	
	//Listes des images d'instructions
	private String aPropos[]={"aPropos.jpg"};
	

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
					APropos frame = new APropos();
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
	//Melie L
	
	public APropos() {//Debut constructeur
		setTitle("A propos");

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

		panAPropos = new ImageAvecDefilement();


		panAPropos.setBounds((int)(screen.getWidth()/20), (int)(screen.getHeight()/20), (int)(screen.getWidth()/1.75), (int)(screen.getHeight()/1.25));
		contentPane.add(panAPropos);

		//Pour fixer couleur du cadre
		panAPropos.setBackground(Color.white);
		//Pour modifier la largeur du cadre 
		panAPropos.setLargeurCadre(10);
		//Pour charger l'image pre-fabriquee
		panAPropos.setFichierImage(aPropos[0]);


		btnQuitterLapplication = new JButton("Quitter l'application");
		btnQuitterLapplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//debut
				System.exit(0);
			}//fin
		});
		btnQuitterLapplication.setBounds((int)(screen.getWidth()/20+(screen.getWidth()/2)), (int)(screen.getHeight()/20+(screen.getHeight()/1.15)), (int)(screen.getWidth()/10), (int)(screen.getHeight()/40));
		contentPane.add(btnQuitterLapplication);

		
	}//fin constructeur

}//fin classe
