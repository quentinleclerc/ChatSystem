package model;

import java.io.Serializable;
import java.net.InetAddress;
/**
 * <b>model.MessageUser est la classe exceptionnelle qui permet de standardiser les échanges multicast.</b>

 * <p>
 * Elle comporte : 

 * <ul>

 * <li>Un pseudo unique pour être le plus beaugoss.</li>

 * <li>Un statut personnalisable.</li>

 * <li>L'IP et le port d'ecoute.</li>
 * 
 * <li>Et encore plus...</li>

 * </ul>

 * </p>

 * <p>

 * </p>
 * @author Luis RODRIGUEZ VALLEJO™®
 * @contact lrodrigu@etud.insa-toulouse.fr
 * @version 1.0.42.2
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Le pseudo du user
	 */
	private  String pseudo;
	
	/**
	 * Statut de type : Content, Vive les vacances, J'aime le Botlan :-) 
	 */
	private String statut="";
	
	@Override
	public String toString() {
		return "model.MessageUser [pseudo=" + pseudo + ", statut=" + statut + ", IP="
				+ IP + ", port=" + port + ", etat=" + etat + "]";
	}


	/**
	 * L'adresse IP du user
	 */
	private  InetAddress IP;
	
	/**
	 * Le port du user
	 */
	private  int port;

	public enum typeConnect {
		  CONNECTED,
		  DECONNECTED;
	}
	
	
	/**
	 * Enumeration qui indique l'état (Connecté ou deconnecté) du user
	 */
	private typeConnect etat; 
	
	/***
	 * 
	 * @param pseudo Pseudo que les autres utilisateurs verront
	 * @param iP L'adresse IP du user
	 * @param port Le port d'ecoute du user
	 * @param etat CONNECTION ou DECONNECTION
	 */
	public User(String pseudo, InetAddress iP, int port, typeConnect etat) {
		this.pseudo = pseudo;
		this.IP = iP;
		this.port = port;
		this.etat=etat;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public InetAddress getIP() {
		return IP;
	}

	public int getPort() {
		return port;
	}

	public typeConnect getEtat() {
		return etat;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public void setEtat(typeConnect etat) {
		this.etat = etat;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return pseudo.equals(user.pseudo);

	}

	@Override
	public int hashCode() {
		return pseudo.hashCode();
	}
}


