/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.project.Test;


/**
 *
 * @author Pc
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.project.classes.Categorie;
import ma.project.classes.Commande;
import ma.project.classes.CommandeProduitPK;
import ma.project.classes.LigneCommande;
import ma.project.classes.Produit;
import ma.project.util.HibernateUtil;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;


public class Exo2 {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        HibernateUtil.getSessionFactory().openSession();
        LigneCommandeService lc = new LigneCommandeService();
        CategorieService ct = new CategorieService();
        ProduitService pr = new ProduitService();
        CommandeService co = new CommandeService();
        //Creation des categories
        Categorie C1 =  new Categorie("1", "Maquillage");
        Categorie C2 =  new Categorie("2", "Vetements");
        
        ct.create(C2);
        ct.create(C1);
        //Creation des produits
         Produit produit1 = new Produit("100", 1200.50f, C1);
         Produit produit2 = new Produit("111", 450.99f,C1 );
         Produit produit3 = new Produit("200", 990.99f,C2 );
        pr.create(produit1);
        pr.create(produit2);
        pr.create(produit3);
        
        //Creation des commandes
        Commande commande1 = new Commande(new Date());
        Commande commande2 = new Commande(new Date());
        co.create(commande1);
        co.create(commande2);
       //Creation des lignes
        LigneCommande ligne1 = new LigneCommande(produit1,commande1,8); 
        ligne1.setPk(new CommandeProduitPK(1,1));
        LigneCommande ligne2 = new LigneCommande(produit2,commande1,9); 
        ligne2.setPk(new CommandeProduitPK(2,1));
        LigneCommande ligne3 = new LigneCommande(produit3,commande2,10); 
        ligne3.setPk(new CommandeProduitPK(3,2));
        lc.create(ligne1);
        lc.create(ligne2);
        lc.create(ligne3);
        //Affichage des produits par date
  
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
        Date startDate = sdf.parse("2024-10-13");
        Date endDate = sdf.parse("2024-10-15");
             for (Produit produit : pr.findPerDate(startDate, endDate)) {
            System.out.println(produit); 
        } 
        
    }   catch (ParseException ex) {
            Logger.getLogger(Exo2.class.getName()).log(Level.SEVERE, null, ex);
        }
       /* //Affichagedes produits par commande
                pr.listeProduitsPerCommande(commande1);
        
      
         
          // 4. Afficher les produits dont le prix est supérieur à 100 DH
        pr. trouverProduitsAvecPrixSuperieurA100() ;*/
    }
}