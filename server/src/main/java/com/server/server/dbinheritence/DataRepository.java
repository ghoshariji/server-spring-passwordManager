package com.server.server.dbinheritence;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.server.model.DataModel;
import java.util.List;

public interface DataRepository extends MongoRepository<DataModel, String> {

    ArrayList<DataModel> findByEmail(String email);

}
