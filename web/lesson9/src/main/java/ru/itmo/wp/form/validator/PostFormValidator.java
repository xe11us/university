package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;

@Component
public class PostFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) target;
            String[] tags = postForm.getTags().split("\\s+");

            for (String tag : tags) {
                if (tag.length() > 50) {
                    errors.rejectValue("tags", "tag.is-too-long", "tags must be 50 symbols or shorter");
                    return;
                }
            }
        }
    }
}
