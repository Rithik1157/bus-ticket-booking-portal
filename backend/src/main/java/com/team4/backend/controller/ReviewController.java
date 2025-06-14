package com.team4.backend.controller;

import com.team4.backend.dto.ReviewDto;
import com.team4.backend.entities.Review;
import com.team4.backend.mapper.ReviewMapper;
import com.team4.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    @GetMapping("")
    public ResponseEntity<List<ReviewDto>> getAllReview(){
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> reviewMapper.toDto(review))
                .toList();
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("review_id") int review_id){
        Optional<Review> review = reviewRepository.findById(review_id);
        if(review.isPresent()){
            ReviewDto reviewDto = reviewMapper.toDto(review.get());
            return new ResponseEntity<>(reviewDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/tripid/{trip_id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByTripId(@PathVariable("trip_id") Integer tripId) {
        List<ReviewDto> dtos = reviewRepository.findByTripId(tripId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/customerid/{customer_id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByCustomerId(@PathVariable("customer_id") Integer customerId) {
        List<ReviewDto> dtos = reviewRepository.findByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsByRating(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/gt/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsWithRatingGreaterThan(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/lt/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsWithRatingLessThan(@PathVariable("rating") Integer rating) {
        List<ReviewDto> dtos = reviewRepository.findByRatingLessThan(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/customer/{customer_id}/trip/{trip_id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByCustomerAndTrip(
            @PathVariable("customer_id") Integer customerId,
            @PathVariable("trip_id") Integer tripId) {

        List<ReviewDto> dtos = reviewRepository.findByCustomerIdAndTripId(customerId, tripId)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/date/{reviewDate}")
    public ResponseEntity<List<ReviewDto>> getReviewsByReviewDate(@PathVariable("reviewDate") String reviewDateStr) {
        try {
            Instant reviewDate = Instant.parse(reviewDateStr);
            List<ReviewDto> dtos = reviewRepository.findByReviewDate(reviewDate)
                    .stream()
                    .map(reviewMapper::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}