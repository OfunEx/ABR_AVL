import java.awt.*;
import java.util.*;

// Classe des Arbres H-Equilibrés de recherche ou AVL 
class ArbreAVL extends ABR {

    // Constructeur
    public ArbreAVL(Object val) {
        valeur = val;
        filsg=null;
        filsd=null;
        frere = false;
        typeElem=val.getClass();
    }

    //fonction de calcul du déséquilibre identique à celle des arbres H-équilibrés
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
    
    //fonction de rééquilibrage de tout l'arbre identique à celle des arbres H-équilibrés      
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
            
    // Comme les AVL sont des arbres Héquilibrés de recherche l'ajout se fait de la même manière que pour les arbres de recherche
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
        
    // Pour ajouter un élément, il faut l'ajouter puis rééquilibrer l'arbre
    public ArbreAVL ajouter(Object val) {
        Arbre res=this;
        res.ajouterElt(val);
        return ((ArbreAVL)res).equilibrage();
    }
    
    // Les trois dernières fonctions sont identiques à celles des arbres binaire de recherche
    // Suppression de l'élément le plus à droite du fils gauche
    private Object supprimerMaxFG() {
        Object max=null;
        Arbre tmp = this;
        Arbre père=null;
        if (filsg.filsd==null) {
                max=filsg.valeur;
                filsg=filsg.filsg;
            }
        else {
                tmp = tmp.filsg;
                while (tmp.filsd!=null) {
                    père = tmp;
                    tmp=tmp.filsd;
                }
                max = tmp.valeur;
                père.filsd = tmp.filsd;
            
        }        
        return max;
    }
    
    // Suppression de l'élément le plus à gauche du fils droit
    private Object supprimerMinFD() {
        Object max=null;
        Arbre tmp = this;
        Arbre père=null;
        if (filsd.filsg==null) {
                max=filsd.valeur;
                filsd=filsd.filsd;
            }
        else {
                tmp = tmp.filsd;
                while (tmp.filsg!=null) {
                    père = tmp;
                    tmp=tmp.filsg;
                }
                max = tmp.valeur;
                père.filsg = tmp.filsg;
            
        }        
        return max;
    }
    
    // Suppression de l'élément désiré
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
        // On équilibre l'arbre résultant de la suppression et on le renvoie
        ArbreAVL tmp=this;
        tmp = ((ArbreAVL)tmp).equilibrage();
        return tmp;
    }

}