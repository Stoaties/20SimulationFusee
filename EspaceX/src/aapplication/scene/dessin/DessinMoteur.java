package aapplication.scene.dessin;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import listeners.MoteurListener;
import modele.ModeleAffichage;
import objets.fusee.composants.Moteur;
import objets.fusee.composants.enums.SystemeDePropulsion;
import util.Vecteur; 

/**
 * Classe qui va dessiner le moteur
 * 
 * @author Johnatan Gao
 * @author Melie Leclerc
 *
 */

public class DessinMoteur extends JPanel {//debut classe

	private static final long serialVersionUID = 1L;
	
	//Variables de la largeur et de la hauteur du panel dessinMoteur
	private double largeurMonde, hauteurMonde;
	
	//Matrices qui permettent la transition de pixel en metre
	private AffineTransform mat;
	
	//Variables qui contiennent le composant qui dessine les moteurs ssme, rd180, merlin
	private Moteur SSME, RD180, MERLIN;
	
	//Variable qui contient la liste des moteur
	private ArrayList <Moteur> listMoteur = new ArrayList<Moteur>();
	
	//Variable qui qui gere si le moteur est selectionne
	private boolean isMoteurSelectionne = false;
	
	//Variable qui gere quel moteur est selectionne
	private int nbMoteurSelectionne;
	
	//Variable qui contient le composant qui dessine le moteur selectionne 
	private Moteur moteurSelectionne;
	
	//Variables qui permettent le deplacement des moteurs
	private double translateX = 0, translateY = 0, xPrecedent = 0, yPrecedent = 0;
	
	//Variable qui gere la position initiale dans le panel dessinMoteur et celui de dessinFusee
	private Vecteur positionInitiale;
	
	//Variable qui gere la liste d'ecouteurs
	private ArrayList <MoteurListener> listeEcouteurs = new ArrayList <MoteurListener>();
	
	//Variable du nom du moteur selectionne
	private String nomMoteurSelectionne = "";
	
	//Variable de la longueur de la liste de moteur
	private int listMoteurSize = 0;
	
	//Variables qui gerent quel combustible est utilise
	private boolean isKerosene = true;
	private boolean isLOX = true;
	
	//Variable qui contient les informations des moteurs
	private JTextArea txtInfoMoteur;
	
	//Variable qui determine si le moteur est en deplacement
	private boolean isDragging = false;
		
	/**
	 * Constructeur de la classe DessinMoteur qui va creer un Objet Dessinable de type Moteur
	 * @param mdMoteur La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 */
	//Johnatan G

	public DessinMoteur(ModeleAffichage mdMoteur) {//debut constructeur

		this.mat = mdMoteur.getMatMC();
		setBackground(Color.white);
	
		this.largeurMonde = mdMoteur.getLargUnitesReelles();
		this.hauteurMonde = mdMoteur.getHautUnitesReelles();
		
		mat.translate(largeurMonde/2, hauteurMonde/2);
		mat.scale(1, -1);
		
		ajouterGestionSouris();
		

		this.SSME = new Moteur(SystemeDePropulsion.SSME_RS25, new Vecteur(-largeurMonde/5, -SystemeDePropulsion.SSME_RS25.getHauteur()/2 , 0));
		this.RD180 = new Moteur(SystemeDePropulsion.RD_180, new Vecteur(SSME.getPosition().getX() + SSME.getDiametre() + largeurMonde/10, -SystemeDePropulsion.RD_180.getHauteur()/2 , 0));
		this.MERLIN = new Moteur(SystemeDePropulsion.MERLIN, new Vecteur(RD180.getPosition().getX() + RD180.getDiametre() + largeurMonde/10, -SystemeDePropulsion.MERLIN.getHauteur()/2, 0));
		setLayout(null);
	
		listMoteur.add(SSME);
		listMoteur.add(RD180);
		listMoteur.add(MERLIN);
		listMoteur.trimToSize();
		
		int x = (int) (this.largeurMonde/200 * mat.getScaleX());
		int y = (int) (this.hauteurMonde*-1/40 * mat.getScaleY());
		int w = (int) (this.largeurMonde/3.4 * mat.getScaleX());
		int h = (int) (this.hauteurMonde*-1*0.5 * mat.getScaleY());

		txtInfoMoteur = new JTextArea();
		txtInfoMoteur.setBackground(null);
		txtInfoMoteur.setEditable(false);
		txtInfoMoteur.setText("Nom: "
				+"\nMasse: "
				+ "\nPrix:"
				+ "\nErgols: " 
				+ "\nImpulsion specifique (vide): "
				+ "\nImpulsion specifique (terrestre):"
				+ "\nVitesse d'ejection (terrestre) : " 
				+ "\nVitesse d'ejection (vide): "
				+ "\nPoussee (terrestre): " 
				+ "\nPoussee (vide): " 
				+ "\nHauteur: " 
				+ "\nDiametre: " 
				+ "\nDuree de fonctionnement: ");
		txtInfoMoteur.setBounds(x, y, w, h);
		txtInfoMoteur.setVisible(false);
		add(txtInfoMoteur);
		
	}//fin constructeur 
	
