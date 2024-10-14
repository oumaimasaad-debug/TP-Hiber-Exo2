/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.project.classes.Categorie;
import ma.project.classes.Commande;
import ma.project.classes.LigneCommande;
import ma.project.classes.Produit;
import ma.project.util.HibernateUtil;
import ma.projet.dao.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author DELL
 */
public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(Produit o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public Produit findById(int id) {
        Session session = null;
        Produit e  = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Produit) session.get(Produit.class, id);
            session.getTransaction().commit();
            return e;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return e;
    }

    @Override
    public List<Produit> findAll() {
        Session session = null;
        List<Produit>  produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            produits = session.createQuery("from Produit").list();
            session.getTransaction().commit();
            return produits;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return produits;
    }
     public void listeProduits(Categorie c){
     for(Produit p : c.getProduits()){
                    System.out.println("La liste des produits est : "+p.getReference());
                }
     }
      public List<Produit> findPerDate(Date startDate,Date endDate) {
        Session session = null;
    List<Commande> commandes = new ArrayList<>();
    List<Produit> produits = new ArrayList<>();
    

    try {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        commandes = session.createQuery("from Commande where date between :startDate and :endDate").setParameter("startDate", startDate).setParameter("endDate", endDate).list();
        session.getTransaction().commit();
        System.out.println("La liste");
         for(Commande p : commandes){
             
                    for(LigneCommande ps : p.getLignesCommande()){
                       Produit k=ps.getProduit();
                       produits.add(k);
                }
         }
    }
    catch (HibernateException e) {
        if (session.getTransaction() != null) {
            session.getTransaction().rollback();
        }
    } finally {
      return produits;
      }

    }
     public List<LigneCommande> listeProduitsPerCommande(Commande c){
         Session session = null;
          List<LigneCommande> p = new ArrayList<>();
         
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        p = session.createQuery("from LigneCommande lc where lc.commande = :commande").setParameter("commande", c).list();
        session.getTransaction().commit();
        return p;

     }
     public List<Produit> trouverProduitsAvecPrixSuperieurA100() {
    Session session = null;
    List<Produit> produits = null;

    try {
        // Ouvrir la session
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Requête HQL pour récupérer les produits dont le prix est supérieur à 100 DH
        produits = session.createQuery(
                "FROM Produit p WHERE p.prix > 100")
                .list();

        // Affichage des produits
        System.out.println("Produits avec un prix supérieur à 100 DH:");
        for (Produit produit : produits) {
            System.out.printf("Référence: %s, Prix: %.2f DH%n", produit.getReference(), produit.getPrix());
        }

        // Commit des transactions
        session.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();  // Afficher les erreurs si elles surviennent
    } finally {
        if (session != null) {
            session.close();  // Fermer la session dans le bloc finally
        }
    }

    return produits;  // Retourner la liste des produits
}

}
     
     
    

