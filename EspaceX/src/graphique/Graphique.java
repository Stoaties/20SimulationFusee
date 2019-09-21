package graphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import util.Constantes;
import util.Dessinable;

/**
 * Classe qui permet de dessiner les graphiques
 * @author Johnatan G
 *
 */
public class Graphique implements Dessinable, Constantes {//debut classe 

	//Variables de la position et des dimensions des graphiques 
	private double positionX, positionY, width, height;

	//Variables des maximums en X et en Y
	private double xMin, xMax, yMin, yMax;

	//Variables qui gere le quadriallage des graphiques
	private Rectangle2D.Double rectGraphique;

	//Variables qui gere la distance entre chaque ligne en X et en Y
	private final double DISTANCE_COLONNE_X, DISTANCE_LIGNE_Y;

	//Variables qui gerent les graduatione en X et en Y
	private double[] arrayX, arrayY;

	//Variable qui gere les points
	private Ellipse2D.Double point;
	private Path2D.Double ligne;

	//Variable qui gere les valeurs de Y
	private double valeurParY = 1;

	//Variables qui gerent la plus petite valeur X et Y
	private double minimumArrayY, maximumArrayY;
	
	//Variable qui gere la couleur des points
	private Color couleurPoint;

	/**
	 * Constructeur qui permet le dessinage des graphiques
	 * @param positionX  La position en X
	 * @param positionY  La position en Y
	 * @param width      La longueur
	 * @param height     La hauteur
	 * @param arrayX     Le tableau en x
	 * @param arrayY     Le tableau en y
	 * @param colorPoint La couleur des points
	 */
	//Johnatan G
	
	public Graphique(double positionX, double positionY, double width, double height, double[] arrayX, double[] arrayY, Color colorPoint) {//debut constructeur

		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;

		this.rectGraphique = new Rectangle2D.Double(this.positionX, this.positionY, this.width, this.height);

		this.xMin = 0;
		this.xMax = width;

		this.yMin = -height/2;
		this.yMax = height/2;

		this.DISTANCE_COLONNE_X = width/NB_COLONNE_GRAPHIQUE; //Determine la distance entre chaque colonne
		this.DISTANCE_LIGNE_Y = height/NB_LIGNE_GRAPHIQUE; //Determine la distance entre chaque ligne

		this.arrayX = arrayX;
		this.arrayY = arrayY;

		this.couleurPoint = colorPoint;
		
		ligne = new Path2D.Double();

	}//fin constructeur

	/**
	 * Methode qui va permettre le dessinage du nuage
	 * 
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param aff La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	public void draw(Graphics2D g2d, AffineTransform aff) {//debut methode
		
		Color cInit = g2d.getColor();

		g2d.setColor(Color.black);
		g2d.draw(aff.createTransformedShape(rectGraphique));

		AffineTransform matLocale = new AffineTransform(aff);
		matLocale.translate(positionX, 0); //Pour dire que la position 0 commence dans le rectangle

		dessinerQuadrille(g2d, matLocale);

		dessinerValeursAxes(g2d, matLocale);

		if(arrayY.length != 0) {

			ajusterValeursParY();
			dessinerPoints(g2d, matLocale);

		}


		g2d.setColor(cInit);


	}//fin methode

	/**
	 * Methode qui ajuste les valeurs en y
	 */
	//Johnatan G
	
	private void ajusterValeursParY() {//debut methode

		minimumArrayY = arrayY[0];
		maximumArrayY = arrayY[0];


		for(int n = 0; n < arrayY.length; n++) {

			minimumArrayY = Math.min(minimumArrayY, arrayY[n]);
			maximumArrayY = Math.max(maximumArrayY, arrayY[n]);
		}


		if(minimumArrayY >= 0) {

			valeurParY = maximumArrayY/(NB_LIGNE_GRAPHIQUE/2);

		}else if(maximumArrayY <= 0) {

			valeurParY = minimumArrayY/(NB_LIGNE_GRAPHIQUE/2);

		}else {

			valeurParY = (maximumArrayY - minimumArrayY)/ NB_LIGNE_GRAPHIQUE;

		}

		if(valeurParY != 0) {
			valeurParY = Math.abs(valeurParY) * 1.10;

			for(int n = 0; n < arrayY.length; n++) {

				arrayY[n] /= valeurParY;

			}
		}
		
	}//fin methode

