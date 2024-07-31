package com.nagne.domain.user.repository;

import com.nagne.domain.user.entity.Oauthid;
import com.nagne.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthRepository extends JpaRepository<Oauthid, Long> {

    Optional<Oauthid> findByUser(@Param("user") User user);
}
