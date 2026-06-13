package com.sii.gym.repository;

import com.sii.gym.dto.RevenueReportResponse;
import com.sii.gym.model.Gym;
import com.sii.gym.model.Member;
import com.sii.gym.model.MembershipPlan;
import com.sii.gym.model.enums.Currency;
import com.sii.gym.model.enums.MemberStatus;
import com.sii.gym.model.enums.MembershipPlanType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class GymRepositoryTest {

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    @Test
    void shouldReturnCorrectReport() {
        // GIVEN
        String name = "Gym";
        BigDecimal amount = new BigDecimal("100.99");
        Currency currency = Currency.USD;

        Gym gym = new Gym();
        gym.setName(name);
        gym.setAddress("Warsaw");
        gym.setPhoneNumber("123456789");
        gymRepository.save(gym);

        MembershipPlan membershipPlan = new MembershipPlan();
        membershipPlan.setName("Plan");
        membershipPlan.setMembershipPlanType(MembershipPlanType.BASIC);
        membershipPlan.setMonthlyPrice(amount);
        membershipPlan.setCurrency(currency);
        membershipPlan.setDurationMonths(1);
        membershipPlan.setMaxMembers(5);
        membershipPlan.setGym(gym);
        membershipPlanRepository.save(membershipPlan);

        Member member = new Member();
        member.setFullName("Jan Kowal");
        member.setEmail("mail@example.com");
        member.setMembershipStartDate(LocalDate.now());
        member.setMemberStatus(MemberStatus.ACTIVE);
        member.setMembershipPlan(membershipPlan);
        memberRepository.save(member);

        // WHEN
        List<RevenueReportResponse> reports = gymRepository.getReports();

        // THEN
        RevenueReportResponse report = reports.getFirst();
        assertEquals(name, report.gymName());
        assertEquals(amount, report.amount());
        assertEquals(currency, report.currency());
    }
}
