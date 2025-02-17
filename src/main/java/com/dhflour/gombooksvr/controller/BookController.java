package com.dhflour.gombooksvr.controller;


import com.dhflour.gombooksvr.beans.ResultVO;
import com.dhflour.gombooksvr.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "MariaDB Controller", description = "maria DB 커넥션 및 기능 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/book")
public class BookController {


    private final BookService bookService;

//    @Operation(summary = "mariaDB 테스트", description = "카테고리 리스트 불러오기", tags = {"Test Controller"})
//    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
//    public ResponseEntity<ResultVO> getCategoryList(@RequestParam Map<String, Object> paramMap) {
//        ResultVO rvo = new ResultVO();
//
//        try{
//            rvo.setCode(HttpStatus.OK.value());
//            rvo.setBody(testService.getCategoryList(paramMap));
//        }
//        catch(Exception e){
//            rvo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//
//        return new ResponseEntity<>(rvo, HttpStatus.OK);
//
//    }

    @Operation(summary = "책 목록 전체 조회", description = "전체 조회 Sample")
    @RequestMapping(value = "/getBookList", method = RequestMethod.GET)
    public ResponseEntity<ResultVO> getCategoryList() {
        ResultVO resultVO = new ResultVO();

        try{
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(bookService.getBookList("1"));
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }

}
