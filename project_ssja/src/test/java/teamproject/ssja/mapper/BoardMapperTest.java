package teamproject.ssja.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.ssja.dto.BoardCategoryDto;
import teamproject.ssja.dto.BoardDto;
import teamproject.ssja.dto.BoardImgsDto;
import teamproject.ssja.dto.community.CommunityBoardDto;
import teamproject.ssja.page.Criteria;

@Slf4j
@SpringBootTest
class BoardMapperTest {
	
	@Autowired
	BoardMapper boardMapper;

//	@Test
//	void testSelectList() {
//		for (BoardDto dto : boardMapper.selectList()) {
//			log.info("dto : " + dto);
//		}
//	}

	@Disabled
	@Transactional
	@Test
	void testInsertBoard() {
		BoardDto boardDto = new BoardDto();
		boardDto.setBmno(1);
		boardDto.setBbcno(98);
		boardDto.setBwriter("testmember");
		boardDto.setBtitle("test?");
		boardDto.setBcontent("test!");
		boardMapper.insertBoard(boardDto);
		Criteria criteria = new Criteria(); 
		for (BoardDto dto : boardMapper.selectListWithPaging(criteria)) {
			if (dto.getBno() > 200)
				log.info("dto : " + dto);
		}
	}
	
	@Disabled
	@Test
	void testDeleteBoard() {
		Criteria criteria = new Criteria(); 
		for (BoardDto dto : boardMapper.selectListWithPaging(criteria)) {
			log.info("dto : " + dto);
		}

		BoardDto boardDto = new BoardDto();
		boardDto.setBno(203);
		boardMapper.deleteBoard(boardDto);

		for (BoardDto dto : boardMapper.selectListWithPaging(criteria)) {
			log.info("dto : " + dto);
		}
	}

	@Disabled
	@Test
	void testRead() {
		log.info("this dto : " + boardMapper.read(140));
	}

	@Disabled
	@Test
	void testUpdateBoard() {
		BoardDto boardDto = new BoardDto();
		boardDto.setBno(205);
		boardDto.setBtitle("바꾼것");
		boardDto.setBcontent("바꿨지롱?!");

		boardMapper.read(205);
		boardMapper.updateBoard(boardDto);
		boardMapper.read(205);
	}

	@Disabled
	@Test
	void testUpdateHit() {
		boardMapper.read(205);
		boardMapper.updateHit(205);
		boardMapper.read(205);
	}

	@Disabled
	@Test
	void testUpdateBLikeUp() {
		BoardDto dto = boardMapper.read(130);
		log.info("dto's like before up : " + dto.getBlike());
		boardMapper.updateBLikeUp(dto.getBno());
		
		dto = boardMapper.read(130);
		log.info("dto's like up after : " + dto.getBlike());		
	}

	@Disabled
	@Test
	void testUpdateBLikeDown() {
		BoardDto dto = boardMapper.read(130);
		log.info("dto's like before down : " + dto.getBlike());
		boardMapper.updateBLikeDown(dto.getBno());
		
		dto = boardMapper.read(130);
		log.info("dto's like down after : " + dto.getBlike());	
	}

	@Disabled
	@Test
	void testSelectTotalCount() {
		log.info("총 게시글 수는 " + boardMapper.selectTotalCount(20) + "개입니다.");
	}

	@Disabled
	@Test
	void testSelectListWithPaging() {
		// 기본 criteria : 1페이지에 출력되는 10개  
		for(BoardDto dto : boardMapper.selectListWithPaging(new Criteria())) {
			log.info("dto : " + dto);
		}
	}

	@Disabled
	@Test
	void testSelectBoardLikes() {
		long likes = boardMapper.selectBoardLikes(205);
		assertNotNull(likes);
		log.info("205번 게시글 좋아요 수 : " + likes);
	}
	
	@Disabled
	@Test
	void TestSelectBoardCategorys() {
		for(BoardCategoryDto dto : boardMapper.selectBoardCategorys()) {
			log.info("BC dto : " + dto);
		}
	}
	
	@Disabled
	@Test
	void TestSelectCommunity() {
		
		for(CommunityBoardDto dto : boardMapper.selectCommunityDto(1,10)){
			log.info("communitydto : " + dto);
		}
	}

	@Disabled
	@Test
	void TestSelectBestCommunity() {
		
		for(CommunityBoardDto dto : boardMapper.selectBestCommunityDto()){
			log.info("communitydto : " + dto);
		}
	}
	@Disabled
	@Test
	void TestSelectCommunityContent() {
			log.info("communitydto : " + boardMapper.selectCommunityContent(600));
	
	}
	// 보드 이미지 체크 테스트
	
	@Disabled
	@Test
	void selectBoardImgTest() {
		log.info(boardMapper.selectBoardImg(11614)+"");
	}
	// 보드 이미지 체크 테스트
	
	@Disabled
	@Test
	void updateBoardImgTest() {
		boardMapper.updateBoardImg(new BoardImgsDto(0, 11614, "/images/board_content/temp.png"));
	}
	
	@Disabled
	@Test
	void selectSearchTotal() {
		System.out.println(boardMapper.selectSearchTotalCount(40, "title", "파일 구현"));
	}

	@Test
	void selectSearchPost() {
		System.out.println(boardMapper.selectSearchCommunityDto(1,20, "content", "파일 구현"));
	}
	
//	@Test
//	void temp() {
//		File file=new File("C:/Users/601-5/git/temaproject_ssja/project_ssja/src/main/resources/static/images/board_content/board_img_11614.png");
//		System.out.println(file.exists());
//	}
}
