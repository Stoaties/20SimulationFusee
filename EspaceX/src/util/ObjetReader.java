package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import objets.Objet;

/**
 * Classe qui permet de lire des fichiers
 * @author Johnatan G
 *
 */
public class ObjetReader {//debut classe

	private final String NOM_FICHIER;
	private ObjectInputStream ois;
	private ArrayList <Objet> listObjet;
	private int nbObjet = 0;
	
	File fichierTravail;
	
	/**
	 * Constructeur qui permet de lire un fichier
	 * @param nomFichier Le nom du fichier
	 */
	///Johnatan G
	
	public ObjetReader(String nomFichier) {//debut contructeur
		
		NOM_FICHIER = nomFichier;
		ois = null;
		fichierTravail = new File(NOM_FICHIER);	
		
		listObjet = new ArrayList<Objet>();
		
	}//fin constructeur

	/**
	 * Methode qui permet la lecture du fichier
	 */
	public void readFile() {//debut methode

		try {
			ois = new ObjectInputStream(new FileInputStream(fichierTravail));
			listObjet = (ArrayList <Objet>) ois.readObject();
		}   
		catch (ClassNotFoundException e) {
			System.out.println("L'objet lu est d'une classe inconnue");
			e.printStackTrace();
        }
        catch (InvalidClassException e) {
            System.out.println("Les classes utilisées pour l'écriture et la lecture diffèrent!");
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            System.out.println("Fichier  " + fichierTravail.getAbsolutePath() + "  introuvable!");
            System.exit(0);
        }
         
        catch (IOException e) {
           System.out.println("Erreur rencontree lors de la lecture");
            e.printStackTrace();
            System.exit(0);
        }
         
        finally {
            //on exécutera toujours ceci, erreur ou pas
            try { 
                ois.close();
            }
            catch (IOException e) { 
                System.out.println("Erreur rencontrée lors de la fermeture!"); 
            }
        }//fin finally
             
	}//fin methode


	/**
	 * Methode qui retourne la liste d'objets
	 * @return La liste d'objet
	 */
	//Johnatan G
	
	public ArrayList<Objet> getListObjet() {//debut methode
		return listObjet;
	}//fin methode


	/**
	 * Methode qui modifie la liste d'objets
	 * @param listObjet La liste d'objets
	 */
	//Johnatan G
	public void setListObjet(ArrayList<Objet> listObjet) {//debut methode
		this.listObjet = listObjet;
	}//fin methode
	
}//fin classe
