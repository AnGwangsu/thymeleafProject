package com.thymeleaf.bootproject.validator;

import com.thymeleaf.bootproject.Model.BoardVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardVO.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        BoardVO b = (BoardVO) obj;
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content","key","내용을 입력하세요.");
        }
    }
}
