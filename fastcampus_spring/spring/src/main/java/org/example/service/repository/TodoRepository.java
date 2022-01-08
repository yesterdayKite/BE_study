package org.example.service.repository;

// repository = 데이터베이스와 데이터를 주고받기 위한 인터페이스를 정의한 부분
// 실제 데이터를 저장하는 '클래스'가 아니라 주고받는 '인터페이스'이다


// JPA repository interface를 상속해서 만들거임.
import org.example.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>{
}
