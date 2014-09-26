import java.io.Console;


// Classe des Arbres H-Equilibr�s de recherche ou AVL 
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
	//Entr�e : un arbre de type AVL
	//Sortie : une valeur booleene Vrai ou Fausse qui indique si un arbre est une feuille
	public boolean estFeuille(AVL arbre){
		boolean resultat = false;
		
		if(arbre.filsG == null && arbre.filsD == null){
			resultat = true;
		}
		return resultat;
	}
	
	//Principe : cette procedure permet de calculer le desequilibre d'un arbre ou sous arbre
	//Entr�e :
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
    
    //fonctions de rotation � gauche et � droite            
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


	//Principe: On ins�re l'�l�ment comme dans un ABR et ensuite on v�rifie l'�quilibre de tout les AVL parcouru
	//Entr�e: l'�l�ment a inser� dans l'AVL
	//Sortie: L'AVL avec l'�lement ins�r�
    public AVL insertionAVL(T element){

    	AVL res = new AVL<T>(element);
    	
    	
    	//On ins�re normalement l'�l�ment dans l'arbre (comme un ABR)
    	
    	if(element.compareTo((T) this.valeur) < 0 && this.filsG == null)
    		this.filsG = res;
    	else if(element.compareTo((T) this.valeur) > 0 && this.filsD == null)
    		this.filsD = res;

    	else if(element.compareTo((T) this.valeur) < 0)
    		this.filsG.insertionAVL(element);
    	else
    		this.filsD.insertionAVL(element);

    	// On calcule le facteur de d�s�quilibre de chaque anc�tre de ce noeud
    	this.deseq = this.calculDesequilibre();
    	
    	//si le noeud est d�s�quilibr� il y a quatre cas : 

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
	
	//Principe : On recherche d'abord l'arbre de l'�l�ment � supprimer dans l'arbre. Si l'�l�ment � supprimer est une feuille on la supprime.
	// Si l'arbre poss�de un fils, on le remplace par son fils. Si l'arbre poss�de 2 fils, on prend le plus petit �l�ment de son sous arbre droit
	// et on le place � la place de la racine de l'�l�ment � supprimer, en pensant bien � recoller tout les noeuds de l'arbre. 
	// On recalcule ensuite le d�s�quilibre de l'arbre et on applique son r��quilibrage au besoin.
	//Entr�e : un arbre AVL, et un �l�ment 
	//Sortie : l'arbre AVL avec une occurence de l'�l�ment enlev� de l'arbre.
	public void suppressionAVL(T element){
		//on recherche l'element � supprimer
		AVL eltrouve= this.rechercheNoeud(element);
			
			
		// Si l'element recherch� est une feuille on la supprime
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
			AVL tempo2 = eltrouve.filsD; // parcours vers le cot� le plus � gauche du sous arbre droit de l'arbre de l'element a supprimer.
			while(!estFeuille(tempo2)){
				tempo2 = tempo2.filsG; 
			}
			//On modifie l'arbre avec le plus petit element du sous arbre droit plac� a la racine de l'element supprim�.
			AVL tempo = new AVL(eltrouve.parent, tempo2.valeur, eltrouve.filsG, eltrouve.filsD);
				
				
			eltrouve.parent = tempo.parent;
			eltrouve.valeur = (T)tempo.valeur;
			eltrouve.filsG = tempo.filsG;
			eltrouve.filsD = tempo.filsD;
			tempo2 = tempo2.filsD; // On recolle l'arbre de racine eltrouve avec l'arbre de l'element remplac� 	
				
		}
		parcoursDesequilibre(this); //calcul des desequilibres
		
		//r��quilibrage si necessaire
		this.equilibrage();
	}
	
	// Principe : On regarde si la racine de l'arbre est feuille, et si il l'est on renvoie l'arbre sans rien toucher.
	// sinon si la racine a un d�s�quilibre diff�rent de zero, on regarde si la racine et d�s�quilibr� et on applique les 
	// rotations dans ce cas. Tant qu'on a pas atteint tout l'arbre, la fonction se poursuit r�cursivement dans les sous arbres. On renvoie l'arbre �quilibr� a la fin.
	//Entr�e : un arbre AVL non vide
	//Sortie : l'arbre AVL �quilibr�
	public AVL equilibrage() {
		AVL tmp = null;
		if (estFeuille(tmp)){ // si l'arbre est une feuille pas besoin de r��quilibrage, on renvoir l'arbre intact
			tmp = this;
		}
		
		// sinon on verifie le d�s�quilibre de la racine de l'arbre
		else { 
			tmp = this;
			int desequilibre = tmp.deseq;
			if (desequilibre >=1){ //Si on a un d�s�quilibre positif sur la racine suivi de son sous arbre gauche n�gatif l'arbre est d�s�quilibr� 
				if (tmp.filsG != null) {
					if (tmp.filsG.deseq<0){  //on applique donc la rotation appropri� : ici, la rotation gauche droite
						tmp.filsG = tmp.filsG.rotationG();
					tmp = tmp.rotationD();
					}
				}
			}
			if (desequilibre <=1){ //Si on a un d�s�quilibre n�gatif sur la racine suivi de son sous arbre gauche positif l'arbre est d�s�quilibr� 
				if (tmp.filsD != null){
					if (tmp.filsD.deseq >0){  //on applique donc la rotation appropri� : ici, la rotation droite gauche
						tmp.filsD = tmp.filsD.rotationD();
					}
					tmp = tmp.rotationG();
				}
			}
			// tant qu'on a pas atteint le bout de l'arbre, on applique une r�cursivit� de la fonction sur ses 2 sous arbres
			if (tmp.filsG != null){  
				tmp.filsG = tmp.filsG.equilibrage();
			}
			if (tmp.filsD != null) {
				tmp.filsD = tmp.filsD.equilibrage();
			}
			
		}
		return(tmp);
	}
		


	//Principe: On recherche un �l�ment � partir d'un arbre et on recherche r�cursivement sur tout les sous arbres gauche et droite
	//Entr�e: un �l�ment qui doit exister dans l'AVL
	//Sortie: l'AVL contenant comme valeur l'�l�ment recherch�


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