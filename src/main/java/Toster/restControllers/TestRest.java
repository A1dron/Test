package Toster.restControllers;

import Toster.entity.Test;
import Toster.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@Component
@RestController
@RequestMapping(value = "/test")
public class TestRest {
    @Autowired
    private TestService testService;

    @PostMapping(value = "/add")
    public Test addTest(Test test) {
        return testService.addTest(test);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTest(@PathParam("id") Long id) {
        testService.deleteTest(id);
    }

    @GetMapping(value = "/{id}")
    public Test getTest(@PathParam("id") Long id) {
        return testService.getTest(id);
    }
}
