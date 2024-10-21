package com.my.articles.api.controller;

import com.my.articles.api.exceition.ApiResponse;
import com.my.articles.api.exceition.BadRequestException;
import com.my.articles.dto.CommentDTO;
import com.my.articles.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CommentApiController {
    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Exception Test
    @GetMapping("/api/exception")
    public String exHandler(){
        throw new BadRequestException("TEST");
    }

    // 1. 댓글 조회
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentSearch(@PathVariable("commentId")Long commentId){
        CommentDTO findComment = extracted(commentId, "댓글 조회에 실패했습니다.");
        CommentDTO dto = (CommentDTO) findComment;
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<?> commentCreate(@PathVariable("articleId")Long articleId, @RequestBody CommentDTO dto){
        commentService.insertComment(articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 생성 성공").build());
    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentUpdate(@PathVariable("commentId")Long commentId, @RequestBody CommentDTO dto){
        CommentDTO result = extracted(commentId, "댓글 수정에 실패했습니다.");
        dto.setId(commentId);
        commentService.updateComment(dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 수정 성공").build());
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable("commentId")Long commentId){
        CommentDTO result = extracted(commentId, "댓글 삭제에 실패했습니다.");
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 삭제 성공").build());
    }

    // 자주 사용해서 함수 처리
    private CommentDTO extracted(Long commentId, String message) {
        Map<String, Object> findComment = commentService.findByIdComment(commentId);
        if(ObjectUtils.isEmpty(findComment.get("dto"))){
            throw new BadRequestException(message);
        }
        return (CommentDTO) findComment.get("dto");
    }

    // 내가 작성한 댓글 입력
//    @PatchMapping("{id}/commentApiInsert")
//    public ResponseEntity<String> commentApiInsert(@RequestBody CommentDTO dto, @PathVariable("id")Long articleId){
//        dto.setId(null);
//        commentService.insertComment(articleId, dto);
//        String url = "/articles/" + articleId;
//        return ResponseEntity.status(HttpStatus.OK).body(url);
//    }
//
//    // 내가 작성한 댓글 수정
//    @PostMapping("{id}/commentApiUpdate")
//    public ResponseEntity<String> commentApiUpdate(@RequestBody CommentDTO dto, @PathVariable("id")Long articleId){
//        System.out.println(dto.toString());
//        dto.setId(dto.getId());
//        commentService.updateComment(dto);
//        String url = "/articles/" + articleId;
//        return ResponseEntity.status(HttpStatus.OK).body(url);
//    }
}