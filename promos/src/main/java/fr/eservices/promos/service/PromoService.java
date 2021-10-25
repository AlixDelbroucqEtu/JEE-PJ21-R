package fr.eservices.promos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eservices.promos.model.Promo;
import fr.eservices.promos.repository.PromoRepository;

@Service
public class PromoService {
    
    @Autowired
    PromoRepository promoRepository;

    public List<Promo> findAll() {
        return (List<Promo>) promoRepository.findAll();
    }

    public Promo getPromoById(int id) {
        return promoRepository.findById(id).get();
    }

    public void saveOrUpdate(Promo promo) {
        promoRepository.save(promo);
    }

    public void delete(int id) {
        promoRepository.deleteById(id);
    }

    /**
     * Récupère tous les champs pormo du type offre marketing
     * @return l'ensemble des offres marketing créées
     */
    public List<Promo> findMarketingCampain(){
        return (List<Promo>)promoRepository.findByType("OFFRE_MARKETING");
    }
}

