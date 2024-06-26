package com.example.backend.board.domain;

import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ThumbsUp {
    @Id @GeneratedValue
    @Column(name = "thumbs_up_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_board_id")
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_board_id")
    private QuestionBoard questionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_board_id")
    private PromotionBoard promotionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    public void setFreeBoard(FreeBoard freeBoard) {
        this.freeBoard = freeBoard;
    }

    public void setPromotionBoard(PromotionBoard promotionBoard) {
        this.promotionBoard = promotionBoard;
    }

    public void setProjectBoard(ProjectBoard projectBoard) {
        this.projectBoard = projectBoard;
    }

    public ThumbsUp(Member member) {
        this.member = member;
    }

    public void setStudyBoard(StudyBoard studyBoard) {
        this.studyBoard = studyBoard;
    }

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }
}
