 
package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modele.ModeleDeDonnees;
import objets.fusee.Fusee;

/**
 * Classe qui permet l'enregistrement et la charge du systeme solaire et de la fusee
 * 
 * @author Corentin Gouanvic
 */
public class Save {
	
	/**
	 * Methode qui sauve le modele de donne
	 * @param directory Le directory
	 * @param md Le modele de donne
	 * @throws IOException
	 */
	public static void saveMd(String directory, ModeleDeDonnees md) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(directory));
		os.writeObject(md);
		os.close();
		
	}
	
	/**
	 * Methode qui charge le modele de donne
	 * @param directory Le directory
	 * @return Le modele de donne
	 * @throws IOException
	 */
	public static ModeleDeDonnees loadMd(String directory) throws IOException {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(directory));
		ModeleDeDonnees md = new ModeleDeDonnees();
		try {
			md = (ModeleDeDonnees) is.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		is.close();
		return md;
	}
	
	/**
	 * Methode qui sauve la fusee
	 * @param directory Le directory
	 * @param fusee La fusee
	 * @throws IOException
	 */
	public static void saveFusee(String directory, Fusee fusee) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(directory));
		os.writeObject(fusee);
		os.close();
	}
	
	/**
	 * Methode qui charge la fusee
	 * @param directory Le directory
	 * @return La fusee
	 * @throws IOException
	 */
	public Fusee loadFusee(String directory) throws IOException{
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(directory));
		Fusee fusee = new Fusee();
		try {
			fusee = (Fusee) is.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		is.close();
		return fusee;
	}
}