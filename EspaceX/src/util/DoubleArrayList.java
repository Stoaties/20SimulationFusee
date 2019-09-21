package util;
//Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* ArrayList of double primitives.
* @author Jodd Team (jodd.org). All Rights Reserved.
*/

public class DoubleArrayList {

	 private double[] array;
	  private int size;

	  public static int initialCapacity = 10;

	  /**
	   * Construit une liste vide avec une capacité initiale
	   */
	  public DoubleArrayList() {
	    this(initialCapacity);
	  }

	  /**
	   * Construit une liste vide avec une capacite specicifique
	   * @param initialCapacity La capacite initiale
	   */
	  public DoubleArrayList(int initialCapacity) {
	    if (initialCapacity < 0) {
	      throw new IllegalArgumentException("Capacity can't be negative: " + initialCapacity);
	    }
	    array = new double[initialCapacity];
	    size = 0;
	  }

	  /**
	   * Construit une liste contenant les elements specifique de cette liste
	   * Cette liste est instanciee pour avoir une capacite de 110% la capacite de la liste specifique 
	   * @param data La longueur de la liste
	   */
	  public DoubleArrayList(double[] data) {
	    array = new double[(int) (data.length * 1.1) + 1];
	    size = data.length;
	    System.arraycopy(data, 0, array, 0, size);
	  }

	  // ---------------------------------------------------------------- conversion

	  /**
	   * Retourne un tableau contenant tout les elements dans le bon ordre de la liste 
	   * @return Le resultat
	   */
	  public double[] toArray() {
	    double[] result = new double[size];
	    System.arraycopy(array, 0, result, 0, size);
	    return result;
	  }

	  // ---------------------------------------------------------------- methods

	  /**
	   * Retourne un element a une position specifique de la liste
	   * @param index La position de l'element
	   * @return L'element
	   */
	  public double get(int index) {
	    checkRange(index);
	    return array[index];
	  }

	  /**
	   * Retourne le nombre d'elements dans la liste
	   * @return size Le nombre d'elements dans la liste
	   */
	  public int size() {
	    return size;
	  }

	  /**
	   * Enleve l'element a une posision specifique de la liste
	   * Decale tous les elements vers la gauche du tableau
	   *
	   * @param index La position de l'element a enlever
	   * @return La valeur de l'element
	   * @throws UnsupportedOperationException when this operation is not supported
	   * @throws IndexOutOfBoundsException   if the specified index is out of range
	   */
	  public double remove(int index) {
	    checkRange(index);
	    double oldval = array[index];
	    int numtomove = size - index - 1;
	    if (numtomove > 0) {
	      System.arraycopy(array, index + 1, array, index, numtomove);
	    }
	    size--;
	    return oldval;
	  }
	  /**
	   * Enleve tous les elements de la liste entre les positions desirees
	   * Decale tous les elements vers la gauche du tableau
	   * @param fromIndex Le premier element de l'intervalle
	   * @param toIndex Le dernier element de l'intervalle
	   */
	  public void removeRange(int fromIndex, int toIndex) {
	    checkRange(fromIndex);
	    checkRange(toIndex);
	    if (fromIndex >= toIndex) {
	      return;
	    }
	    int numtomove = size - toIndex;
	    if (numtomove > 0) {
	      System.arraycopy(array, toIndex, array, fromIndex, numtomove);
	    }
	    size -= (toIndex - fromIndex);
	  }

	  /**
	   * Remplace un element specifique de la liste par un nouveau
	   *
	   * @param index   La position de l'element a changer
	   * @param element Le nouvel element
	   * @return l'ancien element
	   */
	  public double set(int index, double element) {
	    checkRange(index);
	    double oldval = array[index];
	    array[index] = element;
	    return oldval;
	  }

	  /**
	   * Ajout un nouvel element dans la liste
	   * @param element L'element
	   */
	  public void add(double element) {
	    ensureCapacity(size + 1);
	    array[size++] = element;
	  }

	  /**
	   * Ajoute l'element a une posision specifique de la liste
	   * Decale tous les elements vers la droite du tableau
	   *
	   * @param index   La position ou on ajoute l'element
	   * @param element L'element a ajouter
	   */
	  public void add(int index, double element) {
	    checkRangeIncludingEndpoint(index);
	    ensureCapacity(size + 1);
	    int numtomove = size - index;
	    System.arraycopy(array, index, array, index + 1, numtomove);
	    array[index] = element;
	    size++;
	  }

