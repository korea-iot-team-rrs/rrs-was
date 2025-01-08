package com.korit.projectrrs.repositoiry;

import com.korit.projectrrs.entity.CustomerSupportAttachment;
import com.korit.projectrrs.entity.Pet;
import com.korit.projectrrs.entity.WalkingRecordAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalkingRecordAttachmentRepository extends JpaRepository<WalkingRecordAttachment, Long> {
    @Query("SELECT wra " +
            "FROM WalkingRecordAttachment wra " +
            "WHERE wra.walkingRecord.pet.user.userId = :userId " +
            "AND wra.walkingRecordAttachmentId = :wrId")
    Optional<WalkingRecordAttachment> findByWRId(@Param("userId") Long userId, @Param("wrId")Long wrId);

    @Query("SELECT p " +
            "FROM Pet p " +
            "WHERE p.user.userId = :userId " +
            "AND p.petId = :petId")
    Optional<Pet> findPetByUserId(@Param("userId") Long userId, @Param("petId") Long petId);

}
