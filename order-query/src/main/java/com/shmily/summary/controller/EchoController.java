package com.shmily.summary.controller;

import com.shmily.summary.message.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class EchoController extends BaseController {

    @GetMapping("/echo")
    public @ResponseBody MessageBean echo() {

        log.info("echo req {}", System.currentTimeMillis());
        return this.process("echo", (ControllerSupplier<String>) o -> {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        });
    }
}