	  /**
	   *  Ajoute tous les elements dans la liste
	   *  @param data La grandeur de la liste
	   */
	  public void addAll(double[] data) {
	    int dataLen = data.length;
	    if (dataLen == 0) {
	      return;
	    }
	    int newcap = size + (int) (dataLen * 1.1) + 1;
	    ensureCapacity(newcap);
	    System.arraycopy(data, 0, array, size, dataLen);
	    size += dataLen;
	  }

	  /**
	   *  Ajoute tous les elements de la liste a la position desiree
	   *  @param index La postion
	   *  @param data La grandeur de la liste
	   */
	  public void addAll(int index, double[] data) {
	    int dataLen = data.length;
	    if (dataLen == 0) {
	      return;
	    }
	    int newcap = size + (int) (dataLen * 1.1) + 1;
	    ensureCapacity(newcap);
	    System.arraycopy(array, index, array, index + dataLen, size - index);
	    System.arraycopy(data, 0, array, index, dataLen);
	    size += dataLen;
	  }

	  /**
	   *Enleve tous les elements de la liste
	   */
	  public void clear() {
	    size = 0;
	  }

	  // ---------------------------------------------------------------- search

	  /**
	   * Retourne vrai si la liste contient un element specifique
	   * @param data La longueur de la liste
	   * @param delta L'intervalle
	   * @return vrai si la liste contient un element specifique
	   * @return faux si la liste ne contient pas un element specifique
	   */
	  public boolean contains(double data, double delta) {
	    for (int i = 0; i < size; i++) {
	      if (Math.abs(array[i] - data) <= delta) {
	        return true;
	      }
	    }
	    return false;
	  }


	  /**
	   * Trouve la premiere instance l'objet en parametre
	   * @param data La longueur de la liste
	   * @param delta L'intervalle
	   * @return La position
	   * @return -1
	   */
	  public int indexOf(double data, double delta) {
	    for (int i = 0; i < size; i++) {
	      if (Math.abs(array[i] - data) <= delta) {
	        return i;
	      }
	    }
	    return -1;
	  }

	  /**
	   * 
	   * Retourne la position de la dernière occurrence de l'objet spécifié dans cette liste
	   * @param data La longueur de la liste
	   * @param delta L'intervalle*
	   * @return La position
	   * @return -1
	   */
	  public int lastIndexOf(double data, double delta) {
	    for (int i = size - 1; i >= 0; i--) {
	      if (Math.abs(array[i] - data) <= delta) {
	        return i;
	      }
	    }
	    return -1;
	  }

	  /**
	   * Verifie que la liste est vide
	   * @return 0
	   */
	  public boolean isEmpty() {
	    return size == 0;
	  }



	  // ---------------------------------------------------------------- capacity

	  /**
	   * Augmente la grandeur de la liste, si necessaire, pour s'assurer qu'elle peut contenir au moins le nombre d'elements specifie par l'argument de capacite minimale
	   *@param mincap La capacite minimale
	   */
	  public void ensureCapacity(int mincap) {
	    if (mincap > array.length) {
	      int newcap = ((array.length * 3) >> 1) + 1;
	      double[] olddata = array;
	      array = new double[newcap < mincap ? mincap : newcap];
	      System.arraycopy(olddata, 0, array, 0, size);
	    }
	  }

	  /**
	   * Reduit la capacite de la liste a la position du dernier element
	   */
	  public void trimToSize() {
	    if (size < array.length) {
	      double[] olddata = array;
	      array = new double[size];
	      System.arraycopy(olddata, 0, array, 0, size);
	    }
	  }

	  // ---------------------------------------------------------------- serializable

	  /**
	   * Ecrit un objet
	   * @param out
	   * @throws IOException
	   */
	  private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();
	    out.writeInt(array.length);
	    for (int i = 0; i < size; i++) {
	      out.writeDouble(array[i]);
	    }
	  }

	  /**
	   * Lit un objet
	   * @param in
	   * @throws IOException
	   * @throws ClassNotFoundException
	   */
	  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();
	    array = new double[in.readInt()];
	    for (int i = 0; i < size; i++) {
	      array[i] = in.readDouble();
	    }
	  }
                            
	  // ---------------------------------------------------------------- privates

	  /**
	   * Verifie l'intervalle de la liste
	   * @param index La position
	   */
	  private void checkRange(int index) {
	    if (index < 0 || index >= size) {
	      throw new IndexOutOfBoundsException("Index should be at least 0 and less than " + size + ", found " + index);
	    }
	  }

	  /**
	   * Verifie l'intervalle de la liste
	   * @param index La position
	   */
	  private void checkRangeIncludingEndpoint(int index) {
	    if (index < 0 || index > size) {
	      throw new IndexOutOfBoundsException("Index should be at least 0 and at most " + size + ", found " + index);
	    }
	  }
	
}
