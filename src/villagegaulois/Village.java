package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit
				+ ".\n");
		int indice = marche.trouverEtalLibre();
		if(indice != -1) {
			marche.utiliserEtal(indice, vendeur, produit, nbProduit);
			chaine.append("vendeur " + vendeur.getNom() + " vend des " + produit + " � l'�tal n�"
					+ (indice+1) + ".\n");
		}
		
		return chaine.toString();
		
	}
	
	public String rechercheVendeursProduit(String produit) {
		marche.trouverEtals(produit);
	}
	
	
	
	
	private static class Marche{
		private Etal[] etals;

		public Marche(int nbEtals) {
			super();
			Etal[] newEtals = new Etal[nbEtals];
			for (int i=0;i<nbEtals;i++) {
				newEtals[i] = new Etal();
			}
			this.etals = newEtals;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i=0;i<etals.length;i++) {
				if (!etals[i].isEtalOccupe()) return i;
			}
			
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int size=0;
			int[] iEtal = new int[50];
			int iTemp=0;
			
			
			for (int i=0;i<etals.length;i++) {
				if(etals[i].contientProduit(produit)) {
					size++;
					iEtal[iTemp] = i;
					iTemp++;
				}
			}
			
			Etal[] allEtals = new Etal[size];
			
			for (int i=0;i<size;i++) {
				allEtals[i] = etals[iEtal[i]];
			}
			
			return allEtals;

		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0;i<etals.length;i++) {
				if(etals[i].getVendeur() == gaulois) return etals[i];
			}
			
			return null;
		}
		
		private void afficherMarche() {
			int nbEtalVide=0;
			
			for (int i=0;i<etals.length;i++) {
				if(etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}else {
					nbEtalVide++;
				}
			}
			
			if(nbEtalVide>0) System.out.println("Il reste " + nbEtalVide + " etals non utilis�s dans le march�. \n");
		}
		
		
		
		
	}
}





