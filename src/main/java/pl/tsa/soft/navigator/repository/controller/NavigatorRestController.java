/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.tsa.soft.navigator.repository.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.tsa.soft.navigator.repository.NavigationSnapshotRepository;
import pl.tsa.soft.navigator.repository.SuperNumberRepository;
import pl.tsa.soft.navigator.repository.UserRepository;
import pl.tsa.soft.navigator.repository.entity.NavigationSnapshot;
import pl.tsa.soft.navigator.repository.entity.SuperNumber;
import pl.tsa.soft.navigator.repository.entity.User;

/**
 *
 * @author potatolot
 */
@RestController
public class NavigatorRestController {

    private final UserRepository userRepository;
    private final NavigationSnapshotRepository navigationSnapshotRepository;
    private final SuperNumberRepository superNumberRepository;

    @Autowired
    public NavigatorRestController(
            UserRepository userRepository,
            NavigationSnapshotRepository navigationSnapshotRepository,
            SuperNumberRepository superNumberRepository) {
        this.userRepository = userRepository;
        this.navigationSnapshotRepository = navigationSnapshotRepository;
        this.superNumberRepository = superNumberRepository;
    }

    @GetMapping("/user/{userName}")
    ResponseEntity<User> getUser(@PathVariable String userName) {
        return userRepository.findByUserName(userName)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/snapshot/{userName}/from/{fromTimestamp}/to/{toTimestamp}")
    ResponseEntity<List<NavigationSnapshot>> getNavigationSnapshots(@PathVariable String userName, @PathVariable long fromTimestamp, @PathVariable long toTimestamp) {
        return ResponseEntity.ok(navigationSnapshotRepository.findUserSnapshots(userName, new Date(fromTimestamp), new Date(toTimestamp)));
    }

    @GetMapping("/numbers/{name}")
    ResponseEntity<List<SuperNumber>> getSuperNumbers(@PathVariable String name) {
        return ResponseEntity.ok(superNumberRepository.findByOwner(name));
    }

    @GetMapping("/numbers/latest/{minutes}")
    ResponseEntity<List<SuperNumber>> getLatests(@PathVariable int minutes) {
        long startDate = LocalDateTime.now().minus(minutes, ChronoUnit.MINUTES)
                .atZone(ZoneId.of("Europe/Warsaw")).toInstant().toEpochMilli();

        long endDate = LocalDateTime.now()
                .atZone(ZoneId.of("Europe/Warsaw")).toInstant().toEpochMilli();
        return ResponseEntity.ok(superNumberRepository.getLatest(startDate, endDate));
    }
}
