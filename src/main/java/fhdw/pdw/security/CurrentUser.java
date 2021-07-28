package fhdw.pdw.security;

import java.lang.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/** Access currently logged in user from controller */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {}
