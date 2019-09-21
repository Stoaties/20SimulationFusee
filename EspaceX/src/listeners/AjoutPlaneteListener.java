package listeners;

import java.util.EventListener;

import objets.Objet;

/**
 * L'interface AjoutPlaneteListener permet d'enregistrer la masse, le type, le rayon, le rayon d'orbitale, le nom et la position de l'objet céleste qui va être ajouté dans le système solaire
 * @author Ivana Bera
 *
 */
public interface AjoutPlaneteListener extends EventListener {
	public void setMasse(double masse);
	public void setType(int typeObj);
	public void setRayon(double rayon);
	public void setRayonOrbitale(double rayonOrbitale);
	public void setNom(String nom);
	public void setPositionX(int x);
	public void setPositionY(int y);

}
