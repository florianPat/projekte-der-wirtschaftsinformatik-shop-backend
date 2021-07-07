package fhdw.pdw.repository;

import fhdw.pdw.model.Greeting;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GreetingRepository extends CrudRepository<Greeting, Long> {
  public List<Greeting> findByContent(String content);

  // NOTE: This resolves by foreign property (id, that was specified after the relation property
  // name)
  // public List<Relation> findByRelationId(String id);
}
