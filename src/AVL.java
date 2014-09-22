import java.awt.*;
import java.util.*;

// Classe des Arbres H-Equilibr�s de recherche ou AVL 
class ArbreAVL extends ABR {

    // Constructeur
    public ArbreAVL(Object val) {
        valeur = val;
        filsg=null;
        filsd=null;
        frere = false;
        typeElem=val.getClass();
    }

    //fonction de calcul du d�s�quilibre identique � celle des arbres H-�quilibr�s
    public int desequilibre() {
        if (filsg==null) {
            if (filsd==null) return 0;
            else return (-filsd.profondeur());
        }
        else {
            if (filsd==null) return (filsg.profondeur());
                else return (filsg.profondeur() - filsd.profondeur());
        }
    }
    
    //fonction de r��quilibrage de tout l'arbre identique � celle des arbres H-�quilibr�s      
    public ArbreAVL equilibrage() {
        
        Arbre tmp=this;
        if (tmp.estFeuille()) return ((ArbreAVL)tmp);
        else {
            
            int desequilibre = ((ArbreAVL)tmp).desequilibre();
            if (desequilibre>=1) {
                if (tmp.filsg!=null) {
                        if (((ArbreAVL)tmp.filsg).desequilibre()<0)
                            tmp.filsg = tmp.filsg.rotationG();
                        tmp = tmp.rotationD();
                }
            }
            
            if (desequilibre<=-1) {
                    if (tmp.filsd!=null) {
                            if (((ArbreAVL)tmp.filsd).desequilibre()>0)
                                tmp.filsd = tmp.filsd.rotationD();
                            tmp = tmp.rotationG();
                    }
            }
                
            if (tmp.filsg!=null) tmp.filsg = ((ArbreAVL)tmp.filsg).equilibrage();
            if (tmp.filsd!=null) tmp.filsd = ((ArbreAVL)tmp.filsd).equilibrage();
        }
        return (ArbreAVL)tmp;
    }
            
    // Comme les AVL sont des arbres H�quilibr�s de recherche l'ajout se fait de la m�me mani�re que pour les arbres de recherche
    public void ajouterElt(Object val) throws TypeIncompatibleException{
        if (val.getClass()!=typeElem ) throw new TypeIncompatibleException();    
        if (((Comparable)val).compareTo(this.valeur)!=0)    
            if (((Comparable)val).compareTo(this.valeur)<0) {
                if (filsg==null) {
                    filsg = new ArbreAVL(val);
                }
                else {
                    filsg.ajouterElt(val);
                }
            }
            else {
                if (filsd==null) {
                    filsd = new ArbreAVL(val);
                }
                else {
                    filsd.ajouterElt(val);
                }
            }      
    }
        
    // Pour ajouter un �l�ment, il faut l'ajouter puis r��quilibrer l'arbre
    public ArbreAVL ajouter(Object val) {
        Arbre res=this;
        res.ajouterElt(val);
        return ((ArbreAVL)res).equilibrage();
    }
    
    // Les trois derni�res fonctions sont identiques � celles des arbres binaire de recherche
    // Suppression de l'�l�ment le plus � droite du fils gauche
    private Object supprimerMaxFG() {
        Object max=null;
        Arbre tmp = this;
        Arbre p�re=null;
        if (filsg.filsd==null) {
                max=filsg.valeur;
                filsg=filsg.filsg;
            }
        else {
                tmp = tmp.filsg;
                while (tmp.filsd!=null) {
                    p�re = tmp;
                    tmp=tmp.filsd;
                }
                max = tmp.valeur;
                p�re.filsd = tmp.filsd;
            
        }        
        return max;
    }
    
    // Suppression de l'�l�ment le plus � gauche du fils droit
    private Object supprimerMinFD() {
        Object max=null;
        Arbre tmp = this;
        Arbre p�re=null;
        if (filsd.filsg==null) {
                max=filsd.valeur;
                filsd=filsd.filsd;
            }
        else {
                tmp = tmp.filsd;
                while (tmp.filsg!=null) {
                    p�re = tmp;
                    tmp=tmp.filsg;
                }
                max = tmp.valeur;
                p�re.filsg = tmp.filsg;
            
        }        
        return max;
    }
    
    // Suppression de l'�l�ment d�sir�
    public Arbre supprimer(Object elt) {
        if (this!=null)
                if (((Comparable)elt).compareTo(this.valeur)==0) {
                    Object max;
                    if (this.filsg!=null) {
                        max = this.supprimerMaxFG();
                        this.valeur = max;
                    }
                    else if (this.filsd==null) return null;
                         else {
                        max = this.supprimerMinFD();
                        this.valeur = max;
                        }
                }
                else if (((Comparable)elt).compareTo(this.valeur)<0)
                     {
                         this.filsg = filsg.supprimer(elt);
                     }
                     else
                     {
                        this.filsd = filsd.supprimer(elt);
                     }
        // On �quilibre l'arbre r�sultant de la suppression et on le renvoie
        ArbreAVL tmp=this;
        tmp = ((ArbreAVL)tmp).equilibrage();
        return tmp;
    }

}