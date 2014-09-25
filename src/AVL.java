
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
	
	public void equilibrage(AVL arbre){
		
	}
	
	//Principe : cette procedure permet de calculer le desequilibre d'un arbre ou sous arbre
	//Entrée :
	//Sortie :
	public int calculDesequilibre(){
		//cette variable va contenir le resultat que la fonction doit retourner
		//ce sera un entier correspondant au desequilbre
		int resultat;
		
		if (filsG==null) {
	        if (filsD==null){
	        	resultat = 0; //si le fils gauche et le fils droit sont vide alors le desequilibre sera de 0
	        }
	        else{
	        	//si il n y a pas de fils gauche mais il y a un fils droit alors le desequilibre sera la profondeur du fils droit en negatif
	        	resultat = (-(1 + filsD.profondeur())); 
	        }
	    }
	    else {
	        if (filsD==null){
	        	//si le fils gauche n'est pas null mais le fils droit est null on stock dans 
	        	//la variable resultat la profondeur du fils gauche + 1
	        	resultat = (1 + filsG.profondeur());
	        	}
	        else{
	        	resultat = 1 + (filsG.profondeur() - filsD.profondeur());
	        }
	    }
		
		return resultat;
	}
	
	//Principe : 
	public int profondeur() {
		//cette variable va contenir le resultat que la fonction doit retourner
		//ce sera un entier correspondant au desequilbre
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
	
 ///////////////////////////////////
    
    //fonctions de rotation à gauche et à droite            
    public AVL rotationG() {
    	AVL b ;
        b=this.filsD;
        if(b!=null) {
            this.filsD=b.filsG;
            b.filsG=this;
        }
        return b;
    }
    
    public AVL rotationD() {
    	AVL b ;
        b=this.filsG;
        if(b!=null) {
            this.filsG=b.filsD;
            b.filsD=this;
        }
        return b;
    }
    ///////////////////////////////:
    
    //composées de rotation
    public AVL rotationDG() {
        /* rotation droite autour fils droit et rotation gauche autour racine */
    	AVL b;
        b=this;
        b.filsD=b.filsD.rotationD();
        b=b.rotationG();
        return b;
    }
    
    public AVL rotationGD() {
        /* rotation gauche autour fils gauche et rotation droite autour racine */
    	AVL b;
        b=this;
        b.filsG=b.filsG.rotationG();
        b=b.rotationD();
        return b;
    }
    //////////////////////////////:
	
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