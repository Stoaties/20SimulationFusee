package aapplication;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aapplication.scene.dessin.DessinAccueil;

/**
 * Classe qui va afficher la page d'Accueil
 * @author Melie Leclerc
 *
 */

public class Accueil extends JFrame  {//debut classe

	private static final long serialVersionUID = 1L;

	////////////////////////////////////////////////////////////COMPOSANT QUI ORGANISE LES AUTRES COMPOSANTS

	private JPanel contentPane;

	////////////////////////////////////////////////////////////COMPOSANT QUI DESSINE LA PAGE D'ACCUEIL

	private DessinAccueil dessinAccueil;
	
	/**
	 * La methode main de l'application qui va creer la fenetre JFrame sur lequel les autres composants se baseront
	 * @param args
	 */
	//Melie L

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accueil fenetreAccueil = new Accueil();
					
					fenetreAccueil.setUndecorated(true);
					fenetreAccueil.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Le constructeur de la classe Accueil l'application qui va creer tous les composants sur le JFrame
	 */
	//Melie L

	public Accueil() {//debut constructeur
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)(screen.getWidth()/2 - screen.getWidth()/3), (int) (screen.getHeight()/2 - screen.getHeight() * 0.4), (int)(screen.getWidth()/1.5), (int)(screen.getHeight() * 0.8));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dessinAccueil = new DessinAccueil();
		dessinAccueil.setBounds(0, 0, (int)(screen.getWidth()/1.5), (int)(screen.getHeight()));
		contentPane.add(dessinAccueil);	
		dessinAccueil.demarrer();

	}//fin constructeur
	
}//fin classe