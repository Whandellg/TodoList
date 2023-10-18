package br.com.dellist.todolist.task;

import br.com.dellist.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository iTaskRepository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Chegou noi controller" + request.getAttribute("idUser"));
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID)idUser);

        var currenteDate = LocalDateTime.now();

        //VALIDAÇÕES
        if(currenteDate.isAfter(taskModel.getStartAt()) || currenteDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio / data de termino deve ser maior que a data atual");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor que a data de termino");
        }

        var task = this.iTaskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.iTaskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
    //http://localhost:8080/tasks/892347823-cdfgzcfa-234585678
    @PutMapping("/{id}")
    public ResponseEntity update (@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
        var task = this.iTaskRepository.findById(id).orElse(null);

        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario nao tem permissao para alterar tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var taskupdated = this.iTaskRepository.save(task);
        return ResponseEntity.ok().body(taskupdated);
    }
}
