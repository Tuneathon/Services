package com.accedia.tuneathon.flutter.webservices.repository;

import com.accedia.tuneathon.flutter.webservices.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
