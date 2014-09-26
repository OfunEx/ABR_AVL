import java.io.Console;


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
    	AVL T2 = this.filsD.filsG;
    	
    	this.filsD.filsG = this;
    	this.filsD = T2;
    	
    	return this.filsD;
    }
    
    public AVL rotationD() {
    	
    	AVL T2 = this.filsG.filsD;
    	
    	(this.filsG).filsD = this;
    	this.filsG = T2;
    	
    	return this.filsG;
    	
    }


	//Principe: On insère l'élément comme dans un ABR et ensuite on vérifie l'équilibre de tout les AVL parcouru
	//Entrée: l'élément a inseré dans l'AVL
	//Sortie: L'AVL avec l'élement inséré
    public AVL insertionAVL(T element){

    	AVL res = new AVL<T>(element);
    	
    	
    	//On insère normalement l'élément dans l'arbre (comme un ABR)
    	
    	if(element.compareTo((T) this.valeur) < 0 && this.filsG == null)
    		this.filsG = res;
    	else if(element.compareTo((T) this.valeur) > 0 && this.filsD == null)
    		this.filsD = res;

    	else if(element.compareTo((T) this.valeur) < 0)
    		this.filsG.insertionAVL(element);
    	else
    		this.filsD.insertionAVL(element);

    	// On calcule le facteur de désèquilibre de chaque ancêtre de ce noeud
    	this.deseq = this.calculDesequilibre();
    	
    	//si le noeud est désèquilibré il y a quatre cas : 

    	//Rotation droite-droite:
    	if(this.deseq > 1 && element.compareTo((T) this.filsG.valeur) < 0)
    		this.rotationD();
		//Rotation gauche-gauche:
		else if(this.deseq < -1 && element.compareTo((T) this.filsD.valeur) > 0)
			this.rotationG();
		//Rotation gauche-droite:
		else if(this.deseq > 1 && element.compareTo((T) this.filsG.valeur) > 0)
		{
			this.filsG.rotationG();
			this.rotationD();
		}	
		//Rotation droite-gauche:
		else if(this.deseq < -1 && element.compareTo((T) this.filsD.valeur) < 0)
		{
			this.filsD.rotationD();
			this.rotationG();
    	}
    	
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
	
	//Principe : On recherche d'abord l'arbre de l'élément à supprimer dans l'arbre. Si l'élément à supprimer est une feuille on la supprime.
	// Si l'arbre possède un fils, on le remplace par son fils. Si l'arbre possède 2 fils, on prend le plus petit élément de son sous arbre droit
	// et on le place à la place de la racine de l'élément à supprimer, en pensant bien à recoller tout les noeuds de l'arbre. 
	// On recalcule ensuite le déséquilibre de l'arbre et on applique son rééquilibrage au besoin.
	//Entrée : un arbre AVL, et un élément 
	//Sortie : l'arbre AVL avec une occurence de l'élément enlevé de l'arbre.
	public void suppressionAVL(T element){
		//on recherche l'element à supprimer
		AVL eltrouve= this.rechercheNoeud(element);
			
			
		// Si l'element recherché est une feuille on la supprime
		if(estFeuille(eltrouve)){
				
			eltrouve.parent = null;
			eltrouve.valeur = null;
			eltrouve.filsG = null;
			eltrouve.filsD = null;
				
				
		}
			
		else if(eltrouve.filsG==null){
			//si x n'a qu'un fils (ici l'arbre gauche vide), on le remplace par son fils
			AVL tempo = new AVL(eltrouve.parent, (T)eltrouve.filsD.valeur, eltrouve.filsD.filsG, eltrouve.filsD.filsD);
			eltrouve.parent = eltrouve.parent;
			eltrouve.valeur = (T)eltrouve.filsD.valeur;
			eltrouve.filsG = eltrouve.filsD.filsG;
			eltrouve.filsD = eltrouve.filsD.filsG;
				
				
		}
		else if(eltrouve.filsD==null){
			//si x n'a qu'un fils (ici l'arbre droit vide), on le remplace par son fils
			AVL tempo = new AVL(eltrouve.parent, (T)eltrouve.filsG.valeur, eltrouve.filsG.filsG, eltrouve.filsG.filsD);
			eltrouve.parent = tempo.parent;
			eltrouve.valeur = (T)tempo.valeur;
			eltrouve.filsG = tempo.filsG;
			eltrouve.filsD = tempo.filsD;
				
		}
		else{
			//si x a 2 fils, on le remplace par le plus petit element de son sous arbre droit
			AVL tempo2 = eltrouve.filsD; // parcours vers le coté le plus à gauche du sous arbre droit de l'arbre de l'element a supprimer.
			while(!estFeuille(tempo2)){
				tempo2 = tempo2.filsG; 
			}
			//On modifie l'arbre avec le plus petit element du sous arbre droit placé a la racine de l'element supprimé.
			AVL tempo = new AVL(eltrouve.parent, tempo2.valeur, eltrouve.filsG, eltrouve.filsD);
				
				
			eltrouve.parent = tempo.parent;
			eltrouve.valeur = (T)tempo.valeur;
			eltrouve.filsG = tempo.filsG;
			eltrouve.filsD = tempo.filsD;
			tempo2 = tempo2.filsD; // On recolle l'arbre de racine eltrouve avec l'arbre de l'element remplacé 	
				
		}
		parcoursDesequilibre(this); //calcul des desequilibres
		
		//rééquilibrage si necessaire
		this.equilibrage();
	}
	
	// Principe : On regarde si la racine de l'arbre est feuille, et si il l'est on renvoie l'arbre sans rien toucher.
	// sinon si la racine a un déséquilibre différent de zero, on regarde si la racine et déséquilibré et on applique les 
	// rotations dans ce cas. Tant qu'on a pas atteint tout l'arbre, la fonction se poursuit récursivement dans les sous arbres. On renvoie l'arbre équilibré a la fin.
	//Entrée : un arbre AVL non vide
	//Sortie : l'arbre AVL équilibré
	public AVL equilibrage() {
		AVL tmp = null;
		if (estFeuille(tmp)){ // si l'arbre est une feuille pas besoin de rééquilibrage, on renvoir l'arbre intact
			tmp = this;
		}
		
		// sinon on verifie le déséquilibre de la racine de l'arbre
		else { 
			tmp = this;
			int desequilibre = tmp.deseq;
			if (desequilibre >=1){ //Si on a un déséquilibre positif sur la racine suivi de son sous arbre gauche négatif l'arbre est déséquilibré 
				if (tmp.filsG != null) {
					if (tmp.filsG.deseq<0){  //on applique donc la rotation approprié : ici, la rotation gauche droite
						tmp.filsG = tmp.filsG.rotationG();
					tmp = tmp.rotationD();
					}
				}
			}
			if (desequilibre <=1){ //Si on a un déséquilibre négatif sur la racine suivi de son sous arbre gauche positif l'arbre est déséquilibré 
				if (tmp.filsD != null){
					if (tmp.filsD.deseq >0){  //on applique donc la rotation approprié : ici, la rotation droite gauche
						tmp.filsD = tmp.filsD.rotationD();
					}
					tmp = tmp.rotationG();
				}
			}
			// tant qu'on a pas atteint le bout de l'arbre, on applique une récursivité de la fonction sur ses 2 sous arbres
			if (tmp.filsG != null){  
				tmp.filsG = tmp.filsG.equilibrage();
			}
			if (tmp.filsD != null) {
				tmp.filsD = tmp.filsD.equilibrage();
			}
			
		}
		return(tmp);
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