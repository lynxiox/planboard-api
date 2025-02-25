package com.example.planboardapi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    private String description;
    @Column(name = "is_completed")
    @Builder.Default
    boolean iscompleted = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }
}
