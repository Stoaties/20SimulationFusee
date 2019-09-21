package graphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import modele.ModeleAffichage;
import util.Constantes;
import util.DoubleArrayList;

/**
 * Classe qui va gerer les sorties de l'application
 * 
 * @author Johnatan Gao
 *
 */

public class DessinGraphiqueSortie extends JPanel implements Constantes {//debut classe

private static final long serialVersionUID = 1L;
	
	//Matrice qui permet la transition de pixel en metre
		private double largeurMonde, hauteurMonde;
		private AffineTransform mat;

	//Listes qui vont permettre de tracer le graphique
	private DoubleArrayList arrayTempsEcouleFusee = new DoubleArrayList(); //Le X pour les graphiques
	
	private DoubleArrayList arraySommeDesForcesX = new DoubleArrayList();
	private DoubleArrayList arraySommeDesForcesY = new DoubleArrayList();
	
	private DoubleArrayList arrayVitesseFuseeX = new DoubleArrayList();
	private DoubleArrayList arrayVitesseFuseeY = new DoubleArrayList();
	
	private DoubleArrayList arrayAccelerationX = new DoubleArrayList();
	private DoubleArrayList arrayAccelerationY = new DoubleArrayList();
	
	private DoubleArrayList arrayMasseFusee = new DoubleArrayList();
	private DoubleArrayList arrayMasseReservoir = new DoubleArrayList();
	private DoubleArrayList arrayMasseBooster = new DoubleArrayList();
	private DoubleArrayList arrayResistanceAir = new DoubleArrayList();
	
	private DoubleArrayList arrayDeviationAngle = new DoubleArrayList();
	private DoubleArrayList arrayDistanceFuseeTerre = new DoubleArrayList();
	
	//Variables qui gerent le type de graphique
	private int typeGraphique;
	
	//Variable qui gere le titre d'un graphique
	private String titreGraphique = "";
	private String titreAxeX = "";
	private String titreAxeY = "";
	
	//Variables qui gerent la position et les dimensions des graphiques
	private double positionX, positionY, widthGraphique, heightGraphique;
	
	//Variable de graphique de sortie
	private Graphique graphiqueSortie;
	
	//Variables qui gerent les graduations en x et en y
	private double[] arrayX, arrayY;
	
	//Variable qui gere la couleur des points du graphique
	private Color couleurPoint;
	
	/**
	 * Constructeur qui va dessiner les graphiques
	 * @param ma           La matrice de transformation en unite reelle qui va permettre d'appliquer les transformations lors du dessinage des elements
	 * @param couleurPoint La couleur des points 
	 */
	//Johnatan G
	
	public DessinGraphiqueSortie(ModeleAffichage ma, Color couleurPoint) {//debut constructeur

		setBackground(Color.white);
		this.mat = ma.getMatMC();	
		this.mat.scale(1, -1);	
		
		this.largeurMonde = ma.getLargUnitesReelles();
		this.hauteurMonde = ma.getHautUnitesReelles();
		
		this.mat.translate(0, -hauteurMonde/2);
		
		this.positionX = largeurMonde/10;
		this.positionY = -hauteurMonde/2 + hauteurMonde/10;
		
		this.widthGraphique = largeurMonde - 2 * positionX;
		this.heightGraphique = hauteurMonde - hauteurMonde/5;
		
		arrayX = new double[NB_COLONNE_GRAPHIQUE];
		arrayY = new double[NB_COLONNE_GRAPHIQUE];
		
		for(int n = 0; n < arrayX.length; n++) {		
			arrayX[n] = DELTA_T_FUSEE * n;
		}
		this.setCouleurPoint(couleurPoint);
		this.graphiqueSortie = new Graphique(positionX, positionY, widthGraphique, heightGraphique, arrayX, arrayY, couleurPoint);
		
	}//fin constructeur
	
	/**
	 * Methode qui va permettre le dessinage du graphique dans la scene d'animation
	 * 
	 * @param g composant graphique qui va permettre le dessinage des elements
	 */
	//Johnatan G
	
	public void paintComponent(Graphics g) {//debut methode

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.black);

		ajusterArrayX();
		changerGraphique();