	/**
	 * Methode qui contient toutes les gestions de souris
	 * 
	 * 
	 */
	//Johnatan G
	
	private void ajouterGestionSouris() {//debut methode
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {//debut pressed
				
				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);
					
				for(int n = 0; n < listMoteur.size(); n++) {
					
					if(listMoteur.get(n).contains(positionSouris)) {
						
						if(listMoteurSize == 0) { //Si il n'y a pas de moteur selectionne
							
							if(isKerosene && isLOX) { // Si il n'y a pas d'ergols selectionne					
								isMoteurSelectionne = true;					
							}else {			
								if(isKerosene && listMoteur.get(n).getErgols().equalsIgnoreCase("kerosene_rp1")) {
									isMoteurSelectionne = true;
								}else if(isLOX && listMoteur.get(n).getErgols().equalsIgnoreCase("lox_lh2")) {
									isMoteurSelectionne = true;
								}else {						
									isMoteurSelectionne = false;				
								}
							}		
						}else {			
							if(listMoteur.get(n).getNom().equalsIgnoreCase(nomMoteurSelectionne)) {				
								isMoteurSelectionne = true;				
							}else if(isKerosene && listMoteur.get(n).getErgols().equalsIgnoreCase("kerosene_rp1")) {
								isMoteurSelectionne = true;
							}else if(isLOX && listMoteur.get(n).getErgols().equalsIgnoreCase("lox_lh2")) {			
								isMoteurSelectionne = true;				
							}else {				
								isMoteurSelectionne = false;						
							}	
						}

						nbMoteurSelectionne = n;
						moteurSelectionne = listMoteur.get(nbMoteurSelectionne);

						xPrecedent = positionSouris.getX();
						yPrecedent = positionSouris.getY();

						if(isMoteurSelectionne) {
							setMoteurEvenement(moteurSelectionne, positionSouris, largeurMonde, mat); 
						}

						if(isDragging == false) {
							positionInitiale = new Vecteur(moteurSelectionne.getPosition().getX(), moteurSelectionne.getPosition().getY(), 0);
						}

					}
				}
				
				montrerInformations();
				
			}//fin pressed
			
