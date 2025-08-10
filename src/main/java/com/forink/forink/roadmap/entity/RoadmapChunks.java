package com.forink.forink.roadmap.entity;

import static org.hibernate.type.SqlTypes.JSON;

import com.forink.forink.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "roadmap_chunks")
public class RoadmapChunks extends BaseEntity {

    private static final int MAX_CHUNK_ID_LENGTH = 50;
    private static final int MAX_CATEGORY_NAME_LENGTH = 100;
    private static final int MAX_TITLE_LENGTH = 200;

    @Id
    @Column(nullable = false)
    private Long defaultOrder;

    @Column(unique = true, length = MAX_CHUNK_ID_LENGTH, nullable = false)
    private String chunkId;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> visaTypes;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> stayDurationCategories;

    @Column(length = MAX_CATEGORY_NAME_LENGTH, nullable = false)
    private String majorCategoryName;

    @Column(length = MAX_CATEGORY_NAME_LENGTH, nullable = false)
    private String minorCategoryName;

    @Column(length = MAX_TITLE_LENGTH, nullable = false)
    private String chunkTitle;

    @Column(columnDefinition = "TEXT")
    private String chunkDescription;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Object> conditionalRulesJson;
}
