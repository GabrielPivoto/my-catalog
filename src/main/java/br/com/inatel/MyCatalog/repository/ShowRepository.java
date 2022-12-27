package br.com.inatel.MyCatalog.repository;

import br.com.inatel.MyCatalog.model.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Repository of the entity which is being saved in the database.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
public interface ShowRepository extends JpaRepository<TvShow, Integer> {

    Optional<TvShow> findByTitle(String title);

    List<TvShow> findAllByType(@NotNull String type);


}
