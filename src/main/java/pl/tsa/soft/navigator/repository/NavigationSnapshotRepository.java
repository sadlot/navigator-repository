/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.tsa.soft.navigator.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tsa.soft.navigator.repository.entity.NavigationSnapshot;

/**
 *
 * @author potatolot
 */
@Repository
public interface NavigationSnapshotRepository extends JpaRepository<NavigationSnapshotRepository, Long> {

    @Query("SELECT * FROM NavigationSnapshot s JOIN User u ON u.id = s.userId WHERE u.name = ?1 AND s.date > ?2 AND s.date < ?3")
    List<NavigationSnapshot> findUserSnapshots(String userName, Date startDate, Date stopDate);

}