			public void mouseReleased(MouseEvent e) {//debut released
				
				isDragging = false;
				
				if(isMoteurSelectionne) {
					moteurSelectionne.setPosition(new Vecteur(positionInitiale.getX(), positionInitiale.getY(), 0));
					listMoteur.set(nbMoteurSelectionne, moteurSelectionne);

					moteurSelectionne = null;
					isMoteurSelectionne = false;				
					deselectionnerMoteurEvenement();
					repaint();
				}

			}//fin released
			
		});
		
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {//debut dragged
				
				Vecteur positionSouris = new Vecteur(e.getX()/mat.getScaleX() - mat.getTranslateX()/mat.getScaleX() , e.getY()/mat.getScaleY() - mat.getTranslateY()/mat.getScaleY(), 0);
				isDragging = true;
				txtInfoMoteur.setVisible(false);
				
				if(isMoteurSelectionne) {
					
					
					translateX = positionSouris.getX() - xPrecedent;
					translateY = positionSouris.getY() - yPrecedent;
					
						
					xPrecedent = positionSouris.getX();
					yPrecedent = positionSouris.getY();


					moteurSelectionne.setPosition(new Vecteur(moteurSelectionne.getPosition().getX() + translateX , moteurSelectionne.getPosition().getY()+translateY, 0));		
					
					listMoteur.set(nbMoteurSelectionne, moteurSelectionne);	
					dragMoteurEvenement(positionSouris, largeurMonde, mat); //lever un evenement si le moteur depasse la largeur du monde
					repaint();
					
				}	
				
			}//fin dragged
		});
		
	}//fin methode
	
	/**
	 * Methode qui permet de dessiner une scene qui inclut les moteurs
	 * 
	 * @param g Contexte graphique qui va permettre de dessiner les elements
	 */
	//Johnatan G
	
	public void paintComponent(Graphics g) {//debut methode
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(int n = 0; n < listMoteur.size(); n++) {
			
			if(this.listMoteurSize != 0) { //Si la liste de moteur dans la fusee contient deja un moteur
				
				if(this.nomMoteurSelectionne.equalsIgnoreCase(listMoteur.get(n).getNom())) { //Assurer que le nom du moteur dans la liste correspond au moteur dans dessinMoteur
					listMoteur.get(n).draw(g2d, mat);
				}
				
			}else {
				
				if(isLOX && isKerosene) { //Si il n'y a pas d'ergol selectionne
					
					listMoteur.get(n).draw(g2d, mat);
					
				}else {
					
					if(isKerosene && listMoteur.get(n).getErgols().equalsIgnoreCase("kerosene_rp1")) { //Si l'ergol est le kerosene
						listMoteur.get(n).draw(g2d, mat);
					}else
						if(isLOX && listMoteur.get(n).getErgols().equalsIgnoreCase("lox_lh2")) { //Si l'ergol est l'hydrogene
							listMoteur.get(n).draw(g2d, mat);
						}
				}	
			}
			
		}		
		
	}//fin methode
	
	/**
	 * Methode qui montre les informations des moteurs
	 */
	//Melie L
	
	private void montrerInformations() {
	if(isMoteurSelectionne) {
		txtInfoMoteur.setVisible(true);
		
		txtInfoMoteur.setText("Nom: " +moteurSelectionne.getNom()
		+"\nMasse : " +moteurSelectionne.getMasse()+" kg"
		+ "\nPrix : " +moteurSelectionne.getPrix()+ " M$"
		+ "\nErgols : " +moteurSelectionne.getErgols()
		+ "\nImpulsion specifique (vide) : "+ moteurSelectionne.getImpulsionSpecifiqueVide()+ " secondes"
		+ "\nImpulsion specifique (terrestre) :"+moteurSelectionne.getImpulsionSpecifiqueTerrestre()+ " secondes"
		+ "\nVitesse d'ejection (terrestre) : " +moteurSelectionne.getVitesseEjectionTerrestre()+ " m/s"
		+ "\nVitesse d'ejection (vide) : "+ moteurSelectionne.getVitesseEjectionVide()+ " m/s"
		+ "\nPoussee (terrestre) : " +moteurSelectionne.getPousseeTerrestre()+ " N"
		+ "\nPoussee (vide) : " +moteurSelectionne.getPousseeVide()+ " N"
		+ "\nHauteur : " +moteurSelectionne.getHauteur()+ " m"
		+ "\nDiametre : " +moteurSelectionne.getDiametre()+ " m"
		+ "\nDuree de fonctionnement : "+moteurSelectionne.getDureeFonctionnement()+" s");			
	}else {			
		txtInfoMoteur.setVisible(false);
	}
	
	}
	
	/**
	 * Methode qui va permettre d'ajouter les ecouteur personalise au ArrayList
	 * @param objEcout Ecouteur personalise
	 */
	//Johnatan G
	
	public void addMoteurListener(MoteurListener objEcout) {//debut methode
		
		this.listeEcouteurs.add(objEcout);
		
	}//fin methode
	
	/**
	 * Methode qui lance un evenement qui notifie que le moteur est selectionne 
	 * @param moteurSelectionne Le moteur selectionne
	 * @param positionSouris    La position du curseur
	 * @param largeur           La largeur du monde reel
	 * @param matMoteur         La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations 
	 */
	//Johnatan G
	
	private void setMoteurEvenement(Moteur moteurSelectionne, Vecteur positionSouris, double largeur, AffineTransform matMoteur) {//debut methode
		
		for(MoteurListener objEcout: listeEcouteurs) {
			
			objEcout.setMoteurSelectionne(moteurSelectionne, positionSouris, largeur, matMoteur);
			
		}

	}//fin methode
	
	/**
	 * Methode qui lance un evenement qui notifie quand le moteur est deselectionne
	 * 
	 */
	//Johnatan G
	
	private void deselectionnerMoteurEvenement() {//debut methode
		
		for(MoteurListener objEcout: listeEcouteurs) {

			objEcout.deselectionnerMoteur();

		}
		
	}//fin methode
	
	/**
	 * Methode qui lance un evenement qui notifie quand le moteur est en mouvement
	 * @param positionSouris La position du curseur
	 * @param largeurMonde   La largeur du monde reel
	 * @param matMoteur      La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations   
	 */
	//Johnatan G
	
	private void dragMoteurEvenement(Vecteur positionSouris, double largeurMonde, AffineTransform matMoteur) {//debut methode
		
		for(MoteurListener objEcout: listeEcouteurs) {
			
			objEcout.dragMoteurSelectionne(positionSouris, largeurMonde, matMoteur);
		}
		
	}//fin methode

	/**
	 * Methode qui retourne le nom du moteur selectionne
	 * @return nomMoteurSelectionne Le nom du moteur selectionne
	 */
	//Johnatan G
	
	public String getNomMoteurSelectionne() {//debut methode
		return nomMoteurSelectionne;
	}//fin methode

	/**
	 * Methode qui modifie le nom du moteur selectionne
	 * @param nomMoteurSelectionne Le nom du moteur selectionne
	 */
	//Johnatan G
	
	public void setNomMoteurSelectionne(String nomMoteurSelectionne) {//debut methode
		this.nomMoteurSelectionne = nomMoteurSelectionne;
	}//fin methode

	/**
	 * Methode qui retourne la grandeur de la liste de moteurs
	 * @return listMoteurSize La grandeur de la liste de moteurs
	 */
	//Johnatan G
	
	public int getListMoteurSize() {//debut methode
		return listMoteurSize;
	}//fin methode

	/**
	 * Methode qui modifie la grandeur de la liste de moteurs
	 * @param listMoteurSize La grandeur de la liste de moteurs
	 */
	//Johnatan G
	
	public void setListMoteurSize(int listMoteurSize) {//debut methode
		this.listMoteurSize = listMoteurSize;
	}//fin methode

	/**
	 * Methode qui retourne si le kerosene est utilise
	 * @return isKerosene Le combustible utilise
	 */
	//Johnatan G
	
	public boolean isKerosene() {//debut methode
		return isKerosene;
	}//fin methode

	/**
	 * Methode qui gere si le kerosene est utilise
	 * @param isKerosene Le combustible utilise
	 */
	//Johnatan G
	
	public void setKerosene(boolean isKerosene) {//debut methode
		this.isKerosene = isKerosene;
	}//fin methode

	/**
	 * Methode qui retourne si le LOX est utilise
	 * @return isLOX Le combustible utilise
	 */
	//Johnatan G
	
	public boolean isLOX() {//debut methode
		return isLOX;
	}//fin methode
	
	/**
	 * Methode qui gere si le LOX est utilise
	 * @param isLOX Le combustible utilise
	 */
	//Johnatan G
	
	public void setLOX(boolean isLOX) {//debut methode
		this.isLOX = isLOX;
	}//fin methode
	
}//fin classe