	/**
	 * Methode qui dessine les points
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	private void dessinerPoints(Graphics2D g2d, AffineTransform mat) {//debut methode

		AffineTransform matLocale = new AffineTransform(mat);
		Color cInit = g2d.getColor();

		g2d.setColor(this.couleurPoint);

		ligne.moveTo( 0 * this.DISTANCE_COLONNE_X, arrayY[0]);
		
		for (int n = 0 ; n < arrayY.length; n++) {

			if(arrayX[n] < 12 && arrayY[n] == 0) {

			}else {
				
				ligne = new Path2D.Double();
				ligne.moveTo( n * this.DISTANCE_COLONNE_X, arrayY[n]);
				
				point = new Ellipse2D.Double( n * this.DISTANCE_COLONNE_X, arrayY[n], SIZE_POINT, SIZE_POINT);
				
				ligne.lineTo( n * this.DISTANCE_COLONNE_X,  arrayY[n]);
				
				g2d.fill(matLocale.createTransformedShape(point));
				g2d.draw(matLocale.createTransformedShape(ligne));
				
				
			}


		}


		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui dessine les axes
	 * @param g2d Composant graphique qui va permettre le dessinage des elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G

	private void dessinerValeursAxes(Graphics2D g2d, AffineTransform mat) {//debut methode

		Color cInit = g2d.getColor();
		g2d.setColor(Color.red);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 7));

		for(int n = (int) xMin; n < (NB_COLONNE_GRAPHIQUE); n++) {

			g2d.drawString(""+ String.format("%.2f", arrayX[n]), (int) ( (n * this.DISTANCE_COLONNE_X + positionX) * mat.getScaleX() ), (int) ( (height/2 - positionY/4) * mat.getScaleX()));	
		}

		for(double n = yMin; n <= (yMax); n++) {

			g2d.drawString(""+ String.format("%.0f", n * valeurParY ), (int) ( (positionX * 0.9 ) * mat.getScaleX() ), (int) ( (2 * height/5 - n - positionY/2.4) * mat.getScaleX()));			

		}



		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui va permettre le dessinage du quadrillage dans la scene d'animation
	 * 
	 * @param g2d composant graphique qui va permettre le dessinage des elements
	 * @param mat La matrice de transformation qui va permettre le passage des unites pixels en unites reelles
	 */
	//Johnatan G
	
	private void dessinerQuadrille(Graphics2D g2d, AffineTransform mat) {//debut methode

		Color cInit = g2d.getColor();
		AffineTransform matLocale = new AffineTransform(mat);

		g2d.setColor(Color.black);

		Path2D.Double quadrilleeX = new Path2D.Double();
		Path2D.Double quadrilleeY = new Path2D.Double();

		double x = xMin;
		double y = yMin;

		for(double k = 0; k <= NB_COLONNE_GRAPHIQUE; k++) {

			quadrilleeX.moveTo(x, yMin);
			quadrilleeX.lineTo(x, yMax);
			x += this.DISTANCE_COLONNE_X;

		}

		for(double k = 0; k <= NB_LIGNE_GRAPHIQUE; k++ ) {

			quadrilleeY.moveTo(xMin, y);
			quadrilleeY.lineTo(xMax, y);
			y += this.DISTANCE_LIGNE_Y;	

		}

		g2d.draw(matLocale.createTransformedShape(quadrilleeX));
		g2d.draw(matLocale.createTransformedShape(quadrilleeY));

		g2d.setColor(cInit);

	}//fin methode

	/**
	 * Methode qui retourne le tableau en x
	 * @return arrayX Le tableau en x
	 */
	//Johnatan G
	
	public double[] getArrayX() {//debut methode
		return arrayX;
	}//fin methode

	/**
	 * Methode qui modifie le tableau en x
	 * @param arrayX Le tableau en x
	 */
	//Johnatan G
	
	public void setArrayX(double[] arrayX) {//debut methode
		this.arrayX = arrayX;
	}//fin methode

	/**
	 * Methode qui retourne le tableau en y
	 * @return arrayY Le tableau en y
	 */
	//Johnatan G
	
	public double[] getArrayY() {//debut methode
		return arrayY;
	}//fin methode

	/**
	 * Methode qui modifie le tableau en y
	 * @param arrayY Le tableau en y
	 */
	//Johnatan G
	
	public void setArrayY(double[] arrayY) {//debut methode
		this.arrayY = arrayY;
	}//fin methode

}//fin classe 
