package listeners;
/**
 * L'interface ModificationListener permet de modifier le nom, la masse, le rayon et la vitesse en X, Y et Z d'un objet céleste quelquonque dans le système solaire
 * @author Ivana Bera
 *
 */
public interface ModificationListener {
	
	public void modifierNom(String nom);
	public void modifierMasse(double masse);
	public void modifierRayon(double rayon);
	public void modifierVitesseX(double x);
	public void modifierVitesseY(double y);
	public void modifierVitesseZ(double z);

}
