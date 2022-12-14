package br.com.inatel.MyCatalog.repository;

import br.com.inatel.MyCatalog.model.entity.TvShow;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<TvShow, Integer> {

    Optional<TvShow> findByTitle(String title);

    List<TvShow> findAllByType(@NotNull String type);

//    @Query("SELECT t FROM TvShow t where t.type = :type")
//    List<TvShow> findAllByType(@Param("type") String type);

}
