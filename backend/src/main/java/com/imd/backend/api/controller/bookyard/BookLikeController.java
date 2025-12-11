package com.imd.backend.api.controller.bookyard;

import com.imd.backend.api.controller.core.BaseLikeController;
import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.app.service.bookyard.LikeBookService;
import com.imd.backend.domain.entities.bookyard.BookLike;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.entities.core.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bookyard/likes")
public class BookLikeController extends BaseLikeController<BookLike, BookReview> {

    public BookLikeController(LikeBookService likeService) {
        super(likeService);
    }

    @Override
    protected BookLike buildLike(LikeCreateDTO dto) {
        return BookLike.builder()
                .id(dto.getId())
                .profile(Profile.builder().id(dto.getProfileId()).build())
                .post(BookReview.builder().id(dto.getTuneetId()).build())
                .build();
    }
}
