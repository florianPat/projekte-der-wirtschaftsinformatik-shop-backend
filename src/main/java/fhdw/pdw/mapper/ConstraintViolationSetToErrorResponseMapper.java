package fhdw.pdw.mapper;

import fhdw.pdw.model.dto.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConstraintViolationSetToErrorResponseMapper {
  public <T> ResponseEntity<?> mapFrom(Set<ConstraintViolation<T>> constraintViolations) {
    List<ErrorResponse> result = new ArrayList<>();
    for (ConstraintViolation<?> violation : constraintViolations) {
      result.add(new ErrorResponse(violation));
    }
    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
}
