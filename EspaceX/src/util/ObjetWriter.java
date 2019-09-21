package util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import objets.Objet;

/**
 * Classe qui permet d'ecrire des fichiers
 * @author Melie Leclerc
 *
 */
public class ObjetWriter {//debut classe

	private final String NOM_FICHIER;
	private ObjectOutputStream oos;
	private File fichier;
	
	/**
	 * Constructeur qui permet d'ecrire un fichier
	 * @param nomFichier Le nom du fichier
	 */
	//Melie L
	public ObjetWriter(String nomFichier) {//debut constructeur

		NOM_FICHIER = nomFichier;
		fichier = new File(NOM_FICHIER);

		oos = null;
	}//fin constructeur

	/**
	 * Methode qui permet d'ecrire un fichier
	 * @param listObjet La liste d'objets
	 */
	//Melie L
	public void writeObject(ArrayList <Objet> listObjet) {//debut methode

		try {

			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(listObjet);

			System.out.println("\nLe fichier " + fichier.getAbsolutePath() + " est pret pour la lecture");
		}catch(IOException e) {
			System.out.println("Erreur à l'écriture:");
			e.printStackTrace();
		}finally {
			//on exécutera toujours ceci, erreur ou pas
			try {
				oos.close();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null,"Erreur rencontrée lors de la fermeture!"); 
			}


		}
	}//fin methode
	}//fin classe
