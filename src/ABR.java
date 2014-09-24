
public class ABR<T extends Comparable<T>>{
	public ABR parent;
	public T valeur;
	public ABR filsG;
	public ABR filsD;
	
	public ABR(T valeur){
		this.parent = null;
		this.valeur = valeur;
		this.filsG = null;
		this.filsD = null;	
	}
	
	public ABR(ABR parent, T valeur, ABR filsG, ABR filsD){
		this.parent = parent;
		this.valeur = valeur;
		this.filsG = filsG;
		this.filsD = filsD;	
	}
	
	public T getValeur(){
		return valeur;
	}
	
	public int estFeuille(ABR arbre){
		return 1;
	}
	
	public void insertionABR(ABR arbre){
		
		if(arbre.valeur.compareTo(this.valeur) > 0 ){
			if(this.filsD == null){
				this.filsD = arbre;
			}
			else{
				this.filsD.insertionABR(arbre);
			}
		}
		else{
			if(this.filsG == null){
				this.filsG = arbre;
			}
			else{
				this.filsG.insertionABR(arbre);
			}
		}
	}
	
	public void suppressionABR(){
		
	}
}
