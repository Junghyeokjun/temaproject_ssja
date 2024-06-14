package teamproject.ssja.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import teamproject.ssja.dto.BoardDto;
import teamproject.ssja.dto.ProductDto;
import teamproject.ssja.dto.ProductImgDto;
import teamproject.ssja.dto.vendor.VendorInfoDTO;
import teamproject.ssja.page.Criteria;

@Mapper
public interface VendorMapper {
	
	// 판매자 자신의 판매자 데이터 가져오기
	VendorInfoDTO selectVendor(long mNo);

	// 판매자 자신의 판매 물품 목록 가져오기
	List<ProductDto> selectVendorProducts(Criteria criteria);
	
	// 판매자 자신이 입력했던 물품의 번호 가져오기
	long selectInsertedProNum(ProductDto product);
	
	// 상품 정보 집어넣기
	int insertProduct(ProductDto product);
	
	// 상품 이미지 경로 집어넣기
	int insertProductImgs(ProductImgDto productImg); 
}