package aapplication.information;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe qui va afficher les instructions de l'application
 * 
 * @author Melie Leclerc
 *
 */

public class Instructions extends JFrame {//debut classe

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	//Variable qui affiche la barrede defilement avec les instructions
	private ImageAvecDefilement panConcepts;
	
	//Variables qui gerent les boutons
	private JButton btnQuitter;
	private JButton btnQuitterLapplication;
	private JButton btnPrecedent;
	private JButton btnSuivant;
	
	//Listes des images d'instructions
	private String fichiersInstructions[]={"instructions-1.jpg","instructions-2.jpg","instructions-3.jpg","instructions-4.jpg","instructions-5.jpg"};
	
	//Compteur d'images
	private int k=0;
	
	/**
	 * 
	 * La methode main de l'application qui va creer la fenetre JFrame sur lequel
	 * les autres composants se baseront
	 * 
	 * @param args
	 */
	//Melie L
	 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructions frame = new Instructions();
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
	//Melie L
	
	public Instructions() {//Debut constructeur
		setTitle("Guide d'utilisation\r\n");

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
		panConcepts.setFichierImage(fichiersInstructions[k]);
		
		
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
			public void actionPerformed(ActionEvent e) {//debut
				
				panConcepts.setFichierImage(fichiersInstructions[k-1]);
				k=k-1;
				
				if(k==0) {
					btnPrecedent.setEnabled(false);
				}
				
				btnSuivant.setEnabled(true);
			}//fin
		});
		
		btnPrecedent.setBounds(15, 938, 115, 29);
		contentPane.add(btnPrecedent);
		
		btnSuivant = new JButton("Suivant");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//debut
				
				panConcepts.setFichierImage(fichiersInstructions[k+1]);
				k=k+1;
				btnPrecedent.setEnabled(true);
				
				if(k==fichiersInstructions.length-1) {
					btnSuivant.setEnabled(false);
				}
			}//fin 
		});
		
		if(k==0) {
			btnPrecedent.setEnabled(false);
		}
		
		btnSuivant.setBounds(180, 938, 115, 29);
		contentPane.add(btnSuivant);
	}//fin constructeur
}//fin classe
