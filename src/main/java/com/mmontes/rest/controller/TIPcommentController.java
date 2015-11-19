package com.mmontes.rest.controller;

import com.mmontes.model.service.CommentService;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TIPcommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/social/tip/{TIPId}/comment", method = RequestMethod.POST)
    public ResponseEntity
    createComment(@PathVariable Long TIPId,
                  @RequestParam(value = "facebookUserId", required = true) Long facebookUserId,
                  @RequestParam(value = "commentText", required = true) String commentText){

        try {
            commentService.comment(commentText,facebookUserId,TIPId);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
