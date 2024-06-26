package teamproject.ssja.service.Reply;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import teamproject.ssja.dto.BoardDto;
import teamproject.ssja.dto.BoardIsLikedDto;
import teamproject.ssja.dto.LikesVO;
import teamproject.ssja.dto.ReplysDto;
import teamproject.ssja.dto.login.CustomPrincipal;
import teamproject.ssja.dto.userinfo.ReviewForm;
import teamproject.ssja.page.Criteria;



public interface ReplyService {	
	public List<ReplysDto> showReplys(long bno);
	int addReply(ReplysDto replysDto);
	int modifyReply(ReplysDto replysDto);
	int removeReply(ReplysDto replysDto);

	// 해당 글의 총 댓글 개수
	long getTotal(long bno);
	
	// 해당 댓글 좋아요 처리 및 해당 댓글의 좋아요 수 리턴 
	LikesVO modifyGetReplyLikes(String rno, String mno);
	
	void appleReview(ReviewForm form);
}
