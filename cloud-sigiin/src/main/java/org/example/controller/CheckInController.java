package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudentity.domain.R;
import org.example.service.CheckInService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("checkin")
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping("/{userId}")
    public R checkIn(@PathVariable(name = "userId") Long userId) {
        return checkInService.checkIn(userId);
    }
    @GetMapping("/count")
    public R<Long> countDateCheckIn(String date) {
        return checkInService.countDateCheckIn(date);
    }
    @GetMapping("/{userId}")
    public R<Long> countCheckIn(@PathVariable(name = "userId") Long userId,
                             @RequestParam(name = "startDate") String startDate,
                             @RequestParam(name = "endDate") String endDate) {
        return checkInService.countCheckIn(userId, startDate, endDate);
    }

    /**
     * 获取某个用户连续签到次数
     *
     * @param userId 用户id
     * @return long 连续签到次数
     */
    @GetMapping("/continuousdays/{userId}")
    public R<Long> getContinuousCheckIn(@PathVariable(name = "userId") Long userId) {
        return checkInService.getContinuousCheckIn(userId);
    }

}

