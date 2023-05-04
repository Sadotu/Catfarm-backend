package team.catfarm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.catfarm.Models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
