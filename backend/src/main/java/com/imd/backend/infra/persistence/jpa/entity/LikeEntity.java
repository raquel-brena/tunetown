package com.imd.backend.infra.persistence.jpa.entity;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tuneet_id", "profile_id"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuneet_id", nullable = false)
    private TuneetEntity tuneet;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
