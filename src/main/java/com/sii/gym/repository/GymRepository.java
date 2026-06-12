package com.sii.gym.repository;

import com.sii.gym.dto.RevenueReportResponse;
import com.sii.gym.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {
    boolean existsByName(String name);

    @Query("""
        SELECT new com.sii.gym.dto.RevenueReportResponse (
                g.name,
                SUM(CASE WHEN m IS NOT NULL THEN mp.monthlyPrice ELSE NULL END),
                mp.currency
                )
        FROM Gym g
        LEFT JOIN MembershipPlan mp ON mp.gym = g
        LEFT JOIN Member m ON m.membershipPlan = mp AND m.memberStatus = com.sii.gym.model.enums.MemberStatus.ACTIVE
        GROUP BY g.name, mp.currency
        ORDER BY mp.currency
        """)
    List<RevenueReportResponse> getReports();
}