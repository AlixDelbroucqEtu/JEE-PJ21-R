package fr.eservices.promos.service;

import fr.eservices.promos.model.PromoType;
import fr.eservices.promos.model.UsedPromo;
import fr.eservices.promos.repository.PromoTypeRepository;
import fr.eservices.promos.repository.UsedPromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Used_PromoService {

    @Autowired
    private UsedPromoRepository usedPromoRepository;

    public List<UsedPromo> findAll() {

        return (List<UsedPromo>) usedPromoRepository.findAll();
    }

    public List<UsedPromo> findAllByCustomer_Id(Integer id) {
        return usedPromoRepository.findAllByCustomer_Id(id);
    }

}
