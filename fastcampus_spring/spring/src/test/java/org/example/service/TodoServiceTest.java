package org.example.service;

import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.service.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

// Mock 사용 이유 : 외부 시스템에 의존하지 않고 자체 테스트 & 실제 DB 사용하지 않도록

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        when(this.todoRepository.save(any(TodoEntity.class))) // todoRepository가 save호출해서 todoEntity값 받기
                .then(AdditionalAnswers.returnsFirstArg()); // 받은 entity값 반환하도록함
        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        TodoEntity actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    void searchById() {
        TodoEntity entity = new TodoEntity();
        // 아무거나 넣어줌
        entity.setId(123L);
        entity.setTitle("Title");
        entity.setOrder(0L);
        entity.setCompleted(false);
        Optional<TodoEntity> optional = Optional.of(entity); // optional로 매핑해줌

        //todoRepository에서 findByID로 어떤 값이던 Id값이 주어졌을때
        //optional값을 리턴하도록한다.
        given(this.todoRepository.findById(anyLong()))
                .willReturn(optional);

        TodoEntity actual = this.todoService.searchById(123L);

        TodoEntity expected = optional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getOrder(), actual.getOrder());
        assertEquals(expected.getCompleted(), actual.getCompleted());

        this.todoRepository.findById(anyLong());
    }

    @Test
    public void searchByIdFailed(){ // 없는 값이 조회했을때의 에러가 잘 발생하는지 테스트
        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, ()->{
            this.todoService.searchById(123L); // 없는 값을 넣어
        });
    }
}