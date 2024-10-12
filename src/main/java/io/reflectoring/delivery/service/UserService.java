package io.reflectoring.delivery.service;


import io.reflectoring.delivery.modal.UserModel;
import io.reflectoring.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserModel registerUser(String username, String firstname, String lastname, String phone, Date dateofbirth, String password, String email){
        if (username == null && password==null && email==null){
            return null;
        }
        else{
            if(userRepository.findFirstByEmail(email).isPresent()){
                System.out.println("duplicate email");
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setFirstname(firstname);
            userModel.setLastname(lastname);
            userModel.setPhone(phone);
            userModel.setDateofbirth(dateofbirth);
            userModel.setEmail(email);
            userModel.setPassword(password);
            userRepository.save(userModel);
            return userRepository.save(userModel);
        }

    }
    public UserModel authentication(String email, String password) {
        return  userRepository.findByEmailAndPassword(email, password).orElse(null);
    }
}

