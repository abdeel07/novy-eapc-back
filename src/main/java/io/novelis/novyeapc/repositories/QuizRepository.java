package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByInterviewId(Long interview_id);
}
