package br.com.inatel.MyCatalog.repository;

import br.com.inatel.MyCatalog.model.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<TvShow, Integer> {

    Optional<TvShow> findByTitle(String title);

    List<TvShow> findAllByType(@NotNull String type);


}
