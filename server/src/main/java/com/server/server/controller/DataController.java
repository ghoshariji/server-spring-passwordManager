package com.server.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.dbinheritence.DataRepository;
import com.server.server.model.DataModel;
import com.server.server.services.EmailServices;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/data")
public class DataController {
    // building connection to interface class to uses in another class -> make
    // connection
    @Autowired
    DataRepository dataRepository;
    // creating a new dataset for the storing data

    // mail class getting
    @Autowired
    private EmailServices emailServices;

    @PostMapping("/new-data")
    public ResponseEntity<?> newData(@RequestBody DataModel post) {
        try {
            DataModel data = new DataModel();
            data.setEmail(post.getEmail());
            data.setAppName(post.getAppName());
            data.setAppPassword(post.getAppPassword());
            data.setLastUpdate(new Date());
            dataRepository.save(data);
            emailServices.sendMail(post.getEmail(), "New App Added",
                    "This is a confirmation message for the Adding a new Email in the website PASSWORDMANAGER");
            return ResponseEntity.ok("Saved Successfully");
        } catch (Exception e) {
            System.out.println("Error" + e);
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @GetMapping("/get-data")
    public ResponseEntity<?> getUserData(@RequestParam String email) {
        try {
            ArrayList<DataModel> userData = dataRepository.findByEmail(email);
            if (userData.isEmpty()) {
                return new ResponseEntity<>("No data found for the given email", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Error retrieving data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-data")
    public ResponseEntity<?> updateData(@RequestParam String id, @RequestBody DataModel post) {
        try {
            // optional is used for the is the data present or not -> return type will be
            // the DataModel
            System.out.println(post);
            Optional<DataModel> data = dataRepository.findById(id);
            if (data.isPresent()) {
                DataModel existingData = data.get();
                existingData.setAppName(post.getAppName());
                existingData.setAppPassword(post.getAppPassword());
                existingData.setLastUpdate(new Date());
                dataRepository.save(existingData);
                return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Internal Server Error", HttpStatus.OK);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Error retrieving data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-data")
    public ResponseEntity<?> deleteData(@RequestParam String id) {
        try {
            Optional<DataModel> data = dataRepository.findById(id);
            if (data.isPresent()) {
                dataRepository.deleteById(id);
                return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Internal Server Error", HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Error retrieving data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
