
// Classe des Arbres H-Equilibrés de recherche ou AVL 
class AVL<T extends Comparable<T>>{

	public AVL parent;
	public T valeur;
	public AVL filsG;
	public AVL filsD;
	public int deseq;
	
	public AVL(T valeur){
		this.parent = null;
		this.valeur = valeur;
		this.filsG = null;
		this.filsD = null;	
		this.deseq = 0;
	}
	
	public AVL(AVL parent, T valeur, AVL filsG, AVL filsD){
		this.parent = parent;
		this.valeur = valeur;
		this.filsG = filsG;
		this.filsD = filsD;
		this.deseq = 0;
	}
	
	public T recupValeur(){
		return valeur;
	}
	
	//Principe : cette fonction permet de savoir si un sous arbre est une feuille
	//Entrée : un arbre de type AVL
	//Sortie : une valeur booleene Vrai ou Fausse qui indique si un arbre est une feuille
	public boolean estFeuille(AVL arbre){
		boolean resultat = false;
		
		if(arbre.filsG == null && arbre.filsD == null){
			resultat = true;
		}
		return resultat;
	}
	
	//Principe : cette procedure permet de calculer le desequilibre d'un arbre et modifie l'attribu
	//deseq dans la structure de données pour pouvoir retenir la valeur de desequilibre
	//Entrée : un arbre de type AVL
	//Sortie : l'attribut deseq est modifié
	public void calculDesequilibre(AVL arbre){
		int hauteurMaxG = recupLongueurMaxG(arbre);
		int hauteurMaxD = recupLongueurMaxD(arbre);
		int desequilibre = hauteurMaxG - hauteurMaxD;
		
		arbre.deseq = desequilibre;
	}
	
	//Principe : 
	public int profondeur() {
		int resultat;
		
        if (filsG==null) {
            if (filsD==null){
            	resultat = 0;
            } 
            else{
            	resultat = (1+filsD.profondeur());
            } 
        }
        else {
            if (filsD==null){
            	resultat = (1+filsG.profondeur());
            }
            else{
                resultat = (1+java.lang.Math.max(filsD.profondeur(),filsG.profondeur()));
            }
        }        
        return resultat;
    }
	
	public int recupLongueurMaxG(AVL arbre){
		int hauteurMaxG = 1;
		hauteurMaxG += arbre.filsG.profondeur();
		return hauteurMaxG;
	}
	
	public int recupLongueurMaxD(AVL arbre){
		int hauteurMaxD = 1;
		hauteurMaxD += arbre.filsD.profondeur();
		return hauteurMaxD;
	}
	
	public void insertionAVL(AVL arbre){
		
		if(arbre.valeur.compareTo(this.valeur) > 0 ){
			if(this.filsD == null){
				this.filsD = arbre;
			}
			else{
				this.filsD.insertionAVL(arbre);
			}
		}
		else{
			if(this.filsG == null){
				this.filsG = arbre;
			}
			else{
				this.filsG.insertionAVL(arbre);
			}
		}
	}
	
	public void suppressionAVL(){
		
	}

}