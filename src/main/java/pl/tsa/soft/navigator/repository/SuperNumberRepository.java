/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.tsa.soft.navigator.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tsa.soft.navigator.repository.entity.SuperNumber;

/**
 *
 * @author potatolot
 */
@Repository
public interface SuperNumberRepository extends MongoRepository<SuperNumber, String> {

    List<SuperNumber> findByOwner(String name);
    
    @Query("{timestamp:{$gte:?0, $lte:?1}}")
    List<SuperNumber> getLatest(long startDate, long endDate);
    
}
