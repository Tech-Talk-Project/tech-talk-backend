package com.example.backend.entity.follow;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document
@Getter
public class Following {
    @Id
    private Long id;

    private Set<Person> followingPerson = new HashSet<>();

    public Following(Long id) {
        this.id = id;
    }

    @Getter
    public static class Person {
        private Long memberId;
        private LocalDateTime followingTime;

        public Person(Long memberId, LocalDateTime followingTime) {
            this.memberId = memberId;
            this.followingTime = followingTime;
        }
    }
}
