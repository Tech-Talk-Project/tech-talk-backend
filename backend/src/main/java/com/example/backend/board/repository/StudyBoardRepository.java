package com.example.backend.board.repository;

import com.example.backend.board.domain.BoardEntity;
import com.example.backend.board.domain.StudyBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QStudyBoard.studyBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardRepository implements BoardRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public StudyBoard findById(Long studyBoardId) {
        return queryFactory
                .selectFrom(studyBoard)
                .where(studyBoard.id.eq(studyBoardId))
                .fetchOne();
    }

    @Override
    public StudyBoard findByIdWithAll(Long studyBoardId) {
        return queryFactory
                .selectFrom(studyBoard)
                .join(studyBoard.author).fetchJoin()
                .where(studyBoard.id.eq(studyBoardId))
                .fetchOne();
    }

    @Transactional
    @Override
    public void save(BoardEntity studyBoard) {
        em.persist(studyBoard);
    }

    @Transactional
    @Override
    public void remove(BoardEntity studyBoard) {
        em.remove(studyBoard);
    }

    @Transactional
    @Override
    public void increaseViewCount(Long boardId) {
        queryFactory
                .update(studyBoard)
                .where(studyBoard.id.eq(boardId))
                .set(studyBoard.viewCount, studyBoard.viewCount.add(1))
                .execute();
    }
}