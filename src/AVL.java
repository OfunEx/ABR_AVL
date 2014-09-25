
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
	        	resultat = (1 + filsG.profondeur()) - (1 + filsD.profondeur());
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


	//Principe: On insère l'élément comme dans un ABR et ensuite on vérifie l'équilibre de tout les AVL parcouru
	//Entrée: un AVL et l'élément a inseré
	//Sortie: L'AVL avec l'élement inséré
    public AVL insertionAVL(AVL arbre,T element){
        
    	AVL res; //variable contenant l'AVL qu'on va retourner

    	res = arbre;


    	//On insère normalement l'élément dans l'arbre (comme un ABR)

    	if(arbre == null)
    		res = new AVL<T>(element);

    	else if(element.compareTo((T) arbre.valeur) < 0)
    		arbre.filsG = insertionAVL(arbre.filsG, element);
    	else
    		arbre.filsD = insertionAVL(arbre.filsD, element);

    	// On calcule le facteur de désèquilibre de chaque ancêtre de ce noeud
    	arbre.deseq = arbre.calculDesequilibre();

    	//si le noeud est désèquilibré il y a quatre cas : 

    	//Rotation droite-droite:
    	if(arbre.deseq > 1 && element.compareTo((T) arbre.filsG) < 0)
			arbre.rotationD();
		//Rotation gauche-gauche:
		else if(arbre.deseq < -1 && element.compareTo((T) arbre.filsD) > 0)
			arbre.rotationG();
		//Rotation droite-gauche:
		else if(arbre.deseq > 1 && element.compareTo((T) arbre.filsG) > 0)
			arbre.rotationDG();
		//Rotation gauche-droite:
		else if(arbre.deseq < -1 && element.compareTo((T) arbre.filsD) < 0)
			arbre.rotationGD();


    	return res;
    }

	
	public void parcoursDesequilibre(AVL arbre){
		if(filsG==null){
			if(filsD==null){
				this.deseq = 0;
			}
			else{
				this.deseq = arbre.calculDesequilibre();
				arbre.parcoursDesequilibre(filsD);
			}
		}
		else{
			if(filsD==null){
				this.deseq = arbre.calculDesequilibre();
				arbre.parcoursDesequilibre(filsG);
			}
			else{
				this.deseq = arbre.calculDesequilibre();
				arbre.parcoursDesequilibre(filsG);
				arbre.parcoursDesequilibre(filsD);				
			}
		}		
	}
	
	public void suppressionAVL(T element){
		//recherche element
		AVL eltrouve= this.rechercheNoeud(element);
		
		if(estFeuille(eltrouve)){
			//on supprime
			eltrouve.parent = null;
			eltrouve.valeur = null;
			eltrouve.filsG = null;
			eltrouve.filsD = null;
		}
		
		else if(eltrouve.filsG==null){
			//si x n'a qu'un fils, on le remplace par son fils
			AVL tempo = new AVL(eltrouve.parent, (T)eltrouve.filsD.valeur, eltrouve.filsD.filsG, eltrouve.filsD.filsD);
			eltrouve.parent = eltrouve.parent;
			eltrouve.valeur = (T)eltrouve.filsD.valeur;
			eltrouve.filsG = eltrouve.filsD.filsG;
			eltrouve.filsD = eltrouve.filsD.filsG;
		}
		else if(eltrouve.filsD==null){
			//si x n'a qu'un fils, on le remplace par son fils
			AVL tempo = new AVL(eltrouve.parent, (T)eltrouve.filsG.valeur, eltrouve.filsG.filsG, eltrouve.filsG.filsD);
			eltrouve.parent = tempo.parent;
			eltrouve.valeur = (T)tempo.valeur;
			eltrouve.filsG = tempo.filsG;
			eltrouve.filsD = tempo.filsD;
		}
		else{
			//si x a 2 fils, on le remplace par le plus petit element de son sous arbre droit
			AVL tempo2 = eltrouve.filsD;
			while(!estFeuille(tempo2)){
				tempo2 = tempo2.filsG;
			}

			AVL tempo = new AVL(eltrouve.parent, tempo2.valeur, eltrouve.filsG, eltrouve.filsD);
			
			
			eltrouve.parent = tempo.parent;
			eltrouve.valeur = (T)tempo.valeur;
			eltrouve.filsG = tempo.filsG;
			eltrouve.filsD = tempo.filsD;
			tempo2 = tempo2.filsD; // On recolle l'arbre avec l'arbre de l'element remplacé 
			
		}
	}
	
	//Principe: On recherche un élément à partir d'un arbre et on recherche récursivement sur tout les sous arbres gauche et droite
	//Entrée: un élément qui doit exister dans l'AVL
	//Sortie: l'AVL contenant comme valeur l'élément recherché


	public AVL rechercheNoeud(T element){
		AVL resultat = null;
		
		if(this.valeur.compareTo(element) == 0){
			resultat.parent = this.parent;
			resultat.valeur = (T)this.valeur;
			resultat.filsG = this.filsG;
			resultat.filsD = this.filsD;
			
		}
		else{
			this.filsG.rechercheNoeud(element);
			this.filsD.rechercheNoeud(element);
		}
		
		return resultat;
	}

}