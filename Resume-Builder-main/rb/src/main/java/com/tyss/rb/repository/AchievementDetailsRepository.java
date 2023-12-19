package com.tyss.rb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.rb.entity.AchievementDetails;

@Repository
public interface AchievementDetailsRepository extends JpaRepository<AchievementDetails, Integer> {

}
