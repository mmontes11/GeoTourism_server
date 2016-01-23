package com.mmontes.rest.controller;

import com.mmontes.model.service.CommentService;
import com.mmontes.util.dto.CommentDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TIPcommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/social/tip/{TIPId}/comment", method = RequestMethod.POST)
    public ResponseEntity<List<CommentDto>>
    createComment(@PathVariable Long TIPId,
                  @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId,
                  @RequestParam(value = "commentText", required = true) String commentText){

        try {
            List<CommentDto> commentDtos = commentService.comment(commentText,facebookUserId,TIPId);
            return new ResponseEntity<>(commentDtos,HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/social/tip/{TIPId}/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<List<CommentDto>>
    deleteComment(@PathVariable Long TIPId,
                  @PathVariable Long commentId,
                  @RequestHeader(value = "FacebookUserId", required = true) Long facebookUserId){
        try {
            List<CommentDto> commentDtos = commentService.deleteComment(commentId,TIPId,facebookUserId);
            return new ResponseEntity<>(commentDtos,HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
