package com.example.backend.controller.user.profile;

import com.example.backend.controller.dto.request.UpdateDescRequestDto;
import com.example.backend.controller.dto.request.UpdateInfoRequestDto;
import com.example.backend.controller.dto.request.UpdateIntroductionRequestDto;
import com.example.backend.service.profile.ProfileUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile/update")
@RequiredArgsConstructor
public class ProfileUpdateController {
    private final ProfileUpdateService profileUpdateService;

    @PostMapping("/info")
    public ResponseEntity<String> updateInfo(@Valid @RequestBody UpdateInfoRequestDto updateInfoRequestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateInfo(memberId, updateInfoRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/introduction")
    public ResponseEntity<String> updateIntroduction(@Valid @RequestBody UpdateIntroductionRequestDto updateIntroductionRequestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateIntroduction(memberId, updateIntroductionRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/description")
    public ResponseEntity<String> updateDescription(@Valid @RequestBody UpdateDescRequestDto updateDescRequestDto) {
        Long memberId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUpdateService.updateDescription(memberId, updateDescRequestDto);
        return ResponseEntity.ok("success");
    }
}
