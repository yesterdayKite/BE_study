package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.service.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

//1	todo 리스트 목록에 아이템을 추가
//2	todo 리스트 목록 중 특정 아이템을 조회
//3	todo 리스트 전체 목록을 조회
//4	todo 리스트 목록 중 특정 아이템을 수정
//5	todo 리스트 목록 중 특정 아이템을 삭제
//6	todo 리스트 전체 목록을 삭제

    // 아이템 추가하는 메소드
    public TodoEntity add(TodoRequest request){
        TodoEntity todoEntity = new TodoEntity(); // 객체 생성
        // 데이터 입력해주기
        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());
        return this.todoRepository.save(todoEntity); // repository로 데이터값이 입력됨.
    }

    public TodoEntity searchById(Long id){
        return this.todoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 값이 없으면 notfound error handling
    }

    public List<TodoEntity> searchAll(){
        return this.todoRepository.findAll();
    }

    // 수정해서 entitity를 반환하는 메소드
    public TodoEntity updateById(Long id, TodoRequest request){

        TodoEntity todoEntity = this.searchById(id); // 먼저 존재하는지 찾기

        if (request.getTitle() != null){
            todoEntity.setTitle(request.getTitle());
        }
        if (request.getOrder() != null){
            todoEntity.setOrder(request.getOrder());
        }
        if (request.getCompleted() != null){
            todoEntity.setCompleted(request.getCompleted());
        }
        return this.todoRepository.save(todoEntity); // repository에 저장후, 저장된 결과값 리
    }

    // 하나의 entity를 삭제하는 메소드
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }

    // 전체를 삭제하는 메소드
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }
}
