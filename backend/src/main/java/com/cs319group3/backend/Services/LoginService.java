package com.cs319group3.backend.Services;


import com.cs319group3.backend.Repositories.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;

public interface LoginService {


    public boolean authenticate(String email, String password);
}
