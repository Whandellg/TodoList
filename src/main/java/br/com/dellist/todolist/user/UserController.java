package br.com.dellist.todolist.user;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
// http://localhost:8080/primeirarota

public class UserController {
    @Autowired
    private IUserRepository iUserRepository;
    @PostMapping("/")
    public ResponseEntity create (@RequestBody UserModel userModel){
       var user = this.iUserRepository.findByUsername(userModel.getUsername());

       if(user != null){
           // Mensagem de erro
           // Status Code
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
       }

       //cripto BCrypt transforma o getPassword em uma Array de crypto
       var passwordHashred = BCrypt.withDefaults()
               .hashToString(12, userModel.getPassword().toCharArray());

       userModel.setPassword(passwordHashred);

       var userCreated = this.iUserRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}
