package fr.eservices.promos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eservices.promos.model.PromoType;
import fr.eservices.promos.repository.PromoTypeRepository;

@Service
public class PromoTypeService {
    
    @Autowired
    PromoTypeRepository promoTypeRepository;

    public List<PromoType> findAll() {
        return (List<PromoType>) promoTypeRepository.findAll();
    }

    public PromoType getPromoTypeById(int id) {
        return promoTypeRepository.findById(id).get();
    }

    public void saveOrUpdate(PromoType promoType) {
        promoTypeRepository.save(promoType);
    }

    public void delete(int id) {
        promoTypeRepository.deleteById(id);
    }
}
