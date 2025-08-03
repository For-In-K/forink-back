package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.entity.Roadmap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    Optional<Roadmap> findByMember(final Member member);

    List<Roadmap> findAllByMember(final Member member);
}
