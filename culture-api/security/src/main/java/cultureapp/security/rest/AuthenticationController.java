package cultureapp.security.rest;

import cultureapp.domain.authentication.LoginService;
import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.security.CustomUserDetailsService;
import cultureapp.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final LoginService loginService;
    private final TokenUtils tokenUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws AccountNotActivatedException {
        LoginUseCase.LoginCommand command =
                new LoginUseCase.LoginCommand(request.getEmail(), request.getPassword());
        LoginUseCase.LoginDTO loginInfo = loginService.login(command);
        return ResponseEntity.ok(mapLoginInfo(loginInfo));
    }

    private LoginResponse mapLoginInfo(LoginUseCase.LoginDTO loginInfo) {
        return new LoginResponse(
                tokenUtils.generateToken(loginInfo.getEmail()),
                loginInfo.getRole(),
                tokenUtils.getExpiredIn());
    }

//    @PostMapping(value = "/refresh")
//    public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {
//
//        String token = tokenUtils.getToken(request);
//        String username = this.tokenUtils.getUsernameFromToken(token);
//        User user = (User) this.userDetailsService.loadUserByUsername(username);
//
//        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//            String refreshedToken = tokenUtils.refreshToken(token);
//            int expiresIn = tokenUtils.getExpiredIn();
//
//            return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
//        } else {
//            UserTokenStateDTO userTokenState = new UserTokenStateDTO();
//            return ResponseEntity.badRequest().body(userTokenState);
//        }
//    }
//
//    @PostMapping("/password")
//    public ResponseEntity<Map<String, String>> changePassword(@RequestBody PasswordRequest request) throws AccountNotFoundException {
//        ChangePasswordCommand command = new ChangePasswordCommand(request.getOldPassword(), request.getNewPassword());
//        boolean changed = changePasswordUseCase.changePassword(command);
//        if (changed) {
//            return ResponseEntity
//                    .accepted()
//                    .body(Map.of("message", "Password successfully changed"));
//        }
//        else {
//            return ResponseEntity
//                    .badRequest()
//                    .body(Map.of("message", "New password cannot be same as old password"));
//        }
//    }
//
//    @PostMapping("/activate/{accountId}")
//    public void activate(@PathVariable Long accountId) throws NotFoundException, ForbiddenException {
//        activateAccountUseCase.activateAccount(accountId);
//    }
}