		dessinerTitresAxes(g2d, mat);
		graphiqueSortie.draw(g2d, mat);
		
	}//fin methode

	/**
	 * Methode qui dessine les titres des axes
	 * @param g2d composant graphique qui va permettre le dessinage des elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	private void dessinerTitresAxes(Graphics2D g2d, AffineTransform mat) {//debut methode
		
		Color cInit = g2d.getColor();
		
		AffineTransform transOrig = g2d.getTransform();
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		
		g2d.drawString(titreGraphique, (int) (getWidth()/8), (int) (getHeight()/10));
		g2d.drawString(titreAxeX, (int) (getWidth()/3), (int) (getHeight() * 0.95));
		
		g2d.rotate(-Math.PI/2, getWidth()/2, getHeight()/2);
		g2d.setColor(Color.black);
		g2d.drawString(titreAxeY, (int) (getWidth()/2.5), (int)(getHeight()/15));
		
		g2d.setColor(cInit);
		g2d.setTransform(transOrig);
		
	}//fin methode
	
	/**
	 * Methode qui met a jour les sorties de l'application
	 * @param tempsEcoule        Le temps ecoule
	 * @param sommeDesForcesX   La somme des forces en X
	 * @param sommeDesForcesY   La somme des forces en Y
	 * @param vitesseX           La vitesse de la fusee en X
	 * @param vitesseY           La vitesse de la fusee en Y
	 * @param accelerationX      L'acceleration de la fusee en X
	 * @param accelerationY      L'acceleration de la fusee en Y
	 * @param masseFusee         La masse de la fusee
	 * @param masseReservoir     La masse du reservoir de la fusee
	 * @param masseBooster       La masse du booster de la fusee
	 * @param resistanceAir      La resistance de l'air de la fusee
	 * @param distanceFuseeTerre La distance de la fusee entre elle et la Terre
	 * @param angleDeviation     L'angle de deviaion de la fusee
	 */
	//Johnatan G
	
	public void updateValeurs(double tempsEcoule, double sommeDesForcesX, double sommeDesForcesY, double vitesseX, double vitesseY, double accelerationX, double accelerationY,
			double masseFusee, double masseReservoir, double masseBooster, double resistanceAir, double distanceFuseeTerre, double angleDeviation) {//debut methode
		
		arrayTempsEcouleFusee.add(tempsEcoule);
		
		arraySommeDesForcesX.add(sommeDesForcesX);
		arraySommeDesForcesY.add(sommeDesForcesY);
		
		arrayVitesseFuseeX.add(vitesseX);
		arrayVitesseFuseeY.add(vitesseY);
		
		arrayAccelerationX.add(accelerationX);
		arrayAccelerationY.add(accelerationY);
		
		arrayMasseFusee.add(masseFusee);
		arrayMasseReservoir.add(masseReservoir);
		arrayMasseBooster.add(masseBooster);
		arrayResistanceAir.add(resistanceAir);
		
		arrayDistanceFuseeTerre.add(distanceFuseeTerre);
		arrayDeviationAngle.add(angleDeviation);
	
		arrayTempsEcouleFusee.trimToSize();
		arrayDistanceFuseeTerre.trimToSize();
		arraySommeDesForcesX.trimToSize();
		arraySommeDesForcesY.trimToSize();
		
		arrayVitesseFuseeX.trimToSize();
		arrayVitesseFuseeY.trimToSize();
		
		arrayAccelerationX.trimToSize();
		arrayAccelerationY.trimToSize();
		
		arrayMasseFusee.trimToSize();
		arrayMasseReservoir.trimToSize();
		arrayMasseBooster.trimToSize();
		arrayResistanceAir.trimToSize();
		arrayDeviationAngle.trimToSize();
		
	}//fin methode

	/**
	 * Methode qui retourne le type de graphique
	 * @return typeGraphique Le type de graphique
	 */
	//Johnatan G
	
	public int getTypeGraphique() {//debut methode
		return typeGraphique;
	}//fin methode

	/**
	 * Methode qui modifie le type de graphique
	 * @param typeGraphique Le type de graphique
	 */
	//Johnatan G
	
	public void setTypeGraphique(int typeGraphique) {//debut methode
		this.typeGraphique = typeGraphique;
	}//fin methode
	
	/**
	 * Methode qui ajuste les graduations en x
	 */
	//Johnatan G
	
	private void ajusterArrayX() {//debut methode
		if(arrayTempsEcouleFusee.size() > NB_COLONNE_GRAPHIQUE) {

			for(int n = 0; n < arrayX.length; n++) {
				arrayX[n] = arrayTempsEcouleFusee.
						get(arrayTempsEcouleFusee.
								size()-1 - NB_COLONNE_GRAPHIQUE + n);
			}
			graphiqueSortie.setArrayX(arrayX);
		}else {
			
			for(int n = 0; n < arrayTempsEcouleFusee.size(); n++) {
				arrayX[n] = arrayTempsEcouleFusee.get(n);
			}
			
			
			
		}
	}//fin methode

	/**
	 * Methode qui ajuste les graduations en y
	 * @param arrayList Les graduations en y
	 */
	//Johnatan G
	
	private void ajusterArrayY(DoubleArrayList arrayList) {//debut methode

		if(arrayTempsEcouleFusee.size() > NB_COLONNE_GRAPHIQUE) {

			for(int n = 0; n < arrayY.length; n++) {

				arrayY[n] = arrayList.
						get(arrayList.
								size()-1 - NB_COLONNE_GRAPHIQUE + n);	
			}		

		}else {

			for(int n = 0; n < arrayList.size(); n++) {

				arrayY[n] = arrayList.get(n);
			}		
		}		

		graphiqueSortie.setArrayY(arrayY);

		repaint();
		
	}//fin methode
	
	/**
	 * Methode qui change le graphique
	 */
	//Johnatan G

	private void changerGraphique() {//debut methode
		
		switch(typeGraphique) {

		case 1: 
			changerTitreGraphique("Somme des forces en X en fonction du temps (dF/dT)", "Somme des forces (en N)");
			ajusterArrayY(arraySommeDesForcesX);	
			break;

		case 2: 
			changerTitreGraphique("Vitesse en X en fonction du temps (dV/dT)", "Vitesse (en m/s)");
			ajusterArrayY(arrayVitesseFuseeX);
			break;

		case 3: 
			changerTitreGraphique("Acceleration en X en fonction du temps (dA/dT)",  "Acceleration (en m/s^2)");
			ajusterArrayY(arrayAccelerationX);	
			break;

		case 4: 
			changerTitreGraphique("Masse de la fusee en fonction du temps (dM/dT)",  "Masse (en kg)");
			ajusterArrayY(arrayMasseFusee);	
			break;

		case 5:
			changerTitreGraphique("Masse du reservoir en fonction du temps (dM/dT)",  "Masse (en kg)");
			ajusterArrayY(arrayMasseReservoir);
			break;

		case 6: 
			changerTitreGraphique("Masse des boosters en fonction du temps (dM/dT)", "Masse (en kg)");
			ajusterArrayY(arrayMasseBooster);	
			break;

		case 7: 
			changerTitreGraphique("Resistance de l'air en fonction du temps (dR/dT)", "Resistance de l'air (en N)");
			ajusterArrayY(arrayResistanceAir);		
			break;
			
		case 8:
			
			changerTitreGraphique("Somme des forces en Y en fonction du temps (dF/dT)", "Somme des forces (en N)");
			ajusterArrayY(arraySommeDesForcesY);
			break;
			
		
		case 9:
			changerTitreGraphique("Vitesse en Y en fonction du temps (dV/dT)", "Vitesse (en m/s)");
			ajusterArrayY(arrayVitesseFuseeY);
			break;
			
		case 10:
			changerTitreGraphique("Acceleration en Y en fonction du temps (dA/dT)", "Acceleration (en m/s^2)");
			ajusterArrayY(arrayAccelerationY);
			break;
			
		case 11:
			changerTitreGraphique("Angle de deviation en fonction du temps (dTeta/dT)", "Angle (en degree)") ;
			ajusterArrayY(arrayDeviationAngle);
			break;
			
		case 12:
			changerTitreGraphique("Distance fusee-Terre en fonction du temps (dH/dT)", "Hauteur (en m)");
			ajusterArrayY(arrayDistanceFuseeTerre);
			break;

		default:
			titreGraphique = "";
			break;

		}
		
	}//fin methode
	
	/**
	 * Methode qui change le titre du graphique
	 * @param titreGraphique Le titre du graphique
	 * @param titreAxesY Le titre de l'axe des y
	 */
	//Johnatan G
	
	private void changerTitreGraphique(String titreGraphique, String titreAxesY) {//debut methode
		
		this.titreAxeX = "Temps Ecoules (en s)";
		this.titreGraphique = titreGraphique;
		this.titreAxeY = titreAxesY;

		
	}//fin methode

	/**
	 * Methode qui retourne la couleur des points du graphique
	 * @return couleurPoint La couleur des points du graphique
	 */
	//Johnatan G
	
	public Color getCouleurPoint() {//debut methode
		return couleurPoint;
	}//fin methode

	/**
	 * Methode qui modifie la couleur des points du graphique
	 * @param couleurPoint La couleur des points du graphique
	 */
	//Johnatan G
	
	public void setCouleurPoint(Color couleurPoint) {//debut methode
		this.couleurPoint = couleurPoint;
	}//fin methode
	

}//fin classe
