package br.com.dellist.todolist.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface IUserRepository extends JpaRepository <UserModel, UUID> {

    UserModel findByUsername(String username);

}
