package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.entity.Roadmap;
import com.forink.forink.roadmap.entity.RoadmapType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    List<Roadmap> findAllByMember(final Member member);

    List<Roadmap> findAllByMemberAndRoadmapTypeOrderByItemOrderAsc(final Member member, final RoadmapType roadmapType);
}
