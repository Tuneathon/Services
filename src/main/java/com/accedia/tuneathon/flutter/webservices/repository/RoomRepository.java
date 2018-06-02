package com.accedia.tuneathon.flutter.webservices.repository;

import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByStatus(RoomStatus roomStatus);
}
