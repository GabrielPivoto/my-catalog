package br.com.inatel.MyCatalog.repository;

import br.com.inatel.MyCatalog.model.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<TvShow, Integer> {

    Optional<TvShow> findByTitle(String title);

}
