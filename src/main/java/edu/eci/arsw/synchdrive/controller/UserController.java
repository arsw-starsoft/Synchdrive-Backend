package edu.eci.arsw.synchdrive.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import edu.eci.arsw.synchdrive.model.Coordinate;
import edu.eci.arsw.synchdrive.model.Customer;
import edu.eci.arsw.synchdrive.model.Servicio;
import edu.eci.arsw.synchdrive.persistence.SynchdrivePersistenceException;
import edu.eci.arsw.synchdrive.services.UserServices;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    //Método de prueba para verificar autowired y repo
    @GetMapping(value = "/usertest")
    public ResponseEntity<?> testMethod() throws SynchdrivePersistenceException {
        Customer customer = new Customer();
        customer.setName("test name");
        customer.setEmail("test1@mail.com");
        customer.setPassword("123");
        userServices.saveUser(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    /**
     * Gets the User
     * @return All User
     */
    @GetMapping
    public ResponseEntity<?> getAllUser(){
        try{
            List<Customer> customers = userServices.getAllUsers();
            return new ResponseEntity<>(customers,HttpStatus.ACCEPTED);
        }catch (SynchdrivePersistenceException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping(path = "/{user}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("user") String email){
        Customer customer = null;
        try{
            customer = userServices.findUserByEmail(email);
            return new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
        }catch (SynchdrivePersistenceException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{user}/apps")
    public ResponseEntity<?> getAppsByEmail(@PathVariable("user") String email){
        try{
            
            return new ResponseEntity<>(userServices.findAppsByEmail(email),HttpStatus.ACCEPTED);
        }catch (SynchdrivePersistenceException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    
    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody Customer customer){
        try{
            userServices.saveUser(customer);
            return new ResponseEntity<>(customer,HttpStatus.CREATED);
        }catch (SynchdrivePersistenceException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

/*
    @PutMapping(path = "/{user}/apps")
    public ResponseEntity<?> addNewApp(@PathVariable("user") String user,@RequestBody App app){
        try{
            userServices.updateApps(user,app);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (SynchdrivePersistenceException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }*/


    
    @PutMapping(path = "/{user}")
    public ResponseEntity<?> updateUser(@PathVariable("user") String user,@Valid @RequestBody Customer customer){
        try {
            //System.out.println(customer.getEmail());
            userServices.updateUser(user,customer);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (SynchdrivePersistenceException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);            
        }catch (IOException ex) {
            return new ResponseEntity<>("Problemas con la app de uber",HttpStatus.BAD_REQUEST);            
        }

    }


    
    @GetMapping(value = "/{user}/service")
    public ResponseEntity<?> getCloseServices(@PathVariable("user") String user,@Valid @RequestBody Coordinate coordinate){
        
        List<Servicio> servicios;
        try {
            servicios = userServices.getCloseServices(user,coordinate);
            return new ResponseEntity<>(servicios,HttpStatus.ACCEPTED);
        } catch (SynchdrivePersistenceException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN); 
        }catch (IOException ex) {
            return new ResponseEntity<>("Problemas con las apps",HttpStatus.BAD_REQUEST);            
        }
        
    }

}
