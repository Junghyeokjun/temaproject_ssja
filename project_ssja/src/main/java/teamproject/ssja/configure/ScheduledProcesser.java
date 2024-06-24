package teamproject.ssja.configure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import teamproject.ssja.mapper.AdminPageMapper;
import teamproject.ssja.service.mypage.MyPageService;
import teamproject.ssja.service.scheduler.SchedulerService;

@Component
@EnableScheduling
public class ScheduledProcesser {
	@Autowired
	MyPageService myPageService;
	
	@Autowired
	SchedulerService schedulerService;
	
	//회원 삭제의 경우 삭제의 동의할 경우 m_deletedate의 오늘 날짜에 7일을 더한 값이 null에서 insert
	//그리고 스케쥴러를 통해 매 00시 00 분 마다 오늘 날짜를 지난 m_deletedate를 가진 유저의 id를 
	//deleted + 시퀀스 로 변경하여 정보는 유지하나 id가 바뀌어 로그인이 불가능하여 아이디 찾기를 통해 아이디를 변경하여 다시 들어오고 삭제된 권한을 재부여

	//@Scheduled(fixedRate = 86400000)
	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	void deleteEnrolledUser() {
		try {
		List<String> enrolledDeleteUsers = myPageService.findDeleteEnrolledUsers();

		if (enrolledDeleteUsers.size() > 0) {
			myPageService.deleteEnrolledUsersAuth(enrolledDeleteUsers);

			myPageService.deleteEnrolled();
		}
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Transactional
	@Scheduled(cron ="0 10 0 * * *") // 매일 00시 10 분에 전날 이익 db에 등록 스케쥴러
	void calculateDailyProfit() {
		try {
			schedulerService.statisticProfit();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	@Transactional
	@Scheduled(cron ="0 5 0 * * *")// 매일 00시 05 분에 만료기간 지난 쿠폰 삭제
	void deleteExpiredCoupon() {
		try {
			schedulerService.deleteExpiredCoupon();
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
