package aapplication.scene;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Classe qui lit les images
 * 
 * @author Ivana Bera
 *
 */

public class LecteurImage {
	private URL urlMercure;
	private URL urlVenus;
	private URL urlTerre;
	private URL urlMars;
	private URL urlJupiter;
	private URL urlSaturne;
	private URL urlUranus;
	private URL urlNeptune;

	private BufferedImage img = null;

	private BufferedImage imgMercure = null;
	private BufferedImage imgVenus = null;
	private BufferedImage imgTerre = null;
	private BufferedImage imgMars = null;
	private BufferedImage imgJupiter = null;
	private BufferedImage imgSaturne = null;
	private BufferedImage imgUranus = null;
	private BufferedImage imgNeptune = null;

	public LecteurImage() {
		urlMercure = getClass().getClassLoader().getResource("Texture_Mercure.png");
		urlVenus = getClass().getClassLoader().getResource("Texture_Venus_Transparent_Rogner.png");
		urlTerre = getClass().getClassLoader().getResource("Texture_Terre_Transparent_Rogner.jpg");
		urlMars = getClass().getClassLoader().getResource("Texture_Mars.jpg");
		urlJupiter = getClass().getClassLoader().getResource("Texture_Jupiter_Transparent_Rogner.jpg");
		urlSaturne = getClass().getClassLoader().getResource("Texture_Saturne_Sans_Rings_Transparent_Rogner.jpg");
		urlUranus = getClass().getClassLoader().getResource("Texture_Uranus_Transparent_Rogner.jpg");
		urlNeptune = getClass().getClassLoader().getResource("Texture_Neptune_Transparent_Rogner.jpg");

		// Mercure
		if (urlMercure == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Mercure.png introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlMercure);
			imgMercure = img;

		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Venus
		if (urlVenus == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Venus_Transparent.png introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlVenus);
			imgVenus = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Terre
		if (urlTerre == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Terre_Transparent.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlTerre);
			imgTerre = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Mars
		if (urlMars == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Mars.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlMars);
			imgMars = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Jupiter
		if (urlJupiter == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Jupiter_Transparent.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlJupiter);
			imgJupiter = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Saturne
		if (urlSaturne == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Saturne_Sans_Rings_Transparent.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlSaturne);
			imgSaturne = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Uranus
		if (urlUranus == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Uranus_Transparent.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlUranus);
			imgUranus = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}

		// Neptune
		if (urlNeptune == null) {
			JOptionPane.showMessageDialog(null, "Fichier Texture_Neptune_Transparent.jpg introuvable");
			System.exit(0);
		}
		try {
			img = ImageIO.read(urlNeptune);
			imgNeptune = img;
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}
	}

	/**
	 * Methode qui donne acces a l'image de Mercure
	 * @return l'image de Mercure
	 */
	public BufferedImage getImgMercure() {
		return imgMercure;
	}

	/**
	 * Methode qui donne acces a l'image de Venus
	 * @return l'image de Venus
	 */
	public BufferedImage getImgVenus() {
		return imgVenus;
	}

	/**
	 * Methode qui donne acces a l'image de la Terre
	 * @return l'image de la Terre
	 */
	public BufferedImage getImgTerre() {
		return imgTerre;
	}

	/**
	 * Methode qui donne acces a l'image de Mars
	 * @return l'image de Mars
	 */
	public BufferedImage getImgMars() {
		return imgMars;
	}

	/**
	 * Methode qui donne acces a l'image de Jupiter
	 * @return l'image de Jupiter
	 */
	public BufferedImage getImgJupiter() {
		return imgJupiter;
	}

	/**
	 * Methode qui donne acces a l'image de Saturne
	 * @return l'image de Saturne
	 */
	public BufferedImage getImgSaturne() {
		return imgSaturne;
	}

	/**
	 * Methode qui donne acces a l'image d'Uranus
	 * @return l'image d'Uranus
	 */
	public BufferedImage getImgUranus() {
		return imgUranus;
	}

	/**
	 * Methode qui donne acces a l'image de Neptune
	 * @return l'image de Neptune
	 */
	public BufferedImage getImgNeptune() {
		return imgNeptune;
	}

}
